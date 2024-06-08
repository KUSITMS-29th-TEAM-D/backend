package kusitms.jangkku.domain.persona.domain.dao;

import kusitms.jangkku.domain.persona.domain.model.DefinePersona;
import kusitms.jangkku.domain.persona.domain.model.DefinePersonaKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefinePersonaKeywordRepository extends JpaRepository<DefinePersonaKeyword,Long> {
    List<DefinePersonaKeyword> findAllByDefinePersona(DefinePersona definePersona);
}