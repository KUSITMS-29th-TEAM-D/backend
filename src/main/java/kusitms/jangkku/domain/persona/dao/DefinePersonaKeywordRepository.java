package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DefinePersona;
import kusitms.jangkku.domain.persona.domain.DefinePersonaKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefinePersonaKeywordRepository extends JpaRepository<DefinePersonaKeyword,Long> {
    List<DefinePersonaKeyword> findAllByDefinePersona(DefinePersona definePersona);
}