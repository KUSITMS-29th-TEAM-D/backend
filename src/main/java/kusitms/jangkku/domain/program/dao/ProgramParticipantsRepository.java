package kusitms.jangkku.domain.program.dao;

import kusitms.jangkku.domain.program.domain.model.Branding;
import kusitms.jangkku.domain.program.domain.model.ProgramParticipants;
import kusitms.jangkku.domain.program.domain.model.SelfUnderstanding;
import kusitms.jangkku.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramParticipantsRepository extends JpaRepository<ProgramParticipants,Long> {
    boolean existsByUserAndBranding(User user, Branding branding);
    boolean existsByUserAndSelfUnderstanding(User user, SelfUnderstanding selfUnderstanding);
    List<ProgramParticipants> findAllByUser(User user);
}
