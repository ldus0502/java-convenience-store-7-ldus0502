package store;

import store.controller.OrderController;
import store.model.PromotionManager;
import store.model.StockManager;
import store.view.InputView;
import store.view.OutputView;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            PromotionManager.loadPromotions("src/main/resources/promotions.md");
            StockManager stockManager = new StockManager();
            stockManager.loadProducts("src/main/resources/products.md");

            InputView inputView = new InputView();
            OutputView outputView = new OutputView();
            OrderController controller = new OrderController(inputView, outputView, stockManager);

            controller.processOrder();
        } catch (IOException e) {
            System.out.println("[ERROR] 파일을 읽는 중 오류가 발생했습니다.");
        }
    }
}
