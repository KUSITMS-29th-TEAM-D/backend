package kusitms.jangkku.domain.persona.application;

import jakarta.transaction.Transactional;
import kusitms.jangkku.domain.clova.application.ClovaService;
import kusitms.jangkku.domain.persona.constant.Question;
import kusitms.jangkku.domain.persona.dao.DiscoverPersonaChattingRepository;
import kusitms.jangkku.domain.persona.dao.DiscoverPersonaKeywordRepository;
import kusitms.jangkku.domain.persona.dao.DiscoverPersonaRepository;
import kusitms.jangkku.domain.persona.domain.DiscoverPersona;
import kusitms.jangkku.domain.persona.domain.DiscoverPersonaChatting;
import kusitms.jangkku.domain.persona.domain.DiscoverPersonaKeyword;
import kusitms.jangkku.domain.persona.dto.DiscoverPersonaDto;
import kusitms.jangkku.domain.persona.exception.PersonaErrorResult;
import kusitms.jangkku.domain.persona.exception.PersonaException;
import kusitms.jangkku.domain.user.domain.User;
import kusitms.jangkku.global.util.JwtUtil;
import kusitms.jangkku.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscoverPersonaServiceImpl implements DiscoverPersonaService {
    private final JwtUtil jwtUtil;
    private final StringUtil stringUtil;
    private final ClovaService clovaService;
    private final DiscoverPersonaRepository discoverPersonaRepository;
    private final DiscoverPersonaChattingRepository discoverPersonaChattingRepository;
    private final DiscoverPersonaKeywordRepository discoverPersonaKeywordRepository;

    // 질문을 새롭게 생성하며 채팅을 시작하는 메서드
    @Override
    public DiscoverPersonaDto.QuestionResponse getNewQuestion(String authorizationHeader, String category) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona discoverPersona;
        if (discoverPersonaRepository.existsByUserAndCategory(user, category)) {
            discoverPersona = getDiscoverPersona(user, category);
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
        int newQuestionNumber = questionNumbers.size() + 1;
        if (newQuestionNumber == 3) {
            discoverPersona.updateComplete(true); // 완료 처리
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

        DiscoverPersona discoverPersona = discoverPersonaChatting.getDiscoverPersona();

        String reaction = clovaService.createDiscoverPersonaReaction(answerRequest.getAnswer());
        // 마지막 대화인 경우 마무리 멘트 추가
        if (discoverPersona.getIsComplete()) {
            String category = discoverPersona.getCategory();
            String finalComment = getQuestionContent(category, 0);
            reaction += finalComment;
        }
        String summary = clovaService.createDiscoverPersonaSummary(answerRequest.getAnswer());

        discoverPersonaChatting.updateAnswer(answerRequest.getAnswer());
        discoverPersonaChatting.updateReaction(reaction);
        discoverPersonaChatting.updateSummary(summary);
        discoverPersonaChattingRepository.save(discoverPersonaChatting);

        // 대화가 완료된 경우 키워드 생성
        if (discoverPersona.getIsComplete()) {
            createPersonaKeywords(discoverPersona);
        }

        return DiscoverPersonaDto.AnswerResponse.of(reaction, summary);
    }

    // 카테고리별 채팅 내역을 반환하는 메서드
    @Override
    public DiscoverPersonaDto.ChattingResponse getChattings(String authorizationHeader, String category) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona discoverPersona = getDiscoverPersona(user, category);
        List<DiscoverPersonaChatting> chattings = discoverPersonaChattingRepository.findAllByDiscoverPersonaOrderByCreatedDateAsc(discoverPersona);
        // 미완료된 대화가 있다면 제거
        for (DiscoverPersonaChatting chatting : chattings) {
            if (Objects.isNull(chatting.getAnswer())) {
                discoverPersonaChattingRepository.delete(chatting);
                discoverPersona.updateComplete(false); // 미완료 처리
            }
        }

        List<String> stageQuestions = createStageQuestions(category, chattings);

        return DiscoverPersonaDto.ChattingResponse.of(stageQuestions, chattings);
    }

    // 답변 요약 내역을 반환하는 메서드
    @Override
    public DiscoverPersonaDto.SummaryResponse getSummaries(String authorizationHeader) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        DiscoverPersona healthDiscoverPersona = getDiscoverPersona(user, "건강");
        DiscoverPersona careerDiscoverPersona = getDiscoverPersona(user, "커리어");
        DiscoverPersona loveDiscoverPersona = getDiscoverPersona(user, "사랑");
        DiscoverPersona leisureDiscoverPersona = getDiscoverPersona(user, "여가");

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

        DiscoverPersona healthDiscoverPersona = getDiscoverPersona(user, "건강");
        DiscoverPersona careerDiscoverPersona = getDiscoverPersona(user, "커리어");
        DiscoverPersona loveDiscoverPersona = getDiscoverPersona(user, "사랑");
        DiscoverPersona leisureDiscoverPersona = getDiscoverPersona(user, "여가");

        return DiscoverPersonaDto.CheckCompleteResponse.of(healthDiscoverPersona, careerDiscoverPersona, loveDiscoverPersona, leisureDiscoverPersona);
    }

    // 모든 카테고리에서 상위 6개의 키워드를 반환하는 메서드
    @Override
    public DiscoverPersonaDto.KeywordResponse getAllKeywords(String authorizationHeader) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);

        List<DiscoverPersonaKeyword> allKeywords = collectKeywords(user);
        if (allKeywords.isEmpty()) {
            throw new PersonaException(PersonaErrorResult.NOT_FOUND_ANY_KEYWORDS);
        }

        // 상위 6개 키워드만 반환
        List<String> topKeywords = getTopKeywords(allKeywords);

        return DiscoverPersonaDto.KeywordResponse.of(topKeywords);
    }

    // 특정 카테고리에서 상위 6개의 키워드를 반환하는 메서드
    @Override
    public DiscoverPersonaDto.KeywordResponse getCategoryKeywords(String authorizationHeader, String category) {
        User user = jwtUtil.getUserFromHeader(authorizationHeader);
        List<DiscoverPersonaKeyword> keywords = collectCategoryKeywords(user, category);

        // 상위 6개 키워드만 반환
        List<String> topKeywords = getTopKeywords(keywords);

        return DiscoverPersonaDto.KeywordResponse.of(topKeywords);
    }

    // 모든 카테고리의 키워드를 반환하는 메서드
    protected List<DiscoverPersonaKeyword> collectKeywords(User user) {
        List<DiscoverPersonaKeyword> allKeywords = new ArrayList<>();

        String[] categories = {"건강", "커리어", "사랑", "여가"};
        for (String category : categories) {
            DiscoverPersona persona = getDiscoverPersona(user, category);
            if (persona != null && persona.getIsComplete()) {
                allKeywords.addAll(getKeywordsFromPersona(persona));
            }
        }

        return allKeywords;
    }

    // 특정 카테고리의 키워드를 반환하는 메서드
    protected List<DiscoverPersonaKeyword> collectCategoryKeywords(User user, String category) {
        DiscoverPersona persona = getDiscoverPersona(user, category);
        if (persona != null && persona.getIsComplete()) {
            return getKeywordsFromPersona(persona);
        } else {
            throw new PersonaException(PersonaErrorResult.NOT_FOUND_KEYWORDS);
        }
    }

    // 카테고리별 돌아보기 페르소나를 반환하는 메서드
    protected DiscoverPersona getDiscoverPersona(User user, String category) {
        return discoverPersonaRepository.findFirstByUserAndCategoryOrderByCreatedDateDesc(user, category);
    }

    // 페르소나에 해당하는 키워드를 찾아 반환하는 메서드
    protected List<DiscoverPersonaKeyword> getKeywordsFromPersona(DiscoverPersona persona) {
        return discoverPersonaKeywordRepository.findAllByDiscoverPersona(persona);
    }



    // 빈도 수를 기준으로, 상위 6개의 키워드만 반환하는 메서드
    private List<String> getTopKeywords(List<DiscoverPersonaKeyword> keywords) {
        return keywords.stream()
                .sorted((k1, k2) -> Integer.compare(k2.getFrequency(), k1.getFrequency()))
                .limit(6)
                .map(DiscoverPersonaKeyword::getName)
                .collect(Collectors.toList());
    }

    /* 질문 번호를 생성하는 메서드
    private int createNewQuestionNumber(List<Integer> questionNumbers) {
        int randomQuestionNumber = numberUtil.getRandomNumberNotInList(questionNumbers);
        questionNumbers.add(randomQuestionNumber);

        return randomQuestionNumber;
    } */

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
    protected List<String> createSummaries(DiscoverPersona discoverPersona) {
        List<DiscoverPersonaChatting> chattings = discoverPersonaChattingRepository.findAllByDiscoverPersonaOrderByCreatedDateAsc(discoverPersona);
        List<String> summaries = new ArrayList<>();
        for (DiscoverPersonaChatting discoverPersonaChatting : chattings) {
            summaries.add(discoverPersonaChatting.getSummary());
        }

        return summaries;
    }

    // 페르소나 키워드를 생성하고 저장하는 메서드
    protected void createPersonaKeywords(DiscoverPersona discoverPersona) {
        List<String> summaries = discoverPersonaChattingRepository.findSummariesByDiscoverPersona(discoverPersona);
        String clovaRequestText = stringUtil.joinWithNewLine(summaries);
        String keywordResponse = clovaService.createDiscoverPersonaKeywords(clovaRequestText);
        String[] keywords = keywordResponse.split(",");

        for (String keyword : keywords) {
            processKeyword(discoverPersona, keyword.trim());
        }
    }

    // 새로 뽑아낸 키워드를 업데이트 or 저장하는 메서드
    protected void processKeyword(DiscoverPersona discoverPersona, String name) {
        DiscoverPersonaKeyword personaKeyword = discoverPersonaKeywordRepository.findByDiscoverPersonaAndName(discoverPersona, name);
        if (!Objects.isNull(personaKeyword)) {
            personaKeyword.increaseFrequency();
        } else {
            personaKeyword = DiscoverPersonaKeyword.builder()
                    .discoverPersona(discoverPersona)
                    .name(name)
                    .build();
        }
        discoverPersonaKeywordRepository.save(personaKeyword);
    }
}