package kusitms.jangkku.global.auth.dto;

import kusitms.jangkku.global.common.constant.Provider;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return Provider.NAVER_PROVIDER.getProvider();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}