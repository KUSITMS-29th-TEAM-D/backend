package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DiscoverPersona;
import kusitms.jangkku.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscoverPersonaRepository extends JpaRepository<DiscoverPersona,Long> {
    DiscoverPersona findFirstByUserAndCategoryOrderByCreatedDateDesc(User user, String category);
    boolean existsByUserAndCategory(User user, String category);
}