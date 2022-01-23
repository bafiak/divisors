package bafiak.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bafiak.adapter.outbound.InMemoryMappingsRepository;
import bafiak.adapter.outbound.ModuloBasedDivisorsCalculator;
import bafiak.domain.Input;
import bafiak.domain.MappingNumber;
import bafiak.domain.MappingWasNotFoundException;
import bafiak.domain.MappingsAreNotUniqueException;
import bafiak.domain.MappingsName;
import bafiak.domain.MappingsNameIsNotUniqueException;
import bafiak.domain.Output;
import bafiak.domain.Word;

public class DivisorsMappingServiceTest {
    private DivisorsMappingService mappingService;

    @BeforeEach
    void setUp() {
        mappingService = new DivisorsMappingService(new InMemoryMappingsRepository(),
                new ModuloBasedDivisorsCalculator());
    }

    @Test
    void should_throw_exception_if_words_in_mapping_are_not_unique() throws Exception {
        // Given
        final Map<MappingNumber, Word> mappings = Map.of(
                MappingNumber.of(1), Word.of("Mouse"),
                MappingNumber.of(2), Word.of("Mouse")
                                                        );

        // When
        final Throwable thrown = catchThrowable(() -> {
            mappingService.addMapping(MappingsName.of("Animals"), mappings);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(MappingsAreNotUniqueException.class);
    }
    // FYI: number out of range is specified/tested elsewhere

    @Test
    void should_throw_exception_if_mapping_name_is_not_unique() throws Exception {
        // Given
        final Map<MappingNumber, Word> mappings = Map.of(
                MappingNumber.of(1), Word.of("Mouse")
                                                        );
        final MappingsName animals = MappingsName.of("Animals");
        mappingService.addMapping(animals, mappings);

        // When
        final Throwable thrown = catchThrowable(() -> {
            mappingService.addMapping(animals, mappings);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(MappingsNameIsNotUniqueException.class)
                .hasMessage("Mapping already exists: Animals");
    }

    @Test
    void should_throw_exception_if_mapping_name_doesnt_exits() throws Exception {
        // Given
        final Input input = Input.of(MappingsName.of("Animals"), MappingNumber.of(1, 2, 4));

        // When
        final Throwable thrown = catchThrowable(() -> {
            mappingService.findDivisorsWithMappings(input);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(MappingWasNotFoundException.class)
                .hasMessage("Mapping was not found: Animals");
    }

    @Test
    void should_map_each_divisor_to_word_using_predefined_mapping() throws Exception {
        // Given
        final MappingsName animals = MappingsName.of("Animals");
        mappingService.addMapping(animals, animalsMappingFixture());
        mappingService.addMapping(MappingsName.of("Furniture"), furnitureMappingFixture());

        final Input input = Input.of(animals, MappingNumber.of(1, 2, 4));

        // When
        final List<Output> output = mappingService.findDivisorsWithMappings(input);

        // Then
        assertThat(output).hasSameElementsAs(expectedAnimalsOutput());
    }

    private Map<MappingNumber, Word> animalsMappingFixture() {
        return Map.of(
                MappingNumber.of(1), Word.of("Mouse"),
                MappingNumber.of(2), Word.of("Cat"),
                MappingNumber.of(3), Word.of("Dog"),
                MappingNumber.of(4), Word.of("Elephant")
                     );
    }

    private Map<MappingNumber, Word> furnitureMappingFixture() {
        return Map.of(
                MappingNumber.of(1), Word.of("Chair"),
                MappingNumber.of(2), Word.of("Table"),
                MappingNumber.of(3), Word.of("Cabinet"),
                MappingNumber.of(4), Word.of("Bed")
                     );
    }

    private List<Output> expectedAnimalsOutput() {
        return List.of(
                Output.of(MappingNumber.of(1), List.of(Word.of("Mouse"))),
                Output.of(MappingNumber.of(2), List.of(Word.of("Mouse"), Word.of("Cat"))),
                Output.of(MappingNumber.of(4), List.of(Word.of("Mouse"), Word.of("Cat"), Word.of("Elephant")))
                      );
    }
}
