package exercise.hotbusinesslogic.repository;

import exercise.hotbusinesslogic.entity.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 상품 저장소
 */
public class ProductRepository {

    private final Map<Long, Product> store = new HashMap<>();

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));          // Null 체크
    }

    public void save(Product product) {
        store.put(product.getId(), product);
    }

}
