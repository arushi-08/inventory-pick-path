# Inventory-pick-path


## Motivation

> I’m passionate about finding innovative ways to solve real-world problems.

---

<!--- [![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/pObBoZDsz50/0.jpg)](https://youtu.be/pObBoZDsz50)  --->



## Overview

Proof-of-concept for an inventory pick path solution. It models an inventory as a graph and compute the most efficient route for picking items. The API uses Dijkstra’s algorithm to determine:
- High-level order of key nodes (entry, item locations, exit)
- Detailed path between these nodes

---

## Key Features

**Spring Boot**: maintainable REST API, focussing on business logic.  
**Graph-Based Routing**: using JGraphT to model inventory layouts and calculate the shortest paths.  
**Optimized Pick Path**: using a nearest-neighbor heuristic to plan the best order for picking items.  
**Detailed Route Construction**: separating high-level route planning from detailed navigation steps.  

---

## Technologies

- **Java 17.0.11**
- **Spring Boot**: RESTful API development
- **JGraphT**: Graph modeling and pathfinding
- **Lombok**: Boilerplate reduction
- **Jakarta Bean Validation**: Input validation and data integrity
