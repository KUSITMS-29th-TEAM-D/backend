package kusitms.jangkku.domain.clova.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.ArrayList;

public class ClovaDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ChatBotRequestDto {
        private ArrayList<Message> messages;
        private double topP;
        private double temperature;
        private int maxTokens;
        private double repeatPenalty;

        public static ChatBotRequestDto DesignPersonaRequestOf() {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(Message.createDesignPersonaSystemOf());

            return ChatBotRequestDto.builder()
                    .messages(messages)
                    .topP(0.8)
                    .temperature(0.3)
                    .maxTokens(256)
                    .repeatPenalty(5.0)
                    .build();
        }

        public static ChatBotRequestDto DiscoverPersonaReactionRequestOf() {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(Message.createReactionOf());

            return ChatBotRequestDto.builder()
                    .messages(messages)
                    .topP(0.8)
                    .temperature(0.3)
                    .maxTokens(256)
                    .repeatPenalty(5.0)
                    .build();
        }

        public static ChatBotRequestDto DiscoverPersonaSummaryRequestOf() {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(Message.createSummaryOf());

            return ChatBotRequestDto.builder()
                    .messages(messages)
                    .topP(0.8)
                    .temperature(0.3)
                    .maxTokens(256)
                    .repeatPenalty(5.0)
                    .build();
        }

        public static ChatBotRequestDto DiscoverPersonaKeywordRequestOf() {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(Message.createKeywordOf());

            return ChatBotRequestDto.builder()
                    .messages(messages)
                    .topP(0.8)
                    .temperature(0.3)
                    .maxTokens(256)
                    .repeatPenalty(5.0)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatBotResponse {
        private Result result;
    }
}