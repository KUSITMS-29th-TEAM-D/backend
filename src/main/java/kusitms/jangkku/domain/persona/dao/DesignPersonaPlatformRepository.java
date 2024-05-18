package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DesignPersona;
import kusitms.jangkku.domain.persona.domain.DesignPersonaPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignPersonaPlatformRepository extends JpaRepository<DesignPersonaPlatform,Long> {
    List<DesignPersonaPlatform> findAllByDesignPersona(DesignPersona designPersona);
}