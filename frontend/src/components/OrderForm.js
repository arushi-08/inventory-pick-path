import React, { useEffect, useState } from 'react';

const OrderForm = ({ orderData, setOrderData }) => {

  const [start, setStart] = useState(orderData.start);
  const [items, setItems] = useState(orderData.items.join(', '));
  const [end, setEnd] = useState(orderData.end);
  const [algorithm, setAlgorithm] = useState(orderData.algorithm || 'DIJKSTRA');


  const handleSubmit = (e) => {
    e.preventDefault();
    const itemsArr = items.split(',').map(item => item.trim()).filter(item => item);
    setOrderData({
      start,
      items: itemsArr,
      end,
      algorithm
    });
  };


  useEffect(() => {
    setStart(orderData.start);
    setItems(orderData.items.join(', '));
    setEnd(orderData.end);
    setAlgorithm(orderData.algorithm || 'DIJKSTRA');
  }, [orderData]);

  return (
    <div style={{ border: '1px solid #ccc', padding: '1rem', marginBottom: '1rem' }}>
      <h2>Order Form</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '0.5rem' }}>
          <label>
            Start Node:
            <input
              type="text"
              value={start}
              onChange={(e) => setStart(e.target.value)}
              required
              style={{ marginLeft: '0.5rem' }}
            />
          </label>
        </div>
        <div style={{ marginBottom: '0.5rem' }}>
          <label>
            Items (comma separated):
            <input
              type="text"
              value={items}
              onChange={(e) => setItems(e.target.value)}
              required
              style={{ marginLeft: '0.5rem', width: '60%' }}
            />
          </label>
        </div>
        <div style={{ marginBottom: '0.5rem' }}>
          <label>
            End Node (optional):
            <input
              type="text"
              value={end}
              onChange={(e) => setEnd(e.target.value)}
              style={{ marginLeft: '0.5rem' }}
            />
          </label>
        </div>
        <div style={{ marginBottom: '0.5rem' }}>
          <label>
            Algorithm:
            <select value={algorithm} onChange={(e) => setAlgorithm(e.target.value)} style={{ marginLeft: '0.5rem' }}>
              <option value="DIJKSTRA">Dijkstra (Nearest Neighbor)</option>
              <option value="TSP">TSP (Nearest Neighbor Heuristic)</option>
            </select>
          </label>
        </div>
        <button type="submit">Set Order</button>
      </form>
    </div>
  );
};

export default OrderForm;
