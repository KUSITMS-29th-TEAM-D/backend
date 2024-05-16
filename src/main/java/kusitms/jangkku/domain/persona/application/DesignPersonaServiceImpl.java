package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.chatbot.application.ChatBotService;
import kusitms.jangkku.domain.persona.constant.DesignStage;
import kusitms.jangkku.domain.persona.dao.*;
import kusitms.jangkku.domain.persona.domain.*;
import kusitms.jangkku.domain.persona.dto.DesignPersonaDto;
import kusitms.jangkku.domain.user.dao.UserRepository;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.exception.UserErrorResult;
import kusitms.jangkku.domain.user.exception.UserException;
import kusitms.jangkku.global.util.JwtUtil;
import kusitms.jangkku.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DesignPersonaServiceImpl implements DesignPersonaService {
    private final StringUtil stringUtil;
    private final JwtUtil jwtUtil;
    private final ChatBotService chatBotService;
    private final UserRepository userRepository;
    private final DesignPersonaRepository designPersonaRepository;
    private final DesignPersonaFieldRepository designPersonaFieldRepository;
    private final DesignPersonaDistinctionRepository designPersonaDistinctionRepository;
    private final DesignPersonaAbilityRepository designPersonaAbilityRepository;
    private final DesignPersonaPlatformRepository designPersonaPlatformRepository;

    // 설계하기 페르소나 결과를 도출하는 메서드
    @Override
    public DesignPersonaDto.DesignPersonaResponse createDesignPersona(String authorizationHeader, DesignPersonaDto.DesignPersonaRequest designPersonaRequest) {
        String message = createClovaRequestMessage(designPersonaRequest);
        String designPersonaDefinition = chatBotService.createDesignPersona(message);

    if (authorizationHeader != null) {
        DesignPersona designPersona = saveDesignPersona(authorizationHeader, designPersonaDefinition, designPersonaRequest.getCareer());
        saveDesignPersonaFields(designPersona, designPersonaRequest.getFields());
        saveDesignPersonaDistinctions(designPersona, designPersonaRequest.getDistinctions());
        saveDesignPersonaAbilities(designPersona, designPersonaRequest.getAbilities());
        saveDesignPersonaPlatforms(designPersona, designPersonaRequest.getPlatforms());
    }

        return DesignPersonaDto.DesignPersonaResponse.builder()
                .definition(stringUtil.removeQuotesAndBackslashes(designPersonaDefinition))
                .build();
    }

    // CLOVA로 보낼 메세지를 생성하는 메서드
    private String createClovaRequestMessage(DesignPersonaDto.DesignPersonaRequest designPersonaRequest) {
        List<String> stageContents = new ArrayList<>();
        String stageOneContent = DesignStage.STAGE_ONE.getContent() + stringUtil.joinWithComma(designPersonaRequest.getFields());
        String stageTwoContent = DesignStage.STAGE_ONE.getContent() + stringUtil.joinWithComma(designPersonaRequest.getDistinctions());
        String stageThreeContent = DesignStage.STAGE_ONE.getContent() + stringUtil.joinWithComma(designPersonaRequest.getAbilities());
        String stageFourContent = DesignStage.STAGE_ONE.getContent() + stringUtil.joinWithComma(designPersonaRequest.getPlatforms());
        String stageFiveContent = DesignStage.STAGE_ONE.getContent() + designPersonaRequest.getCareer();

        stageContents.add(stageOneContent);
        stageContents.add(stageTwoContent);
        stageContents.add(stageThreeContent);
        stageContents.add(stageFourContent);
        stageContents.add(stageFiveContent);

        return stringUtil.joinWithNewLine(stageContents);
    }

    // 설계하기 페르소나를 저장하는 메서드
    private DesignPersona saveDesignPersona(String authorizationHeader, String designPersonaDefinition, String career) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        UUID userId = UUID.fromString(jwtUtil.getUserIdFromToken(token));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        DesignPersona designPersona = DesignPersona.builder()
                .user(user)
                .career(career)
                .definition(designPersonaDefinition)
                .build();
        designPersonaRepository.save(designPersona);

        return designPersona;
    }

    // 설계하기 페르소나 분야를 저장하는 메서드
    public void saveDesignPersonaFields(DesignPersona designPersona, List<String> fields) {
        for (String field : fields) {
            DesignPersonaField designPersonaField = DesignPersonaField.builder()
                    .designPersona(designPersona)
                    .name(field)
                    .build();
            designPersonaFieldRepository.save(designPersonaField);
        }
    }

    // 설계하기 페르소나 특징을 저장하는 메서드
    public void saveDesignPersonaDistinctions(DesignPersona designPersona, List<String> distinctions) {
        for (String distinction : distinctions) {
            DesignPersonaDistinction designPersonaDistinction = DesignPersonaDistinction.builder()
                    .designPersona(designPersona)
                    .name(distinction)
                    .build();
            designPersonaDistinctionRepository.save(designPersonaDistinction);
        }
    }

    // 설계하기 페르소나 강점을 저장하는 메서드
    public void saveDesignPersonaAbilities(DesignPersona designPersona, List<String> abilities) {
        for (String ability : abilities) {
            DesignPersonaAbility designPersonaAbility = DesignPersonaAbility.builder()
                    .designPersona(designPersona)
                    .name(ability)
                    .build();
            designPersonaAbilityRepository.save(designPersonaAbility);
        }
    }

    // 설계하기 페르소나 플랫폼을 저장하는 메서드
    public void saveDesignPersonaPlatforms(DesignPersona designPersona, List<String> platforms) {
        for (String platform : platforms) {
            DesignPersonaPlatform designPersonaPlatform = DesignPersonaPlatform.builder()
                    .designPersona(designPersona)
                    .name(platform)
                    .build();
            designPersonaPlatformRepository.save(designPersonaPlatform);
        }
    }
}