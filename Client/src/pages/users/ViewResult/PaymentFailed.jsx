import React from 'react';
import { useNavigate } from 'react-router-dom';
import './PaymentFailed.css';

const PaymentFailed = () => {
  const navigate = useNavigate();

  const handleHomeClick = () => {
    navigate('/');
  };

  return (
    <div className="payment-failed-container">
      <h1>Payment Failed</h1>
      <p>Sorry, there was an issue with your payment. Please try again or contact support.</p>
      <button onClick={handleHomeClick}>Go to Home</button>
    </div>
  );
};

export default PaymentFailed;
