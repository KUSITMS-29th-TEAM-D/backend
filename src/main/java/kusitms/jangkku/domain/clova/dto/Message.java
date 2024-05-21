package kusitms.jangkku.domain.clova.dto;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Data
@Builder
public class Message {
    private static String designPersonaPrompt;
    private static String reactionPrompt;
    private static String summaryPrompt;
    private ROLE role;
    private String content;

    public enum ROLE {
        system, user, assistant
    }

    public static Message creatUserOf(String content) {
        return Message.builder()
                .role(ROLE.user)
                .content(content)
                .build();
    }

    public static Message creatSystemOf(String content) {
        return Message.builder()
                .role(ROLE.system)
                .content(content)
                .build();
    }

    public static Message createDesignPersonaSystemOf() {
        return Message.builder()
                .role(Message.ROLE.system)
                .content(new String(Base64.getDecoder().decode(designPersonaPrompt)))
                .build();
    }

    public static Message createReactionOf() {
        return Message.builder()
                .role(Message.ROLE.system)
                .content(new String(Base64.getDecoder().decode(reactionPrompt)))
                .build();
    }

    public static Message createSummaryOf() {
        return Message.builder()
                .role(Message.ROLE.system)
                .content(new String(Base64.getDecoder().decode(summaryPrompt)))
                .build();
    }

    @Component
    public static class Config {
        @Value("${clova.prompt.design}")
        private String design;
        @Value("${clova.prompt.discover.reaction}")
        private String reaction;
        @Value("${clova.prompt.discover.summary}")
        private String summary;

        @PostConstruct
        public void init() {
            designPersonaPrompt = design;
            reactionPrompt = reaction;
            summaryPrompt = summary;
        }
    }
}