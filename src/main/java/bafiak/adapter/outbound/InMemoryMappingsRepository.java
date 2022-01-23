package bafiak.adapter.outbound;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import bafiak.domain.Mapping;
import bafiak.domain.MappingsName;
import bafiak.domain.MappingsRepository;

// TODO test this adapter (if real DB, query and statements test will be done)
public class InMemoryMappingsRepository implements MappingsRepository {
    private final Set<Mapping> db = new HashSet<>();

    @Override
    public Optional<Mapping> findBy(final MappingsName mappingsName) {
        return db.stream().filter(record -> record.getMappingsName().equals(mappingsName)).findAny();
    }

    @Override
    public boolean existsBy(final MappingsName mappingsName) {
        return db.stream().anyMatch(record -> record.getMappingsName().equals(mappingsName));
    }

    @Override
    public void save(final Mapping mapping) {
        db.add(mapping);
    }
}
