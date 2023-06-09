package com.amazonparser.service;

import com.amazonparser.Util.ParserUtil;
import com.amazonparser.domain.Product;
import com.amazonparser.domain.Review;
import com.amazonparser.repo.ProductRepo;
import com.amazonparser.repo.ReviewRepo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final ProductRepo productRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo, ProductRepo productRepo) {
        this.reviewRepo = reviewRepo;
        this.productRepo = productRepo;
    }
    public List<Review> saveAll(List<Review> reviewList) {
        if (reviewList.size()==0){
            return Collections.emptyList();
        }
        Product product = productRepo.findByAsin(reviewList.get(0).getProductAsin());
        List <Review> filteredReviewList = reviewList.stream()
                .filter(review -> !reviewRepo.existsByAmazonReviewId(review.getAmazonReviewId()))
                .peek(review -> review.setProduct(product))
                .collect(Collectors.toList());
        return reviewRepo.saveAll(filteredReviewList);
    }
    public List<Review> parseReviewsFromPage(String pageReference){
        Document doc = ParserUtil.getHTMLDocument(pageReference);

//        Optional.ofNullable(doc.selectFirst("div[data-hook=\"reviews-medley-footer\"] a")).ifPresent(
//                element -> seeAllReviewsReference.append("https://www.amazon.com").append(element.attr("href")));
//        if (!seeAllReviewsReference.toString().equals("")){
        //doc = ParserUtil.getHTMLDocument(seeAllReviewsReference.toString());

//        doc.select("div#reviews-sort-order-options > select option[value=recent]");

        Elements reviewsElements = doc.select("div[data-hook=review]");

        return reviewsElements.stream().map(this::extractReviewFromElement).collect(Collectors.toList());

    }

    private Review extractReviewFromElement(Element reviewElement){
        Review review = new Review();
//        String productAsin;
        review.setAmazonReviewId(reviewElement.id());
        Optional.ofNullable(reviewElement.selectFirst("span[data-hook='review-date']")).ifPresent(
                element -> {
                    review.setReviewDate(extractReviewDate(element.text()));
                    review.setReviewCountry(extractReviewCountry(element.text()));
                });
        review.setReviewTitle(Optional.ofNullable(reviewElement.selectFirst("a[data-hook='review-title'] span"))
                .map(element -> element.text())
                .orElse(""));
//        productAsin = Optional.ofNullable(reviewElement.selectFirst("a[data-hook='review-title']"))
//                .map(element -> element.attr("href").split("(.*)ASIN=")[1])
//                .orElse("");
        review.setProductAsin(Optional.ofNullable(reviewElement.selectFirst("a[data-hook='review-title']"))
                .map(element -> element.attr("href").split("(.*)ASIN=")[1])
                .orElse(""));
//        review.setProduct(productRepo.findByAsin(productAsin));
        Optional.ofNullable(reviewElement.selectFirst("div[class='a-row'] a[class='a-link-normal']")).ifPresent(
                element ->{
                    review.setRating(Integer.parseInt(element.attr("title").split("\\.")[0]));
                    review.setSentiment(extractSentiment(review.getRating()));
                });
        review.setReview(Optional.ofNullable(reviewElement.selectFirst("span[data-hook='review-body']"))
                .map(element -> element.text())
                .orElse(""));
        return review;
    }

    private LocalDate extractReviewDate(String reviewDateAndCountry){
        String [] st = reviewDateAndCountry.split(" on ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        return LocalDate.parse(st[1], formatter);
    }

    private String extractReviewCountry(String reviewDateAndCountry){
        Pattern regex = Pattern.compile("((?<= in )(.*)(?= on ))");
        Matcher matcher = regex.matcher(reviewDateAndCountry);
        return matcher.find()
                ? matcher.group(0).replace("the ", "")
                : "";
    }

    private String extractSentiment(Integer rating){
        if (rating > 3){
            return "positive";
        } else if (rating < 3){
            return "negative";
        }
        return "neutral";
    }

    private String extractProductReviewsPageReference(String asin){
        return productRepo.findByAsin(asin).getReference()
                .replaceFirst("/dp/", "/product-reviews/")
                + "&reviewerType=all_reviews&pageNumber=";

    }

    public List<Review> parseReviewsByAsin(String asin) {
        int counter = 1;
        List<Review> filteredReviews;
        List<Review> allFilteredReviews = new ArrayList<>();
        String productReviewsPageReference = extractProductReviewsPageReference(asin);
        LocalDate monthAgoLocalDate = LocalDate.now().minusMonths(1L);
        do{
            filteredReviews = parseReviewsFromPage(productReviewsPageReference + counter).stream()
                    .filter(review -> review.getReviewDate().isAfter(monthAgoLocalDate))
                    .collect(Collectors.toList());
            allFilteredReviews.addAll(filteredReviews);
            counter++;
        }while(filteredReviews.size() > 0);
        return allFilteredReviews;
    }

    public List<Review> getSavedReviewsByAsin(String asin){
        return reviewRepo.findAllByProductAsin(asin);
    }
}
