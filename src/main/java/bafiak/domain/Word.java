package bafiak.domain;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Word {
    @NonNull
    String value;
    // TODO ask business if empty string words are OK
}
