package shop.mtcoding.mallrequeststudy1.model;


import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public class ProductRepository {

    // DB 통신 연결, 싱글톤
    @Autowired
    private EntityManager em;

    // INSERT
    @Transactional
    public void insert(String name, Integer price, Integer qty){
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty, created_at) values (:name, :price, :qty, CURRENT_TIMESTAMP(0))");
        // CURRENT_TIMESTAMP(0)은 데이터베이스에서 사용되는 함수로, 현재 시간(날짜와 시간)을 반환하는 기능을 가지고 있습니다. 숫자 0은 옵션으로, 소수점 이하의 초를 표시하지 않음을 나타냅니다. 즉, CURRENT_TIMESTAMP(0)은 현재 시간을 초 이하의 소수점 없이 정수로 표현한 값을 반환합니다.
        //예를 들어, CURRENT_TIMESTAMP(0)을 실행하면 다음과 같은 결과를 얻을 수 있습니다:
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();

        // Timestamp쓸 때, 주의할 점
        // 1. 쿼리 바인딩은 받지 않으므로, :는 쓰지 않는다.
        // 2. 실제 DB 속 테이블에 있는 created_at의 타입과 스프링에서 사용하는 타입과 일치하는 편이 좋다(DB - timestamp, datatime 둘 다 호환 가능)
    }

    // UPDATE
    @Transactional
    public void update(String name, Integer price, Integer qty, Integer id){
        Query query = em.createNativeQuery("update product_tb set name = :name, price = :price, qty = :qty where id = :id");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // DELETE
    @Transactional
    public void delete(Integer id){
        Query query = em.createNativeQuery("delete from product_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // FindAll
    public List<Product> findAll(){
        Query query = em.createNativeQuery("select * from product_tb", Product.class);
        List<Product> productList = query.getResultList();
        // getResultList() : select 한 데이터를 mallList에 바인딩한다.
        return productList;
    }

    // FindById
    public Product findById(Integer id){
        Query query = em.createNativeQuery("select * from product_tb where id = :id", Product.class);
        // model인 Product.class에 바로 바인딩한다. Entity에 등록되어 있어야 가능하다.
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        // getSingleResult(): 1행 값만 받는다.
        return product;
    }

    // ▣ 아직
    // FindByName -> 같은 상품 중복 제외할 때, 같은 판매자에서 같은 상품을 추가 등록할 수 없고 update로 해야한다.
    public Product findByName(String name){
        Query query = em.createNativeQuery("select * from product_tb where name = :name", Product.class);
        query.setParameter("name", name);
        Product product = (Product) query.getSingleResult();
        return product;
        // model의 데이터로 피벗, 연산작업으로 새로운 데이터저장소가 필요하나 DB에 저장하지 않을 데이터 매핑하기(기존데이터가 존재하므로)
        // 직접 오브젝트 매핑을 해야한다. - QLRM -> 클래스명.class(X)
    }

    public ProductlDTO findIdDTO(Integer id){
        Query query = em.createNativeQuery("select id, name, price, qty, '설명'as des from product_tb where id = :id");
        query.setParameter("id", id);

        JpaResultMapper mapper = new JpaResultMapper();
        // JpaResultMapper() : DB결과를 DTO로 변환하기 위해 사용된다.
        ProductlDTO productlDTO = mapper.uniqueResult(query, ProductlDTO.class);
        // uniqueResult()를 사용하여 DB결과를 DTO로 변환한다.
        // uniqueResult() : id, 즉 단일 상품만을 조회해서 단일 결과가 나오는 경우 사용한다.
        // query : DB로 부터 조회할 쿼리
        // MallDTO.class : 조회된 결과를 매핑할 DTO클래스
        return productlDTO;
        // DB의 mall 테이블에는 '설명' column이 없지만, 이를 임시로 상수 값으로 지정하여
        // mallDTO에 담아서 반환하도록 한 것이다.
    }

    // INSERT -> seller_id 추가 된 버전
    @Transactional
    public void insertWithFk(Product product){
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty, seller_id, created_at) values (:name, :price, :qty, :sellerId, CURRENT_TIMESTAMP(0))");
        query.setParameter("name", product.getName());
        query.setParameter("price", product.getPrice());
        query.setParameter("qty", product.getQty());
        query.setParameter("sellerId", product.getSeller().getId());
        query.executeUpdate();
    }

    // seller와 join 하기
    public List<Product> findByIdJoinSeller(Integer id) {
        Query query = em.createNativeQuery("select * \n" +
                "from product_tb as p \n" +
                "inner join seller_tb as s \n" +
                "on p.seller_id = s.id \n" +
                "where s.id = :id", Product.class);
        query.setParameter("id", id);
        List<Product> product = (List<Product>) query.getResultList();
        return product;
        // 복수의 행이 출력될 때는 getResultList이다.
    }






}
