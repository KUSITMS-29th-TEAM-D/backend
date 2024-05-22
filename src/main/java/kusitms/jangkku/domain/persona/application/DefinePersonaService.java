package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.persona.dto.DefinePersonaDto;
import org.springframework.stereotype.Service;

@Service
public interface DefinePersonaService {
    DefinePersonaDto.DefinePersonaResponse createDefinePersona(String authorizationHeader, DefinePersonaDto.DefinePersonaRequest definePersonaRequest);
    DefinePersonaDto.DefinePersonaResponse createDefinePersonaForSharing(DefinePersonaDto.DefinePersonaRequest definePersonaRequest);
    DefinePersonaDto.DefinePersonaResponse getDefinePersona(String authorizationHeader);
    DefinePersonaDto.DefinePersonaResponse getDefinePersonaForSharing(String definePersonaId);
}