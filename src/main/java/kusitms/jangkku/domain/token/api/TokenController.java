package kusitms.jangkku.domain.token.api;

import kusitms.jangkku.domain.token.application.TokenService;
import kusitms.jangkku.domain.token.dto.response.TokenResponse;
import kusitms.jangkku.domain.token.exception.TokenErrorResult;
import kusitms.jangkku.domain.token.exception.TokenException;
import kusitms.jangkku.global.common.ApiResponse;
import kusitms.jangkku.global.common.constant.SuccessStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService authService;

    // 액세스 토큰을 재발행하는 API
    @GetMapping("/reissue/access-token")
    public ResponseEntity<ApiResponse<Object>> reissueAccessToken(
            @RequestHeader("Authorization") String authorizationHeader) {

        try {
            TokenResponse accessToken = authService.reissueAccessToken(authorizationHeader);
            return ApiResponse.onSuccess(SuccessStatus._CREATED_ACCESS_TOKEN, accessToken);
        } catch (TokenException e) {
            // 토큰 예외 처리
            TokenErrorResult errorResult = e.getTokenErrorResult();
            return ApiResponse.onFailure(errorResult);
        }
    }
}