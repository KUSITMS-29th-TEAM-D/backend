package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DesignPersona;
import kusitms.jangkku.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignPersonaRepository extends JpaRepository<DesignPersona,Long> {
    DesignPersona findFirstByUserOrderByCreatedDateDesc(User user);
}