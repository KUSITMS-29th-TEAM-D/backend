package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DesignPersona;
import kusitms.jangkku.domain.persona.domain.DesignPersonaField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaFieldRepository extends JpaRepository<DesignPersonaField,Long> {
    List<DesignPersonaField> findAllByDesignPersona(DesignPersona designPersona);
}