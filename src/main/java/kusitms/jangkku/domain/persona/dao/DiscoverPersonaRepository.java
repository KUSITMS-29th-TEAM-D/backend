package kusitms.jangkku.domain.persona.dao;

import io.lettuce.core.dynamic.annotation.Param;
import kusitms.jangkku.domain.persona.domain.DiscoverPersona;
import kusitms.jangkku.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscoverPersonaRepository extends JpaRepository<DiscoverPersona,Long> {
    @Query("SELECT dp FROM DiscoverPersona dp WHERE dp.user = :user AND dp.category = :category ORDER BY dp.createdDate DESC")
    DiscoverPersona findTopByUserAndCategoryOrderByCreateDateDesc(@Param("user") User user, @Param("category") String category);
    boolean existsByUserAndCategory(User user, String category);
    DiscoverPersona findByUserAndCategory(User user, String category);
}