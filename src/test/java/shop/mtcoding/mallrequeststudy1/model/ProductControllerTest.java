package shop.mtcoding.mallrequeststudy1.model;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

// @Test 하는 방법
// 1. h2 Database를 다운받고, 가짜 DB를 생성한다.(Test 용으로, 단위 테스트가 가능하다.)
// 2. 테스트 하고 싶은 메소드가 있는 클래스를 @Autowired와 Import해준다.
// 3. 테스트 클래스_test 라는 이름으로 테스트 한다.
// 4. 테스트 시, given -> when -> then 순으로 테스트를 진행한다.
// * given : 테스트를 하기 위해서 필요한 데이터를 만든다. - h2
// * when : 테스트 진행
// * then : 테스트 결과 확인
// 5. 테스트하는 메소드의 진행이 끝날 때 마다, 자동 롤백이 진행된다.


@Import({
        ProductRepository.class, SellerRepository.class
}) // Import할 클래스
@DataJpaTest // 톰캣 -> DS -> Controller -> [ Repository -> DB ]
public class ProductControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private EntityManager em;


    @Test
    public void findByIdWithSeller_test(){
        // given
        sellerRepository.insert("홍길동", "ssar@naver.com");
        productRepository.insertWithFk(Product.builder().name("딸기").price(1000).qty(5).seller(Seller.builder().id(1).build()).build());
            productRepository.insertWithFk(Product.builder().name("복숭아").price(1000).qty(5).seller(Seller.builder().id(1).build()).build());

        // when
        List<Product> productList = productRepository.findByIdJoinSeller(1);

        // then
        for (Product product:productList
             ) {
            System.out.println(product.getName());
        }
    }


    @Test
    public void findAll_test(){
        // given
        productRepository.insert("파운데이션", 10000, 10);
        productRepository.insert("마스카라", 10000, 10);

        // when
        List<Product> mallList = productRepository.findAll();

        // then
        for (Product mall:mallList ) {
            System.out.println("name : " + mall.getName());
            System.out.println("price : " + mall.getPrice());
            System.out.println("qty : " + mall.getQty());
        }

    }
}
