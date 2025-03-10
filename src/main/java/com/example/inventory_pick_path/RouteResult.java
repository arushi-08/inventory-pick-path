package com.example.inventory_pick_path;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class RouteResult {
    private List<String> orderedRoute;
    private double totalDistance;
}
