package kusitms.jangkku.domain.program.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.keyword.domain.Keyword;
import kusitms.jangkku.global.common.dao.BaseEntity;

@Entity
@Table(name = "programs_image_keywords")
public class ProgramsImageKeyword extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keywords_id", foreignKey = @ForeignKey(name = "programs_images_keywords_fk_keywords_id"))
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandings_id", foreignKey = @ForeignKey(name = "programs_images_brandings_id"))
    private Branding branding;
}
