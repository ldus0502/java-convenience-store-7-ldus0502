package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.exception.PromotionNotAvailableException;

public class Promotion {
    private final String type;
    private final int requiredQuantity; // 구매 조건
    private final int freeQuantity;     // 증정 개수
    private final String startDate;
    private final String endDate;
    private int defaultPrice; // 기본 가격 추가

    public Promotion(String type, int requiredQuantity, int freeQuantity, String startDate, String endDate) {
        this.type = type;
        this.requiredQuantity = requiredQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(int price) {
        this.defaultPrice = price;
    }

    public String getType() {
        return type;
    }

    public boolean isEligible(int quantity) {
        return quantity >= requiredQuantity;
    }

    public int applyPromotion(int quantity) {
        if (!isEligible(quantity)) {
            throw new PromotionNotAvailableException(type);
        }
        return (quantity / requiredQuantity) * freeQuantity;
    }

    public boolean isWithinPromotionPeriod() {
        String currentDate = String.valueOf(DateTimes.now());
        return currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0;
    }
}
