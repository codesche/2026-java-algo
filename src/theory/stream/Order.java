package theory.stream;

class Order {
    private Long id;
    private String customerName;
    private String category;
    private int amount;
    private boolean vip;

    public Order(Long id, String customerName, String category, int amount, boolean vip) {
        this.id = id;
        this.customerName = customerName;
        this.category = category;
        this.amount = amount;
        this.vip = vip;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isVip() {
        return vip;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", customer='" + customerName + '\'' +
            ", category='" + category + '\'' +
            ", amount=" + amount +
            ", vip=" + vip +
            '}';
    }

}
