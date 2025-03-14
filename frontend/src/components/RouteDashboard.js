import React from 'react';

const RouteDashboard = ({ result }) => {
  if (!result) {
    return null;
  }
  return (
    <div style={{ border: '1px solid #ccc', padding: '1rem', marginTop: '1rem' }}>
      <h2>Optimized Route</h2>
      <p>
        <strong>Total Distance:</strong> {result.total_distance}
      </p>
      <p>
        <strong>Route:</strong> {result.route.join(' -> ')}
      </p>
    </div>
  );
};

export default RouteDashboard;
