package kusitms.jangkku.domain.clova.application;

import kusitms.jangkku.domain.clova.dto.ClovaDto;

public interface ClovaService {
    String createDesignPersona(String message);
    String createDiscoverPersonaReaction(String message);
    String createDiscoverPersonaSummary(String message);
}