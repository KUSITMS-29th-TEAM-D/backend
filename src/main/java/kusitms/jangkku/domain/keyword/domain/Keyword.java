package kusitms.jangkku.domain.keyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keywords_id")
    private Long id;

    @Column(name = "keywords_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID keywordId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;
}
