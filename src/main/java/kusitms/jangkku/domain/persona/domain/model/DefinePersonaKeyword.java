package kusitms.jangkku.domain.persona.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "define_personas_keywords")
public class DefinePersonaKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "define_personas_keywords_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "define_personas_id", foreignKey = @ForeignKey(name = "define_personas_keywords_fk_define_personas_id"))
    private DefinePersona definePersona;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Builder
    public DefinePersonaKeyword(DefinePersona definePersona, String name) {
        this.definePersona = definePersona;
        this.name = name;
    }
}