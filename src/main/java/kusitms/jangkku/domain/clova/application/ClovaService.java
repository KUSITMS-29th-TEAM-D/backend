package kusitms.jangkku.domain.clova.application;

public interface ClovaService {
    String createDesignPersona(String message);
    String createDiscoverPersonaReaction(String message);
    String createDiscoverPersonaSummary(String message);
    String createDiscoverPersonaKeywords(String message);
}