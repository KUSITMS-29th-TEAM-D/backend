package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DesignPersona;
import kusitms.jangkku.domain.persona.domain.model.DesignPersonaPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaPlatformRepository extends JpaRepository<DesignPersonaPlatform,Long> {
    List<DesignPersonaPlatform> findAllByDesignPersona(DesignPersona designPersona);
}