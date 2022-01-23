package bafiak.domain;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class MappingsName {
    @NonNull
    String value;
}
