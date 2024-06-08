package kusitms.jangkku.domain.program.domain.model;

import jakarta.persistence.*;
import kusitms.jangkku.domain.interest.domain.Interest;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "programs_interests")
@Getter
public class ProgramsInterest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branding_id", foreignKey = @ForeignKey(name = "programs_interests_fk_branding_id"))
    private Branding branding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id", foreignKey = @ForeignKey(name = "programs_interests_fk_interest_id"))
    private Interest interest;
}