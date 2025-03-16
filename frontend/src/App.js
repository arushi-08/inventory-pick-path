import axios from 'axios';
import React, { useCallback, useState } from 'react';
import './App.css';
import GraphEditor from './components/GraphEditor';
import OrderForm from './components/OrderForm';
import RouteDashboard from './components/RouteDashboard';
import RouteSimulation from './components/RouteSimulation';

// Deep equality check utility
const deepEqual = (a, b) => JSON.stringify(a) === JSON.stringify(b);

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

  // Stable graph data updater
  const stableSetGraphData = useCallback((updater) => {
    setGraphData(prev => {
      const newData = typeof updater === 'function' ? updater(prev) : updater;
      return deepEqual(prev, newData) ? prev : newData;
    });
  }, []);

  // Stable order data updater
  const stableSetOrderData = useCallback((updater) => {
    setOrderData(prev => {
      const newData = typeof updater === 'function' ? updater(prev) : updater;
      return deepEqual(prev, newData) ? prev : newData;
    });
  }, []);

  const handleOptimizeRoute = useCallback(async () => {
    try {
      const payload = {
        graph: graphData,
        order: {
          start: orderData.start,
          items: orderData.items,
          end: orderData.end
        },
        algorithm: orderData.algorithm
      };

      const response = await axios.post('/find-best-path', payload);
      if (!deepEqual(result, response.data)) {
        setResult(response.data);
      }
      setError('');
    } catch (err) {
      const errMsg = err.response?.data?.message || 'An error occurred';
      if (error !== errMsg) setError(errMsg);
      if (result !== null) setResult(null);
    }
  }, [graphData, orderData, result, error]);

  return (
    <div className="app-container">
      <div className="left-panel">
        <h1>Inventory Route Optimization System</h1>
        <GraphEditor 
          graphData={graphData} 
          setGraphData={stableSetGraphData} 
        />
        <OrderForm 
          orderData={orderData} 
          setOrderData={stableSetOrderData} 
        />
        <button onClick={handleOptimizeRoute} className="optimize-btn">
          Optimize Route
        </button>
        {error && <p className="error">{error}</p>}
        <RouteDashboard result={result} />
        
      </div>
      <div className="right-panel">
        {result?.route?.length > 0 && (
          <RouteSimulation route={result.route} />
        )}
      </div>
    </div>
  );
}

export default App;