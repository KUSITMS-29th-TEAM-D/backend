package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DesignPersona;
import kusitms.jangkku.domain.persona.domain.model.DesignPersonaField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaFieldRepository extends JpaRepository<DesignPersonaField,Long> {
    List<DesignPersonaField> findAllByDesignPersona(DesignPersona designPersona);
}