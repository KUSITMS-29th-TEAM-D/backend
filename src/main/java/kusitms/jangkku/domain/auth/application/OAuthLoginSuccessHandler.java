package kusitms.jangkku.domain.auth.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.auth.dto.GoogleUserInfo;
import kusitms.jangkku.domain.auth.dto.OAuth2UserInfo;
import kusitms.jangkku.domain.persona.dao.DefinePersonaRepository;
import kusitms.jangkku.domain.persona.domain.DefinePersona;
import kusitms.jangkku.domain.token.dao.RefreshTokenRepository;
import kusitms.jangkku.domain.token.domain.RefreshToken;
import kusitms.jangkku.domain.user.dao.UserOnboardingInfoRepository;
import kusitms.jangkku.domain.user.dao.UserRepository;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.auth.dto.KakaoUserInfo;
import kusitms.jangkku.domain.auth.dto.NaverUserInfo;
import kusitms.jangkku.domain.user.domain.UserOnboardingInfo;
import kusitms.jangkku.global.util.CookieUtil;
import kusitms.jangkku.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${jwt.redirect.access}")
    private String ACCESS_TOKEN_REDIRECT_URI; // 기존 유저 로그인 시 리다이렉트 URI

    @Value("${jwt.redirect.register}")
    private String REGISTER_TOKEN_REDIRECT_URI; // 신규 유저 로그인 시 리다이렉트 URI

    @Value("${jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME; // 액세스 토큰 유효기간

    @Value("${jwt.refresh-token.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME; // 리프레쉬 토큰 유효기간

    private OAuth2UserInfo oAuth2UserInfo = null;

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserRepository userRepository;
    private final UserOnboardingInfoRepository userOnboardingInfoRepository;
    private final DefinePersonaRepository definePersonaRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication; // 토큰
        final String provider = token.getAuthorizedClientRegistrationId(); // provider 추출

        // 구글 || 카카오 || 네이버 로그인 요청
        switch (provider) {
            case "google" -> {
                log.info("구글 로그인 요청");
                oAuth2UserInfo = new GoogleUserInfo(token.getPrincipal().getAttributes());
            }
            case "kakao" -> {
                log.info("카카오 로그인 요청");
                oAuth2UserInfo = new KakaoUserInfo(token.getPrincipal().getAttributes());
            }
            case "naver" -> {
                log.info("네이버 로그인 요청");
                oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) token.getPrincipal().getAttributes().get("response"));
            }
        }

        // 정보 추출
        String providerId = oAuth2UserInfo.getProviderId();
        String name = oAuth2UserInfo.getName();

        User existUser = userRepository.findByProviderId(providerId);
        User user;

        if (existUser == null) {
            // 신규 유저인 경우
            log.info("신규 유저입니다.");

            // 레지스터 토큰 발급
            String registerToken = jwtUtil.generateRegisterToken(provider, providerId, name, ACCESS_TOKEN_EXPIRATION_TIME);

            // 레지스터 토큰을 담아 리다이렉트
            String redirectUri = String.format(REGISTER_TOKEN_REDIRECT_URI, registerToken);
            getRedirectStrategy().sendRedirect(request, response, redirectUri);

        } else {
            // 기존 유저인 경우
            log.info("기존 유저입니다.");
            user = existUser;
            UserOnboardingInfo userOnboardingInfo = userOnboardingInfoRepository.findByUser(user);

            // 리프레쉬 토큰이 담긴 쿠키 생성 후 설정
            ResponseCookie cookie = cookieUtil.createCookie(user.getUserId(), REFRESH_TOKEN_EXPIRATION_TIME);
            response.addHeader("Set-Cookie", cookie.toString());

            // 새로운 리프레쉬 토큰 Redis 저장
            RefreshToken newRefreshToken = new RefreshToken(user.getUserId(), cookie.getValue());
            refreshTokenRepository.save(newRefreshToken);

            // 액세스 토큰 발급
            String accessToken = jwtUtil.generateAccessToken(user.getUserId(), ACCESS_TOKEN_EXPIRATION_TIME);

            // 닉네임, 테스트 여부, 액세스 토큰을 담아 리다이렉트
            DefinePersona definePersona = definePersonaRepository.findTopByUserOrderByCreatedAtDesc(user);
            String isTest = definePersona != null ? "T" : "F";
            String encodedName = URLEncoder.encode(userOnboardingInfo.getNickname(), StandardCharsets.UTF_8);
            String redirectUri = String.format(ACCESS_TOKEN_REDIRECT_URI, encodedName, isTest, accessToken);
            getRedirectStrategy().sendRedirect(request, response, redirectUri);
        }

        log.info("유저 이름 : {}", name);
        log.info("PROVIDER : {}", provider);
        log.info("PROVIDER_ID : {}", providerId);
    }
}