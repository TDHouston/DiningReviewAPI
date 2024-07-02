package com.example.DiningReviewAPI.controller;

import com.example.DiningReviewAPI.model.DiningReview;
import com.example.DiningReviewAPI.model.DiningReviewStatus;
import com.example.DiningReviewAPI.model.Restaurant;
import com.example.DiningReviewAPI.model.User;
import com.example.DiningReviewAPI.repository.DiningReviewRepository;
import com.example.DiningReviewAPI.repository.RestaurantRepository;
import com.example.DiningReviewAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final DiningReviewRepository diningReviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ReviewController(DiningReviewRepository diningReviewRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @PostMapping()
    public void addReview(@RequestBody DiningReview diningReview) {
        validateUserReview(diningReview);

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(diningReview.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        diningReview.setStatus(DiningReviewStatus.PENDING);

        this.diningReviewRepository.save(diningReview);
    }


    private void validateUserReview(DiningReview diningReview) {
        if (ObjectUtils.isEmpty(diningReview.getSubmittedBy())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (ObjectUtils.isEmpty(diningReview.getRestaurantId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (ObjectUtils.isEmpty(diningReview.getEggScore()) && ObjectUtils.isEmpty(diningReview.getPeanutScore()) && ObjectUtils.isEmpty(diningReview.getDairyScore())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findUserByName(diningReview.getSubmittedBy());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }





}
