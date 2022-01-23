package bafiak.domain;

import java.util.Optional;

public interface MappingsRepository {
    Optional<Mapping> findBy(MappingsName mappingsName);

    boolean existsBy(final MappingsName mappingsName);

    void save(final Mapping mapping);
}
