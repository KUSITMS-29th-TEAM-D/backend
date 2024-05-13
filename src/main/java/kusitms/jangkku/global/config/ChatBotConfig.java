package kusitms.jangkku.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatBotConfig {
    @Value("${clova.api.api-key}")
    private String apiKey;
    @Value("${clova.api.api-gateway-key}")
    private String apiGatewayKey;


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add(createAuthInterceptor());

        return template;
    }

    //HTTP 요청 헤더에 Authorization 헤더를 추가
    private ClientHttpRequestInterceptor createAuthInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().add("X-NCP-CLOVASTUDIO-API-KEY",apiKey);
            request.getHeaders().add("X-NCP-APIGW-API-KEY",apiGatewayKey);
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return execution.execute(request, body);
        };
    }
}
