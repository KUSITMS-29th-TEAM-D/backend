package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DefinePersona;
import kusitms.jangkku.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DefinePersonaRepository extends JpaRepository<DefinePersona,Long> {
    DefinePersona findByUser(User user);
    DefinePersona findByDefinePersonaId(UUID definePersonaId);
    DefinePersona findTopByUserOrderByCreatedAtDesc(User user);
}