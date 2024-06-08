package kusitms.jangkku.domain.program.application;

import kusitms.jangkku.domain.program.constant.FORM;
import kusitms.jangkku.domain.program.dao.BrandingRepository;
import kusitms.jangkku.domain.program.dao.ProgramParticipantsRepository;
import kusitms.jangkku.domain.program.dao.SelfUnderstandingsRepository;
import kusitms.jangkku.domain.program.domain.ProgramAdapter;
import kusitms.jangkku.domain.program.domain.model.Branding;
import kusitms.jangkku.domain.program.domain.model.ProgramParticipants;
import kusitms.jangkku.domain.program.domain.model.SelfUnderstanding;
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

import static kusitms.jangkku.domain.program.exception.ProgramErrorResult.*;
import static kusitms.jangkku.domain.user.exception.UserErrorResult.NOT_FOUND_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final JwtUtil jwtUtil;
    private final SelfUnderstandingsRepository selfUnderstandingsRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final BrandingRepository brandingRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserRepository userRepository;
    private final ProgramParticipantsRepository programParticipantsRepository;
    private final ProgramAdapter programAdapter;

    @Override
    public List<ProgramDto.ProgramsMainResponseDto> getMainSelfUnderstanding() {
        return selfUnderstandingsRepository.findTop9ByOrderByCreatedDateDesc().stream().map(ProgramDto.ProgramsMainResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ProgramDto.ProgramsMainResponseDto> getMainBranding(String authorizationHeader) {
        //브랜딩 프로그램에서 유저의 관심분야와 키워드
        return findAllBrandingByUsersKeywordsAndInterests(authorizationHeader).stream().limit(9).map(ProgramDto.ProgramsMainResponseDto::of).collect(Collectors.toList());
    }


    @Override
    public List<ProgramDto.ProgramsMainResponseDto> getMoreSelfUnderstanding(ProgramDto.ProgramSelfUnderstandingRequestDto requestDto) {
        int maxPrice = selfUnderstandingsRepository.findTopByOrderByPriceDesc().getPrice();

        return findSelfUnderstandingByFilter(requestDto, maxPrice).stream().map(v -> ProgramDto.ProgramsMainResponseDto.of(v, maxPrice)).collect(Collectors.toList());
    }


    @Override
    public List<ProgramDto.ProgramsMainResponseDto> getMoreBranding(String authorizationHeader, ProgramDto.ProgramBrandingRequestDto requestDto) {
        List<Branding> brandings = findBrandingsByFilter(authorizationHeader, requestDto);
        return brandings.stream().map(ProgramDto.ProgramsMainResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public ProgramDetailDto.ProgramDetailResponseDto getDetailProgram(String authorizationHeader, Long programId, String type) {

        User user = findUserByUUID(authorizationHeader);
        boolean isApply = verifyCanApplyPrograms(user, type, programId);
        int participants;
        if (type.equals("self-understanding")) {
            participants = findSelfUnderstandingById(programId).getProgramParticipants().size();
            return ProgramDetailDto.ProgramDetailResponseDto.of(findSelfUnderStandingById(programId), findAllUserKeyword(user), participants, isApply);
        } else if (type.equals("branding")) {
            participants = programAdapter.findById(programId).getProgramParticipants().size();
            return ProgramDetailDto.ProgramDetailResponseDto.of(programAdapter.findById(programId), participants, isApply);
        } else throw new ProgramException(NOT_FOUND_PROGRAM);
    }

    @Override
    public List<ProgramsHomeDto.ProgramsHomeResponseDto> getHomeSelfUnderstanding(String authorizationHeader, ProgramDto.ProgramSelfUnderstandingRequestDto requestDto) {
        int maxPrice = selfUnderstandingsRepository.findTopByOrderByPriceDesc().getPrice();
        User user = findUserByUUID(authorizationHeader);

        return findSelfUnderstandingByFilter(requestDto, maxPrice).stream().limit(5).map(v -> ProgramsHomeDto.ProgramsHomeResponseDto.of(v, maxPrice, findAllUserKeyword(user))).collect(Collectors.toList());
    }

    @Override
    public List<ProgramsHomeDto.ProgramsHomeResponseDto> getHomeBranding(String authorizationHeader, ProgramDto.ProgramBrandingRequestDto requestDto) {
        return findBrandingsByFilter(authorizationHeader, requestDto).stream().limit(5).map(ProgramsHomeDto.ProgramsHomeResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ProgramsHomeDto.ProgramsHomeResponseDto> getHomeBrandingNonLogin(ProgramDto.ProgramBrandingRequestDto requestDto) {
        return brandingRepository.findTop5ByOrderByCreatedDateDesc().stream().map(ProgramsHomeDto.ProgramsHomeResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public void applyPrograms(String authorizationHeader, ProgramDto.ProgramJoinRequestDto requestDto) {
        User user = findUserByUUID(authorizationHeader);
        String programsType = requestDto.getType();
        Long programId = requestDto.getProgramId();

        if (verifyCanApplyPrograms(user, programsType, programId)) {
            throw new ProgramException(ALREADY_USER_APPLY_PROGRAM);
        }

        if (programsType.equals("branding")) {
            programParticipantsRepository.save(ProgramParticipants.toEntity(user, programAdapter.findById(programId)));
        } else if (programsType.equals("self-understanding")) {
            programParticipantsRepository.save(ProgramParticipants.toEntity(user, findSelfUnderstandingById(programId)));
        } else throw new ProgramException(PROGRAM_ENUM_NOT_FOUND);
    }

    private boolean verifyCanApplyPrograms(User user, String programsType, Long programId) {
        if (programsType.equals("branding")) {
            return programParticipantsRepository.existsByUserAndBranding(user, programAdapter.findById(programId));
        } else if (programsType.equals("self-understanding")) {
            return programParticipantsRepository.existsByUserAndSelfUnderstanding(user, findSelfUnderstandingById(programId));
        } else throw new ProgramException(PROGRAM_ENUM_NOT_FOUND);
    }

    private SelfUnderstanding findSelfUnderstandingById(Long programId) {
        return selfUnderstandingsRepository.findById(programId).orElseThrow(() -> new ProgramException(NOT_FOUND_PROGRAM));
    }

    private User findUserByUUID(String authorizationHeader) {
        UUID userId = findUserIdFromauthorizationHeader(authorizationHeader);
        return findUserByUUID(userId);
    }


    private UUID findUserIdFromauthorizationHeader(String authorizationHeader) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        jwtUtil.getUserIdFromToken(token);
        return UUID.fromString(jwtUtil.getUserIdFromToken(token));
    }

    private List<SelfUnderstanding> findSelfUnderstandingByFilter(ProgramDto.ProgramSelfUnderstandingRequestDto requestDto, int maxPrice) {
        Integer endPrice = requestDto.getEndPrice();
        if (endPrice == null) endPrice = maxPrice;

        if (requestDto.getForm() == null || requestDto.getForm().equals("온오프라인")) {
            return selfUnderstandingsRepository.findByPriceBetween(requestDto.getStartPrice(), endPrice);
        } else if (requestDto.getForm().equals("온라인") || requestDto.getForm().equals("오프라인"))
            return selfUnderstandingsRepository.findByPriceBetweenAndForm(requestDto.getStartPrice(), endPrice, FORM.ofCode(requestDto.getForm()));
        else throw new ProgramException(PROGRAM_ENUM_NOT_FOUND);
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
