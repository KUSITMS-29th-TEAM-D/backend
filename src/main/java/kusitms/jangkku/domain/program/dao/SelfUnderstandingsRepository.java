package kusitms.jangkku.domain.program.dao;

import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface SelfUnderstandingsRepository extends JpaRepository<SelfUnderstanding,Long> {
    List<SelfUnderstanding> findTop9ByOrderByCreatedDateDesc();

    List<SelfUnderstanding> findAllByPrice(int price);

    List<SelfUnderstanding> findAllByOrderByCreatedDateDesc();
}
