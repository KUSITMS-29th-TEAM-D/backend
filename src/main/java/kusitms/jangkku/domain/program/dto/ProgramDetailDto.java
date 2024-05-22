package kusitms.jangkku.domain.program.dto;

import kusitms.jangkku.domain.program.constant.FORM;
import kusitms.jangkku.domain.program.domain.Branding;
import kusitms.jangkku.domain.program.domain.SelfUnderstanding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProgramDetailDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProgramDetailResponseDto {
        private String imageUrl; // 썸네일 이미지
        private String name;// 제목
        private String oneLineDescription;// 한줄소개
        private int price;
        private FORM form;
        private String descriptionUrl;// 소개 이미지
        private String link;
        private List<String> keywords;
        private int participants;
        private String providerImage;
        private String providerName;
        private String providerJob;
        private String providerKeyword;
        private String type;
        private boolean isApply;

        // 자기이해
        public static ProgramDetailResponseDto of(SelfUnderstanding selfUnderstandings, List<String> userKeywords, int participants, boolean isApply) {
            return ProgramDetailResponseDto.builder()
                    .imageUrl(selfUnderstandings.getSelfUnderstandingUrl())
                    .name(selfUnderstandings.getName())
                    .oneLineDescription(selfUnderstandings.getOneLineDescription())
                    .price(selfUnderstandings.getPrice())
                    .form(selfUnderstandings.getForm())
                    .descriptionUrl(selfUnderstandings.getDescriptionUrl())
                    .link(selfUnderstandings.getLink())
                    .keywords(userKeywords)
                    .providerImage(selfUnderstandings.getProgramProvider().getProviderImage())
                    .providerName(selfUnderstandings.getProgramProvider().getProviderName())
                    .providerJob(selfUnderstandings.getProgramProvider().getProviderJob())
                    .providerKeyword(selfUnderstandings.getProgramProvider().getProviderKeyword())
                    .type("self-understanding")
                    .participants(participants)
                    .isApply(isApply)
                    .build();
        }

        // 브랜딩
        public static ProgramDetailResponseDto of(Branding branding, List<String> userKeywords, int participants, boolean isApply) {
            return ProgramDetailResponseDto.builder()
                    .imageUrl(branding.getBrandingUrl())
                    .name(branding.getName())
                    .oneLineDescription(branding.getOneLineDescription())
                    .descriptionUrl(branding.getDescriptionUrl())
                    .link(branding.getLink())
                    .keywords(userKeywords)
                    .providerImage(branding.getProgramProvider().getProviderImage())
                    .providerName(branding.getProgramProvider().getProviderName())
                    .providerJob(branding.getProgramProvider().getProviderJob())
                    .providerKeyword(branding.getProgramProvider().getProviderKeyword())
                    .type("branding")
                    .participants(participants)
                    .isApply(isApply)
                    .build();
        }
    }
}