package com.reborn.golf.repository;

import com.reborn.golf.entity.Notice;
import com.reborn.golf.entity.NoticeFractionation;
import com.reborn.golf.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Long> {

    @Query("SELECT p, pi, " +
            "AVG(COALESCE(r.grade,0)),  " +
            "COUNT(DISTINCT r) " +
            "FROM Product p " +
            "LEFT OUTER JOIN ProductImage pi ON pi.product = p " +
            "LEFT OUTER JOIN ProductReply r ON r.product = p " +
            "GROUP BY p ")
    Page<Object[]> getListPage(Pageable pageable);


    @Query("SELECT p, pi," +
            "AVG(COALESCE(r.grade,0)),  " +
            "COUNT(r)" +
            "FROM Product p " +
            "LEFT OUTER JOIN ProductImage pi ON pi.product = p " +
            "LEFT OUTER JOIN ProductReply  r ON r.product = p " +
            "WHERE p.pno = :pno " +
            "GROUP BY pi")
    List<Object[]> getProductWithAll(Long pno);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.pno = :pno " +
            "AND p.removed = false ")
    Optional<Product> getProductByPno(Long pno);

}
