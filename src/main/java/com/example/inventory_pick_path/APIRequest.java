package com.example.inventory_pick_path;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class APIRequest {
    private GraphData graph;
    private OrderData order;
}