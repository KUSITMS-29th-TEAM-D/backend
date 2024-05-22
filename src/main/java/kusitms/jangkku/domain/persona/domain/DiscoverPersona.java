package kusitms.jangkku.domain.persona.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "discover_personas")
public class DiscoverPersona extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "discover_personas_fk_users_id"))
    private User user;

    @Column(name = "category", nullable = false, length = 20)
    private String category;

    @Column(name = "is_complete", nullable = false)
    private Boolean isComplete;

    @Builder
    public DiscoverPersona(User user, String category) {
        this.user = user;
        this.category = category;
        this.isComplete = false;
    }

    public void updateComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

}