import React, { useState } from 'react';

const GraphEditor = ({ graphData, setGraphData }) => {
  // For nodes input, we use a local state to manage comma-separated string.
  const [nodesText, setNodesText] = useState(graphData.nodes.join(', '));
  const [edges, setEdges] = useState(graphData.edges);

  const handleNodesChange = (e) => {
    setNodesText(e.target.value);
    const nodesArr = e.target.value.split(',').map(n => n.trim()).filter(n => n);
    setGraphData(prev => ({ ...prev, nodes: nodesArr }));
  };

  const handleAddEdge = () => {
    setEdges(prev => {
      const updatedEdges = [...prev, { source: '', target: '', weight: 0 }];
      setGraphData(prevData => ({ ...prevData, edges: updatedEdges }));
      return updatedEdges;
    });
  };

  const handleEdgeChange = (index, field, value) => {
    const updatedEdges = [...edges];
    updatedEdges[index][field] = field === 'weight' ? parseFloat(value) : value;
    setEdges(updatedEdges);
    setGraphData(prev => ({ ...prev, edges: updatedEdges }));
  };

  const handleDirectedChange = (e) => {
    setGraphData(prev => ({ ...prev, directed: e.target.checked }));
  };

  return (
    <div style={{ border: '1px solid #ccc', padding: '1rem', marginBottom: '1rem' }}>
      <h2>Graph Editor</h2>
      <label>
        Nodes (comma separated):
        <input
          type="text"
          value={nodesText}
          onChange={handleNodesChange}
          style={{ width: '100%', marginTop: '0.5rem' }}
        />
      </label>
      <br />
      <label style={{ marginTop: '0.5rem', display: 'block' }}>
        Directed:
        <input
          type="checkbox"
          checked={graphData.directed}
          onChange={handleDirectedChange}
          style={{ marginLeft: '0.5rem' }}
        />
      </label>
      <h3 style={{ marginTop: '1rem' }}>Edges</h3>
      {edges.map((edge, index) => (
        <div key={index} style={{ marginBottom: '0.5rem' }}>
          <input
            type="text"
            placeholder="Source"
            value={edge.source}
            onChange={(e) => handleEdgeChange(index, 'source', e.target.value)}
            style={{ marginRight: '0.5rem' }}
          />
          <input
            type="text"
            placeholder="Target"
            value={edge.target}
            onChange={(e) => handleEdgeChange(index, 'target', e.target.value)}
            style={{ marginRight: '0.5rem' }}
          />
          <input
            type="number"
            placeholder="Weight"
            value={edge.weight}
            onChange={(e) => handleEdgeChange(index, 'weight', e.target.value)}
            style={{ width: '80px' }}
          />
        </div>
      ))}
      <button onClick={handleAddEdge}>Add Edge</button>
    </div>
  );
};

export default GraphEditor;
