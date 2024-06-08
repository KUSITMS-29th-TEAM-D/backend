package kusitms.jangkku.domain.program.domain.model;

import jakarta.persistence.*;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="branding_programs")
public class Branding extends BaseEntity{

    @Column(name="name")
    private String name;

    @Column(name="one_line_description")
    private String oneLineDescription;

    @Column(name = "link")
    private String link;

    @Column(name="branding_url")
    private String brandingUrl;

    @Column(name = "description_url")
    private String descriptionUrl;

    @OneToMany(mappedBy = "branding",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProgramsImageKeyword> programsImageKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "branding",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProgramsInterest> programsInterests = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_provdiers_id", foreignKey = @ForeignKey(name = "branding_programs_program_provdiers_id"))
    private ProgramProvider programProvider;

    @OneToMany(mappedBy = "branding",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProgramParticipants> programParticipants = new ArrayList<>();

}
