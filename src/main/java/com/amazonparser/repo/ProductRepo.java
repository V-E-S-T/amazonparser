package com.amazonparser.repo;

import com.amazonparser.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByAsin(String asin);

    @Transactional
    Integer deleteProductByAsin(String asin);
}
