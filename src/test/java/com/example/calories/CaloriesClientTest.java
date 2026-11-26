package com.example.calories;


import com.example.calories.client.CaloriesClient;
import com.example.calories.model.Food;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CaloriesClientTest {

    @Autowired
    private CaloriesClient caloriesClient;

    @Test
    void shouldFetchCaloriesFromGithub(){
        List<Food> response = caloriesClient.fetchCalories();
        Assertions.assertThat(response).isNotNull().isNotEmpty();
        Food firstFood =  response.get(0);

        Assertions.assertThat(firstFood.getId()).isNotNull();
        Assertions.assertThat(firstFood.getName()).isNotBlank();

        System.out.println("Total records " + response.size());
        System.out.println("First food record " + firstFood.getName());
    }
}
