package store.controller;

import store.exception.OutOfStockException;
import store.exception.PromotionNotAvailableException;
import store.model.Order;
import store.model.Receipt;
import store.model.StockManager;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StockManager stockManager;

    public OrderController(InputView inputView, OutputView outputView, StockManager stockManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.stockManager = stockManager;
    }

    public void processOrder() {
        try {
            executeOrderProcess();
        } catch (OutOfStockException | PromotionNotAvailableException e) {
            outputView.printErrorMessage(e.getMessage());
            processOrder();
        } catch (Exception e) {
            outputView.printErrorMessage("[ERROR] 알 수 없는 오류가 발생했습니다.");
        }
    }

    private void executeOrderProcess() {
        displayProducts();
        String orderInput = getOrderInput();
        Order order = createOrder(orderInput);
        boolean applyMembership = confirmMembershipDiscount();
        Receipt receipt = createReceipt(order, applyMembership);
        displayReceipt(receipt);
    }

    private void displayProducts() {
        outputView.printProducts(stockManager.getAllProducts());
    }

    private String getOrderInput() {
        return inputView.readItem();
    }

    private Order createOrder(String orderInput) {
        return new Order(stockManager, orderInput);
    }

    private boolean confirmMembershipDiscount() {
        return inputView.confirmMembershipDiscount();
    }

    private Receipt createReceipt(Order order, boolean applyMembership) {
        return order.createReceipt(applyMembership);
    }

    private void displayReceipt(Receipt receipt) {
        outputView.printReceipt(receipt);
    }
}
