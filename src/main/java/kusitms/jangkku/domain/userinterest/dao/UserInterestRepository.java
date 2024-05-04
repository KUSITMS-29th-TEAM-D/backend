package kusitms.jangkku.domain.userinterest.dao;

import kusitms.jangkku.domain.userinterest.domain.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest,Long> {
}
