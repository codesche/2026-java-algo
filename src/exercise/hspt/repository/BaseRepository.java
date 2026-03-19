package exercise.hspt.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    void save(T t);
    Optional<T> findById(String id);
    void delete(String id);
    List<T> findAll();
}
