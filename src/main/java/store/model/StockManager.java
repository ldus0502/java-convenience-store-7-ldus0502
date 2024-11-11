package store.model;

import store.exception.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class StockManager {
    private final List<Product> products = new ArrayList<>(); // List로 변경

    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(name + " 상품을 찾을 수 없습니다."));
    }

    public void loadProducts(String filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            reader.readLine(); // 헤더 건너뛰기
            reader.lines().forEach(this::parseAndAddProduct);
            ensurePromotionProducts();
        }
    }

    private void parseAndAddProduct(String line) {
        String[] parts = line.split(",");
        Product product = createProduct(parts);
        products.add(product);
    }

    private Product createProduct(String[] parts) {
        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        int stock = Integer.parseInt(parts[2].trim());
        String promotionName = parts[3].trim().equals("null") ? null : parts[3].trim();
        Promotion promotion = promotionName != null ? PromotionManager.getPromotionByName(promotionName) : null;
        return new Product(name, price, stock, promotion);
    }

    public List<Product> getAllProducts() {
        return products; // 모든 상품 반환
    }

    public void ensurePromotionProducts() {
        Set<String> promotionProductNames = products.stream()
                .filter(product -> product.getPromotion() != null) // 프로모션이 있는 상품만
                .map(Product::getName)
                .collect(Collectors.toSet());
        promotionProductNames.forEach(name -> {
            boolean hasNullPromotion = products.stream()
                    .anyMatch(product -> product.getName().equals(name) && product.getPromotion() == null);
            if (!hasNullPromotion) { // null 프로모션 상품이 없다면 추가
                int price = findPriceByName(name); // 상품의 가격 조회
                products.add(new Product(name, price, 0, null));
            }
        });
    }

    public void printAllProducts() {
        System.out.println("=== Final Products List ===");
        products.forEach(product -> {
            System.out.printf("Product: %s, Price: %,d, Stock: %d, Promotion: %s%n",
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    product.getPromotion() != null ? product.getPromotion().getType() : "None");
        });
    }

    // 상품 이름으로 가격을 찾는 메서드
    private int findPriceByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .map(Product::getPrice)
                .findFirst()
                .orElse(0); // 기본값 0원 처리
    }

    // 실제 상품인지 확인하는 헬퍼 메서드 추가
    private boolean isRealProduct(String name) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(name));
    }
}
