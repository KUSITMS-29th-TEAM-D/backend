package kusitms.jangkku.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class NumberUtil {

    // 목록에 없는 숫자 하나를 랜덤하게 뽑는 메서드
    public int getRandomNumberNotInList(List<Integer> questionNumbers) {
        // 랜덤한 숫자 생성
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1; // 1부터 5까지의 숫자를 랜덤하게 생성

        // 리스트에 없는 숫자인지 확인
        while (questionNumbers.contains(randomNumber)) {
            randomNumber = random.nextInt(5) + 1; // 다시 새로운 숫자를 생성
        }

        return randomNumber;
    }
}