package kusitms.jangkku.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StringUtil {

    // 쉼표로 합쳐서 반환하는 메서드
    public String joinWithComma(List<String> contents) {
        return String.join(",", contents);
    }

    // 줄바꿈으로 합쳐서 반환하는 메서드
    public String joinWithNewLine(List<String> stageContents) {
        return String.join("\n", stageContents);
    }

    // 양끝의 따옴표와 백슬래시를 제거하는 메서드
    public String removeQuotesAndBackslashes(String input) {
        input = input.replace("\\", "");
        input = input.replace("\"", "");

        return input;
    }
}