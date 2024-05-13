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
    public static class ChatBotRequestDto{
        private ArrayList<Message> messages; //
        private double topP; //토큰 생성 관련
        private int maxTokens; //생성되는 텍스트의 최대 길이
        private double temperature; //높을수록 모델이 생성하는 문장이 더 다양해지고, 값이 낮을수록 더 일관성 있는 문장이 생성
        private double repeatPenalty; //반복적으로 사용하는 단어나 구를 조절함. 값이 높을수록 이미 사용한 단어나 구를 다시 사용하지 x
        private int seed; //모델 반복 실행 시 결괏값의 일관성 수준 조정. 0: 일관성 수준 랜덤 적용 (기본값)
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatBotResponse{
        private List<Message> choices;
    }

}
