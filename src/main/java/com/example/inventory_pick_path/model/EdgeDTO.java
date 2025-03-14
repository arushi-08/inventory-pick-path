package com.example.inventory_pick_path.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDTO {
    @NotBlank(message = "Source cannot be blank")
    private String source;

    @NotBlank(message = "Target cannot be blank")
    private String target;

    @NotNull(message = "Weight cannot be null")
    private Double weight;
}

