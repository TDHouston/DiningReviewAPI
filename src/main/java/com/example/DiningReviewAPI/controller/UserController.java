package com.example.DiningReviewAPI.controller;

import com.example.DiningReviewAPI.model.User;
import com.example.DiningReviewAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{name}")
    public Optional<User> getUser(@PathVariable String name) {
        return this.userRepository.findUserByName(name);
    }

    @PostMapping()
    public void createUser(@RequestBody User user) {
        validateUser(user.getName());
        this.userRepository.save(user);
    }

    @PutMapping("/{name}")
    public void updateUser(@PathVariable String name, @RequestBody User updatedUser) {

        Optional<User> optionalUser = this.userRepository.findUserByName(name);
         if (optionalUser.isEmpty()) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND);
         }

         User existingUser = optionalUser.get();

         if (!ObjectUtils.isEmpty(updatedUser.getCity())) {
             existingUser.setCity(updatedUser.getCity());
         }
         if (!ObjectUtils.isEmpty(updatedUser.getState())) {
            existingUser.setState(updatedUser.getState());
         }
         if (!ObjectUtils.isEmpty(updatedUser.getZipCode())) {
            existingUser.setZipCode(updatedUser.getZipCode());
         }
         if (!ObjectUtils.isEmpty(updatedUser.getPeanutInterest())) {
            existingUser.setPeanutInterest(updatedUser.getPeanutInterest());
         }
         if (!ObjectUtils.isEmpty(updatedUser.getDairyInterest())) {
            existingUser.setDairyInterest(updatedUser.getDairyInterest());
         }
         if (!ObjectUtils.isEmpty(updatedUser.getEggInterest())) {
            existingUser.setEggInterest(updatedUser.getEggInterest());
         }

         this.userRepository.save(existingUser);
    }

    private void validateUser(String name) {
        if (ObjectUtils.isEmpty(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (this.userRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }



}


