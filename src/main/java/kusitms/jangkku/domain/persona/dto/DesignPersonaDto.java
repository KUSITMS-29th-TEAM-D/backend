package kusitms.jangkku.domain.persona.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    public static class DesignPersonaClovaRequest {
        private String message;
    }
}