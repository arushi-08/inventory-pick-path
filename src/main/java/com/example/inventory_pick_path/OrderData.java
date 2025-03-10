package com.example.inventory_pick_path;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class OrderData {
    private String start;
    private List<String> items;
    private String end;
}
