package bafiak.domain;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Set;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingNumber {
    int value;

    public static MappingNumber of(final int value) {
        validateInput(value);
        return new MappingNumber(value);
    }

    private static void validateInput(final int i) {
        if(i < 1 || i > 20) {
            throw new NumberOutOfRangeException("Numbers shall be in range from 1 to 20. Found: " + i);
        }
    }

    public static Set<MappingNumber> of(final Integer... numbers) {
        return Arrays.stream(numbers).map(MappingNumber::of).collect(toSet());
    }
}
