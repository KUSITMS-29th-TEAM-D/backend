package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.persona.constant.Question;
import kusitms.jangkku.domain.persona.dao.DiscoverPersonaChattingRepository;
import kusitms.jangkku.domain.persona.dao.DiscoverPersonaRepository;
import kusitms.jangkku.domain.persona.domain.DiscoverPersona;
import kusitms.jangkku.domain.persona.domain.DiscoverPersonaChatting;
import kusitms.jangkku.domain.persona.dto.DiscoverPersonaDto;
import kusitms.jangkku.domain.persona.exception.PersonaErrorResult;
import kusitms.jangkku.domain.persona.exception.PersonaException;
import kusitms.jangkku.domain.user.dao.UserRepository;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.domain.user.exception.UserErrorResult;
import kusitms.jangkku.domain.user.exception.UserException;
import kusitms.jangkku.global.util.JwtUtil;
import kusitms.jangkku.global.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscoverPersonaServiceImpl implements DiscoverPersonaService {
    private final JwtUtil jwtUtil;
    private final NumberUtil numberUtil;
    private final UserRepository userRepository;
    private final DiscoverPersonaRepository discoverPersonaRepository;
    private final DiscoverPersonaChattingRepository discoverPersonaChattingRepository;

    // 질문을 새롭게 생성하며 채팅을 시작하는 메서드
    @Override
    public DiscoverPersonaDto.QuestionResponse getNewQuestion(String authorizationHeader, String category) {
        String token = jwtUtil.getTokenFromHeader(authorizationHeader);
        UUID userId = UUID.fromString(jwtUtil.getUserIdFromToken(token));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        DiscoverPersona discoverPersona;
        if (discoverPersonaRepository.existsByUserAndCategory(user, category)) {
            discoverPersona = discoverPersonaRepository.findTopByUserAndCategoryOrderByCreateDateDesc(user, category);
            if (discoverPersona.getIsComplete()) {
                throw new PersonaException(PersonaErrorResult.IS_ALREADY_COMPLETED);
            }
        } else {
            discoverPersona = DiscoverPersona.builder()
                    .user(user)
                    .category(category)
                    .build();
            discoverPersonaRepository.save(discoverPersona);
        }

        List<Integer> questionNumbers = discoverPersonaChattingRepository.findQuestionNumbersByDiscoverPersona(discoverPersona);
        int newQuestionNumber = createNewQuestionNumber(questionNumbers);
        if (questionNumbers.size() == 3) {
            discoverPersona.updateComplete(); // 완료 처리
            discoverPersonaRepository.save(discoverPersona);
        }
        String newQuestionContent = getQuestionContent(category, newQuestionNumber);

        DiscoverPersonaChatting newDiscoverPersonaChatting = DiscoverPersonaChatting.builder()
                .discoverPersona(discoverPersona)
                .questionNumber(newQuestionNumber)
                .build();
        discoverPersonaChattingRepository.save(newDiscoverPersonaChatting);

        return DiscoverPersonaDto.QuestionResponse.of(newDiscoverPersonaChatting.getId(), newQuestionContent);
    }

    // 질문 번호를 생성하는 메서드
    private int createNewQuestionNumber(List<Integer> questionNumbers) {
        int randomQuestionNumber = numberUtil.getRandomNumberNotInList(questionNumbers);
        questionNumbers.add(randomQuestionNumber);

        return randomQuestionNumber;
    }

    // Enum에서 질문 내용을 가져오는 메서드
    private String getQuestionContent(String category, int number) {
        // Question Enum에서 category와 number에 해당하는 content 찾기
        for (Question question : Question.values()) {
            if (question.getCategory().equals(category) && question.getNumber() == number) {
                return question.getContent();
            }
        }

        throw new PersonaException(PersonaErrorResult.NOT_FOUND_QUESTION);
    }
}