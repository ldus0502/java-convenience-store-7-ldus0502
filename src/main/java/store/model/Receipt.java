package store.model;

import java.util.List;

public class Receipt {
    private final List<OrderItem> purchasedItems;
    private final List<OrderItem> freeItems;
    private final int totalAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int finalAmount;

    public List<OrderItem> getPurchasedItems() {
        return purchasedItems;
    }

    public List<OrderItem> getFreeItems() {
        return freeItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public Receipt(List<OrderItem> purchasedItems, List<OrderItem> freeItems,
                   int totalAmount, int promotionDiscount, int membershipDiscount, int finalAmount) {
        this.purchasedItems = purchasedItems;
        this.freeItems = freeItems;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.finalAmount = finalAmount;
    }
}
