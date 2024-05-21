package kusitms.jangkku.domain.user.domain;

import jakarta.persistence.*;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users_onboarding_infos")
public class UserOnboardingInfo extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "users_onboarding_infos_fk_users_id"))
    private User user;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Column(name = "job", nullable = false, length = 50)
    private String job;

    @Column(name = "understanding_score", nullable = false, length = 10)
    private int understandingScore;

    @Column(name = "profile_img_url", length = 100)
    private String profileImgUrl;

    @Builder
    public UserOnboardingInfo(User user, String nickname, String job, int understandingScore, String profileImgUrl) {
        this.user = user;
        this.nickname = nickname;
        this.job = job;
        this.understandingScore = understandingScore;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void updateInfos(String nickname, String job, int understandingScore) {
        this.nickname = nickname;
        this.job = job;
        this.understandingScore = understandingScore;
    }
}