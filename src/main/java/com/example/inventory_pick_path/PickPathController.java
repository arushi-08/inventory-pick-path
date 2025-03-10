package com.example.inventory_pick_path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/")
class PickPathController {

    @PostMapping(value="/find-best-path", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> findBestPath(@RequestBody APIRequest request) {

        Graph<String, DefaultWeightedEdge> graph;
        if (request.getGraph().isDirected()) {
            graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        } else {
            graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        }

        for (String node : request.getGraph().getNodes()) {
            graph.addVertex(node);
        }
        for (List<Object> edge : request.getGraph().getEdges()) {
            if (edge.size() != 3) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each edge must have 3 elements: source, target, weight");
            }
            String source = edge.get(0).toString();
            String target = edge.get(1).toString();
            double weight;
            try {
                weight = Double.parseDouble(edge.get(2).toString());
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Weight must be a number");
            }
            if (!graph.containsVertex(source) || !graph.containsVertex(target)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Edge vertices must exist in the graph");
            }
            DefaultWeightedEdge e = graph.addEdge(source, target);
            if (e == null) {
                e = graph.getEdge(source, target);
            }
            graph.setEdgeWeight(e, weight);
        }

        OrderData order = request.getOrder();
        for (String item : order.getItems()) {
            if (!graph.containsVertex(item)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item node " + item + " not in graph");
            }
        }

        try {
            RouteResult routeResult = getBestRoute(graph, order.getStart(), order.getItems(), order.getEnd());
            List<String> fullRoute = getFullRoutePath(graph, routeResult.getOrderedRoute());
            APIResponse response = new APIResponse(fullRoute, routeResult.getTotalDistance());

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private RouteResult getBestRoute(Graph<String, DefaultWeightedEdge> graph, String start, List<String> items, String end) throws Exception {
        Set<String> remaining = new HashSet<>(items);
        String current = start;
        List<String> orderedRoute = new ArrayList<>();
        orderedRoute.add(start);
        double totalDistance = 0.0;

        while (!remaining.isEmpty()) {
            String nearest = null;
            double nearestDistance = Double.POSITIVE_INFINITY;
            for (String item : remaining) {
                GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, current, item);
                if (path == null) {
                    continue;
                }
                double distance = path.getWeight();
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = item;
                }
            }
            if (nearest == null) {
                throw new Exception("No path exists to one or more remaining items.");
            }
            orderedRoute.add(nearest);
            totalDistance += nearestDistance;
            current = nearest;
            remaining.remove(nearest);
        }

        if (end != null) {
            GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, current, end);
            if (path == null) {
                throw new Exception("No path exists from the last item to the end node.");
            }
            totalDistance += path.getWeight();
            orderedRoute.add(end);
        }

        return new RouteResult(orderedRoute, totalDistance);
    }

    private List<String> getFullRoutePath(Graph<String, DefaultWeightedEdge> graph, List<String> route) throws Exception {
        List<String> fullPath = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            String source = route.get(i);
            String target = route.get(i + 1);
            GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, source, target);
            if (path == null) {
                throw new Exception("No path exists between " + source + " and " + target + ".");
            }
            List<String> segment = path.getVertexList();

            if (i > 0 && !segment.isEmpty()) {
                segment = segment.subList(1, segment.size());
            }
            fullPath.addAll(segment);
        }
        return fullPath;
    }
}
