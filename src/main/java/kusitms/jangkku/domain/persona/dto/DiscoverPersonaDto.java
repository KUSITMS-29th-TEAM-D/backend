package kusitms.jangkku.domain.persona.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kusitms.jangkku.domain.persona.domain.DiscoverPersona;
import kusitms.jangkku.domain.persona.domain.DiscoverPersonaChatting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

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
                    .stageOne(!questions.isEmpty() && !Objects.isNull(chattings.get(0).getAnswer()) ? Stage.of(questions.get(0), chattings.get(0).getAnswer(), chattings.get(0).getReaction()) : null)
                    .stageTwo(questions.size() > 1 && !Objects.isNull(chattings.get(1).getAnswer()) ? Stage.of(questions.get(1), chattings.get(1).getAnswer(), chattings.get(1).getReaction()) : null)
                    .stageThree(questions.size() > 2 && !Objects.isNull(chattings.get(2).getAnswer()) ? Stage.of(questions.get(2), chattings.get(2).getAnswer(), chattings.get(2).getReaction()) : null)
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SummaryResponse {
        private List<String> health;
        private List<String> career;
        private List<String> love;
        private List<String> leisure;

        public static DiscoverPersonaDto.SummaryResponse of(List<String> healthSummaries, List<String> careerSummaries, List<String> loveSummaries, List<String> leisureSummaries) {
            return SummaryResponse.builder()
                    .health(healthSummaries)
                    .career(careerSummaries)
                    .love(loveSummaries)
                    .leisure(leisureSummaries)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class resetChattingRequest {
        private String category;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CheckCompleteResponse {
        private boolean healthComplete;
        private boolean careerComplete;
        private boolean loveComplete;
        private boolean leisureComplete;

        public static DiscoverPersonaDto.CheckCompleteResponse of(DiscoverPersona healthComplete, DiscoverPersona careerComplete, DiscoverPersona loveComplete, DiscoverPersona leisureComplete) {
            return CheckCompleteResponse.builder()
                    .healthComplete(!Objects.isNull(healthComplete) && healthComplete.getIsComplete())
                    .careerComplete(!Objects.isNull(careerComplete) && careerComplete.getIsComplete())
                    .loveComplete(!Objects.isNull(loveComplete) && loveComplete.getIsComplete())
                    .leisureComplete(!Objects.isNull(leisureComplete) && leisureComplete.getIsComplete())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KeywordResponse {
        private List<String> keywords;

        public static DiscoverPersonaDto.KeywordResponse of(List<String> keywords) {
            return KeywordResponse.builder()
                    .keywords(keywords)
                    .build();
        }
    }
}