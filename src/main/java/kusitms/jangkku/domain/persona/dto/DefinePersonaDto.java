package kusitms.jangkku.domain.persona.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kusitms.jangkku.domain.persona.constant.Content;
import kusitms.jangkku.domain.persona.exception.PersonaErrorResult;
import kusitms.jangkku.domain.persona.exception.PersonaException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DefinePersonaDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DefinePersonaRequest {
        private List<String> stageOneKeywords;
        private List<String> stageTwoKeywords;
        private List<String> stageThreeKeywords;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DefinePersonaResponse {
        private String definePersonaId;
        private String name;
        private String comment;
        private String description;
        private String ability;
        private List<String> values;
        private String strength;
        private String preference;
        private List<String> types;
        private List<String> definePersonaKeywords;
        private String frontImgUrl;
        private String backImgUrl;

        public static DefinePersonaDto.DefinePersonaResponse of(UUID definePersonaId, String definePersonaCode, List<String> definePersonaKeywords) {
            Content content = Arrays.stream(Content.values())
                    .filter(desc -> desc.getCode().equals(definePersonaCode))
                    .findFirst()
                    .orElseThrow(() -> new PersonaException(PersonaErrorResult.NOT_FOUND_PERSONA_TYPE));

            return DefinePersonaDto.DefinePersonaResponse.builder()
                    .definePersonaId(String.valueOf(definePersonaId))
                    .name(content.getName())
                    .comment(content.getComment())
                    .description(content.getDescription())
                    .ability(content.getAbility())
                    .values(content.getValues())
                    .strength(content.getStrength())
                    .preference(content.getPreference())
                    .types(content.getTypes())
                    .definePersonaKeywords(definePersonaKeywords)
                    .frontImgUrl(content.getFrontImgUrl())
                    .backImgUrl(content.getBackImgUrl())
                    .build();
        }
    }
}