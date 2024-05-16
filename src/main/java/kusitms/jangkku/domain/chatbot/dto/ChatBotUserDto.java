package kusitms.jangkku.domain.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatBotUserDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatBotUserRequestDto{
        public boolean isStart;
        public String category;
        public String content;
    }
}
