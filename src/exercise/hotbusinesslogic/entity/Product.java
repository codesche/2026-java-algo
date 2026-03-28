package exercise.hotbusinesslogic.entity;

import exercise.hotbusinesslogic.exception.BusinessException;

/**
 * 상품
 */
public class Product {

    private final long id;
    private final String name;
    private final int price;
    private int stock;

    public Product(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void decreaseStock(int quantity) {
        if (stock < quantity) {
            throw new BusinessException("재고 부족");
        }
        stock -= quantity;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }

}
