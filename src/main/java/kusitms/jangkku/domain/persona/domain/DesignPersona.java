package kusitms.jangkku.domain.persona.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "design_personas")
public class DesignPersona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "design_personas_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "design_personas_fk_users_id"))
    private User user;

    @Column(name = "design_personas_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID designPersonaId;

    @Column(name = "career", nullable = false, length = 20)
    private String career;

    @Column(name = "definition", nullable = false, length = 100)
    private String definition;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @Builder
    public DesignPersona(User user, String career, String definition) {
        this.user = user;
        this.designPersonaId = UUID.randomUUID();
        this.career = career;
        this.definition = definition;
    }
}