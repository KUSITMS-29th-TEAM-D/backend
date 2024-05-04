package kusitms.jangkku.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(name = "users_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID userId;

    @Column(name = "name", nullable = false, length = 5)
    private String name;

    @Column(name = "provider", nullable = false, length = 10)
    private String provider;

    @Column(name = "provider_id", nullable = false, length = 50)
    private String providerId;

    @Column(name = "nickname", nullable = false, length = 10)
    private String nickname;

    @Column(name = "job", nullable = false, length = 10)
    private String job;

    @Column(name = "understanding_score", nullable = false, length = 10)
    private int understandingScore;

    @Column(name = "profile_img_url", length = 50)
    private String profileImgUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @Builder
    public User(UUID userId, String name, String provider, String providerId, String nickname, String job, int understandingScore, String profileImgUrl) {
        this.userId = userId;
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
        this.nickname = nickname;
        this.job = job;
        this.understandingScore = understandingScore;
        this.profileImgUrl = profileImgUrl;
    }
}