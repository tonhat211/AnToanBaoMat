package model;

public class OrderDetail {
    public int orderId;
    public ProductUnit productUnit;
    public double currentPrice;
    public int quantity;
    public OrderDetail() {}

    public OrderDetail(int orderId, ProductUnit productUnit, double currentPrice, int quantity) {
        this.orderId = orderId;
        this.productUnit = productUnit;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productUnit=" + productUnit +
                ", currentPrice=" + currentPrice +
                ", quantity=" + quantity +
                '}';
    }

    public String getCurrentPrice() {
        return Constant.formatPrice(this.currentPrice);
    }
}
