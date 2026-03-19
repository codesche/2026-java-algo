package exercise.hspt.repository;

import exercise.hspt.util.IdExtractor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class InMemoryRepository<T> implements BaseRepository<T> {

    private final Map<String, T> store = new ConcurrentHashMap<>();
    private final IdExtractor<T> extractor;

    public InMemoryRepository(IdExtractor<T> extractor) {
        this.extractor = extractor;
    }

    @Override
    public void save(T t) {
        store.put(extractor.getId(t), t);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }
}
