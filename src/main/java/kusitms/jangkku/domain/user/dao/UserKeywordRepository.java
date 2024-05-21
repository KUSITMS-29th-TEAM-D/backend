package kusitms.jangkku.domain.user.dao;

import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordRepository extends JpaRepository<UserKeyword,Long> {

    List<UserKeyword> findAllByUser(User user);
}
