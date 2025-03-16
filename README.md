## Overview

A visual tool for designing supply chain networks and calculating optimal inventory collection routes using graph algorithms.
It models an inventory as a graph and computes the most efficient route for picking items.

<div style="display: inline-flex; align-items: center;">
  <!-- Video Thumbnail -->
  <a href="https://www.youtube.com/watch?v=vVvMWz68czA" target="_blank" style="display: inline-block;">
    <img src="https://img.youtube.com/vi/vVvMWz68czA/0.jpg" style="width: 100%; display: block;">
  </a>

  <!-- Play Button -->
  <a href="https://www.youtube.com/watch?v=vVvMWz68czA" target="_blank" style="display: inline-block;">
    <img src="https://upload.wikimedia.org/wikipedia/commons/b/b8/YouTube_play_button_icon_%282013%E2%80%932017%29.svg" 
         style="width: 50px; height: auto; margin-left: 5px;">
  </a>
</div>


## Key Features
**Graph Configuration**  
- Define warehouse nodes (storage locations) via comma-separated list
- Create directed/undirected edges with weights
- Interactive edge management with validation
- Automatic graph layout using force-directed algorithms

**Route Optimization**  
- Multiple algorithm support:
  - **Dijkstra** - Exact shortest path for single orders
  - **TSP with Nearest Neighbor** - Efficient multi-item collection
- Multi-item order handling with start/end constraints
- Real-time path calculation

**Visual Analytics**  
- Interactive route simulation with speed control
- Weighted edge visualization
- Step-through debugging for routes

## Tech Stack
- **Frontend**: React + ReactFlow (graph visualization)
- **Algorithms**: Dijkstra, TSP heuristic implementations
- **State Management**: React Hooks with memoization
- **API**: Axios for backend communication

## Installation
```bash
git clone https://github.com/arushi-08/inventory-route-optimization-system.git
cd inventory-route-optimization-system
```

## Build & Run
Backend setup:
```bash
cd backend
mvn clean install
mvn spring-boot:run
# Server starts at http://localhost:8080
```

Frontend setup:
```bash
cd frontend
npm install
npm start
# Application runs at http://localhost:3000
```
