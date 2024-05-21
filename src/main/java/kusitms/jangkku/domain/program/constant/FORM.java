package kusitms.jangkku.domain.program.constant;

import kusitms.jangkku.domain.program.exception.ProgramErrorResult;
import kusitms.jangkku.domain.program.exception.ProgramException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@Getter
@AllArgsConstructor
public enum FORM {
    ONLINE("온라인","ONLINE"),
    OFFLINE("오프라인","OFFLINE"),
    ALL("온오프라인","ALL");

    private final String code;
    private final String name;


    public static FORM ofCode(String dbData) {
        return Arrays.stream(FORM.values())
                .filter(v -> v.getCode().equals(dbData))
                .findAny()
                .orElseThrow(() -> new ProgramException(ProgramErrorResult.PROGRAM_ENUM_NOT_FOUND));
    }

}
