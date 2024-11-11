package store.exception;

public class OutOfStockException extends RuntimeException {
    private final String productName;

    public OutOfStockException(String productName) {
        super(ErrorMessage.OUT_OF_STOCK.getMessage(productName));
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}