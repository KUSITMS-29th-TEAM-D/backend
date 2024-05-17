package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.persona.dto.DefinePersonaDto;

public interface DefinePersonaService {
    DefinePersonaDto.DefinePersonaResponse createDefinePersona(String authorizationHeader, DefinePersonaDto.DefinePersonaRequest definePersonaRequest);
    DefinePersonaDto.DefinePersonaResponse getDefinePersona(String authorizationHeader);
    DefinePersonaDto.DefinePersonaResponse getDefinePersonaForSharing(String definePersonaId);
}