package racingcar.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;

public class RacingCarInputView {
    private static final String DELIMITER = ",";
    private static final String INPUT_CAR_NAMES_MESSAGE = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    private static final String INPUT_ATTEMPT_COUNTS_MESSAGE = "시도할 횟수는 몇회인가요?";

    public List<String> inputCarNames() {
        System.out.println(INPUT_CAR_NAMES_MESSAGE);
        String names = Console.readLine();
        return Arrays.stream(names.split(DELIMITER))
                .toList();
    }

    public String inputAttemptCounts() {
        System.out.println(INPUT_ATTEMPT_COUNTS_MESSAGE);
        return Console.readLine();
    }
}
