package kusitms.jangkku.domain.interest.dao;

import kusitms.jangkku.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest,Long> {
    Interest findByName(String name);
}