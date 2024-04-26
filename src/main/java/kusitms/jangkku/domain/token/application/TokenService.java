package kusitms.jangkku.domain.token.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.token.dto.response.TokenResponse;

public interface TokenService {
    TokenResponse reissueAccessToken(HttpServletRequest request, HttpServletResponse response);
}