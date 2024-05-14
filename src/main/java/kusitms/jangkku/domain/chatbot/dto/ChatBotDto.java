package kusitms.jangkku.domain.chatbot.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class ChatBotDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ChatBotRequestDto {
        private ArrayList<Message> messages; //
        private int maxTokens; //생성되는 텍스트의 최대 길이

        public static ChatBotRequestDto of(ChatBotDto.ChatBotRequestDto dto){
            return ChatBotRequestDto.builder()
                    .messages(dto.getMessages())
                    .maxTokens(dto.getMaxTokens())
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
