package store.model;

import store.exception.ErrorMessage;
import store.exception.OutOfStockException;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final Promotion promotion;

    public Product(String name, int price, int stock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }
    public void reduceStock(int quantity) {
        if (stock < quantity) {
            throw new OutOfStockException(ErrorMessage.OUT_OF_STOCK.getMessage(name));
        }
        stock -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
