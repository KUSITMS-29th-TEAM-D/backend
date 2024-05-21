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
@Table(name = "design_personas")
public class DesignPersona extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "design_personas_fk_users_id"))
    private User user;

    @Column(name = "career", nullable = false, length = 20)
    private String career;

    @Column(name = "definition", nullable = false, length = 200)
    private String definition;

    @Builder
    public DesignPersona(User user, String career, String definition) {
        this.user = user;
        this.career = career;
        this.definition = definition;
    }
}