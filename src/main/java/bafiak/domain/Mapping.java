package bafiak.domain;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Set;

import bafiak.application.DivisorsCalculator;
import lombok.NonNull;
import lombok.Value;

@Value
public class Mapping {
    @NonNull
    MappingsName mappingsName;
    @NonNull
    Map<MappingNumber, Word> mappings;

    public List<Output> findDivisorsWithMappings(
            final Set<MappingNumber> forNumbers,
            final DivisorsCalculator divisorsCalculator) {
        return forNumbers.stream()
                .map(MappingNumber::getValue)
                .map(divisorsCalculator::calculate)
                .map(this::findWords)
                .collect(toList());
    }

    private Output findWords(final List<Integer> divisors) {
        final List<Word> words = divisors.stream().map(MappingNumber::of).map(mappings::get).collect(toList());
        final MappingNumber mappingNumber = MappingNumber.of(divisors.get(divisors.size() - 1));
        return Output.of(mappingNumber, words);
    }
}
