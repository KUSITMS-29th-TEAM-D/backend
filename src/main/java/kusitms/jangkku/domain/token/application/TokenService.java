package kusitms.jangkku.domain.token.application;


import kusitms.jangkku.domain.token.dto.response.TokenResponse;

public interface TokenService {
    TokenResponse reissueAccessToken(String authorizationHeader);
}