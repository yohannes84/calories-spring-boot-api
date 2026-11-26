package com.example.calories;

import com.example.calories.client.CaloriesClient;
import com.example.calories.dto.ExpensiveFoodResponse;
import com.example.calories.dto.HighestNutrientsResponse;
import com.example.calories.dto.MealNutrientResponseType;
import com.example.calories.model.Food;
import com.example.calories.service.CalorieService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CaloriesServiceTest {

    @InjectMocks
    private CalorieService caloriesService;

    @Mock
    private CaloriesClient caloriesClient;

    @Test
    void shouldReturnFirstThreeExpensiveFoodSortedByPricesAscending(){
        Food food1 = new Food();
        food1.setName("Pizza");
        food1.setPrice(new BigDecimal("49.95"));

        Food food2 = new Food();
        food2.setName("Pasta");
        food2.setPrice(new BigDecimal("49.92"));

        Food food3 = new Food();
        food3.setName("Chicken");
        food3.setPrice(new BigDecimal("49.93"));

        Food food4 = new Food();
        food4.setName("Salad");
        food4.setPrice(new BigDecimal("49.90"));

        Food food5 = new Food();
        food5.setName("Burger");
        food5.setPrice(new BigDecimal("49.99"));

        when(caloriesClient.fetchCalories())
                .thenReturn(List.of(food1, food2, food3, food4, food5));

        List<ExpensiveFoodResponse> result = caloriesService.getExpensiveFoods();

        assertThat(result)
                .hasSize(3)
                .extracting(ExpensiveFoodResponse::getName,
                        ExpensiveFoodResponse::getPrice)
                .containsExactly(tuple("Pasta","49.92"), tuple("Chicken","49.93"), tuple ("Pizza", "49.95"));
    }


    @Test
    void shouldReturnMealsForUser10WithinDateRange(){
        Food food1 = new Food();
        food1.setUserId("10");
        food1.setType("Breakfast");
        food1.setDateConsumed(LocalDate.of(2022,12,4));
        food1.setProtein(BigDecimal.valueOf(33.93));
        food1.setCarbs(BigDecimal.valueOf(96.58));
        food1.setFat(BigDecimal.valueOf(45.63));


        Food food2 = new Food();
        food2.setUserId("10");
        food2.setType("Lunch");
        food2.setDateConsumed(LocalDate.of(2022,12,7));
        food2.setProtein(BigDecimal.valueOf(33.93));
        food2.setCarbs(BigDecimal.valueOf(96.58));
        food2.setFat(BigDecimal.valueOf(45.63));


        Food food3 = new Food();
        food3.setUserId("10");
        food3.setType("Dinner");
        food3.setDateConsumed(LocalDate.of(2022,12,9));
        food3.setProtein(BigDecimal.valueOf(10.22));
        food3.setCarbs(BigDecimal.valueOf(85.58));
        food3.setFat(BigDecimal.valueOf(37.63));


        Food food4 = new Food();
        food4.setUserId("10");
        food4.setType("Dinner");
        food4.setDateConsumed(LocalDate.of(2022,12,8));
        food4.setProtein(BigDecimal.valueOf(48.13));
        food4.setCarbs(BigDecimal.valueOf(88.63));
        food4.setFat(BigDecimal.valueOf(65.46));


        Food food5 = new Food();
        food5.setUserId("10");
        food5.setType("Lunch");
        food5.setDateConsumed(LocalDate.of(2022,12,5));
        food5.setProtein(BigDecimal.valueOf(33.93));
        food5.setCarbs(BigDecimal.valueOf(96.58));
        food5.setFat(BigDecimal.valueOf(45.63));


        when(caloriesClient.fetchCalories())
                .thenReturn(List.of(food1, food2, food3, food4, food5));

        List<Food> result = caloriesService.getUserMeals();

        assertThat(result)
                .hasSize(5)
                .containsExactly(food1, food2, food3, food4, food5);

        verify(caloriesClient).fetchCalories();


        Map<String, List<Food>> mealsByType = caloriesService.getMealsGroupedByType();

        assertThat(mealsByType).containsOnlyKeys("Breakfast", "Lunch", "Dinner");

        assertThat(mealsByType.get("Breakfast")).containsExactly(food1);

        assertThat(mealsByType.get("Lunch")).containsExactly(food2, food5);

        assertThat(mealsByType.get("Dinner")).containsExactly(food3,food4);
    }

    @Test
    void shouldCalculateNutrientTotalsByMealType() {

        Food food1 = new Food();
        food1.setUserId("10");
        food1.setType("Breakfast");
        food1.setDateConsumed(LocalDate.of(2022,12,4));
        food1.setProtein(BigDecimal.valueOf(33.93));
        food1.setCarbs(BigDecimal.valueOf(96.58));
        food1.setFat(BigDecimal.valueOf(45.63));


        Food food2 = new Food();
        food2.setUserId("10");
        food2.setType("Lunch");
        food2.setDateConsumed(LocalDate.of(2022,12,7));
        food2.setProtein(BigDecimal.valueOf(33.93));
        food2.setCarbs(BigDecimal.valueOf(96.58));
        food2.setFat(BigDecimal.valueOf(45.63));


        Food food3 = new Food();
        food3.setUserId("10");
        food3.setType("Dinner");
        food3.setDateConsumed(LocalDate.of(2022,12,9));
        food3.setProtein(BigDecimal.valueOf(10.22));
        food3.setCarbs(BigDecimal.valueOf(85.58));
        food3.setFat(BigDecimal.valueOf(37.63));


        Food food4 = new Food();
        food4.setUserId("10");
        food4.setType("Dinner");
        food4.setDateConsumed(LocalDate.of(2022,12,8));
        food4.setProtein(BigDecimal.valueOf(48.13));
        food4.setCarbs(BigDecimal.valueOf(88.63));
        food4.setFat(BigDecimal.valueOf(65.46));


        Food food5 = new Food();
        food5.setUserId("10");
        food5.setType("Lunch");
        food5.setDateConsumed(LocalDate.of(2022,12,5));
        food5.setProtein(BigDecimal.valueOf(33.93));
        food5.setCarbs(BigDecimal.valueOf(96.58));
        food5.setFat(BigDecimal.valueOf(45.63));

        when(caloriesClient.fetchCalories())
                .thenReturn(List.of(food1, food2, food3, food4, food5));

        List<MealNutrientResponseType> result =
                caloriesService.getMealNutrientSummaries();

        assertThat(result).hasSize(3);

        assertThat(result)
                .extracting(
                        MealNutrientResponseType::getMealType,
                        MealNutrientResponseType::getTotalProtein,
                        MealNutrientResponseType::getTotalCarbs,
                        MealNutrientResponseType::getTotalFat
                )
                .containsExactlyInAnyOrder(
                        tuple("Breakfast",
                                new BigDecimal("33.93"),
                                new BigDecimal("96.58"),
                                new BigDecimal("45.63")),

                        tuple("Lunch",
                                new BigDecimal("67.86"),
                                new BigDecimal("193.16"),
                                new BigDecimal("91.26")),

                        tuple("Dinner",
                                new BigDecimal("58.35"),
                                new BigDecimal("174.21"),
                                new BigDecimal("103.09"))
                );
    }

    @Test
    void shouldReturnMealTypesWithHighestNutrientTotals() {

        Food food1 = new Food();
        food1.setUserId("10");
        food1.setType("Breakfast");
        food1.setDateConsumed(LocalDate.of(2022,12,4));
        food1.setProtein(BigDecimal.valueOf(33.93));
        food1.setCarbs(BigDecimal.valueOf(96.58));
        food1.setFat(BigDecimal.valueOf(45.63));


        Food food2 = new Food();
        food2.setUserId("10");
        food2.setType("Lunch");
        food2.setDateConsumed(LocalDate.of(2022,12,7));
        food2.setProtein(BigDecimal.valueOf(33.93));
        food2.setCarbs(BigDecimal.valueOf(96.58));
        food2.setFat(BigDecimal.valueOf(45.63));


        Food food3 = new Food();
        food3.setUserId("10");
        food3.setType("Dinner");
        food3.setDateConsumed(LocalDate.of(2022,12,9));
        food3.setProtein(BigDecimal.valueOf(10.22));
        food3.setCarbs(BigDecimal.valueOf(85.58));
        food3.setFat(BigDecimal.valueOf(37.63));


        Food food4 = new Food();
        food4.setUserId("10");
        food4.setType("Dinner");
        food4.setDateConsumed(LocalDate.of(2022,12,8));
        food4.setProtein(BigDecimal.valueOf(48.13));
        food4.setCarbs(BigDecimal.valueOf(88.63));
        food4.setFat(BigDecimal.valueOf(65.46));


        Food food5 = new Food();
        food5.setUserId("10");
        food5.setType("Lunch");
        food5.setDateConsumed(LocalDate.of(2022,12,5));
        food5.setProtein(BigDecimal.valueOf(33.93));
        food5.setCarbs(BigDecimal.valueOf(96.58));
        food5.setFat(BigDecimal.valueOf(45.63));

        when(caloriesClient.fetchCalories())
                .thenReturn(List.of(food1, food2, food3, food4, food5));

        HighestNutrientsResponse result =
                caloriesService.getHighestNutrients();

        assertThat(result).isNotNull();

        assertThat(result.getHighestProtein().getMealType())
                .isEqualTo("Lunch");

        assertThat(result.getHighestProtein().getTotalProtein())
                .isEqualByComparingTo("67.86");

        assertThat(result.getHighestCarbs().getMealType())
                .isEqualTo("Lunch");

        assertThat(result.getHighestCarbs().getTotalCarbs())
                .isEqualByComparingTo("193.16");

        assertThat(result.getHighestFat().getMealType())
                .isEqualTo("Dinner");

        assertThat(result.getHighestFat().getTotalFat())
                .isEqualByComparingTo("103.09");
    }
}
