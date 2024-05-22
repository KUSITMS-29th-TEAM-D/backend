package kusitms.jangkku.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kusitms.jangkku.domain.program.domain.Branding;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.domain.UserOnboardingInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserRegisterRequest {
        private String nickname;
        private String job;
        private int understandingScore;
        private List<String> interestList;
        private List<String> keywordList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserRegisterResponse {
        private String accessToken;
        private String nickname;

        public static UserDto.UserRegisterResponse of(String accessToken, String nickname) {

            return UserRegisterResponse.builder()
                    .accessToken(accessToken)
                    .nickname(nickname)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EditUserInfosRequest {
        private String nickname;
        private String job;
        private int understandingScore;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserInfosResponse {
        private String name;
        private String provider;
        private String nickname;
        private String job;
        private int understandingScore;
        private String profileImgUrl;

        public static UserDto.UserInfosResponse of(User user, UserOnboardingInfo userOnboardingInfo) {

            return UserDto.UserInfosResponse.builder()
                    .name(user.getName())
                    .provider(user.getProvider())
                    .nickname(userOnboardingInfo.getNickname())
                    .job(userOnboardingInfo.getJob())
                    .understandingScore(userOnboardingInfo.getUnderstandingScore())
                    .profileImgUrl(userOnboardingInfo.getProfileImgUrl())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class userHomeResponse {
        private String imageUrl;
        private String programsTitle;
        private Long programsId;
        private String type;

        public static UserDto.userHomeResponse of(Branding branding){
            return userHomeResponse.builder()
                    .imageUrl(branding.getBrandingUrl())
                    .programsTitle(branding.getName())
                    .programsId(branding.getId())
                    .type("branding")
                    .build();
        }

        public static UserDto.userHomeResponse of(SelfUnderstanding selfUnderstanding){
            return userHomeResponse.builder()
                    .imageUrl(selfUnderstanding.getSelfUnderstandingUrl())
                    .programsTitle(selfUnderstanding.getName())
                    .programsId(selfUnderstanding.getId())
                    .type("self-understanding")
                    .build();
        }
    }
}