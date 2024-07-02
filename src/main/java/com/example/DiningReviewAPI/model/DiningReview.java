package com.example.DiningReviewAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiningReview {
    @Id
    @GeneratedValue
    Long id;

    private Long restaurantId;
    private Integer overallScore;
    private Integer peanutScore;
    private Integer dairyScore;
    private Integer eggScore;
    private String commentary;
    private String submittedBy;

    @Enumerated(EnumType.STRING)
    private DiningReviewStatus status;



}
