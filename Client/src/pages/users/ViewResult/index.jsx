import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ViewResult.css';
import Header from "../Header";

function ViewResult() {
  const [auctionData, setAuctionData] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [error, setError] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }

    const fetchAuctionData = async () => {
      try {
        const response = await fetch('http://localhost:8088/auction/thisUser/wonAuctions', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        if (response.ok) {
          const data = await response.json();
          setAuctionData(data);
          console.log('Fetched auction data:', data); // Log auction data
        }  else {
          console.error('Failed to fetch auction data:', response.statusText);
        }
      } catch (error) {
        console.error('Error fetching auction data:', error);
      }
    };

    fetchAuctionData();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  if (error) {
    return <div>{error}</div>;
  }

  if (!auctionData.length) {
    return <div><h1>You have not won any items yet</h1></div>;
  }

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="containerResult">
        <div className="result">
          <h2>Won Auctions</h2>
          <div className="result-details">
          <div className="Congra"><h1>Congratulations !!!! You have won this item </h1></div>

            {auctionData.map((auction) => (
              <div key={auction.auctionId} className="auction-item">
                <p><strong>Auction ID:</strong> {auction.auctionId}</p>
                <p><strong>Start Date:</strong> {auction.startDate}</p>
                <p><strong>End Date:</strong> {auction.endDate}</p>
                <p><strong>Current Price:</strong> {auction.currentPrice}</p>
                <p><strong>Status:</strong> {auction.status}</p>
                <p><strong>Jewelry ID:</strong> {auction.jewelryId}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}

export default ViewResult;
