package com.example.calories.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HighestNutrientsResponse {
    private final MealNutrientResponseType highestProtein;
    private final MealNutrientResponseType highestCarbs;
    private final MealNutrientResponseType highestFat;
}
