package store.exception;

public enum ErrorMessage {
    INVALID_INPUT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    OUT_OF_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    PROMOTION_NOT_AVAILABLE("[ERROR] 프로모션 재고가 부족합니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
