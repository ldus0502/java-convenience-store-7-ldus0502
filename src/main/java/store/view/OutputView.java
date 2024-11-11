package store.view;

import store.model.Product;
import store.model.Receipt;

import java.util.List;

public class OutputView {
    public void printProducts(List<Product> products) {
        System.out.println("현재 보유하고 있는 상품입니다:");
        products.forEach(this::printProductDetails);
    }

    private void printProductDetails(Product product) {
        if (product.getStock() > 0) {
            System.out.printf("- %s %,d원 %d개 %s%n",
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    getPromotionType(product));
        } else {
            System.out.printf("- %s %,d원 재고 없음%n",
                    product.getName(),
                    product.getPrice());
        }
    }

    private void printAvailableProduct(Product product) {
        if (product.getStock() > 0) {
            System.out.printf("- %s %,d원 %d개 %s%n",
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    getPromotionType(product));
        }
    }

    private void printOutOfStockProduct(Product product) {
        if (product.getStock() == 0) {
            System.out.printf("- %s %,d원 0개%n", product.getName(), product.getPrice());
        }
    }

    private String getPromotionType(Product product) {
        return product.getPromotion() != null ? product.getPromotion().getType().trim() : "";
    }

    public void printReceipt(Receipt receipt) {
        printReceiptHeader(); // 영수증 헤더 출력
        printPurchasedItems(receipt); // 구매한 상품 출력
        printFreeItems(receipt); // 증정 상품 출력
        printReceiptFooter(receipt); // 할인 및 최종 금액 출력
    }

    private void printReceiptHeader() {
        System.out.println("==============W 편의점================");
        System.out.printf("%-10s\t%-6s\t%-10s\n", "상품명", "수량", "금액");
    }

    private void printPurchasedItems(Receipt receipt) {
        receipt.getPurchasedItems().forEach(item ->
                System.out.printf("%-10s\t%-6d\t%,10d원\n",
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.calculateTotal()));
    }

    private void printFreeItems(Receipt receipt) {
        System.out.println("=============증\t정===============");
        receipt.getFreeItems().forEach(item ->
                System.out.printf("%-10s\t%-6d\n",
                        item.getProduct().getName(),
                        item.getQuantity()));
    }

    private void printReceiptFooter(Receipt receipt) {
        System.out.println("==================================");
        System.out.printf("%-10s\t\t%,10d원\n", "총구매액", receipt.getTotalAmount());
        System.out.printf("%-10s\t\t-%10d원\n", "행사할인", receipt.getPromotionDiscount());
        System.out.printf("%-10s\t\t-%10d원\n", "멤버십할인", receipt.getMembershipDiscount());
        System.out.printf("%-10s\t\t%,10d원\n", "내실돈", receipt.getFinalAmount());
        System.out.println("==================================");
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
