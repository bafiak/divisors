package bafiak.adapter.outbound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ModuloBasedDivisorsCalculatorTest {

    @Test
    void should_throw_exception_for_negative_input() throws Exception {
        // Given
        // When
        final Throwable thrown = catchThrowable(() -> {
            new ModuloBasedDivisorsCalculator().calculate(-1);
        });

        // Then
        assertThat(thrown).isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only natural numbers are accepted. Found: -1");
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void should_find_divisors(
            final int input,
            final Set<Integer> expected) throws Exception {
        // Given
        // When
        final List<Integer> divisors = new ModuloBasedDivisorsCalculator().calculate(input);
        // Then
        assertThat(divisors).hasSameElementsAs(expected);
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(1, Set.of(1)),
                Arguments.of(2, Set.of(1, 2)),
                Arguments.of(3, Set.of(1, 3)),
                Arguments.of(4, Set.of(1, 2, 4)),
                Arguments.of(5, Set.of(1, 5)),
                Arguments.of(6, Set.of(1, 2, 3, 6)),
                Arguments.of(7, Set.of(1, 7)),
                Arguments.of(8, Set.of(1, 2, 4, 8)),
                Arguments.of(9, Set.of(1, 3, 9)),
                Arguments.of(10, Set.of(1, 2, 5, 10)),
                Arguments.of(11, Set.of(1, 11)),
                Arguments.of(12, Set.of(1, 2, 3, 4, 6, 12))
                        );
    }
}