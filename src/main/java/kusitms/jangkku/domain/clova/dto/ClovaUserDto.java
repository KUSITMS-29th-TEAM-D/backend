package kusitms.jangkku.domain.clova.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ClovaUserDto {

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