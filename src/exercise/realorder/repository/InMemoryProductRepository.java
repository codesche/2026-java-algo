package exercise.realorder.repository;

/**
 * 메모리 기반 Repository
 * - ConcurrentHashMap 사용
 * - 이유: 멀티스레드 환경에서 안전 + 조회 O(1)
 */
import exercise.realorder.domain.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> storage = new ConcurrentHashMap<>();

    public void save(Product product) {
        storage.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
