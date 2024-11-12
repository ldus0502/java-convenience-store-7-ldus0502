package store.model;

import store.exception.InvalidInputException;
import store.exception.OutOfStockException;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new InvalidInputException("수량은 1 이상이어야 합니다.");
        }
        this.product = product;
        this.quantity = quantity;
        try {
            this.product.reduceStock(quantity);
        } catch (OutOfStockException e) {
            throw new OutOfStockException(product.getName());
        }
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int calculateTotal() {
        return product.getPrice() * quantity;
    }

    public List<OrderItem> applyPromotion(Promotion promotion) {
        List<OrderItem> freeItems = new ArrayList<>();
        if (promotion != null && promotion.isEligible(quantity)) {
            int freeQuantity = promotion.applyPromotion(quantity);
            if (freeQuantity > 0) {
                freeItems.add(new OrderItem(product, freeQuantity));
            }
        }
        return freeItems;
    }
}
