package shop.mtcoding.mallrequeststudy1.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Setter
@Table(name = "product_tb")
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "seller_id", foreignKey = @ForeignKey(name = "fk_product_seller"))
    // 양쪽에 ManyToOne, OneToMany가 있는 경우, fk를 지정해주지 않으면 create 시 외래키 자동 생성으로 오류가 발생할 수 있다.
    private Seller seller;

    //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY : 자동증가기능
    // AUTO : 주요 키 자동증가를 명시적으로 선택하고 싶지 않을 때

    // @ManyToOne : n:1의 관계의 어노테이션
    // seller 1명에 다양한 물건을 등록할 수 있기 때문에, seller가 1이라서
    // @ManyToOne이다.
    // seller의 id와 PK, FK이기 때문에 mall 테이블에 자동으로 seller_id가 생긴다.
    // Seller를 오브젝트로 받은 이유 : 원래 Join시 DB로 부터 전달받는 데이터를 DTO에 받았었는데
    // PK-FK로 이뤄진 Seller 오브젝트 자체를 mall에 넣음으로써, DTO가 아닌 mall에 받으면,
    // mall의 데이터는 mall의 model로, seller의 데이터는 mall속의 seller 객체가 받는데, 또
    // seller 객체는 seller의 model과 연결되어 저장이 된다.
    // mall의 model만으로도 두 개의 테이블 데이터를 받을 수 있게 되었다.
    // 이것이 ORM이다.

    @Builder
    public Product(Integer id, String name, Integer price, Integer qty, Seller seller, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.seller = seller;
        this.createdAt = createdAt;
    }

    public Product() {

    }


    public Seller getSeller() {
        return seller;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQty() {
        return qty;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
