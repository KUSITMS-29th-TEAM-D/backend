package kusitms.jangkku.domain.persona.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PersonaException extends RuntimeException {
    private final PersonaErrorResult personaErrorResult;

    @Override
    public String getMessage() {
        return personaErrorResult.getMessage();
    }
}