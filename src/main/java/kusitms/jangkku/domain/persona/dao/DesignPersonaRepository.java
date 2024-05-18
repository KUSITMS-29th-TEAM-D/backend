package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DesignPersona;
import kusitms.jangkku.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignPersonaRepository extends JpaRepository<DesignPersona,Long> {
    DesignPersona findByUser(User user);
}