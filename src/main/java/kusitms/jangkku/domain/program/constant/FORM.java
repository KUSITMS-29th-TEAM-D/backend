package kusitms.jangkku.domain.program.constant;

import java.util.Arrays;

public enum FORM {
    ONLINE("ONLINE","온라인"),
    OFFLINE("OFFLINE","오프라인"),
    ALL("ALL","온오프라인");

    private final String code;
    private final String name;


    FORM(String code, String name){
        this.code = code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
