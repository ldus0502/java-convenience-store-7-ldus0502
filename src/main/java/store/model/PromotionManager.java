package store.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PromotionManager {
    private static final Map<String, Promotion> promotions = new HashMap<>();

    public static void loadPromotions(String filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            reader.readLine(); // 헤더 스킵
            reader.lines().forEach(PromotionManager::addPromotion);
        }
    }

    public static Promotion getPromotionByName(String name) {
        return promotions.get(name);
    }

    public static Map<String, Promotion> getAllPromotions() {
        return promotions; // 모든 프로모션 반환
    }

    private static void addPromotion(String line) {
        String[] parts = line.split(",");
        String productName = parts[0].trim(); // 상품 이름
        int buyQuantity = Integer.parseInt(parts[1].trim());
        int getQuantity = Integer.parseInt(parts[2].trim());
        String startDate = parts[3].trim();
        String endDate = parts[4].trim();

        promotions.put(productName, new Promotion(productName, buyQuantity, getQuantity, startDate, endDate));
    }
}

