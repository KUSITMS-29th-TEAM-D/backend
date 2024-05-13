package kusitms.jangkku.domain.persona.constant;

import java.util.Arrays;
import java.util.List;

public enum DefinePersonaKeyword {
    REALISTIC(Arrays.asList(
            "남성적임",
            "솔직함",
            "검소함",
            "지구력 있음",
            "신체적으로 건강함",
            "소박함",
            "말이 적음",
            "단순함")),
    SOCIAL(Arrays.asList(
            "사람들을 좋아함",
            "어울리기 좋아함",
            "친절함",
            "이해심 많음",
            "남을 잘 도와줌",
            "봉사적",
            "감정적",
            "이상주의적")),
    INVESTIGATIVE(Arrays.asList(
            "탐구심 많음",
            "논리적",
            "분석적",
            "합리적",
            "정확함",
            "지적 호기심",
            "비판적",
            "내성적",
            "수줍음",
            "신중함")),
    ENTERPRISING(Arrays.asList(
            "지배적",
            "통솔력",
            "지도력 있음",
            "말을 잘함",
            "설득적",
            "경제적",
            "야심적",
            "외향적",
            "낙관적",
            "열성적")),
    ARTISTIC(Arrays.asList(
            "상상력 풍부함",
            "감수성 강함",
            "자유분방함",
            "개방적",
            "독창적",
            "개성적",
            "협동적이지 않음")),
    CONVENTION(Arrays.asList(
            "정확함",
            "빈틈없음",
            "조심성 있음",
            "세밀함",
            "계획적",
            "변화를 좋아하지 않음",
            "완고함",
            "책임감"));

    private final List<String> keywords;

    DefinePersonaKeyword(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}