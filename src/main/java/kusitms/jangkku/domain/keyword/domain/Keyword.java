package kusitms.jangkku.domain.keyword.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.program.domain.model.ProgramsImageKeyword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keywords_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "keyword",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProgramsImageKeyword> programsImageKeywords = new ArrayList<>();
}