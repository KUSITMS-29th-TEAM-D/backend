package kusitms.jangkku.domain.clova.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private ROLE role;
    private String content;

    public enum ROLE {
        system, user, assistant
    }

    public static Message creatUserOf(String content) {
        return Message.builder()
                .role(ROLE.user)
                .content(content)
                .build();
    }

    public static Message creatSystemOf(String content) {
        return Message.builder()
                .role(ROLE.system)
                .content(content)
                .build();
    }

    public static Message createDesignPersonaSystemOf() {
        return Message.builder()
                .role(Message.ROLE.system)
                .content("<주제>\n" +
                        "사용자한테 5가지 카테고리의 응답을 받은 후에, 한 문장으로 어떤 브랜드가 되고 싶은지를 요약한다.\n" +
                        "###\n" +
                        "<규칙>\n" +
                        "1. 가능하면 쉼표를 쓰지 않고, 1문장으로 끝나도록 한다.\n" +
                        "2. 글자수는 절대로 30자를 넘지 않는다.\n" +
                        "3. 꼭 모든 사용자의 응답이 결과물에 들어갈 필요는 없다.\n" +
                        "4. 최종적인 커리어는 1개만 나올 수 있다.\n" +
                        "5. 커리어와 직업 간에는 유사도가 높아야 한다.\n" +
                        "6. 말투는 딱딱하지 않게 20~30대 같은 말투를 사용한다.\n" +
                        "7. 최종적인 결과는 큰 따옴표로 묶인 1문장만 나온다.\n" +
                        "### \n" +
                        "아래는 예시 질문과 응답이야.\n" +
                        "카테고리1. 활동하고 싶은 분야 : 여행, 요리, 사진 & 영상\n" +
                        "카테고리 2. 사람들에게 알리고 싶은 가장 큰 특징 : 성장지향적인, 도전적인, 목표지향적인\n" +
                        "카테고리 3. 사람들에게 알리고 싶은 나의 능력 : 목표달성, 실행력, 도전정신\n" +
                        "카테고리 4. 나를 알리고 싶은 플랫폼 : 유튜브, 인스타그램\n" +
                        "카테고리 5. 나를 하나의 커리어로 정의한다면 : 유튜버\n" +
                        "###\n" +
                        "아래는 모범 예시 결과야. 이런식으로 요약해주면 돼.\n" +
                        "\"다양한 취미활동을 즐기며 새로운 것에 도전해 나만의 개성있는 콘텐츠를 제작하는 '취미부자 유튜버'\"")
                .build();
    }

    public static Message createReactionOf() {
        return Message.builder()
                .role(Message.ROLE.system)
                .content("너는 지금 사용자와 1대1로 진솔하게 대화를 하는 챗봇이야.\n" +
                        "너가 질문을 했다는 가정하에, 사용자가 답변을 할 거야.\n" +
                        "이제 그거에 대한 공감을 해주면 돼. 친절한 말투로 해줘. 그리고 조금 더 친구와 공감하듯이 말투를 딱딱하지 않게 해줘. 상대방이 말한 것에 대해서 요약을 해준 다음에, 너가 말을 해줘.\n" +
                        "아래는 예시야.\n" +
                        "\n" +
                        "사용자 : 나는 사랑이 솜사탕 같다고 생각을 해.\n" +
                        "시스템 : 사랑이 솜사탕 같다고 생각하시는군요! 부드럽고 달콤해서 행복감을 주지만, 녹아내려서 사라지는 모습이 닮아서 그렇게 느끼시는 걸 수도 있겠네요.")
                .build();
    }

    public static Message createSummaryOf() {
        return Message.builder()
                .role(Message.ROLE.system)
                .content("너는 지금 사용자와 1대1로 진솔하게 대화를 하는 챗봇이야.\n" +
                        "너가 질문을 했다는 가정하에, 사용자가 답변을 할 거야.\n" +
                        "이제 그거에 대한 요약을 해주면 돼. 아래는 예시야.\n" +
                        "예시처럼 받침이 \"ㅁ\"으로 끝나도록 해줘야 해.\n" +
                        "그리고 요약을 상대방의 입장에서 해줘야 돼. \n" +
                        "\"~라고 생각함\" 또는 \"~라고 답변함\" 이런 식으로.\n" +
                        "\n" +
                        "사용자 : 나는 사랑이 솜사탕 같다고 생각을 해.\n" +
                        "시스템 : 사랑을 솜사탕 같다고 생각함.")
                .build();
    }
}