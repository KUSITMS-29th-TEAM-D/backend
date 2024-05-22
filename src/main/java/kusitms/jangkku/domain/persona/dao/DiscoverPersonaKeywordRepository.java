package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DiscoverPersona;
import kusitms.jangkku.domain.persona.domain.DiscoverPersonaKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscoverPersonaKeywordRepository extends JpaRepository<DiscoverPersonaKeyword,Long> {
    List<DiscoverPersonaKeyword> findAllByDiscoverPersona(DiscoverPersona discoverPersona);
}