package bafiak.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import bafiak.application.DivisorsCalculator;

class MappingTest {

    @Test
    void should_find_mappings_for_divisor() throws Exception {
        // Given
        final Mapping mapping = new Mapping(MappingsName.of("Animals"), animalsMappingFixture());
        final DivisorsCalculator dummyDivisorsCalculator = number -> List.of(1, 2, 4);

        // When
        final List<Output> actual
                = mapping.findDivisorsWithMappings(Set.of(MappingNumber.of(4)), dummyDivisorsCalculator);

        // Then
        assertThat(actual).contains(
                Output.of(MappingNumber.of(4),
                        List.of(Word.of("Mouse"), Word.of("Cat"), Word.of("Elephant"))));
    }

    private Map<MappingNumber, Word> animalsMappingFixture() {
        return Map.of(
                MappingNumber.of(1), Word.of("Mouse"),
                MappingNumber.of(2), Word.of("Cat"),
                MappingNumber.of(3), Word.of("Dog"),
                MappingNumber.of(4), Word.of("Elephant")
                     );
    }
}