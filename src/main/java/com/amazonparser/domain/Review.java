package com.amazonparser.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"amazonReviewId"}))
@ToString(of = {"review_id", "productAsin", "sentiment", "rating"})
@EqualsAndHashCode(of = {"review_id", "productAsin"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private Long review_id;
    private String productAsin;
    private String sentiment;
    @Column(name = "review", columnDefinition = "text")
    private String review;
    private Integer rating;
    private String totalRatingsAndReviews;
    private String amazonReviewId;
    private String reviewCountry;
    private LocalDate reviewDate;
    private String reviewTitle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getAmazonReviewId() {
        return amazonReviewId;
    }

    public void setAmazonReviewId(String amazonReviewId) {
        this.amazonReviewId = amazonReviewId;
    }

    public String getReviewCountry() {
        return reviewCountry;
    }

    public void setReviewCountry(String reviewCountry) {
        this.reviewCountry = reviewCountry;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Review() {
    }

    public String getTotalRatingsAndReviews() {
        return totalRatingsAndReviews;
    }

    public void setTotalRatingsAndReviews(String totalRatingsAndReviews) {
        this.totalRatingsAndReviews = totalRatingsAndReviews;
    }

    public Long getReview_id() {
        return review_id;
    }

    public void setReview_id(Long id) {
        this.review_id = id;
    }

    public String getProductAsin() {
        return productAsin;
    }

    public void setProductAsin(String productAsin) {
        this.productAsin = productAsin;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
