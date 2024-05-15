package kusitms.jangkku.domain.persona.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "define_personas")
public class DefinePersona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "define_personas_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "define_personas_fk_users_id"))
    private User user;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @Builder
    public DefinePersona(User user, String name, String code) {
        this.user = user;
        this.name = name;
        this.code = code;
    }
}