package kusitms.jangkku.domain.auth.dto;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getName();
}