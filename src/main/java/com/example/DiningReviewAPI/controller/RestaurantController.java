package com.example.DiningReviewAPI.controller;

import com.example.DiningReviewAPI.model.Restaurant;
import com.example.DiningReviewAPI.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("/restaurant")
@RestController
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping()
    public void createRestaurant(@RequestBody Restaurant restaurant) {
        validateRestaurant(restaurant);

        this.restaurantRepository.save(restaurant);
    }

    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurant(@PathVariable Long id) {
        return this.restaurantRepository.findById(id);
    }

    @GetMapping("/search")
    public Iterable<Restaurant> searchRestaurants(@RequestParam String zipcode, @RequestParam String allergy) {
        Iterable<Restaurant> restaurants = this.restaurantRepository.findAll();

        if (allergy.equalsIgnoreCase("peanut")) {
            restaurants = this.restaurantRepository.findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScore(zipcode);
        } else if (allergy.equalsIgnoreCase("dairy")) {
            restaurants = this.restaurantRepository.findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScore(zipcode);
        } else if (allergy.equalsIgnoreCase("egg")) {
            restaurants = this.restaurantRepository.findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScore(zipcode);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return restaurants;
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (ObjectUtils.isEmpty(restaurant.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (ObjectUtils.isEmpty(restaurant.getZipCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
        if (optionalRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }










}

