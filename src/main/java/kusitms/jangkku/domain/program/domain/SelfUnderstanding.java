package kusitms.jangkku.domain.program.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.program.constant.FORM;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "self_understanding_programs")
public class SelfUnderstanding extends BaseEntity {

    @Column(name = "self_understanding_url")
    private String selfUnderstandingUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "one_line_description")
    private String oneLineDescription;

    @Column(name = "price")
    private int price;

    @Column(name = "form")
    @Enumerated(EnumType.STRING)
    private FORM form;

    @Column(name="link")
    private String link;

    @Column(name = "description_url")
    private String descriptionUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_provdiers_id", foreignKey = @ForeignKey(name = "self_understanding_programs_program_provdiers_id"))
    private ProgramProvider programProvider;

    @OneToMany(mappedBy = "selfUnderstanding",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProgramParticipants> programParticipants = new ArrayList<>();
}