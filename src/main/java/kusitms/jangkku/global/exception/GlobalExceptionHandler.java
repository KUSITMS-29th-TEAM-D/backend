package kusitms.jangkku.global.exception;

import kusitms.jangkku.domain.persona.exception.PersonaErrorResult;
import kusitms.jangkku.domain.persona.exception.PersonaException;
import kusitms.jangkku.domain.program.exception.ProgramErrorResult;
import kusitms.jangkku.domain.program.exception.ProgramException;
import kusitms.jangkku.domain.token.exception.TokenErrorResult;
import kusitms.jangkku.domain.token.exception.TokenException;
import kusitms.jangkku.domain.user.exception.UserErrorResult;
import kusitms.jangkku.domain.user.exception.UserException;
import kusitms.jangkku.global.common.ApiResponse;
import kusitms.jangkku.global.common.code.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // Token
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ApiResponse<BaseErrorCode>> handleTokenException(TokenException e) {
        TokenErrorResult errorResult = e.getTokenErrorResult();
        return ApiResponse.onFailure(errorResult);
    }
    // User
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<BaseErrorCode>> handleUserException(UserException e) {
        UserErrorResult errorResult = e.getUserErrorResult();
        return ApiResponse.onFailure(errorResult);
    }
    // Persona
    @ExceptionHandler(PersonaException.class)
    public ResponseEntity<ApiResponse<BaseErrorCode>> handlePersonaException(PersonaException e) {
        PersonaErrorResult errorResult = e.getPersonaErrorResult();
        return ApiResponse.onFailure(errorResult);
    }
    // S3
    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<ApiResponse<BaseErrorCode>> handleS3Exception(S3Exception e) {
        S3ErrorResult errorResult = e.getS3ErrorResult();
        return ApiResponse.onFailure(errorResult);
    }
    // Header
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> handleMissingHeaderException(MissingRequestHeaderException ex) {
        String errorMessage = "Required header '" + ex.getHeaderName() + "' is missing";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    // Program
    @ExceptionHandler(ProgramException.class)
    public ResponseEntity<ApiResponse<BaseErrorCode>> handleProgramException(ProgramException e) {
        ProgramErrorResult errorResult = e.getProgramErrorResult();
        return ApiResponse.onFailure(errorResult);
    }
}