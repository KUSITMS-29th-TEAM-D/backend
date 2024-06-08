package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DesignPersona;
import kusitms.jangkku.domain.persona.domain.model.DesignPersonaDistinction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaDistinctionRepository extends JpaRepository<DesignPersonaDistinction,Long> {
    List<DesignPersonaDistinction> findAllByDesignPersona(DesignPersona designPersona);
}