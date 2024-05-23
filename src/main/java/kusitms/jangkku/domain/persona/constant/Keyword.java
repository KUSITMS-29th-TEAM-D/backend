package kusitms.jangkku.domain.persona.constant;

import java.util.Arrays;
import java.util.List;

public enum Keyword {
    REALISTIC(Arrays.asList(
            "거침 없는",
            "솔직한",
            "욕심이 없는",
            "지구력 있는",
            "활동적인",
            "소소함을 즐기는",
            "낯가리는",
            "단순한")),
    SOCIAL(Arrays.asList(
            "사람들을 좋아하는",
            "어울리기 좋아하는",
            "친절한",
            "이해심 많은",
            "남을 잘 도와주는",
            "봉사적인",
            "감정적인",
            "이상주의적인")),
    INVESTIGATIVE(Arrays.asList(
            "탐구적인",
            "논리적인",
            "분석적인",
            "합리적인",
            "정확한",
            "지적 호기심이 풍부한",
            "비판적인",
            "내성적인",
            "수줍은",
            "신중한")),
    ENTERPRISING(Arrays.asList(
            "리더십 있는",
            "통솔력 있는",
            "지도력 있는",
            "말을 잘 하는",
            "설득력 있는",
            "경제적인",
            "야망 있는",
            "외향적인",
            "낙관적인",
            "열정적인")),
    ARTISTIC(Arrays.asList(
            "상상력이 풍부한",
            "감수성 강한",
            "자유분방한",
            "개방적인",
            "독창적인",
            "개성적인",
            "개인중심의")),
    CONVENTION(Arrays.asList(
            "정확한",
            "완벽주의인",
            "조심성 많은",
            "세밀한",
            "계획적인",
            "안정적인",
            "완고한",
            "책임감 있는"));

    private final List<String> keywords;

    Keyword(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}