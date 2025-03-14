package com.example.inventory_pick_path.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResult {
    private List<String> orderedRoute;
    private double totalDistance;
}
