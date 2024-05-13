package kusitms.jangkku.domain.user.dao;

import kusitms.jangkku.domain.user.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserKeywordRepository extends JpaRepository<UserKeyword,Long> {
}
