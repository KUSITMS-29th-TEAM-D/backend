package kusitms.jangkku.domain.persona.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "design_personas_distinctions")
public class DesignPersonaDistinction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "design_personas_distinctions_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "design_personas_id", foreignKey = @ForeignKey(name = "design_personas_distinctions_fk_design_personas_id"))
    private DesignPersona designPersona;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Builder
    public DesignPersonaDistinction(DesignPersona designPersona, String name) {
        this.designPersona = designPersona;
        this.name = name;
    }
}