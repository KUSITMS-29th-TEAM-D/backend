package kusitms.jangkku.domain.persona.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kusitms.jangkku.domain.persona.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class DesignPersonaDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DesignPersonaRequest {
        private List<String> fields;
        private List<String> distinctions;
        private List<String> abilities;
        private List<String> platforms;
        private String career;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DesignPersonaResponse {
        private String definition;

        public static DesignPersonaDto.DesignPersonaResponse of(String designPersonaDefinition) {
            return DesignPersonaDto.DesignPersonaResponse.builder()
                    .definition(designPersonaDefinition)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DesignPersonaDetailResponse {
        private String definition;
        private List<String> fields;
        private List<String> distinctions;
        private List<String> abilities;
        private List<String> platforms;
        private String career;

        public static DesignPersonaDto.DesignPersonaDetailResponse of(String definition, List<DesignPersonaField> designPersonaFields, List<DesignPersonaDistinction> designPersonaDistinctions, List<DesignPersonaAbility> designPersonaAbilities, List<DesignPersonaPlatform> designPersonaPlatforms, String career) {
            return DesignPersonaDetailResponse.builder()
                    .definition(definition)
                    .fields(getFieldNames(designPersonaFields))
                    .distinctions(getDistinctionNames(designPersonaDistinctions))
                    .abilities(getAbilityNames(designPersonaAbilities))
                    .platforms(getPlatformNames(designPersonaPlatforms))
                    .career(career)
                    .build();
        }

        private static List<String> getFieldNames(List<DesignPersonaField> designPersonaFields) {
            return designPersonaFields.stream()
                    .map(DesignPersonaField::getName)
                    .collect(Collectors.toList());
        }

        private static List<String> getDistinctionNames(List<DesignPersonaDistinction> designPersonaDistinctions) {
            return designPersonaDistinctions.stream()
                    .map(DesignPersonaDistinction::getName)
                    .collect(Collectors.toList());
        }

        private static List<String> getAbilityNames(List<DesignPersonaAbility> designPersonaAbilities) {
            return designPersonaAbilities.stream()
                    .map(DesignPersonaAbility::getName)
                    .collect(Collectors.toList());
        }

        private static List<String> getPlatformNames(List<DesignPersonaPlatform> designPersonaPlatforms) {
            return designPersonaPlatforms.stream()
                    .map(DesignPersonaPlatform::getName)
                    .collect(Collectors.toList());
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DesignPersonaClovaRequest {
        private String message;
    }
}