package kusitms.jangkku.domain.program.application;

import kusitms.jangkku.domain.program.constant.FORM;
import kusitms.jangkku.domain.program.dao.BrandingRepository;
import kusitms.jangkku.domain.program.dao.SelfUnderstandingsRepository;
import kusitms.jangkku.domain.program.domain.Branding;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import kusitms.jangkku.domain.program.dto.ProgramDetailDto;
import kusitms.jangkku.domain.program.dto.ProgramDto;
import kusitms.jangkku.domain.program.dto.ProgramsHomeDto;
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
        return selfUnderstandingsRepository.findTop9ByOrderByCreatedDateDesc().stream().map(ProgramDto.ProgrmsMainResponsetDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ProgramDto.ProgrmsMainResponsetDto> getMainBranding(String authorizationHeader) {
        //브랜딩 프로그램에서 유저의 관심분야와 키워드
        return findAllBrandingByUsersKeywordsAndInterests(authorizationHeader).stream().limit(9).map(ProgramDto.ProgrmsMainResponsetDto::of).collect(Collectors.toList());
    }

    private UUID findUserIdFromauthorizationHeader(String authorizationHeader) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        jwtUtil.getUserIdFromToken(token);
        return UUID.fromString(jwtUtil.getUserIdFromToken(token));
    }

    @Override
    public List<ProgramDto.ProgrmsMainResponsetDto> getMoreSelfUnderstanding(ProgramDto.ProgramSelfUnderstandingRequestDto requestDto) {
        int maxPrice = selfUnderstandingsRepository.findTopByOrderByPriceDesc().getPrice();

        return findSelfUnderstandingByFilter(requestDto, maxPrice).stream().map(v -> ProgramDto.ProgrmsMainResponsetDto.of(v, maxPrice)).collect(Collectors.toList());
    }


    @Override
    public List<ProgramDto.ProgrmsMainResponsetDto> getMoreBranding(String authorizationHeader, ProgramDto.ProgramBrandingRequestDto requestDto) {
        List<Branding> brandings = findBrandingsByFilter(authorizationHeader, requestDto);
        return brandings.stream().map(ProgramDto.ProgrmsMainResponsetDto::of).collect(Collectors.toList());
    }

    @Override
    public ProgramDetailDto.ProgramDetailResponseDto getDetailProgram(String authorizationHeader, Long programId, String type) {
        UUID userId = findUserIdFromauthorizationHeader(authorizationHeader);
        User user = findUserByUUID(userId);
        if (type.equals("self-understanding")) {
            return ProgramDetailDto.ProgramDetailResponseDto.of(findSelfUnderStandingById(programId), findAllUserKeyword(user));
        } else if (type.equals("branding")) {
            return ProgramDetailDto.ProgramDetailResponseDto.of(findBrandingById(programId), findAllUserKeyword(user));
        } else throw new ProgramException(NOT_FOUND_PROGRAM);
    }

    @Override
    public List<ProgramsHomeDto.ProgramsHomeResponseDto> getHomeSelfUnderstanding(String authorizationHeader, ProgramDto.ProgramSelfUnderstandingRequestDto requestDto) {
        int maxPrice = selfUnderstandingsRepository.findTopByOrderByPriceDesc().getPrice();
        UUID userId = findUserIdFromauthorizationHeader(authorizationHeader);
        User user = findUserByUUID(userId);

        return findSelfUnderstandingByFilter(requestDto, maxPrice).stream().limit(5).map(v -> ProgramsHomeDto.ProgramsHomeResponseDto.of(v, maxPrice, findAllUserKeyword(user))).collect(Collectors.toList());
    }

    @Override
    public List<ProgramsHomeDto.ProgramsHomeResponseDto> getHomeBranding(String authorizationHeader, ProgramDto.ProgramBrandingRequestDto requestDto) {
        List<Branding> brandings = findBrandingsByFilter(authorizationHeader, requestDto);
        return brandings.stream().limit(5).map(ProgramsHomeDto.ProgramsHomeResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ProgramsHomeDto.ProgramsHomeResponseDto> getHomeBrandingNonLogin(ProgramDto.ProgramBrandingRequestDto requestDto) {
        return brandingRepository.findTop5ByOrderByCreatedDateDesc().stream().map(ProgramsHomeDto.ProgramsHomeResponseDto::of).collect(Collectors.toList());
    }

    private List<SelfUnderstanding> findSelfUnderstandingByFilter(ProgramDto.ProgramSelfUnderstandingRequestDto requestDto, int maxPrice) {
        Integer endPrice = requestDto.getEndPrice();
        if (endPrice == null) endPrice = maxPrice;

        if (requestDto.getForm() != null) {
            return selfUnderstandingsRepository.findByPriceBetweenAndForm(requestDto.getStartPrice(), endPrice, FORM.ofCode(requestDto.getForm()));
        } else {
            return selfUnderstandingsRepository.findByPriceBetween(requestDto.getStartPrice(), endPrice);
        }
    }

    private List<Branding> findBrandingsByFilter(String authorizationHeader, ProgramDto.ProgramBrandingRequestDto requestDto) {
        List<Branding> brandings = findAllBrandingByUsersKeywordsAndInterests(authorizationHeader);
        if (requestDto.getInterest() != null && requestDto.getImageKeywords() != null) {
            brandings = brandings.stream().filter(v -> v.getProgramsInterests().stream().anyMatch(h -> requestDto.getInterest().contains(h.getInterest().getName())) &&
                    v.getProgramsImageKeywords().stream().anyMatch(q -> requestDto.getImageKeywords().contains(q.getKeyword().getName()))).collect(Collectors.toList());
        } else if (requestDto.getInterest() != null && requestDto.getImageKeywords() == null) {
            brandings = brandings.stream().filter(v -> v.getProgramsInterests().stream().anyMatch(h -> requestDto.getInterest().contains(h.getInterest().getName()))).collect(Collectors.toList());
        } else if (requestDto.getInterest() == null && requestDto.getImageKeywords() != null) {
            brandings.stream().filter(v -> v.getProgramsImageKeywords().stream().anyMatch(h -> requestDto.getImageKeywords().contains(h.getKeyword().getName())));
        }
        return brandings;
    }


    private SelfUnderstanding findSelfUnderStandingById(Long id) {
        return selfUnderstandingsRepository.findById(id).orElseThrow(() -> new ProgramException(NOT_FOUND_PROGRAM));
    }

    private Branding findBrandingById(Long id) {
        return brandingRepository.findById(id).orElseThrow(() -> new ProgramException(NOT_FOUND_PROGRAM));
    }

    private List<SelfUnderstanding> findAllSelfUnderstanding() {
        return selfUnderstandingsRepository.findAllByOrderByCreatedDateDesc()
                .orElseThrow(() -> new ProgramException(NOT_FOUND_PROGRAM));
    }

    private List<String> findAllUserKeyword(User user) {
        return userKeywordRepository.findAllByUser(user).stream().map(v -> v.getKeyword().getName()).toList();
    }

    private List<String> findAllUserInterest(User user) {
        return userInterestRepository.findAllByUser(user).stream().map(v -> v.getInterest().getName()).toList();
    }

    private List<Branding> findAllBrandingByUsersKeywordsAndInterests(String authorizationHeader) {
        UUID userId = findUserIdFromauthorizationHeader(authorizationHeader);
        List<String> keywords = findAllUserKeyword(findUserByUUID(userId)); //유저의 키워드
        List<String> interests = findAllUserInterest(findUserByUUID(userId));//유저의 관심분야
        return brandingRepository.findByProgramsImageKeywordsInOrProgramsInterestsIn(keywords, interests);
    }

    private User findUserByUUID(UUID userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new UserException(NOT_FOUND_USER));
    }

}
