package bafiak.domain;

import java.util.List;

import lombok.Value;

@Value(staticConstructor = "of")
public class Output {
    MappingNumber number;
    List<Word> words;
}
