package shop.mtcoding.mallrequeststudy1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.mallrequeststudy1.model.Product;
import shop.mtcoding.mallrequeststudy1.model.ProductRepository;
import shop.mtcoding.mallrequeststudy1.model.Seller;
import shop.mtcoding.mallrequeststudy1.model.SellerRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Timestamp;
import java.util.List;

@Controller
// @Controller : 리턴되는 파일명과 일치한 파일명을 찾는다. = 즉, 같은 엔드포인트를 찾는다.
// 클라이언트와 상호작용을 하는 곳이며, Repository 속 메소드, jsp파일을 연결해서 반응을 한다.
public class ProductController {

    @Autowired
    // ProductRepository 클래스를 의존성 주입을 시킨다.

    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    // # GET 요청에 대한 응답으로 view하기
    // view, 브라우저에 보이게 하는 방법은 home.jsp파일을 모두 읽어서 데이터를 return하는 것이다.
    // 메소드 호출은 아니다. 이때 템플릿엔진을 사용해야 한다.
    // 1. 매개변수 request에 데이터를 담는다.
    // 2. home.jsp 파일에 가서 request 데이터를 for-each를 돌린다.

    // 기본 홈페이지(findAll)
    @GetMapping("/")
    // @GetMapping : GET(요청)기능을 가지며, 엔트포인트가 /일 경우, 아래의 메소드가 실행된다.

    public String home(HttpServletRequest request) {
        List<Product> productList = productRepository.findAll();
        // ProductRepository의 findAll메소드를 이용해, 데이터를 productList에 담는다.

        for (Product product : productList) {
            System.out.println(product.getName());
            System.out.println(product.getPrice());
        }
        // select - view와 연결
        request.setAttribute("productList", productList);
        // setAtrribute : HttpServletRequest 객체에 데이터를 저장한다.

        return "home";
        // "home"과 파일명이 일치한 파일을 찾는다.
        // "home"파일의 확장자는, application.yml에서
        // viewResolver에서 jsp가 붙는 파일을 찾는다고 설정해놨다.
    }

    // 상품등록 페이지
    // # '상품등록'을 클릭하면 해당 창이 나오게 하는 응답(하이퍼링크 클릭)
    @GetMapping("/product")
    public String productInsertPage() {
        return "product";
        // return "product"는 @Controller로 리턴되는 파일인 product.jsp를 찾아낸다.

    }

    // ★★★ 상품등록 기능
    @PostMapping("/product/insert")
    // @PostMapping : Post(등록)기능을 가지며, 엔드포인트가 /일 경우, 아래의 메소드가 실행된다.
    public String productInsert(@ModelAttribute Product product, @RequestParam("sellerId") Integer sellerId) {
        try {
            // seller 존재 유무
            Seller seller = sellerRepository.findById(sellerId);
            if (seller != null)
                product.setSeller(seller);
            productRepository.insertWithFk(product);
            return "redirect:/";
        } catch (Exception e) {
            return "error-null";
        }
        // 만약 seller가 존재한다면, 상품등록이 가능하다. 그러나 존재하지 않는다면 존재하지 않는 데이터라고 경고가 나온다.
        // 상품의 중복은 가능하다. 판매자 별로 같은 상품을 판매할 수 있기 때문이다.
        // ★ 그리고, 조건에 맞는 return위치를 잘 파악할 것!
        // ★ 그리고, 상태가 null값이 아니라면, if-else가 아닌 try-catch로 해야한다. null값은 예외이기 때문이다.
        // ★ 근데 만약 상태가 null값이라면, Repository에 기능 메소드에서 오류가 터지면 null로 반환되도록 하여 if-else를
        // 써야한다.
    }

    // 상품상세 조회 페이지 - writePage에서 상품명 하이퍼링크로 클릭 -> 상세 조회
    @GetMapping("/product/{id}")
    public String detailPage(@PathVariable Integer id, HttpServletRequest request) {
        Product product = productRepository.findById(id);
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQty());
        System.out.println(product.getCreatedAt());
        request.setAttribute("product", product);
        return "product-detail";
    }

    // 상품삭제 기능 - detailPage에서 상품 삭제 클릭
    @PostMapping("/product/delete")
    public String delete(Integer id) {
        productRepository.delete(id);

        return "redirect:/";
    }

    // 상품수정 기능 - datailPage에서 상품 수정 클릭
    @PostMapping("/product/update")
    public String update(String name, Integer price, Integer qty, Integer id) {
        productRepository.update(name, price, qty, id);

        return "redirect:/";
    }

    // 판매자별 상품 조회 페이지 - 판매자 이름을 눌리면, 판매하는 상품을 알 수 있다.
    @GetMapping("seller/product/{id}")
    public String sellerToProductPage(@PathVariable Integer id, HttpServletRequest request) {
        List<Product> sellerToProductList = productRepository.findByIdJoinSeller(id);
        request.setAttribute("sellerToProductList", sellerToProductList);
        return "product-to-seller";
    }
}
