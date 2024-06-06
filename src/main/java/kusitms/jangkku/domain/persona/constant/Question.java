package kusitms.jangkku.domain.persona.constant;

public enum Question {
    HEALTH_Q1(1, "건강", "좋아하는 운동이 있어? 그 운동이 왜 좋아?"),
    HEALTH_Q2(2, "건강", "너는 평소에 더 행복하게, 건강하게 살기 위해서 남는 시간에 노력하는게 있어?"),
    HEALTH_Q3(3, "건강", "마음이 건강하다는 건 어떤 뜻인거 같아? 너가 중요하다고 생각하는 가치(정직, 책임, 인간관계, 명예 등)를 떠올리면서 답변하면 더 좋을 거 같아."),
    CAREER_Q1(1, "커리어", "너는 어떤 미래를 그리고 있어? 꼭 직업이 아니여도 되고, 어떤 모습으로 하루하루를 살고 싶은지 말해줘."),
    CAREER_Q2(2, "커리어", "삶에서 겪은 성취 중에 가장 큰 성취라면 뭐가 있을까? 혹은 아직 이루지 않은 너의 성취를 말해줘도 좋아."),
    CAREER_Q3(3, "커리어", "그러면 그 성취와 유사한 혹은 그 성취를 이루기 위해서는 무엇을 해볼 수 있을까?"),
    LOVE_Q1(1, "사랑", "너는 사랑에 이유가 있다고 생각해? 있다면 왜 있는지, 없다면 왜 없다고 생각하는지 말해줘!"),
    LOVE_Q2(2, "사랑", "네 인생에서 사랑은 몇순위야? 다른 일이나 인간관계와 사랑을 비교했을때 뭐가 더 먼저라고 생각해?"),
    LOVE_Q3(3, "사랑", "너는 사랑을 주는 걸 좋아해 아님 받는걸 좋아해?"),
    LEISURE_Q1(1, "여가", "하루에 꼭 하는 습관이나 매일매일 느끼는 소소한 행복이 있어? 정말 소소한 것도 괜찮아."),
    LEISURE_Q2(2, "여가", "그러면 지금 너에게 일이나 공부 등 방해요인이 다 사라지고, 너만을 위한 1주일이 생긴다면 뭘 하고 싶어?"),
    LEISURE_Q3(3, "여가", "마지막으로, 바쁜 일상 속에서 소소한 행복을 위해 즐기는 것들이 있으면 나에게 소개해줘.");

    private int number;
    private String category;
    private String content;

    Question(int number, String category, String content) {
        this.number = number;
        this.category = category;
        this.content = content;

    }

    public int getNumber() {
        return number;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }
}