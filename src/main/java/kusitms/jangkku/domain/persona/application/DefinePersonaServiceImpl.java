package kusitms.jangkku.domain.persona.application;


import kusitms.jangkku.domain.persona.dto.DefinePersonaDto;
import org.springframework.stereotype.Service;

@Service
public class DefinePersonaServiceImpl implements DefinePersonaService {

    @Override
    public DefinePersonaDto.DefinePersonaResponse getDefinePersona(String authorizationHeader, DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {
        return null;
    }
}