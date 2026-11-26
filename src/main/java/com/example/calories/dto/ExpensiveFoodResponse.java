package com.example.calories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpensiveFoodResponse {
    private final String name;
    private final String price;
}
