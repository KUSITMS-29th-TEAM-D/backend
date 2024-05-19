package kusitms.jangkku.domain.persona.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DiscoverPersonaDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QuestionResponse {
        private Long chattingId;
        private String question;

        public static DiscoverPersonaDto.QuestionResponse of(Long chattingId, String question) {
            return DiscoverPersonaDto.QuestionResponse.builder()
                    .chattingId(chattingId)
                    .question(question)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AnswerRequest {
        private Long chattingId;
        private String answer;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AnswerResponse {
        private String reaction;
        private String summary;

        public static DiscoverPersonaDto.AnswerResponse of(String reaction, String summary) {
            return DiscoverPersonaDto.AnswerResponse.builder()
                    .reaction(reaction)
                    .summary(summary)
                    .build();
        }
    }
}