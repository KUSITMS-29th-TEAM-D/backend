package kusitms.jangkku.domain.program.dto;

import kusitms.jangkku.domain.program.domain.Branding;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProgramDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProgrmsMainResponsetDto {
        private String selfUnderstandingUrl; //썸네일 이미지
        private String name;//제목
        private String link;
        private Long programsId;
        private String type;
        private int maxPrice;
        private String form;
        private int price;

        public static ProgrmsMainResponsetDto of(SelfUnderstanding selfUnderstandings) {
            return ProgrmsMainResponsetDto.builder()
                    .selfUnderstandingUrl(selfUnderstandings.getSelfUnderstandingUrl())
                    .name(selfUnderstandings.getName())
                    .link(selfUnderstandings.getLink())
                    .programsId(selfUnderstandings.getId())
                    .type("self-understanding")
                    .build();
        }

        public static ProgrmsMainResponsetDto of(SelfUnderstanding selfUnderstandings,int maxPrice) {
            return ProgrmsMainResponsetDto.builder()
                    .selfUnderstandingUrl(selfUnderstandings.getSelfUnderstandingUrl())
                    .name(selfUnderstandings.getName())
                    .link(selfUnderstandings.getLink())
                    .programsId(selfUnderstandings.getId())
                    .type("self-understanding")
                    .form(selfUnderstandings.getForm().getCode())
                    .price(selfUnderstandings.getPrice())
                    .maxPrice(maxPrice)
                    .build();
        }

        public static ProgrmsMainResponsetDto of(Branding branding) {
            return ProgrmsMainResponsetDto.builder()
                    .selfUnderstandingUrl(branding.getBrandingUrl())
                    .name(branding.getName())
                    .link(branding.getLink())
                    .programsId(branding.getId())
                    .type("branding")
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProgramSelfUnderstandingRequestDto {
        private Integer startPrice;
        private Integer endPrice;
        private String form;//온라인,오프라인,온/오프라인
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProgramBrandingRequestDto {
        List<String> interest;
        List<String> imageKeywords;
    }
}
