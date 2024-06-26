package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.persona.dto.DesignPersonaDto;
import org.springframework.stereotype.Service;

@Service
public interface DesignPersonaService {
    DesignPersonaDto.DesignPersonaResponse createDesignPersona(String authorizationHeader, DesignPersonaDto.DesignPersonaRequest designPersonaRequest);
    DesignPersonaDto.DesignPersonaDetailResponse getDesignPersona(String authorizationHeader);
}