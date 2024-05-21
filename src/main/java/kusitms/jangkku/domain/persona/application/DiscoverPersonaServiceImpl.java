package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.clova.application.ClovaService;
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
import kusitms.jangkku.global.util.JwtUtil;
import kusitms.jangkku.global.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscoverPersonaServiceImpl implements DiscoverPersonaService {
    private final JwtUtil jwtUtil;
    private final NumberUtil numberUtil;
    private final ClovaService clovaService;
    private final UserRepository userRepository;
    private final DiscoverPersonaRepository discoverPersonaRepository;
    private final DiscoverPersonaChattingRepository discoverPersonaChattingRepository;

    // 질문을 새롭게 생성하며 채팅을 시작하는 메서드
    @Override
    public DiscoverPersonaDto.QuestionResponse getNewQuestion(String authorizationHeader, String category) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona discoverPersona;
        if (discoverPersonaRepository.existsByUserAndCategory(user, category)) {
            discoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, category);
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

        return DiscoverPersonaDto.QuestionResponse.of(newDiscoverPersonaChatting.getId(), newQuestionContent, discoverPersona.getIsComplete());
    }

    // 공감과 요약을 생성해 응답하는 메서드
    @Override
    public DiscoverPersonaDto.AnswerResponse getReactionAndSummary(String authorizationHeader, DiscoverPersonaDto.AnswerRequest answerRequest) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersonaChatting discoverPersonaChatting = discoverPersonaChattingRepository.findById(answerRequest.getChattingId())
                .orElseThrow(() -> new PersonaException(PersonaErrorResult.NOT_FOUND_CHATTING));

        String reaction = clovaService.createDiscoverPersonaReaction(answerRequest.getAnswer());
        String summary = clovaService.createDiscoverPersonaSummary(answerRequest.getAnswer());

        discoverPersonaChatting.updateAnswer(answerRequest.getAnswer());
        discoverPersonaChatting.updateReaction(reaction);
        discoverPersonaChatting.updateSummary(summary);
        discoverPersonaChattingRepository.save(discoverPersonaChatting);

        return DiscoverPersonaDto.AnswerResponse.of(reaction, summary);
    }

    // 카테고리별 채팅 내역을 반환하는 메서드
    @Override
    public DiscoverPersonaDto.ChattingResponse getChattings(String authorizationHeader, String category) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona discoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, category);
        List<DiscoverPersonaChatting> chattings = discoverPersonaChattingRepository.findAllByDiscoverPersonaOrderByCreatedDateAsc(discoverPersona);
        List<String> stageQuestions = createStageQuestions(category, chattings);

        return DiscoverPersonaDto.ChattingResponse.of(stageQuestions, chattings);
    }

    // 답변 요약 내역을 반환하는 메서드
    @Override
    public DiscoverPersonaDto.SummaryResponse getSummaries(String authorizationHeader) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona healthDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "건강");
        DiscoverPersona careerDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "커리어");
        DiscoverPersona loveDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "사랑");
        DiscoverPersona leisureDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "여가");

        List<String> healthSummaries = createSummaries(healthDiscoverPersona);
        List<String> careerSummaries = createSummaries(careerDiscoverPersona);
        List<String> loveSummaries = createSummaries(loveDiscoverPersona);
        List<String> leisureSummaries = createSummaries(leisureDiscoverPersona);

        return DiscoverPersonaDto.SummaryResponse.of(healthSummaries, careerSummaries, loveSummaries, leisureSummaries);
    }

    // 채팅 다시하기를 위해 테이블을 새롭게 생성하는 메서드
    @Override
    public void restartChatting(String authorizationHeader, DiscoverPersonaDto.resetChattingRequest resetChattingRequest) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona newDiscoverPersona = DiscoverPersona.builder()
                .user(user)
                .category(resetChattingRequest.getCategory())
                .build();
        discoverPersonaRepository.save(newDiscoverPersona);
    }

    // 카테고리별 대화 완료 여부를 반환하는 메서드
    @Override
    public DiscoverPersonaDto.CheckCompleteResponse checkComplete(String authorizationHeader) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona healthDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "건강");
        DiscoverPersona careerDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "커리어");
        DiscoverPersona loveDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "사랑");
        DiscoverPersona leisureDiscoverPersona = discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, "여가");

        return DiscoverPersonaDto.CheckCompleteResponse.of(healthDiscoverPersona, careerDiscoverPersona, loveDiscoverPersona, leisureDiscoverPersona);
    }

    // 질문 번호를 생성하는 메서드
    private int createNewQuestionNumber(List<Integer> questionNumbers) {
        int randomQuestionNumber = numberUtil.getRandomNumberNotInList(questionNumbers);
        questionNumbers.add(randomQuestionNumber);

        return randomQuestionNumber;
    }

    // Enum에서 질문 내용을 가져오는 메서드
    private String getQuestionContent(String category, int number) {
        for (Question question : Question.values()) {
            if (question.getCategory().equals(category) && question.getNumber() == number) {
                return question.getContent();
            }
        }

        throw new PersonaException(PersonaErrorResult.NOT_FOUND_QUESTION);
    }

    // 질문 목록을 생성하는 메서드
    private List<String> createStageQuestions(String category, List<DiscoverPersonaChatting> chattings) {
        List<String> stageQuestions = new ArrayList<>();
        for (DiscoverPersonaChatting discoverPersonaChatting : chattings) {
            stageQuestions.add(getQuestionContent(category, discoverPersonaChatting.getQuestionNumber()));
        }

        return stageQuestions;
    }

    // 답변 요약 목록을 반환하는 메서드
    private List<String> createSummaries(DiscoverPersona discoverPersona) {
        List<DiscoverPersonaChatting> chattings = discoverPersonaChattingRepository.findAllByDiscoverPersonaOrderByCreatedDateAsc(discoverPersona);
        List<String> summaries = new ArrayList<>();
        for (DiscoverPersonaChatting discoverPersonaChatting : chattings) {
            summaries.add(discoverPersonaChatting.getSummary());
        }

        return summaries;
    }
}