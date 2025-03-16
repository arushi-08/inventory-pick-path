import React, { useEffect, useMemo, useState } from 'react';
import ReactFlow, { Background, Controls } from 'reactflow';
import 'reactflow/dist/style.css';

function circularLayout(nodes, radius = 150) {
  const angleStep = (2 * Math.PI) / nodes.length;
  return nodes.map((label, i) => {
    const angle = i * angleStep;
    return {
      id: label,
      position: { x: radius * Math.cos(angle), y: radius * Math.sin(angle) },
      data: { label }
    };
  });
}

const RouteSimulation = ({ route }) => {
  const [currentStep, setCurrentStep] = useState(0);
  const [markerNode, setMarkerNode] = useState(null);
  const [intervalDelay, setIntervalDelay] = useState(1500);

  // Reset simulation when route changes
  useEffect(() => {
    setCurrentStep(0);
  }, [route]);

  const uniqueNodes = useMemo(() => Array.from(new Set(route)), [route]);
  const layoutNodes = useMemo(() => circularLayout(uniqueNodes), [uniqueNodes]);

  // Node styling with progress indication
  const baseNodes = useMemo(
    () =>
      layoutNodes.map((node) => {
        const isCompleted = route
          .slice(0, currentStep + 1)
          .includes(node.id);
        
        return {
          ...node,
          data: { label: node.id },
          style: {
            backgroundColor: isCompleted ? '#cfc' : '#f1f1f1',
            border: `2px solid ${isCompleted ? '#9c9' : '#999'}`,
            borderRadius: '8px',
            padding: '10px'
          }
        };
      }),
    [layoutNodes, currentStep, route]
  );

  // Animated path edges
  const routeEdges = useMemo(
    () =>
      route.slice(0, -1).map((_, i) => ({
        id: `edge-${i}`,
        source: route[i],
        target: route[i + 1],
        animated: true,
        style: {
          stroke: currentStep > i ? '#9c9' : '#999',
          strokeWidth: 2
        }
      })),
    [route, currentStep]
  );

  // Marker animation
  useEffect(() => {
    if (currentStep < route.length) {
      const currentLabel = route[currentStep];
      const targetNode = layoutNodes.find((n) => n.id === currentLabel);
      targetNode && setMarkerNode({
        id: 'picker-marker',
        position: targetNode.position,
        data: { label: '🚚 Current Location' },
        style: {
          backgroundColor: 'orange',
          color: '#fff',
          border: '2px solid #f90',
          borderRadius: '8px',
          padding: '10px'
        }
      });
    } else {
      setMarkerNode(null);
    }
  }, [currentStep, route, layoutNodes]);

  // Simulation controls
  useEffect(() => {
    let interval = null;
    if (route.length > 0) {
      interval = setInterval(() => {
        setCurrentStep((prev) => (prev < route.length - 1 ? prev + 1 : prev));
      }, intervalDelay);
    }
    return () => interval && clearInterval(interval);
  }, [route, intervalDelay]);

  const flowNodes = markerNode ? [...baseNodes, markerNode] : baseNodes;

  return (
    <div style={{ width: '100%', height: 500, border: '1px solid #ccc', marginTop: '1rem' }}>
      <div style={{ padding: '1rem', display: 'flex', gap: '1rem', alignItems: 'center' }}>
        <h2>Route Simulation</h2>
        <div style={{ display: 'flex', gap: '0.5rem' }}>
          <button
            onClick={() => setCurrentStep(0)}
            style={{ padding: '8px 12px', borderRadius: '4px' }}
          >
            ↺ Reset
          </button>
          <button
            onClick={() => setCurrentStep(p => Math.max(0, p - 1))}
            style={{ padding: '8px 12px', borderRadius: '4px' }}
          >
            ← Previous
          </button>
          <button
            onClick={() => setCurrentStep(p => Math.min(route.length - 1, p + 1))}
            style={{ padding: '8px 12px', borderRadius: '4px' }}
          >
            Next →
          </button>
          <select
            value={intervalDelay}
            onChange={(e) => setIntervalDelay(Number(e.target.value))}
            style={{ padding: '8px', borderRadius: '4px' }}
          >
            <option value={500}>Fast Speed</option>
            <option value={1000}>Normal Speed</option>
            <option value={1500}>Slow Speed</option>
            <option value={3000}>Demo Speed</option>
          </select>
        </div>
      </div>
      
      <ReactFlow 
        nodes={flowNodes} 
        edges={routeEdges} 
        fitView
        nodesDraggable={false}
      >
        <Background variant="dots" gap={20} size={1} />
        <Controls showInteractive={false} />
      </ReactFlow>
    </div>
  );
};

export default RouteSimulation;