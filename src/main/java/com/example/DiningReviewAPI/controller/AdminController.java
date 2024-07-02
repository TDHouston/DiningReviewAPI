package com.example.DiningReviewAPI.controller;

import com.example.DiningReviewAPI.model.AdminReviewAction;
import com.example.DiningReviewAPI.model.DiningReview;
import com.example.DiningReviewAPI.model.DiningReviewStatus;
import com.example.DiningReviewAPI.model.Restaurant;
import com.example.DiningReviewAPI.repository.DiningReviewRepository;
import com.example.DiningReviewAPI.repository.RestaurantRepository;
import com.example.DiningReviewAPI.repository.UserRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final DiningReviewRepository diningReviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;


    public AdminController(DiningReviewRepository diningReviewRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/reviews")
    public List<DiningReview> getReviewsByStatus() {
       return this.diningReviewRepository.findByStatus(DiningReviewStatus.PENDING);

    }

    @PutMapping("/reviews/{id}")
    public void reviewAction(@PathVariable Long id, AdminReviewAction adminReviewAction) {
        Optional<DiningReview> optionalDiningReview = this.diningReviewRepository.findById(id);

        if (optionalDiningReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        DiningReview review = optionalDiningReview.get();

        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (adminReviewAction.getAcceptReview()) {
            review.setStatus(DiningReviewStatus.ACCEPTED);
        } else {
            review.setStatus(DiningReviewStatus.REJECTED);
        }

        this.diningReviewRepository.save(review);


    }

}
