package kusitms.jangkku.domain.program.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProgramException extends RuntimeException {
    private final ProgramErrorResult programErrorResult;

    @Override
    public String getMessage() {
        return programErrorResult.getMessage();
    }
}