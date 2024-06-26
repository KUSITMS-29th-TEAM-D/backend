package kusitms.jangkku.domain.user.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.interest.domain.Interest;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users_interests")
public class UserInterest extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "users_interests_fk_users_id"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interests_id", foreignKey = @ForeignKey(name = "users_interests_fk_interests_id"))
    private Interest interest;

    @Builder
    public UserInterest(User user, Interest interest) {
        this.user = user;
        this.interest = interest;
    }
}