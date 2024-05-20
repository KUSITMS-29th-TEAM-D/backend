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
@Table(name = "discover_personas_keywords")
public class DiscoverPersonaKeyword extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discover_personas_id", foreignKey = @ForeignKey(name = "discover_personas_keywords_fk_discover_personas_id"))
    private DiscoverPersona discoverPersona;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "frequency", nullable = false)
    private int frequency;

    @Builder
    public DiscoverPersonaKeyword(DiscoverPersona discoverPersona, String name) {
        this.discoverPersona = discoverPersona;
        this.name = name;
        this.frequency = 1;
    }

    public void increaseFrequency() {
        this.frequency++;
    }
}