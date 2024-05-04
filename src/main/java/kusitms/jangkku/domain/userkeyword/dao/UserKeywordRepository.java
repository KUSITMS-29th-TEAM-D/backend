package kusitms.jangkku.domain.userkeyword.dao;

import kusitms.jangkku.domain.userkeyword.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserKeywordRepository extends JpaRepository<UserKeyword,Long> {
}
