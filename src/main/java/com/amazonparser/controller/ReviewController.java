package com.amazonparser.controller;

import com.amazonparser.domain.Review;
import com.amazonparser.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
//    @GetMapping("{asin}")
//    public List<Review> listReviews(@PathVariable("asin") String asin){
//        return reviewService.getAllReviewsByAsin(asin);
//    }


//    @GetMapping("{asin}")
//    @GetMapping
//    public ModelAndView  parseProductReviews(){
//        List <Review> reviewsList = reviewService.getAllReviewsByAsin("asin");
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("reviewsData", reviewsList);
//        mv.setViewName("index");
//        return mv;

//        model.addAttribute("reviewsData", reviewsList);
//        reviewService.getAllReviewsByAsin(asin);
//        model.addAttribute("frontendData", reviewService.getAllReviewsByAsin("B0988X73FW"));

//            Map<String, Object> model = new HashMap<>();
//            model.put("reviewsData", reviewsList);
//        return new ModelAndView("index.html", model);
//    }

    @PostMapping
    public List<Review> save(@RequestBody List<Review> reviewList){
        return reviewService.saveAll(reviewList);
    }

    @GetMapping("/parseReviews/{asin}")
    public List <Review>  parseProductReviews(@PathVariable("asin")String asin){
        List <Review> reviewsList = reviewService.parseReviewsByAsin(asin);
        //model.addAttribute("reviewsData", reviewsList);
        return reviewsList;
    }
    @GetMapping("/getSavedReviews/{asin}")
    public List <Review>  getSavedReviews(@PathVariable("asin")String asin){
        return reviewService.getSavedReviewsByAsin(asin);
    }
}
