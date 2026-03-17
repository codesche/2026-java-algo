package exercise.realorder.repository;

import exercise.realorder.domain.Product;
import java.util.Optional;

/**
 * 상품 조회 인터페이스
 * - DIP (의존성 역전 전략)
 */
public interface ProductRepository {
    Optional<Product> findById(String id);
}
