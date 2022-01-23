package bafiak.domain;

import java.util.Set;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Input {
    @NonNull
    MappingsName mappingsName;
    @NonNull
    Set<MappingNumber> numbers;
}
