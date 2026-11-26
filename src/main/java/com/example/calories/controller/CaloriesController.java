package com.example.calories.controller;

import com.example.calories.dto.ExpensiveFoodResponse;
import com.example.calories.dto.HighestNutrientsResponse;
import com.example.calories.service.CalorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/calories")
public class CaloriesController {


    private final CalorieService caloriesService;


    public CaloriesController(CalorieService calorieService) {
        this.caloriesService = calorieService;
    }

    @GetMapping("/expensive-foods")
    public List<ExpensiveFoodResponse> getExpensiveFoods() {
        return caloriesService.getExpensiveFoods();
    }

    @GetMapping("/highest-nutrients")
    public HighestNutrientsResponse getHighestNutrients() {
        return caloriesService.getHighestNutrients();
    }
}
