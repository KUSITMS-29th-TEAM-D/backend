package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DesignPersona;
import kusitms.jangkku.domain.persona.domain.model.DesignPersonaAbility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaAbilityRepository extends JpaRepository<DesignPersonaAbility,Long> {
    List<DesignPersonaAbility> findAllByDesignPersona(DesignPersona designPersona);
}