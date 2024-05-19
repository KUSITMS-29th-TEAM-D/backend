package kusitms.jangkku.domain.persona.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kusitms.jangkku.domain.persona.domain.DiscoverPersonaChatting;
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
        private Boolean isComplete;


        public static DiscoverPersonaDto.QuestionResponse of(Long chattingId, String question, Boolean isComplete) {
            return DiscoverPersonaDto.QuestionResponse.builder()
                    .chattingId(chattingId)
                    .question(question)
                    .isComplete(isComplete)
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChattingResponse {
        private Stage stageOne;
        private Stage stageTwo;
        private Stage stageThree;

        public static ChattingResponse of(List<String> questions, List<DiscoverPersonaChatting> chattings) {
            return ChattingResponse.builder()
                    .stageOne(Stage.of(questions.get(0), chattings.get(0).getAnswer(), chattings.get(0).getReaction()))
                    .stageTwo(questions.size() > 1 && chattings.size() > 1 ? Stage.of(questions.get(1), chattings.get(1).getAnswer(), chattings.get(1).getReaction()) : null)
                    .stageThree(questions.size() > 2 && chattings.size() > 2 ? Stage.of(questions.get(2), chattings.get(2).getAnswer(), chattings.get(2).getReaction()) : null)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Stage {
        private String question;
        private String answer;
        private String reaction;

        public static DiscoverPersonaDto.Stage of(String question, String answer, String reaction) {
            return DiscoverPersonaDto.Stage.builder()
                    .question(question)
                    .answer(answer)
                    .reaction(reaction)
                    .build();
        }
    }
}