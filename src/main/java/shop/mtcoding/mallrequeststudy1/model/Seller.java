package shop.mtcoding.mallrequeststudy1.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Table(name = "seller_tb")
@Entity
public class Seller {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "seller") // mappedBy로 연관관계 주인을 지정
    private List<Product> product;
    // 1:N관계로써, product가 많은 행을 가질 수 있으므로 List로 받는다.

    @Builder
    public Seller(Integer id, String name, String email, List<Product> product) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.product = product;
    }

    public Seller() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Product> getProduct() {
        return product;
    }
}
