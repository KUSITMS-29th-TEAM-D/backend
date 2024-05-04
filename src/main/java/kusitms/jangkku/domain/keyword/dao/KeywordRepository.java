package kusitms.jangkku.domain.keyword.dao;

import kusitms.jangkku.domain.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword,Long> {
    Keyword findByName(String name);
}