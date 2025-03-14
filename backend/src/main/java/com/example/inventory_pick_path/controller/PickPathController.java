package com.example.inventory_pick_path.controller;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.inventory_pick_path.model.APIRequest;
import com.example.inventory_pick_path.model.APIResponse;
import com.example.inventory_pick_path.model.OrderData;
import com.example.inventory_pick_path.model.RouteResult;
import com.example.inventory_pick_path.service.RouteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class PickPathController {

    private final RouteService routeService;

    public PickPathController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping(value="/find-best-path", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> findBestPath(@Valid @RequestBody APIRequest request) {
        Graph<String, DefaultWeightedEdge> graph = routeService.buildGraph(request.getGraph());
        OrderData order = request.getOrder();
        try {
            RouteResult routeResult = routeService.getBestRoute(
                    graph,
                    order.getStart(),
                    order.getItems(),
                    order.getEnd(),
                    request.getAlgorithm()
            );
            List<String> fullRoute = routeService.getFullRoutePath(graph, routeResult.getOrderedRoute());
            APIResponse response = new APIResponse(fullRoute, routeResult.getTotalDistance());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
