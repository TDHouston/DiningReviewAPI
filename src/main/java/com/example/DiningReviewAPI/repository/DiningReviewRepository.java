package com.example.DiningReviewAPI.repository;

import com.example.DiningReviewAPI.model.DiningReview;
import com.example.DiningReviewAPI.model.DiningReviewStatus;
import com.example.DiningReviewAPI.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
    List<DiningReview> findByStatus(DiningReviewStatus status);
    List<DiningReview> findByRestaurantIdAndStatus(Restaurant restaurant, DiningReviewStatus status);
}
