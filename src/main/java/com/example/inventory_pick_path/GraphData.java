package com.example.inventory_pick_path;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class GraphData {
    private List<String> nodes;
    private List<List<Object>> edges;
    private boolean directed = false;
}
