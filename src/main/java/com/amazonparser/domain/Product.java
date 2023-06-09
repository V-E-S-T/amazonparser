package com.amazonparser.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"asin"}))
@ToString(of = {"product_id", "asin", "title"})
@EqualsAndHashCode(of = {"product_id", "asin"})
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long product_id;
    private String asin;
    private String reference;
    private String price;
    @Column(columnDefinition = "text")

    private String description;

    @Column(columnDefinition = "text")
    private String title;
    private String rating;
    private String ratingCount;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<com.amazonparser.domain.Review> reviews = new HashSet<>();

    //TODO implement 'totalRatings' and 'totalReviews' parsing
    private Integer totalRatings;
    private Integer totalReviews;

    public Set<com.amazonparser.domain.Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<com.amazonparser.domain.Review> reviews) {
        this.reviews = reviews;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String productRating) {
        this.rating = productRating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String productRatingCount) {
        this.ratingCount = productRatingCount;
    }

    public Product(String asin, String reference, Long product_id) {
        this.asin = asin;
        this.reference = reference;
        this.product_id = product_id;
    }

    public Product() {

    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long id) {
        this.product_id = id;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
