# inventory-pick-path


## Motivation

> I’m passionate about finding innovative ways to solve real-world problems. In the realm of logistics, I see immense potential to improve efficiency and reduce costs with smart technology. I built this project because I truly believe that optimizing inventory operations can lead to faster order fulfillment and happier customers. This project represents my drive to leverage technology to make a tangible impact in high-scale operations like those at Walmart.

---

## Overview

This project is a proof-of-concept for an optimized inventory pick path solution. It demonstrates how to model a inventory as a graph and compute the most efficient route for picking items. The API uses a combination of a nearest-neighbor heuristic and Dijkstra’s algorithm to determine:
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
---

## The Impact

In a large-scale operation like Walmart’s, efficient route planning can:  
- **Cut operational costs**: Minimize unnecessary travel  
- **Increase efficiency**: Optimize pick paths and reduce order processing times  
- **Enhance customer satisfaction**: Enable faster, more reliable order fulfillment  

*While this project is an MVP, it's a small step towards tackling logistic challenges.*
