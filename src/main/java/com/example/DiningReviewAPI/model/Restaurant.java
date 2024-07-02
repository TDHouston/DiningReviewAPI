package com.example.DiningReviewAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private String website;

    private Integer overallScore;
    private Integer peanutScore;
    private Integer dairyScore;
    private Integer eggScore;
}
