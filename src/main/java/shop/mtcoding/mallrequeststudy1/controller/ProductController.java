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
public class ProductController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    // 기본 홈페이지(findAll)
    @GetMapping("/")
    public String home(HttpServletRequest request) {
        List<Product> productList = productRepository.findAll();
        // 내가 출력 확인 할 것.
        for (Product product : productList) {
            System.out.println(product.getName());
            System.out.println(product.getPrice());
        }
        // select - view와 연결
        request.setAttribute("productList", productList);

        return "home";
    }

    // 상품등록 페이지
    @GetMapping("/product")
    public String productInsertPage() {
        return "product";
    }

    // ★★★ 상품등록 기능
    @PostMapping("/product/insert")
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
        // ★ 근데 만약 상태가 null값이라면, Repository에 기능 메소드에서 오류가 터지면 null로 반환되도록 하여 if-else를 써야한다.
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
        // /product/update와 연결이 되어 있기 때문에, key값을 입력만 해도 매개변수로 값을 가지고 올 수 있다.
        productRepository.update(name, price, qty, id);

        return "redirect:/";
    }

    // 판매자별 상품 조회 페이지 - 판매자 이름을 눌리면, 판매하는 상품을 알 수 있다.
    @GetMapping("seller/product/{id}")
    public String sellerToProductPage(@PathVariable Integer id, HttpServletRequest request){
        List<Product> sellerToProductList = productRepository.findByIdJoinSeller(id);
        request.setAttribute("sellerToProductList", sellerToProductList);
        return "product-to-seller";
    }
}
