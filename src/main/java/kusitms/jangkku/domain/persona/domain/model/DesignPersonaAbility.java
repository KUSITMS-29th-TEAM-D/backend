package kusitms.jangkku.domain.persona.domain.model;

import jakarta.persistence.*;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "design_personas_abilities")
public class DesignPersonaAbility extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "design_personas_id", foreignKey = @ForeignKey(name = "design_personas_abilities_fk_design_personas_id"))
    private DesignPersona designPersona;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Builder
    public DesignPersonaAbility(DesignPersona designPersona, String name) {
        this.designPersona = designPersona;
        this.name = name;
    }
}