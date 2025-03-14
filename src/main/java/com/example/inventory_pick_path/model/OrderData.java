package com.example.inventory_pick_path.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderData {
    @NotBlank(message = "Start node cannot be blank")
    private String start;

    @NotEmpty(message = "Items list cannot be empty")
    private List<String> items;

    // Optional end node
    private String end;
}
