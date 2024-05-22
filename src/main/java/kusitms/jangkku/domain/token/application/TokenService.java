package kusitms.jangkku.domain.token.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.token.dto.TokenDto;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    TokenDto.TokenResponse reissueAccessToken(HttpServletRequest request, HttpServletResponse response);
}