package store.exception;

public class PromotionNotAvailableException extends RuntimeException {
    private final String promotionType;

    public PromotionNotAvailableException(String promotionType) {
        super(ErrorMessage.PROMOTION_NOT_AVAILABLE.getMessage(promotionType));
        this.promotionType = promotionType;
    }

    public String getPromotionType() {
        return promotionType;
    }
}
