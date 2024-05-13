package kusitms.jangkku.domain.persona.constant;

public enum DefinePersonaType {
    CREATOR("RIA", "크리에이터"),
    INSIGHTER("RIC", "인사이터"),
    INNOVATOR("REA", "이노베이터"),
    INVENTOR("REC", "인벤터"),
    PROJECTOR("SIA", "프로젝터"),
    CONNECTOR("SIC", "커넥터"),
    ENCOURAGER("SEA", "인커리져"),
    ORGANIZER("SEC", "오거나이져");

    private final String code;
    private final String name;


    DefinePersonaType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}