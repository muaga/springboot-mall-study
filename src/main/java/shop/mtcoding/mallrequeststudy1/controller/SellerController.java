package shop.mtcoding.mallrequeststudy1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.mallrequeststudy1.model.Product;
import shop.mtcoding.mallrequeststudy1.model.Seller;
import shop.mtcoding.mallrequeststudy1.model.SellerRepository;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Controller
public class SellerController {

    @Autowired
    private SellerRepository sellerRepository;


    // 판매자목록 페이지
    @GetMapping("/seller/list")
    public String sellerListPage(HttpServletRequest request) {
        List<Seller> sellerList = sellerRepository.findAll();
        for (Seller seller : sellerList) {
            System.out.println(seller.getId());
            System.out.println(seller.getName());
            System.out.println(seller.getEmail());
        }
        request.setAttribute("sellerList", sellerList);
        return "seller-list";
    }

    // 판매자등록 페이지
    @GetMapping("/seller")
    public String sellerPage() {
        return "seller";
    }

    // 판매자등록 기능
    @PostMapping("/seller/insert")
    public String sellerInsert(String name, String email) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null){
            sellerRepository.insert(name, email);
        return "redirect:/seller/list";} else {
            return "error-exist";
        }
//        try {
//            Seller seller = sellerRepository.findByEmail(email);
//            if (seller == null)
//                sellerRepository.insert(name, email);
//            return "redirect:/seller/list";
//        } catch (Exception e) {
//            // 이메일에 해당하는 판매자가 없을 경우 에러 메시지 반환 또는 새로운 판매자 정보를 삽입하고 리다이렉트
//            return "error-exist";
//        }
    }
    // ★★★ 정리!!!!!!!!!!!!


    // 판매자상세 조회 - sellerPage 상품명 하이퍼링크로 클릭 -> 상세 조회
    @GetMapping("/seller/{id}")
    public String detailPage(@PathVariable Integer id, HttpServletRequest request) {
        Seller seller = sellerRepository.findById(id);
        System.out.println(seller.getId());
        System.out.println(seller.getName());
        System.out.println(seller.getEmail());
        request.setAttribute("seller", seller);
        return "seller-detail";
    }

    // 판매자삭제 기능 - detailPage에서 판매자 삭제 클릭
    @PostMapping("/seller/delete")
    public String delete(Integer id) {
        sellerRepository.delete(id);
        return "redirect:/seller/list";
    }

    // 판매자수정 기능 - datailPage에서 판매자 수정 클릭
    @Transactional
    @PostMapping("/seller/update")
    public String update(String name, String email, Integer id) {
        sellerRepository.update(name, email, id);
        return "redirect:/seller/list";
    }


}
