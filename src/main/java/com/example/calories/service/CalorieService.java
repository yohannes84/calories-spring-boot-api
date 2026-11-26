package com.example.calories.service;


import com.example.calories.client.CaloriesClient;
import com.example.calories.dto.ExpensiveFoodResponse;
import com.example.calories.dto.HighestNutrientsResponse;
import com.example.calories.dto.MealNutrientResponseType;
import com.example.calories.model.Food;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalorieService {

    private final CaloriesClient caloriesClient;

    public CalorieService(CaloriesClient caloriesClient) {
        this.caloriesClient = caloriesClient;
    }

    public List<ExpensiveFoodResponse> getExpensiveFoods(){

        return caloriesClient.fetchCalories().stream()
                .filter(x -> x.getPrice().compareTo(BigDecimal.valueOf(49.90)) > 0)
                .sorted(Comparator.comparing(Food::getPrice))
                .limit(3)
                .map(food -> new ExpensiveFoodResponse(food.getName(), food.getPrice().setScale(2, RoundingMode.HALF_UP).toPlainString()))
                .toList();

    }

    public List<Food> getUserMeals(){
        LocalDate startDate =  LocalDate.of(2022,12,4);
        LocalDate endDate =  LocalDate.of(2022,12,10);

        return caloriesClient.fetchCalories()
                .stream()
                .filter(user -> user.getUserId().equals("10"))
                .filter(food -> !food.getDateConsumed().isBefore(startDate) && !food.getDateConsumed().isAfter(endDate))
                .toList();
    }

    public Map<String, List<Food>> getMealsGroupedByType() {
        return getUserMeals()
                .stream()
                .collect(Collectors.groupingBy(Food::getType));
    }

    public List<MealNutrientResponseType> getMealNutrientSummaries() {

        Map<String, List<Food>> groupedMeals = getMealsGroupedByType();

        return groupedMeals.entrySet()
                .stream()
                .map(entry -> {

                    BigDecimal totalProtein = entry.getValue()
                            .stream()
                            .map(Food::getProtein)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalCarbs = entry.getValue()
                            .stream()
                            .map(Food::getCarbs)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalFat = entry.getValue()
                            .stream()
                            .map(Food::getFat)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new MealNutrientResponseType(
                            entry.getKey(),
                            totalProtein,
                            totalCarbs,
                            totalFat
                    );
                })
                .toList();
    }

    public MealNutrientResponseType getMealWithHighestProtein() {

        return getMealNutrientSummaries()
                .stream()
                .max(Comparator.comparing(
                        MealNutrientResponseType::getTotalProtein
                ))
                .orElseThrow(() ->
                        new IllegalStateException("No meal data found"));
    }
    public MealNutrientResponseType getMealWithHighestCarbs() {

        return getMealNutrientSummaries()
                .stream()
                .max(Comparator.comparing(
                        MealNutrientResponseType::getTotalCarbs
                ))
                .orElseThrow(() ->
                        new IllegalStateException("No meal data found"));
    }
    public MealNutrientResponseType getMealWithHighestFat() {

        return getMealNutrientSummaries()
                .stream()
                .max(Comparator.comparing(
                        MealNutrientResponseType::getTotalFat
                ))
                .orElseThrow(() ->
                        new IllegalStateException("No meal data found"));
    }

    public HighestNutrientsResponse getHighestNutrients() {

        List<MealNutrientResponseType> summaries = getMealNutrientSummaries();

        MealNutrientResponseType highestProtein = summaries.stream()
                .max(Comparator.comparing(
                        MealNutrientResponseType::getTotalProtein))
                .orElseThrow(() -> new IllegalStateException("No meal data found"));

        MealNutrientResponseType highestCarbs = summaries.stream()
                .max(Comparator.comparing(
                        MealNutrientResponseType::getTotalCarbs))
                .orElseThrow(() -> new IllegalStateException("No meal data found"));

        MealNutrientResponseType highestFat = summaries.stream()
                .max(Comparator.comparing(
                        MealNutrientResponseType::getTotalFat))
                .orElseThrow(() -> new IllegalStateException("No meal data found"));

        return new HighestNutrientsResponse(
                highestProtein,
                highestCarbs,
                highestFat
        );
    }
}
