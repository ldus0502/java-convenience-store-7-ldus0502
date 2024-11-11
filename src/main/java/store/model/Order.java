package store.model;

import store.exception.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final StockManager stockManager;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(StockManager stockManager, String orderInput) {
        this.stockManager = stockManager;
        parseOrderInput(orderInput);
    }

    private void parseOrderInput(String orderInput) {
        String[] parts = orderInput.replace("[", "").replace("]", "").split(",");
        for (String part : parts) {
            String[] item = part.split("-");
            String name = item[0].trim();
            int quantity = Integer.parseInt(item[1].trim());
            Product product = stockManager.findProductByName(name);
            items.add(new OrderItem(product, quantity));
        }
    }

    public Receipt createReceipt(boolean applyMembership) {
        int total = calculateTotalAmount();
        int promotionDiscount = calculatePromotionDiscount();
        int membershipDiscount = applyMembership ? calculateMembershipDiscount(total - promotionDiscount) : 0;
        int finalAmount = total - promotionDiscount - membershipDiscount;

        return new Receipt(items, calculateFreeItems(), total, promotionDiscount, membershipDiscount, finalAmount);
    }

    private int calculatePromotionDiscount() {
        int totalDiscount = 0;
        for (OrderItem item : items) {
            Promotion promotion = item.getProduct().getPromotion();
            if (promotion != null && promotion.isWithinPromotionPeriod()) {
                int freeQuantity = promotion.applyPromotion(item.getQuantity());
                totalDiscount += freeQuantity * item.getProduct().getPrice();
            }
        }
        return totalDiscount;
    }

    private int calculateMembershipDiscount(int discountedTotal) {
        int discount = (int) (discountedTotal * 0.3);
        return Math.min(discount, 8000); // 최대 8,000원 할인
    }

    private int calculateTotalAmount() {
        return items.stream()
                .mapToInt(OrderItem::calculateTotal)
                .sum();
    }

    private List<OrderItem> calculateFreeItems() {
        List<OrderItem> freeItems = new ArrayList<>();
        for (OrderItem item : items) {
            Promotion promotion = item.getProduct().getPromotion();
            if (promotion != null && promotion.isWithinPromotionPeriod()) {
                freeItems.addAll(item.applyPromotion(promotion));
            }
        }
        return freeItems;
    }

    private int calculateMembershipDiscount(int total, boolean applyMembership) {
        if (!applyMembership) return 0;
        int discount = (int) (total * 0.3);
        return Math.min(discount, 8000); // 최대 한도 8000원 적용
    }
}
