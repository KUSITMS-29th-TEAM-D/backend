package kusitms.jangkku.domain.program.dao;

import io.lettuce.core.dynamic.annotation.Param;
import kusitms.jangkku.domain.program.constant.FORM;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelfUnderstandingsRepository extends JpaRepository<SelfUnderstanding, Long> {
    SelfUnderstanding findTopByOrderByPriceDesc();

    List<SelfUnderstanding> findTop9ByOrderByCreatedDateDesc();

    Optional<List<SelfUnderstanding>> findAllByOrderByCreatedDateDesc();

    @Query("SELECT s FROM SelfUnderstanding s WHERE s.price >= :startPrice AND s.price <= :endPrice")
    List<SelfUnderstanding> findByPriceBetween(@Param("startPrice") int startPrice, @Param("endPrice") int endPrice);

    @Query("SELECT s FROM SelfUnderstanding s WHERE s.price >= :startPrice AND s.price <= :endPrice AND s.form = :form")
    List<SelfUnderstanding> findByPriceBetweenAndForm(@Param("startPrice") int startPrice, @Param("endPrice") int endPrice, @Param("form") FORM form);
}