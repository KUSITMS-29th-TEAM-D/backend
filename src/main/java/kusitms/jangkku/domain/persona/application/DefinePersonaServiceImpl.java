package kusitms.jangkku.domain.persona.application;


import kusitms.jangkku.domain.persona.constant.DefinePersonaKeyword;
import kusitms.jangkku.domain.persona.constant.DefinePersonaType;
import kusitms.jangkku.domain.persona.dto.DefinePersonaDto;
import kusitms.jangkku.domain.persona.exception.PersonaErrorResult;
import kusitms.jangkku.domain.persona.exception.PersonaException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefinePersonaServiceImpl implements DefinePersonaService {

    @Override
    public DefinePersonaDto.DefinePersonaResponse getDefinePersona(String authorizationHeader, DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {
        List<String> definePersonaKeywords = new ArrayList<>();
        String stepOneKeyword = judgeStepOneType(definePersonaRequest.getStageOneKeywords(), definePersonaKeywords);
        String stepTwoKeyword = judgeStepTwoType(definePersonaRequest.getStageTwoKeywords(), definePersonaKeywords);
        String stepThreeKeyword = judgeStepThreeType(definePersonaRequest.getStageThreeKeywords(), definePersonaKeywords);

        String definePersonaType = stepOneKeyword + stepTwoKeyword + stepThreeKeyword;
        String definePersonaName = judgeDefinePersonaName(definePersonaType);

        return DefinePersonaDto.DefinePersonaResponse.builder()
                .definePersonaName(definePersonaName)
                .definePersonaKeywords(definePersonaKeywords)
                .build();
    }

    private String judgeStepOneType(List<String> stepOneKeywords, List<String> definePersonaKeywords) {
        List<String> realisticKeywords = DefinePersonaKeyword.REALISTIC.getKeywords();
        List<String> socialKeywords = DefinePersonaKeyword.SOCIAL.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepOneKeywords, realisticKeywords, socialKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));
        definePersonaKeywords.add(moreCountKeywords.get(1));

        return realisticKeywords.contains(moreCountKeywords.get(0)) ? "R" : "S";
    }

    private String judgeStepTwoType(List<String> stepTwoKeywords, List<String> definePersonaKeywords) {
        List<String> investigativeKeywords = DefinePersonaKeyword.INVESTIGATIVE.getKeywords();
        List<String> enterprisingKeywords = DefinePersonaKeyword.ENTERPRISING.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepTwoKeywords, investigativeKeywords, enterprisingKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));
        definePersonaKeywords.add(moreCountKeywords.get(1));

        return investigativeKeywords.contains(moreCountKeywords.get(0)) ? "I" : "E";
    }

    private String judgeStepThreeType(List<String> stepThreeKeywords, List<String> definePersonaKeywords) {
        List<String> artisticKeywords = DefinePersonaKeyword.ARTISTIC.getKeywords();
        List<String> conventionKeywords = DefinePersonaKeyword.CONVENTION.getKeywords();

        List<String> moreCountKeywords = judgeMoreCountKeywords(stepThreeKeywords, artisticKeywords, conventionKeywords);
        definePersonaKeywords.add(moreCountKeywords.get(0));

        return artisticKeywords.contains(moreCountKeywords.get(0)) ? "A" : "C";
    }

    private String judgeDefinePersonaName(String definePersonaType) {

        for (DefinePersonaType type : DefinePersonaType.values()) {
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
}