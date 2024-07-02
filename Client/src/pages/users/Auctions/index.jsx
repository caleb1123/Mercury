import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./Auctions.css";  // Import the CSS file

const AuctionDataPage = () => {
  const { auctionId } = useParams();
  const [auctionData, setAuctionData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAuctionData = async () => {
      setLoading(true);
      try {
        let response;
        if (auctionId === "1") {
          response = await axios.get("http://localhost:8088/auction/list/live");
        } else if (auctionId === "2") {
          response = await axios.get("http://localhost:8088/auction/list/upcoming");
        } else if (auctionId === "3") {
          response = await axios.get("http://localhost:8088/auction/list/past");
        } else {
          response = await axios.get("http://localhost:8088/auction/list");
        }
        setAuctionData(response.data);
      } catch (error) {
        setError(error.message);
      }
      setLoading(false);
    };

    fetchAuctionData();
  }, [auctionId]);

  if (loading) return <p className="loading-message">Loading...</p>;
  if (error) return <p className="error-message">Error: {error}</p>;

  return (
    <div className="auction-data-container">
      <h1 className="auction-data-header">Auction Data</h1>
      <ul className="auction-list">
        {auctionData.map((auction) => (
          <li key={auction.id} className="auction-item">
            {auction.auctionId} - {auction.status}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AuctionDataPage;
