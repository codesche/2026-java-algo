package exercise.realorder.domain;

/**
 * 상품 도메인
 */
public class Product {

    private final String id;
    private final int price;

    public Product(String id, int price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

}
