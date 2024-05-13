package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DefinePersonaKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefinePersonaKeywordRepository extends JpaRepository<DefinePersonaKeyword,Long> {
}