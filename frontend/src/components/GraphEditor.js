import { isEqual } from 'lodash';
import { useCallback, useEffect, useState } from 'react'; // Added missing hooks import

const GraphEditor = ({ graphData, setGraphData }) => {
  const [nodesText, setNodesText] = useState(graphData.nodes.join(', '));

  // Sync nodes text with external changes
  useEffect(() => {
    setNodesText(graphData.nodes.join(', '));
  }, [graphData.nodes]);

  const handleNodesChange = useCallback((e) => {
    const value = e.target.value;
    setNodesText(value);
    const nodesArr = value.split(',').map(n => n.trim()).filter(Boolean);
    
    setGraphData(prev => {
      if (isEqual(prev.nodes, nodesArr)) return prev;
      return { ...prev, nodes: nodesArr };
    });
  }, [setGraphData]);

  const handleEdgeChange = useCallback((index, field, value) => {
    setGraphData(prev => {
      const newEdges = prev.edges.map((edge, i) => {
        if (i === index) {
          return {
            ...edge,
            [field]: field === 'weight' ? parseFloat(value) || 0 : value
          };
        }
        return edge;
      });
      return { ...prev, edges: newEdges };
    });
  }, [setGraphData]);

  const handleAddEdge = useCallback(() => {
    setGraphData(prev => ({
      ...prev,
      edges: [...prev.edges, { source: '', target: '', weight: 0 }]
    }));
  }, [setGraphData]);

  const handleDirectedChange = useCallback((e) => {
    setGraphData(prev => ({ ...prev, directed: e.target.checked }));
  }, [setGraphData]);

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
      {graphData.edges.map((edge, index) => (
        <div key={`edge-${index}`} style={{ marginBottom: '0.5rem' }}>
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