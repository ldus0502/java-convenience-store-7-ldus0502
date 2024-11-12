package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import store.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "-콜라1,000원10개탄산2+1",
                    "-콜라1,000원10개",
                    "-사이다1,000원8개탄산2+1",
                    "-사이다1,000원7개",
                    "-오렌지주스1,800원9개MD추천상품",
                    "-오렌지주스1,800원재고없음",
                    "-탄산수1,200원5개탄산2+1",
                    "-탄산수1,200원재고없음",
                    "-물500원10개",
                    "-비타민워터1,500원6개",
                    "-감자칩1,500원5개반짝할인",
                    "-감자칩1,500원5개",
                    "-초코바1,200원5개MD추천상품",
                    "-초코바1,200원5개",
                    "-에너지바2,000원5개",
                    "-정식도시락6,400원8개",
                    "-컵라면1,700원1개MD추천상품",
                    "-컵라면1,700원10개"
            );
        });
    }

    @Test
    void productsFileLoadTest() {
        ProductRepository repository = new ProductRepository();
        String filePath = "src/main/resources/products.md";

        List<Product> productList = repository.loadProductsFromFile(filePath);
        assertThat(productList).isNotEmpty();

        Map<String, Product> products = productList.stream()
                .collect(Collectors.toMap(Product::getName, product -> product, (p1, p2) -> p1));

        products.forEach((key, product) -> System.out.println(product));
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 멤버십_할인_적용() {
        assertSimpleTest(() -> {
            run("[정식도시락-2]", "Y", "N"); // 멤버십 할인이 적용됨
            assertThat(output().replaceAll("\\s", "")).contains("내실돈8,960"); // 멤버십 할인 10% 가정
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 프로모션_적용_상품_정상_구매() {
        assertSimpleTest(() -> {
            run("[탄산수-3]", "N", "N");
            String actualOutput = output();
            System.out.println("Actual Output: [" + actualOutput + "]"); // 디버깅용 출력
            assertThat(actualOutput.replaceAll("\\s+", "")).contains("내실돈2,400원");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
