package kusitms.jangkku.domain.user.dao;

import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.domain.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserInterestRepository extends JpaRepository<UserInterest,Long> {
    List<UserInterest> findAllByUser(User user);
}
