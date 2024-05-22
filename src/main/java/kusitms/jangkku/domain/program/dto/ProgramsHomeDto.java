package kusitms.jangkku.domain.program.dto;

import kusitms.jangkku.domain.program.domain.Branding;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class ProgramsHomeDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProgramsHomeResponseDto {
        private String selfUnderstandingUrl; //썸네일 이미지
        private String name;//제목
        private String link;
        private Long programsId;
        private String type;
        private int maxPrice;
        private String form;
        private int price;
        private List<String> keywords;

        public static ProgramsHomeResponseDto of(SelfUnderstanding selfUnderstandings, int maxPrice, List<String> userKeywords) {
            return ProgramsHomeResponseDto.builder()
                    .selfUnderstandingUrl(selfUnderstandings.getSelfUnderstandingUrl())
                    .name(selfUnderstandings.getName())
                    .link(selfUnderstandings.getLink())
                    .programsId(selfUnderstandings.getId())
                    .type("self-understanding")
                    .form(selfUnderstandings.getForm().getCode())
                    .price(selfUnderstandings.getPrice())
                    .maxPrice(maxPrice)
                    .keywords(userKeywords)
                    .build();
        }

        public static ProgramsHomeResponseDto of(Branding branding) {
            return ProgramsHomeResponseDto.builder()
                    .selfUnderstandingUrl(branding.getBrandingUrl())
                    .name(branding.getName())
                    .link(branding.getLink())
                    .programsId(branding.getId())
                    .type("branding")
                    .keywords(branding.getProgramsImageKeywords().stream().map(v -> v.getKeyword().getName()).collect(Collectors.toList()))
                    .build();
        }
    }

}
