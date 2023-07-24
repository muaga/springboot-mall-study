package shop.mtcoding.mallrequeststudy1.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SellerRepository {

    // DB 통신 연결, 싱글톤
    @Autowired
    private EntityManager em;


    // seller 등록하기
    @Transactional
    public String insert(String name, String email){
        Query query = em.createNativeQuery("insert into seller_tb(name, email) values(:name, :email)");
        query.setParameter("name", name);
        query.setParameter("email", email);
        query.executeUpdate();
        return "redirect:/";
    }

    // UPDATE
    @Transactional
    public void update(String name, String email, Integer id){
        Query query = em.createNativeQuery("update seller_tb set name = :name, email = :email where id = :id");
        query.setParameter("name", name);
        query.setParameter("email", email);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // DELETE
    @Transactional
    public void delete(Integer id){
        Query query = em.createNativeQuery("delete from seller_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // FindAll
    public List<Seller> findAll(){
        Query query = em.createNativeQuery("select * from seller_tb", Seller.class);
        List<Seller> sellerList = query.getResultList();
        // getResultList() : select 한 데이터를 mallList에 바인딩한다.
        return sellerList;
    }

    // FindById
    public Seller findById(Integer id){
        Query query = em.createNativeQuery("select * from seller_tb where id = :id", Seller.class);
        query.setParameter("id", id);
        Seller seller = (Seller) query.getSingleResult();
        return seller;
    }

    // FindByEmail
    public Seller findByEmail(String email){
        try{
            Query query = em.createNativeQuery("select * from seller_tb where email = :email", Seller.class);
            query.setParameter("email", email);
            Seller seller = (Seller) query.getSingleResult();
            return seller;
        }catch (Exception e){
            return null;
        }
    }

    // seller별 product 보기 - join
    public Seller findByIdJoinProduct(int id) {
        Query query = em.createNativeQuery("select * \n" +
                "from product_tb as p \n" +
                "inner join seller_tb as s \n" +
                "on p.seller_id = s.id \n" +
                "where s.id = :id", Seller.class);
        query.setParameter("id", id);
        Seller seller = (Seller) query.getSingleResult();
        return seller;
    }

}
