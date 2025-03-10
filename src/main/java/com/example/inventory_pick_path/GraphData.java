package com.example.inventory_pick_path;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class GraphData {
    
     @NotEmpty(message = "Nodes list cannot be empty")
    private List<String> nodes;

    @NotEmpty(message = "Edges list cannot be empty")
    private List<@Valid EdgeDTO> edges;

    private boolean directed = false;
}
