package store.model;

import java.io.*;
import java.util.*;

public class ProductRepository {

    public List<Product> loadProductsFromFile(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // 헤더 건너뛰기
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(parseProduct(line)); // 각 상품을 파싱해서 추가
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private Product parseProduct(String line) {
        String[] parts = line.split(",");
        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        int quantity = Integer.parseInt(parts[2].trim());
        String promotionName = parts[3].trim();

        Promotion promotion = promotionName.equals("null")
                ? null
                : PromotionManager.getPromotionByName(promotionName);
        return new Product(name, price, quantity, promotion);
    }

    private void processFileLines(BufferedReader reader, Map<String, Product> products) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            addProductFromLine(line, products);
        }
    }

    private void addProductFromLine(String line, Map<String, Product> products) {
        String[] parts = line.split(",");
        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        int quantity = Integer.parseInt(parts[2].trim());
        String promotionName = parts[3].trim();

        Promotion promotion = promotionName.equals("null")
                ? null
                : PromotionManager.getPromotionByName(promotionName);

        products.put(name + "-" + quantity, new Product(name, price, quantity, promotion));
    }

    private void handleIOException(IOException e) {
        System.out.println("[ERROR] 파일 처리 중 문제가 발생했습니다: " + e.getMessage());
    }
}