package kusitms.jangkku.domain.persona.application;


import kusitms.jangkku.domain.persona.constant.Content;
import kusitms.jangkku.domain.persona.constant.Type;
import kusitms.jangkku.domain.persona.constant.Keyword;
import kusitms.jangkku.domain.persona.dao.DefinePersonaKeywordRepository;
import kusitms.jangkku.domain.persona.dao.DefinePersonaRepository;
import kusitms.jangkku.domain.persona.domain.DefinePersona;
import kusitms.jangkku.domain.persona.domain.DefinePersonaKeyword;
import kusitms.jangkku.domain.persona.dto.DefinePersonaDto;
import kusitms.jangkku.domain.persona.exception.PersonaErrorResult;
import kusitms.jangkku.domain.persona.exception.PersonaException;
import kusitms.jangkku.domain.user.dao.UserRepository;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.exception.UserErrorResult;
import kusitms.jangkku.domain.user.exception.UserException;
import kusitms.jangkku.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefinePersonaServiceImpl implements DefinePersonaService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final DefinePersonaRepository definePersonaRepository;
    private final DefinePersonaKeywordRepository definePersonaKeywordRepository;

    // 정의하기 페르소나 결과를 도출하는 메서드
    @Override
    public DefinePersonaDto.DefinePersonaResponse createDefinePersona(String authorizationHeader, DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {
        List<String> definePersonaKeywords = new ArrayList<>();
        String stepOneKeyword = judgeStepOneType(definePersonaRequest.getStageOneKeywords(), definePersonaKeywords);
        String stepTwoKeyword = judgeStepTwoType(definePersonaRequest.getStageTwoKeywords(), definePersonaKeywords);
        String stepThreeKeyword = judgeStepThreeType(definePersonaRequest.getStageThreeKeywords(), definePersonaKeywords);

        String definePersonaCode = stepOneKeyword + stepTwoKeyword + stepThreeKeyword;
        String definePersonaName = judgeDefinePersonaName(definePersonaCode);

        if (authorizationHeader != null) {
            saveDefinePersona(authorizationHeader, definePersonaName, definePersonaCode, definePersonaKeywords);
        }

        return createDefinePersonaResponse(definePersonaCode, definePersonaKeywords);
    }

    // 정의하기 페르소나 결과를 반환하는 메서드
    @Override
    public DefinePersonaDto.DefinePersonaResponse getDefinePersona(String authorizationHeader) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        UUID userId = UUID.fromString(jwtUtil.getUserIdFromToken(token));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        DefinePersona definePersona = definePersonaRepository.findTopByUserOrderByCreatedAtDesc(user); // 가장 최근 결과만 가져옴

        if (definePersona == null) {
            throw new PersonaException(PersonaErrorResult.NOT_FOUND_DEFINE_PERSONA);
        }

        List<DefinePersonaKeyword> definePersonaKeywords = definePersonaKeywordRepository.findAllByDefinePersona(definePersona);
        List<String> keywordStrings = definePersonaKeywords.stream()
                .map(DefinePersonaKeyword::getName)
                .toList();

        return createDefinePersonaResponse(definePersona.getCode(), keywordStrings);
    }

    // 첫번째 유형 도출 메서드
    private String judgeStepOneType(List<String> stepOneKeywords, List<String> definePersonaKeywords) {
        List<String> realisticKeywords = Keyword.REALISTIC.getKeywords();
        List<String> socialKeywords = Keyword.SOCIAL.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepOneKeywords, realisticKeywords, socialKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));
        definePersonaKeywords.add(moreCountKeywords.get(1));

        return realisticKeywords.contains(moreCountKeywords.get(0)) ? "R" : "S";
    }

    // 두번째 유형 도출 메서드
    private String judgeStepTwoType(List<String> stepTwoKeywords, List<String> definePersonaKeywords) {
        List<String> investigativeKeywords = Keyword.INVESTIGATIVE.getKeywords();
        List<String> enterprisingKeywords = Keyword.ENTERPRISING.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepTwoKeywords, investigativeKeywords, enterprisingKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));
        definePersonaKeywords.add(moreCountKeywords.get(1));

        return investigativeKeywords.contains(moreCountKeywords.get(0)) ? "I" : "E";
    }

    // 세번째 유형 도출 메서드
    private String judgeStepThreeType(List<String> stepThreeKeywords, List<String> definePersonaKeywords) {
        List<String> artisticKeywords = Keyword.ARTISTIC.getKeywords();
        List<String> conventionKeywords = Keyword.CONVENTION.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepThreeKeywords, artisticKeywords, conventionKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));

        return artisticKeywords.contains(moreCountKeywords.get(0)) ? "A" : "C";
    }

    // 3가지 유형으로 페르소나를 판단하는 메서드
    private String judgeDefinePersonaName(String definePersonaCode) {

        for (Type type : Type.values()) {
            if (type.getCode().equals(definePersonaCode)) {
                return type.getName();
            }
        }

        throw new PersonaException(PersonaErrorResult.NOT_FOUND_PERSONA_TYPE);
    }

    // 더 많이 선택한 키워드로 유형을 판단하는 메서드
    private List<String> judgeMoreCountKeywords (List<String> stepKeywords, List<String> firstKeywords, List<String> secondKeywords) {
        List<String> pickedFirstKeywords = new ArrayList<>();
        List<String> pickedSecondKeywords = new ArrayList<>();

        for (String stepKeyword : stepKeywords) {
            if (firstKeywords.contains(stepKeyword)) {
                pickedFirstKeywords.add(stepKeyword);
            } else if (secondKeywords.contains(stepKeyword)) {
                pickedSecondKeywords.add(stepKeyword);
            }
        }

        return pickedFirstKeywords.size() > pickedSecondKeywords.size() ? pickedFirstKeywords : pickedSecondKeywords;
    }

    // 정의하기 페르소나를 저장하는 메서드
    private void saveDefinePersona(String authorizationHeader, String definePersonaName, String definePersonaCode, List<String> definePersonaKeywords) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        UUID userId = UUID.fromString(jwtUtil.getUserIdFromToken(token));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        DefinePersona definePersona = DefinePersona.builder()
                .user(user)
                .name(definePersonaName)
                .code(definePersonaCode)
                .build();
        definePersonaRepository.save(definePersona);

        for (String keyword : definePersonaKeywords) {
            DefinePersonaKeyword definePersonaKeyword = DefinePersonaKeyword.builder()
                    .definePersona(definePersona)
                    .name(keyword)
                    .build();
            definePersonaKeywordRepository.save(definePersonaKeyword);
        }
    }

    // 정의하기 페르소나 응답 객체를 만드는 메서드
    private DefinePersonaDto.DefinePersonaResponse createDefinePersonaResponse(String definePersonaCode, List<String> definePersonaKeywords) {
        Content content = Arrays.stream(Content.values())
                .filter(desc -> desc.getCode().equals(definePersonaCode))
                .findFirst()
                .orElse(null);

        if (content == null) {
            throw new PersonaException(PersonaErrorResult.NOT_FOUND_PERSONA_TYPE);
        }

        return DefinePersonaDto.DefinePersonaResponse.builder()
                .name(content.getName())
                .comment(content.getComment())
                .description(content.getDescription())
                .ability(content.getAbility())
                .values(content.getValues())
                .strength(content.getStrength())
                .preference(content.getPreference())
                .types(content.getTypes())
                .definePersonaKeywords(definePersonaKeywords)
                .build();
    }
}