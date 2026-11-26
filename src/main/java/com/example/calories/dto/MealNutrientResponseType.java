package com.example.calories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public class MealNutrientResponseType {

    private final String mealType;
    private final BigDecimal totalProtein;
    private final BigDecimal totalCarbs;
    private final BigDecimal totalFat;
}
