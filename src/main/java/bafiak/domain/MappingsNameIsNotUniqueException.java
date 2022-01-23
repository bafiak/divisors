package bafiak.domain;

public class MappingsNameIsNotUniqueException extends RuntimeException {
    public MappingsNameIsNotUniqueException(final MappingsName mappingsName) {
        super("Mapping already exists: " + mappingsName.getValue());
    }
}
