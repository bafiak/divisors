package bafiak.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class MappingNumberTest {

    @ParameterizedTest
    @MethodSource("range")
    void should_create_number_within_valid_range(final int i) throws Exception {
        // Given
        // When
        MappingNumber number = MappingNumber.of(i);
        // Then
        assertThat(number.getValue()).isEqualTo(i);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 21, 22, Integer.MAX_VALUE})
    void should_not_create_number_out_of_range(final int i) throws Exception {
        // Given
        // When
        final Throwable thrown = catchThrowable(() -> {
            MappingNumber.of(i);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(NumberOutOfRangeException.class)
                .hasMessageContaining("Numbers shall be in range from 1 to 20. Found: " + i);
    }

    static Stream<Integer> range() {
        return IntStream.range(1, 20).boxed();
    }
}