package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DefinePersona;
import kusitms.jangkku.domain.persona.domain.DefinePersonaKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefinePersonaKeywordRepository extends JpaRepository<DefinePersonaKeyword,Long> {
    List<DefinePersonaKeyword> findAllByDefinePersona(DefinePersona definePersona);
}