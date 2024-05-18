package kusitms.jangkku.domain.user.dao;

import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.domain.UserOnboardingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOnboardingInfoRepository extends JpaRepository<UserOnboardingInfo, Long> {
    UserOnboardingInfo findByUser(User user);
    UserOnboardingInfo findByNickname(String nickname);
}