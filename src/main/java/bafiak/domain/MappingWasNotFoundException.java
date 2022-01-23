package bafiak.domain;

public class MappingWasNotFoundException extends RuntimeException {
    public MappingWasNotFoundException(final MappingsName mappingsName) {
        super("Mapping was not found: " + mappingsName.getValue());
    }
}
