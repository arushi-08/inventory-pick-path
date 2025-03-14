package com.example.inventory_pick_path.service;

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
import org.springframework.stereotype.Service;

import com.example.inventory_pick_path.model.EdgeDTO;
import com.example.inventory_pick_path.model.GraphData;
import com.example.inventory_pick_path.model.RouteResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RouteService {

    /**
     * Builds the graph using the provided GraphData.
     */
    public Graph<String, DefaultWeightedEdge> buildGraph(GraphData graphData) {
        Graph<String, DefaultWeightedEdge> graph = graphData.isDirected() 
                ? new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class)
                : new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // Add all nodes
        graphData.getNodes().forEach(graph::addVertex);

        // Add edges with weights
        for (EdgeDTO edge : graphData.getEdges()) {
            String source = edge.getSource();
            String target = edge.getTarget();
            double weight = edge.getWeight();
            if (!graph.containsVertex(source) || !graph.containsVertex(target)) {
                throw new IllegalArgumentException("Edge vertices must exist in the graph");
            }
            DefaultWeightedEdge e = graph.addEdge(source, target);
            if (e == null) {
                e = graph.getEdge(source, target);
            }
            graph.setEdgeWeight(e, weight);
        }
        return graph;
    }

    /**
     * Main method to get the best route.
     * Uses "DIJKSTRA" as default if algorithm is not provided.
     */
    public RouteResult getBestRoute(
            Graph<String, DefaultWeightedEdge> graph,
            String start,
            List<String> items,
            String end,
            String algorithm) throws Exception {

        if (algorithm == null || algorithm.trim().isEmpty() || algorithm.equalsIgnoreCase("DIJKSTRA")) {
            return getBestRouteDijkstra(graph, start, items, end);
        } else if (algorithm.equalsIgnoreCase("TSP")) {
            return getBestRouteTSP(graph, start, items, end);
        } else {
            throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
    }

    /**
     * Route optimization using a nearest-neighbor approach with Dijkstra at each step.
     */
    private RouteResult getBestRouteDijkstra(
            Graph<String, DefaultWeightedEdge> graph,
            String start,
            List<String> items,
            String end) throws Exception {

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
                if (path == null) continue;
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

        if (end != null && !end.isEmpty()) {
            GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, current, end);
            if (path == null) {
                throw new Exception("No path exists from the last item to the end node.");
            }
            totalDistance += path.getWeight();
            orderedRoute.add(end);
        }
        return new RouteResult(orderedRoute, totalDistance);
    }

    /**
     * A basic TSP (Traveling Salesman Problem) approach using the nearest-neighbor heuristic.
     */
    private RouteResult getBestRouteTSP(
            Graph<String, DefaultWeightedEdge> graph,
            String start,
            List<String> items,
            String end) throws Exception {

        // Combine start and items for the TSP computation.
        List<String> tspNodes = new ArrayList<>();
        tspNodes.add(start);
        tspNodes.addAll(items);
        boolean includeEnd = (end != null && !end.isEmpty());

        List<String> orderedRoute = new ArrayList<>();
        Set<String> unvisited = new HashSet<>(tspNodes);
        String current = start;
        orderedRoute.add(current);
        unvisited.remove(current);
        double totalDistance = 0.0;

        while (!unvisited.isEmpty()) {
            String next = null;
            double minDist = Double.POSITIVE_INFINITY;
            for (String node : unvisited) {
                GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, current, node);
                if (path == null) continue;
                double d = path.getWeight();
                if (d < minDist) {
                    minDist = d;
                    next = node;
                }
            }
            if (next == null) {
                throw new Exception("No valid path found during TSP computation.");
            }
            orderedRoute.add(next);
            totalDistance += minDist;
            current = next;
            unvisited.remove(next);
        }
        if (includeEnd) {
            GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, current, end);
            if (path == null) {
                throw new Exception("No path exists from the last node to the end node.");
            }
            totalDistance += path.getWeight();
            orderedRoute.add(end);
        }
        return new RouteResult(orderedRoute, totalDistance);
    }

    /**
     * Returns the full detailed route path (including intermediate nodes) for display.
     */
    public List<String> getFullRoutePath(
            Graph<String, DefaultWeightedEdge> graph,
            List<String> route) throws Exception {

        List<String> fullPath = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            String source = route.get(i);
            String target = route.get(i + 1);
            GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, source, target);
            if (path == null) {
                throw new Exception("No path exists between " + source + " and " + target + ".");
            }
            List<String> segment = path.getVertexList();
            // Avoid duplicate nodes when merging segments.
            if (i > 0 && !segment.isEmpty()) {
                segment = segment.subList(1, segment.size());
            }
            fullPath.addAll(segment);
        }
        return fullPath;
    }
}
