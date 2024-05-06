package kusitms.jangkku.domain.interest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "interests")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interests_id")
    private Long id;

    @Column(name = "interests_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID interestId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;
}