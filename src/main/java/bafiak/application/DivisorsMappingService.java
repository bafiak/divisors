package bafiak.application;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import bafiak.domain.Input;
import bafiak.domain.Mapping;
import bafiak.domain.MappingNumber;
import bafiak.domain.MappingWasNotFoundException;
import bafiak.domain.MappingsAreNotUniqueException;
import bafiak.domain.MappingsName;
import bafiak.domain.MappingsNameIsNotUniqueException;
import bafiak.domain.MappingsRepository;
import bafiak.domain.Output;
import bafiak.domain.Word;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DivisorsMappingService {
    private final MappingsRepository mappingsRepository;
    private final DivisorsCalculator divisorsCalculator;

    public List<Output> findDivisorsWithMappings(final Input input) {
        final Mapping mapping = mappingsRepository.findBy(input.getMappingsName())
                .orElseThrow(() -> new MappingWasNotFoundException(input.getMappingsName()));
        return mapping.findDivisorsWithMappings(input.getNumbers(), divisorsCalculator);
    }

    public void addMapping(
            final MappingsName mappingsName,
            final Map<MappingNumber, Word> mappings) {
        validateMappings(mappings);
        if(mappingsRepository.existsBy(mappingsName)) {
            throw new MappingsNameIsNotUniqueException(mappingsName);
        }
        mappingsRepository.save(new Mapping(mappingsName, mappings));
    }

    private void validateMappings(final Map<MappingNumber, Word> mappings) {
        if (mappings.values().size() != new HashSet<>(mappings.values()).size()) {
            throw new MappingsAreNotUniqueException();
        }
        // TODO ask business if empty mappings are acceptable
    }
}
