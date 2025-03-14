package com.example.inventory_pick_path.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphData {

     @NotEmpty(message = "Nodes list cannot be empty")
    private List<String> nodes;

    @NotEmpty(message = "Edges list cannot be empty")
    private List<com.example.inventory_pick_path.model.EdgeDTO> edges;

    private boolean directed = false;
}
