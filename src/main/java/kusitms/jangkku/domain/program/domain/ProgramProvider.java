package kusitms.jangkku.domain.program.domain;

import jakarta.persistence.*;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programs_providers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProgramProvider extends BaseEntity {

    @Column(name = "name")
    private String providerName;

    @Column(name = "job")
    private String providerJob;

    @Column(name = "keyword")
    private String providerKeyword;

    @Column(name = "image")
    private String providerImage;

    @OneToMany(mappedBy = "programProvider",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Branding> brandings = new ArrayList<>();

    @OneToMany(mappedBy = "programProvider",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<SelfUnderstanding> selfUnderstandings = new ArrayList<>();
}
