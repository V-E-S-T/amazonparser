package com.amazonparser.repo;

import com.amazonparser.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    boolean existsByAmazonReviewId(String amazonReviewId);
    List<Review> findAllByProductAsin(String asin);

    @Transactional
    Integer deleteReviewsByProductAsin(String asin);

}
