import axios from 'axios';
import React, { useState } from 'react';
import GraphEditor from './components/GraphEditor';
import OrderForm from './components/OrderForm';
import RouteDashboard from './components/RouteDashboard';

function App() {
  const [graphData, setGraphData] = useState({
    nodes: [],
    edges: [],
    directed: false
  });

  const [orderData, setOrderData] = useState({
    start: '',
    items: [],
    end: '',
    algorithm: 'DIJKSTRA'
  });

  const [result, setResult] = useState(null);
  const [error, setError] = useState('');

  const handleOptimizeRoute = async () => {
    // Construct payload matching your APIRequest structure
    const payload = {
      graph: graphData,
      order: {
        start: orderData.start,
        items: orderData.items,
        end: orderData.end
      },
      algorithm: orderData.algorithm
    };

    try {
      const response = await axios.post('/find-best-path', payload);
      setResult(response.data);
      setError('');
    } catch (err) {
      setError(err.response?.data || 'An error occurred');
      setResult(null);
    }
  };

  return (
    <div className="App" style={{ padding: '2rem' }}>
      <h1>Inventory Route Optimization System</h1>
      <GraphEditor graphData={graphData} setGraphData={setGraphData} />
      <OrderForm orderData={orderData} setOrderData={setOrderData} />
      <button onClick={handleOptimizeRoute} style={{ marginTop: '1rem', padding: '0.5rem 1rem' }}>
        Optimize Route
      </button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <RouteDashboard result={result} />
    </div>
  );
}

export default App;
