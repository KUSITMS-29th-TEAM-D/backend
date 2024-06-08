package kusitms.jangkku.domain.program.domain.model;

import jakarta.persistence.*;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="program_participants")
@Builder
@AllArgsConstructor
public class ProgramParticipants extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "program_participants_fk_users_id"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branding_programs_id", foreignKey = @ForeignKey(name = "program_participants_branding_programs_id"))
    private Branding branding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_understanding_programs_id", foreignKey = @ForeignKey(name = "program_participants_self_understanding_programs_id"))
    private SelfUnderstanding selfUnderstanding;

    public static ProgramParticipants toEntity(User user,SelfUnderstanding selfUnderstanding){
        return ProgramParticipants.builder()
                .user(user)
                .selfUnderstanding(selfUnderstanding)
                .build();
    }

    public static ProgramParticipants toEntity(User user,Branding branding){
        return ProgramParticipants.builder()
                .user(user)
                .branding(branding)
                .build();
    }
}
