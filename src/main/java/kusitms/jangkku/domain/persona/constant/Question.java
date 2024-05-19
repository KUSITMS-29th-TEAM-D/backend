package kusitms.jangkku.domain.persona.constant;

public enum Question {
    HEALTH_Q1(1, "건강", "안녕하세요! 오늘은 몸 상태가 어떤가요?"),
    HEALTH_Q2(2, "건강", "오늘은 운동을 하셨나요?"),
    HEALTH_Q3(3, "건강", "하루에 몇 시간 정도 자고 계신가요?"),
    HEALTH_Q4(4, "건강", "영양을 챙겨 식사하고 계신가요?"),
    HEALTH_Q5(5, "건강", "스트레스 관리는 잘 하고 계신가요?"),
    CAREER_Q1(1, "커리어", "최근에 업무나 공부에서 어떤 어려움을 겪고 계신가요?"),
    CAREER_Q2(2, "커리어", "직무나 학업에 대한 목표가 있나요?"),
    CAREER_Q3(3, "커리어", "자신의 업무 또는 공부에 대해 만족하고 계신가요?"),
    CAREER_Q4(4, "커리어", "좋은 성과를 얻기 위해 노력하고 계신가요?"),
    CAREER_Q5(5, "커리어", "자신의 직업이나 전공에 어떤 자부심을 가지고 계신가요?"),
    LOVE_Q1(1, "사랑", "사랑하는 사람과 함께 시간을 보낼 때 가장 행복한 순간은 언제인가요?"),
    LOVE_Q2(2, "사랑", "애인과의 관계에서 중요하게 생각하는 가치나 원칙이 있나요?"),
    LOVE_Q3(3, "사랑", "사랑하는 사람과 함께하는 시간을 더욱 특별하게 만들기 위해 노력하고 있나요?"),
    LOVE_Q4(4, "사랑", "사랑하는 사람에 대해 생각할 때 특별하게 느끼는 감정이 있나요?"),
    LOVE_Q5(5, "사랑", "사랑하는 사람과의 관계를 발전시키기 위해 어떤 노력을 하고 싶으신가요?"),
    LEISURE_Q1(1, "여가", "여가 시간에 어떤 취미나 활동을 즐기시나요?"),
    LEISURE_Q2(2, "여가", "스트레스를 풀기 위해 어떤 여가 활동을 하시나요?"),
    LEISURE_Q3(3, "여가", "자유 시간을 보내는 것에 대해 어떻게 생각하시나요?"),
    LEISURE_Q4(4, "여가", "최근에 본 영화나 듣고 있는 음악은 무엇인가요?"),
    LEISURE_Q5(5, "여가", "오늘의 취미생활이나 여가 활동에 대해 이야기해주세요!");

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