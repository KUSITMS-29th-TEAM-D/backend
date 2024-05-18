package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DesignPersona;
import kusitms.jangkku.domain.persona.domain.DesignPersonaDistinction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaDistinctionRepository extends JpaRepository<DesignPersonaDistinction,Long> {
    List<DesignPersonaDistinction> findAllByDesignPersona(DesignPersona designPersona);
}