package theory.stream;

class OrderSummary {
    private String customerName;
    private int amount;

    public OrderSummary(String customerName, int amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderSummary{" +
            "customer='" + customerName + '\'' +
            ", amount=" + amount +
            '}';
    }
}
