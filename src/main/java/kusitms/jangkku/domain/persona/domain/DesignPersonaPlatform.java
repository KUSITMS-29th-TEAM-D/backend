package kusitms.jangkku.domain.persona.domain;

import jakarta.persistence.*;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "design_personas_platforms")
public class DesignPersonaPlatform extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "design_personas_id", foreignKey = @ForeignKey(name = "design_personas_platforms_fk_design_personas_id"))
    private DesignPersona designPersona;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Builder
    public DesignPersonaPlatform(DesignPersona designPersona, String name) {
        this.designPersona = designPersona;
        this.name = name;
    }
}