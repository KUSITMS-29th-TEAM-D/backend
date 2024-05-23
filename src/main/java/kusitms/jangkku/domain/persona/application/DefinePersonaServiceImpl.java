package kusitms.jangkku.domain.persona.application;

import jakarta.transaction.Transactional;
import kusitms.jangkku.domain.persona.constant.Type;
import kusitms.jangkku.domain.persona.constant.Keyword;
import kusitms.jangkku.domain.persona.dao.DefinePersonaKeywordRepository;
import kusitms.jangkku.domain.persona.dao.DefinePersonaRepository;
import kusitms.jangkku.domain.persona.domain.DefinePersona;
import kusitms.jangkku.domain.persona.domain.DefinePersonaKeyword;
import kusitms.jangkku.domain.persona.dto.DefinePersonaDto;
import kusitms.jangkku.domain.persona.exception.PersonaErrorResult;
import kusitms.jangkku.domain.persona.exception.PersonaException;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DefinePersonaServiceImpl implements DefinePersonaService {
    private final JwtUtil jwtUtil;
    private final DefinePersonaRepository definePersonaRepository;
    private final DefinePersonaKeywordRepository definePersonaKeywordRepository;

    // 정의하기 페르소나 결과를 도출하는 메서드 (로그인 유저)
    @Override
    public DefinePersonaDto.DefinePersonaResponse createDefinePersona(String authorizationHeader, DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {
        List<String> definePersonaKeywords = new ArrayList<>();
        String stepOneKeyword = judgeStepOneType(definePersonaRequest.getStageOneKeywords(), definePersonaKeywords);
        String stepTwoKeyword = judgeStepTwoType(definePersonaRequest.getStageTwoKeywords(), definePersonaKeywords);
        String stepThreeKeyword = judgeStepThreeType(definePersonaRequest.getStageThreeKeywords(), definePersonaKeywords);

        String definePersonaCode = stepOneKeyword + stepTwoKeyword + stepThreeKeyword;
        String definePersonaName = judgeDefinePersonaName(definePersonaCode);

        DefinePersona definePersona = saveDefinePersona(authorizationHeader, definePersonaName, definePersonaCode, definePersonaKeywords);

        return DefinePersonaDto.DefinePersonaResponse.of(definePersona.getDefinePersonaId(), definePersona.getCode(), definePersonaKeywords);
    }

    // 정의하기 페르소나 결과를 도출하는 메서드 (비로그인 유저)
    @Override
    public DefinePersonaDto.DefinePersonaResponse createDefinePersonaForSharing(DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {
        List<String> definePersonaKeywords = new ArrayList<>();
        String stepOneKeyword = judgeStepOneType(definePersonaRequest.getStageOneKeywords(), definePersonaKeywords);
        String stepTwoKeyword = judgeStepTwoType(definePersonaRequest.getStageTwoKeywords(), definePersonaKeywords);
        String stepThreeKeyword = judgeStepThreeType(definePersonaRequest.getStageThreeKeywords(), definePersonaKeywords);

        String definePersonaCode = stepOneKeyword + stepTwoKeyword + stepThreeKeyword;
        String definePersonaName = judgeDefinePersonaName(definePersonaCode);

        DefinePersona definePersona = saveDefinePersonaForSharing(definePersonaName, definePersonaCode, definePersonaKeywords);

        return DefinePersonaDto.DefinePersonaResponse.of(definePersona.getDefinePersonaId(), definePersona.getCode(), definePersonaKeywords);
    }

    // 정의하기 페르소나 결과를 반환하는 메서드 (로그인 유저)
    @Override
    public DefinePersonaDto.DefinePersonaResponse getDefinePersona(String authorizationHeader) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DefinePersona definePersona = definePersonaRepository.findTopByUserOrderByCreatedAtDesc(user); // 가장 최근 결과만 가져옴

        if (definePersona == null) {
            throw new PersonaException(PersonaErrorResult.NOT_FOUND_DEFINE_PERSONA);
        }

        List<DefinePersonaKeyword> definePersonaKeywords = definePersonaKeywordRepository.findAllByDefinePersona(definePersona);
        List<String> keywordStrings = definePersonaKeywords.stream()
                .map(DefinePersonaKeyword::getName)
                .toList();

        return DefinePersonaDto.DefinePersonaResponse.of(definePersona.getDefinePersonaId(), definePersona.getCode(), keywordStrings);
    }

    // 정의하기 페르소나 결과를 반환하는 메서드 (비로그인 유저)
    @Override
    public DefinePersonaDto.DefinePersonaResponse getDefinePersonaForSharing(String definePersonaId) {

        DefinePersona definePersona = definePersonaRepository.findByDefinePersonaId(UUID.fromString(definePersonaId)); // 고유 id로 검색

        if (definePersona == null) {
            throw new PersonaException(PersonaErrorResult.NOT_FOUND_DEFINE_PERSONA);
        }

        List<DefinePersonaKeyword> definePersonaKeywords = definePersonaKeywordRepository.findAllByDefinePersona(definePersona);
        List<String> keywordStrings = definePersonaKeywords.stream()
                .map(DefinePersonaKeyword::getName)
                .toList();

        return DefinePersonaDto.DefinePersonaResponse.of(definePersona.getDefinePersonaId(), definePersona.getCode(), keywordStrings);
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

    // 정의하기 페르소나를 저장하는 메서드 (로그인 유저)
    protected DefinePersona saveDefinePersona(String authorizationHeader, String definePersonaName, String definePersonaCode, List<String> definePersonaKeywords) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

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

        return definePersona;
    }

    // 정의하기 페르소나를 저장하는 메서드 (비로그인 유저)
    protected DefinePersona saveDefinePersonaForSharing(String definePersonaName, String definePersonaCode, List<String> definePersonaKeywords) {

        DefinePersona definePersona = DefinePersona.builder()
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

        return definePersona;
    }
}