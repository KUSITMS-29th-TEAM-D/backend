package kusitms.jangkku.domain.chatbot.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.ArrayList;

public class ChatBotDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ChatBotRequestDto {
        private ArrayList<Message> messages; //
        private double topP;
        private double temperature;
        private int maxTokens; //생성되는 텍스트의 최대 길이
        private double repeatPenalty; //생성되는 텍스트의 최대 길이


        public static ChatBotRequestDto of() {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(Message.createDefaultOf());

            return ChatBotRequestDto.builder()
                    .messages(messages)
                    .topP(0.8)
                    .temperature(0.5)
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
