package kusitms.jangkku.domain.persona.constant;

public enum DesignStage {
    STAGE_ONE(1, "카테고리 1. 활동하고 싶은 분야 : "),
    STAGE_TWO(2, "카테고리 2. 사람들에게 알리고 싶은 가장 큰 특징 : "),
    STAGE_THREE(3, "카테고리 3. 사람들에게 알리고 싶은 나의 능력 : "),
    STAGE_FOUR(4, "카테고리 4. 나를 알리고 싶은 플랫폼 : "),
    STAGE_FIVE(5, "카테고리 5. 나를 하나의 커리어로 정의한다면 : ");

    private final int stageNumber;
    private final String content;

    DesignStage(int stageNumber, String content) {
        this.stageNumber = stageNumber;
        this.content = content;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public String getContent() {
        return content;
    }
}