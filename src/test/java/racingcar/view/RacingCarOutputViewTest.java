package racingcar.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import racingcar.domain.Car;
import racingcar.domain.FixedCarEngine;
import racingcar.domain.Position;
import racingcar.domain.RacingResult;
import racingcar.domain.RacingRoundResult;

public class RacingCarOutputViewTest {
    private static final ByteArrayOutputStream OUTPUT = new ByteArrayOutputStream();

    private static Car setCar(String carName, boolean engineCanMove, int position) {
        return new Car(carName, new FixedCarEngine(engineCanMove), new Position(position));
    }

    static Stream<Arguments> providePrintEachRacingResultTestArguments() {
        return Stream.of(
                arguments(new RacingRoundResult(List.of(setCar("pobi", true, 1),
                                setCar("woni", false, 0))),
                        """
                                pobi : -
                                woni :\s
                                                                
                                """),
                arguments(new RacingRoundResult(List.of(setCar("pobi", true, 2),
                                setCar("woni", false, 4),
                                setCar("jun", true, 3))),
                        """
                                pobi : --
                                woni : ----
                                jun : ---
                                                                
                                """)
        );
    }

    static Stream<Arguments> providePrintWinnerTestArguments() {
        return Stream.of(
                arguments(new RacingRoundResult(List.of(
                                setCar("pobi", true, 2),
                                setCar("woni", false, 4),
                                setCar("jun", true, 3))),
                        "최종 우승자 : woni\n",
                        new RacingRoundResult(List.of(
                                setCar("pobi", true, 4),
                                setCar("woni", false, 4),
                                setCar("jun", true, 3))),
                        "최종 우승자 : pobi, woni\n",
                        new RacingRoundResult(List.of(
                                setCar("pobi", true, 1),
                                setCar("woni", false, 1),
                                setCar("jun", true, 1))),
                        "최종 우승자 : pobi, woni, jun\n"
                )
        );
    }

    @BeforeEach
    void setup() {
        OUTPUT.reset();
        System.setOut(new PrintStream(OUTPUT));
    }

    @DisplayName("실행 결과 문자열을 출력한다.")
    @Test
    void printStartMessage() {
        RacingCarOutputView racingCarOutputView = new RacingCarOutputView();
        racingCarOutputView.printStartMessage();
        assertThat(OUTPUT.toString()).isEqualTo("실행 결과\n");
    }

    @DisplayName("매 회차마다 자동차 경주의 결과를 출력한다.")
    @ParameterizedTest
    @MethodSource("providePrintEachRacingResultTestArguments")
    void printEachRacingResult(RacingRoundResult racingRoundResult, String expectedMessage) {
        RacingCarOutputView racingCarOutputView = new RacingCarOutputView();
        RacingResult racingResult = new RacingResult();
        racingResult.addResult(racingRoundResult);
        racingCarOutputView.printEachRacingResult(racingResult);
        assertThat(OUTPUT.toString()).isEqualTo(expectedMessage);
    }

    @DisplayName("최종 우승자를 출력한다.")
    @ParameterizedTest
    @MethodSource("providePrintWinnerTestArguments")
    void printWinnerTest(RacingRoundResult racingRoundResult, String expectedMessage) {
        RacingCarOutputView racingCarOutputView = new RacingCarOutputView();
        RacingResult racingResult = new RacingResult();
        racingResult.addResult(racingRoundResult);
        racingCarOutputView.printWinner(racingResult);
        assertThat(OUTPUT.toString()).isEqualTo(expectedMessage);
    }
}
