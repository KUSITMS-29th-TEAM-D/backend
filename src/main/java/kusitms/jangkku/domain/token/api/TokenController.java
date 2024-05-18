package kusitms.jangkku.domain.token.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.token.application.TokenService;
import kusitms.jangkku.domain.token.constant.TokenSuccessStatus;
import kusitms.jangkku.domain.token.dto.TokenDto;
import kusitms.jangkku.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService authService;

    // 액세스 토큰을 재발행하는 API
    @GetMapping("/reissue/access-token")
    public ResponseEntity<ApiResponse<TokenDto.TokenResponse>> reissueAccessToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        TokenDto.TokenResponse accessToken = authService.reissueAccessToken(request, response);
        return ApiResponse.onSuccess(TokenSuccessStatus.CREATED_ACCESS_TOKEN, accessToken);
    }
}