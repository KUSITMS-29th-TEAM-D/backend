package kusitms.jangkku.domain.chatbot.dto;

import lombok.Data;

import java.util.EnumMap;

@Data
public class Message {
    private ROLE role; //system,user,assistant
    private String content;

    public enum ROLE {
        system,user,assistant
    }

}
