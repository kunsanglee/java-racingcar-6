package racingcar;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class RacingCarInputValidatorTest {

    @ParameterizedTest
    @MethodSource("provideValidateCarNamesSuccessTestArguments")
    void validateCarNamesSuccessTest(List<String> names) {
        RacingCarInputValidator racingCarInputValidator = new RacingCarInputValidator();
        assertThatCode(() -> racingCarInputValidator.validateCarNames(names))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> provideValidateCarNamesSuccessTestArguments() {
        return Stream.of(
                arguments(List.of("java1", "JIGI2")),
                arguments(List.of("자바v", "test")),
                arguments(List.of("자바", "test")),
                arguments(List.of("한글12", "123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidateCarNamesFailTestArguments")
    void validateCarNamesFailTest(List<String> names, String message) {
        RacingCarInputValidator racingCarInputValidator = new RacingCarInputValidator();
        assertThatCode(() -> racingCarInputValidator.validateCarNames(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    static Stream<Arguments> provideValidateCarNamesFailTestArguments() {
        return Stream.of(
                arguments(List.of("", "asdasdsadsadas"), "자동차 이름은 최대 5자 이하만 가능합니다."),
                arguments(List.of("jㄹㅇ ", "!@#"), "자동차 이름은 한글, 영어, 숫자만 가능합니다."),
                arguments(List.of("ja va", ""), "자동차 이름은 한글, 영어, 숫자만 가능합니다."),
                arguments(List.of(" java", "    "), "자동차 이름은 한글, 영어, 숫자만 가능합니다.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "99", "100000000"})
    void validateAttemptCountsSuccessTest(String attempt) {
        RacingCarInputValidator racingCarInputValidator = new RacingCarInputValidator();
        assertThatCode(() -> racingCarInputValidator.validateAttemptCounts(attempt))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideValidateAttemptCountsFailTestArguments")
    void validateAttemptCountsFailTest(String attempt, String message) {
        RacingCarInputValidator racingCarInputValidator = new RacingCarInputValidator();
        assertThatCode(() -> racingCarInputValidator.validateAttemptCounts(attempt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    static Stream<Arguments> provideValidateAttemptCountsFailTestArguments() {
        return Stream.of(
                arguments("-1", "시도 횟수는 0 이상 2억 이하의 양수만 입력 가능합니다."),
                arguments("일부러", "입력값이 숫자가 아닙니다."),
                arguments("~!@#$%^&*()_+", "입력값이 숫자가 아닙니다."),
                arguments("fail", "입력값이 숫자가 아닙니다.")
        );
    }
}
