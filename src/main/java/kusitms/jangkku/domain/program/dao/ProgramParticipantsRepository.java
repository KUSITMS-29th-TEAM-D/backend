package kusitms.jangkku.domain.program.dao;

import kusitms.jangkku.domain.program.domain.Branding;
import kusitms.jangkku.domain.program.domain.ProgramParticipants;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import kusitms.jangkku.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramParticipantsRepository extends JpaRepository<ProgramParticipants,Long> {
    boolean existsByUserAndBranding(User user, Branding branding);
    boolean existsByUserAndSelfUnderstanding(User user, SelfUnderstanding selfUnderstanding);
}
