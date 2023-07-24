package shop.mtcoding.mallrequeststudy1.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;



@NoArgsConstructor
@Setter
public class ProductlDTO {

    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private String des;
    private Timestamp createdAt;

    @Builder
    public ProductlDTO(Integer id, String name, Integer price, Integer qty, String des) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.des = des;
        this.createdAt = createdAt;
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

    public String getDes() {
        return des;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
