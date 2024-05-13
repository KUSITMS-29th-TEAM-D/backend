package kusitms.jangkku.domain.persona.application;


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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefinePersonaServiceImpl implements DefinePersonaService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final DefinePersonaRepository definePersonaRepository;
    private final DefinePersonaKeywordRepository definePersonaKeywordRepository;

    @Override
    public DefinePersonaDto.DefinePersonaResponse getDefinePersona(String authorizationHeader, DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {
        List<String> definePersonaKeywords = new ArrayList<>();
        String stepOneKeyword = judgeStepOneType(definePersonaRequest.getStageOneKeywords(), definePersonaKeywords);
        String stepTwoKeyword = judgeStepTwoType(definePersonaRequest.getStageTwoKeywords(), definePersonaKeywords);
        String stepThreeKeyword = judgeStepThreeType(definePersonaRequest.getStageThreeKeywords(), definePersonaKeywords);

        String definePersonaType = stepOneKeyword + stepTwoKeyword + stepThreeKeyword;
        String definePersonaName = judgeDefinePersonaName(definePersonaType);

        if (authorizationHeader != null) {
            saveDefinePersona(authorizationHeader, definePersonaName, definePersonaKeywords);
        }

        return DefinePersonaDto.DefinePersonaResponse.builder()
                .definePersonaName(definePersonaName)
                .definePersonaKeywords(definePersonaKeywords)
                .build();
    }

    private String judgeStepOneType(List<String> stepOneKeywords, List<String> definePersonaKeywords) {
        List<String> realisticKeywords = Keyword.REALISTIC.getKeywords();
        List<String> socialKeywords = Keyword.SOCIAL.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepOneKeywords, realisticKeywords, socialKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));
        definePersonaKeywords.add(moreCountKeywords.get(1));

        return realisticKeywords.contains(moreCountKeywords.get(0)) ? "R" : "S";
    }

    private String judgeStepTwoType(List<String> stepTwoKeywords, List<String> definePersonaKeywords) {
        List<String> investigativeKeywords = Keyword.INVESTIGATIVE.getKeywords();
        List<String> enterprisingKeywords = Keyword.ENTERPRISING.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepTwoKeywords, investigativeKeywords, enterprisingKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));
        definePersonaKeywords.add(moreCountKeywords.get(1));

        return investigativeKeywords.contains(moreCountKeywords.get(0)) ? "I" : "E";
    }

    private String judgeStepThreeType(List<String> stepThreeKeywords, List<String> definePersonaKeywords) {
        List<String> artisticKeywords = Keyword.ARTISTIC.getKeywords();
        List<String> conventionKeywords = Keyword.CONVENTION.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepThreeKeywords, artisticKeywords, conventionKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));

        return artisticKeywords.contains(moreCountKeywords.get(0)) ? "A" : "C";
    }

    private String judgeDefinePersonaName(String definePersonaType) {

        for (Type type : Type.values()) {
            if (type.getCode().equals(definePersonaType)) {
                return type.getName();
            }
        }

        throw new PersonaException(PersonaErrorResult.NOT_FOUND_PERSONA_TYPE);
    }

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

    private void saveDefinePersona(String authorizationHeader, String definePersonaName, List<String> definePersonaKeywords) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        UUID userId = UUID.fromString(jwtUtil.getUserIdFromToken(token));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        DefinePersona definePersona = DefinePersona.builder()
                .user(user)
                .name(definePersonaName)
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
}