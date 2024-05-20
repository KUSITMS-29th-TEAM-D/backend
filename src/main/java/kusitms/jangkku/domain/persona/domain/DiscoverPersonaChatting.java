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
@Table(name = "discover_personas_chattings")
public class DiscoverPersonaChatting extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discover_personas_id", foreignKey = @ForeignKey(name = "discover_personas_chattings_fk_discover_personas_id"))
    private DiscoverPersona discoverPersona;

    @Column(name = "question_number", nullable = false)
    private int questionNumber;

    @Column(name = "answer")
    private String answer;

    @Column(name = "reaction")
    private String reaction;

    @Column(name = "summary")
    private String summary;

    @Builder
    public DiscoverPersonaChatting(DiscoverPersona discoverPersona, int questionNumber, String answer, String reaction, String summary) {
        this.discoverPersona = discoverPersona;
        this.questionNumber = questionNumber;
        this.answer = answer;
        this.reaction = reaction;
        this.summary = summary;
    }
    public void updateAnswer(String answer) {
        this.answer = answer;
    }
    public void updateReaction(String reaction) {
        this.reaction = reaction;
    }

    public void updateSummary(String summary) {
        this.summary = summary;
    }
}