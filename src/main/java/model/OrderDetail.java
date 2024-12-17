package model;

public class OrderDetail {
    private int orderId;
    private ProductUnit productUnit;
    public double currentPrice;
    private int quantity;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
