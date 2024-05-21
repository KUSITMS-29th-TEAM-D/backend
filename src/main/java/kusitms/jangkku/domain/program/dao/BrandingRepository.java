package kusitms.jangkku.domain.program.dao;

import io.lettuce.core.dynamic.annotation.Param;
import kusitms.jangkku.domain.program.domain.Branding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandingRepository extends JpaRepository<Branding, Long> {

    @Query("SELECT DISTINCT b FROM Branding b " +
            "JOIN b.programsImageKeywords pik " +
            "JOIN b.programsInterests pi " +
            "WHERE pik.keyword.name IN :keywords " +
            "OR pi.interest.name IN :interests")
    List<Branding> findByProgramsImageKeywordsInOrProgramsInterestsIn(@Param("keywords") List<String> keywords, @Param("interests") List<String> interests);
}
