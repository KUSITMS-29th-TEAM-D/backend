package kusitms.jangkku.domain.persona.dao;

import kusitms.jangkku.domain.persona.domain.DiscoverPersona;
import kusitms.jangkku.domain.persona.domain.DiscoverPersonaChatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscoverPersonaChattingRepository extends JpaRepository<DiscoverPersonaChatting,Long> {
    @Query("SELECT dc.questionNumber FROM DiscoverPersonaChatting dc WHERE dc.discoverPersona = :discoverPersona")
    List<Integer> findQuestionNumbersByDiscoverPersona(DiscoverPersona discoverPersona);
    @Query("SELECT dc.summary FROM DiscoverPersonaChatting dc WHERE dc.discoverPersona = :discoverPersona")
    List<String> findSummariesByDiscoverPersona(DiscoverPersona discoverPersona);
    List<DiscoverPersonaChatting> findAllByDiscoverPersonaOrderByCreatedDateAsc(DiscoverPersona discoverPersona);
}