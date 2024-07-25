import React from 'react';
import { useNavigate } from 'react-router-dom';
import './PaymentSuccess.css';

const PaymentSuccess = () => {
  const navigate = useNavigate();

  const handleHomeClick = () => {
    navigate('/');
  };

  return (
    <div className="payment-success-container">
      <h1>Payment Successful!</h1>
      <p>Thank you for your payment. Your transaction has been completed successfully.</p>
      <button onClick={handleHomeClick}>Go to Home</button>
    </div>
  );
};

export default PaymentSuccess;
