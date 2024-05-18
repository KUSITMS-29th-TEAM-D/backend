package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DesignPersona;
import kusitms.jangkku.domain.persona.domain.DesignPersonaAbility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaAbilityRepository extends JpaRepository<DesignPersonaAbility,Long> {
    List<DesignPersonaAbility> findAllByDesignPersona(DesignPersona designPersona);
}