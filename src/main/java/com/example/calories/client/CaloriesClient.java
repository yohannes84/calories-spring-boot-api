package com.example.calories.client;


import com.example.calories.model.Food;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Component
public class CaloriesClient {

    private final RestClient restClient;
    private final JsonMapper jsonMapper;

    public CaloriesClient(RestClient.Builder restClientBuilder, JsonMapper jsonMapper,  @Value("${calories.api.url}") String apiUrl) {
        this.restClient = restClientBuilder.baseUrl(apiUrl).build();
        this.jsonMapper = jsonMapper;
    }

    public List<Food> fetchCalories(){
        String jsonResponse =  restClient.get()
                .uri("")
                .retrieve()
                .body(String.class);

        return jsonMapper.readValue(jsonResponse, new TypeReference<List<Food>>() {});
    }
}
