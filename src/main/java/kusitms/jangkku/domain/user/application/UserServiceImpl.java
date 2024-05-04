package kusitms.jangkku.domain.user.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.interest.dao.InterestRepository;
import kusitms.jangkku.domain.interest.domain.Interest;
import kusitms.jangkku.domain.keyword.dao.KeywordRepository;
import kusitms.jangkku.domain.keyword.domain.Keyword;
import kusitms.jangkku.domain.token.dao.RefreshTokenRepository;
import kusitms.jangkku.domain.token.domain.RefreshToken;
import kusitms.jangkku.domain.user.dao.UserRepository;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.dto.UserDto;
import kusitms.jangkku.domain.userinterest.dao.UserInterestRepository;
import kusitms.jangkku.domain.userinterest.domain.UserInterest;
import kusitms.jangkku.domain.userkeyword.dao.UserKeywordRepository;
import kusitms.jangkku.domain.userkeyword.domain.UserKeyword;
import kusitms.jangkku.global.util.CookieUtil;
import kusitms.jangkku.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME; // 액세스 토큰 유효기간

    @Value("${jwt.refresh-token.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME; // 리프레쉬 토큰 유효기간

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final KeywordRepository keywordRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // 기본 정보까지 추가하여 신규 유저를 등록하는 메서드
    @Override
    public UserDto.UserRegisterResponse registerUser(HttpServletResponse response, String authorizationHeader, UserDto.UserRegisterRequest userRegisterRequest) {
        // 토큰을 이용하여 사용자 정보 추출
        String registerToken = jwtUtil.getTokenFromHeader(authorizationHeader);
        String provider = jwtUtil.getProviderFromToken(registerToken);
        String providerId = jwtUtil.getProviderIdFromToken(registerToken);
        String name = jwtUtil.getNameFromToken(registerToken);

        log.info("유저 등록을 진행합니다.");

        // User 객체를 빌더 패턴으로 생성하여 기본 정보 설정
        User user = User.builder()
                .userId(UUID.randomUUID())
                .name(name)
                .provider(provider)
                .providerId(providerId)
                .nickname(userRegisterRequest.getNickname())
                .job(userRegisterRequest.getJob())
                .understandingScore(userRegisterRequest.getUnderstandingScore())
                .build();

        // User 저장
        userRepository.save(user);

        // 사용자가 입력한 관심 분야와 키워드를 찾아서 저장
        saveUserInterests(user, userRegisterRequest.getInterestList());
        saveUserKeywords(user, userRegisterRequest.getKeywordList());

        // 리프레쉬 토큰이 담긴 쿠키 생성 후 설정
        Cookie cookie = cookieUtil.createCookie(user.getUserId(), REFRESH_TOKEN_EXPIRATION_TIME);
        response.addCookie(cookie);

        // 새로운 리프레쉬 토큰 Redis 저장
        RefreshToken newRefreshToken = new RefreshToken(user.getUserId(), cookie.getValue());
        refreshTokenRepository.save(newRefreshToken);

        // 액세스 토큰 발급
        String accessToken = jwtUtil.generateAccessToken(user.getUserId(), ACCESS_TOKEN_EXPIRATION_TIME);

        // 액세스 토큰 반환
        return UserDto.UserRegisterResponse.builder()
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .build();
    }

    // 사용자의 관심 분야를 저장하는 메서드
    private void saveUserInterests(User user, List<UserDto.Interest> interestList) {
        if (interestList != null && !interestList.isEmpty()) {
            for (UserDto.Interest interestDto : interestList) {
                // 관심 분야 이름으로 관심 분야 엔티티를 찾음
                Interest interest = interestRepository.findByName(interestDto.getName());
                if (interest != null) {
                    UserInterest userInterest = UserInterest.builder()
                            .user(user)
                            .interest(interest)
                            .build();
                    userInterestRepository.save(userInterest);
                }
            }
        }
    }

    // 사용자의 키워드를 저장하는 메서드
    private void saveUserKeywords(User user, List<UserDto.Keyword> keywordList) {
        if (keywordList != null && !keywordList.isEmpty()) {
            for (UserDto.Keyword keywordDto : keywordList) {
                // 키워드 이름으로 키워드 엔티티를 찾음
                Keyword keyword = keywordRepository.findByName(keywordDto.getName());
                if (keyword != null) {
                    UserKeyword userKeyword = UserKeyword.builder()
                            .user(user)
                            .keyword(keyword)
                            .build();
                    userKeywordRepository.save(userKeyword);
                }
            }
        }
    }
}