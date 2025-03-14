package com.example.inventory_pick_path.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIRequest {
    @NotNull(message = "Graph data is required")
    @Valid
    private GraphData graph;

    @NotNull(message = "Order data is required")
    @Valid
    private OrderData order;

    // Optional algorithm selection: "DIJKSTRA" (default) or "TSP"
    private String algorithm;
}
