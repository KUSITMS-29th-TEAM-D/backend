package kusitms.jangkku.domain.program.application;

import kusitms.jangkku.domain.program.dao.BrandingRepository;
import kusitms.jangkku.domain.program.dao.SelfUnderstandingsRepository;
import kusitms.jangkku.domain.program.domain.Branding;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import kusitms.jangkku.domain.program.dto.ProgramDetailDto;
import kusitms.jangkku.domain.program.dto.ProgramDto;
import kusitms.jangkku.domain.program.exception.ProgramException;
import kusitms.jangkku.domain.user.dao.UserInterestRepository;
import kusitms.jangkku.domain.user.dao.UserKeywordRepository;
import kusitms.jangkku.domain.user.dao.UserRepository;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.exception.UserException;
import kusitms.jangkku.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static kusitms.jangkku.domain.program.exception.ProgramErrorResult.NOT_FOUND_PROGRAM;
import static kusitms.jangkku.domain.user.exception.UserErrorResult.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final JwtUtil jwtUtil;
    private final SelfUnderstandingsRepository selfUnderstandingsRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final BrandingRepository brandingRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserRepository userRepository;

    @Override
    public List<ProgramDto.ProgrmsMainResponsetDto> getMainSelfUnderstanding() {
        return selfUnderstandingsRepository.findTop9ByOrderByCreatedDateDesc()
                .stream().map(ProgramDto.ProgrmsMainResponsetDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ProgramDto.ProgrmsMainResponsetDto> getMainBranding(String authorizationHeader) {
        UUID userId = findUserIdFromauthorizationHeader(authorizationHeader);
        List<String> keywords = findAllUserKeyword(findUserByUUID(userId)); //유저의 키워드
        System.out.println("kewords : " + keywords);
        List<String> interests = findAllUserInterest(findUserByUUID(userId));//유저의 관심분야
        System.out.println("interests : " + interests);
        //브랜딩 프로그램에서 유저의 관심분야와 키워드
        return findAllBrandingByUsersKeywordsAndInterests(keywords, interests);
    }

    private UUID findUserIdFromauthorizationHeader(String authorizationHeader) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        jwtUtil.getUserIdFromToken(token);
        return UUID.fromString(jwtUtil.getUserIdFromToken(token));
    }

    @Override
    public List<ProgramDto.ProgrmsMainResponsetDto> getMoreSelfUnderstanding(ProgramDto.ProgramSelfUnderstandingRequestDto requestDto) {
//        if (requestDto.getForm())
        return findAllSelfUnderstanding();
    }

    @Override
    public List<ProgramDto.ProgrmsMainResponsetDto> getMoreBranding(ProgramDto.ProgramBrandingRequestDto requestDto) {

        return null;
    }

    @Override
    public ProgramDetailDto.ProgramDetailResponseDto getDetailProgram(String authorizationHeader, Long programId, String form) {
        UUID userId = findUserIdFromauthorizationHeader(authorizationHeader);
        User user = findUserByUUID(userId);
        if (form.equals("self-understanding")) {
            return ProgramDetailDto.ProgramDetailResponseDto.of(findSelfUnderStandingById(programId), findAllUserKeyword(user));
        } else if (form.equals("branding")) {
            return ProgramDetailDto.ProgramDetailResponseDto.of(findBrandingById(programId), findAllUserKeyword(user));
        } else throw new ProgramException(NOT_FOUND_PROGRAM);
    }


    private SelfUnderstanding findSelfUnderStandingById(Long id) {
        return selfUnderstandingsRepository.findById(id).orElseThrow(() -> new ProgramException(NOT_FOUND_PROGRAM));
    }

    private Branding findBrandingById(Long id) {
        return brandingRepository.findById(id).orElseThrow(() -> new ProgramException(NOT_FOUND_PROGRAM));
    }

    private List<ProgramDto.ProgrmsMainResponsetDto> findAllSelfUnderstanding() {
        return selfUnderstandingsRepository.findAllByOrderByCreatedDateDesc().stream().map(ProgramDto.ProgrmsMainResponsetDto::of).collect(Collectors.toList());
    }

    private List<ProgramDto.ProgrmsMainResponsetDto> findAllFormSelfUnderstanding() {
        return null;
    }

    private List<String> findAllUserKeyword(User user) {
        return userKeywordRepository.findAllByUser(user)
                .stream().map(v -> v.getKeyword().getName()).toList();
    }

    private List<String> findAllUserInterest(User user) {
        return userInterestRepository.findAllByUser(user)
                .stream().map(v -> v.getInterest().getName()).toList();
    }

    private List<ProgramDto.ProgrmsMainResponsetDto> findAllBrandingByUsersKeywordsAndInterests(List<String> keywords, List<String> interests) {
        return brandingRepository.findByProgramsImageKeywordsInOrProgramsInterestsIn(keywords, interests)
                .stream().map(ProgramDto.ProgrmsMainResponsetDto::of).collect(Collectors.toList());
    }

    private User findUserByUUID(UUID userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new UserException(NOT_FOUND_USER));
    }

}
