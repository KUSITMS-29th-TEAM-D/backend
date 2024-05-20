package kusitms.jangkku.domain.user.dao;

import kusitms.jangkku.domain.user.domain.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest,Long> {
}
