package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DiscoverPersona;
import kusitms.jangkku.domain.persona.domain.model.DiscoverPersonaKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscoverPersonaKeywordRepository extends JpaRepository<DiscoverPersonaKeyword,Long> {
    List<DiscoverPersonaKeyword> findAllByDiscoverPersona(DiscoverPersona discoverPersona);
    DiscoverPersonaKeyword findByDiscoverPersonaAndName(DiscoverPersona discoverPersona, String name);
}