package com.example.calories.model;

import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Food {

    private Integer id;
    private String userId;
    private String name;
    private BigDecimal price;
    private Integer calories;
    private String userWeight;
    private Integer weight;
    private String age;
    private BigDecimal fat;
    private BigDecimal carbs;
    private BigDecimal protein;
    private LocalTime timeConsumed;
    private LocalDate dateConsumed;
    private String type;
    private String favorite;
    private String procedence;

}
