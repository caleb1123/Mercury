import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./Auctions.css";
import Countdown from "./CountDown";

const ActionButton = ({ text }) => (
  <button className="action-button">{text}</button>
);

const AuctionSection = ({
  title,
  image,
  description,
  auctionId,
  buttonTexts,
}) => {
  const [targetDate, setTargetDate] = useState(null);

  useEffect(() => {
    const fetchTargetDate = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8088/auction/${auctionId}/targetDate`
        );
        setTargetDate(response.data); 
      } catch (error) {
        console.error("Error fetching target date:", error.message);
      }
    };

    fetchTargetDate();
  }, [auctionId]);

  if (!targetDate) return <p>Loading...</p>;

  return (
    <section className="auction-section">
      <div className="auction-content">
        <div className="auction-image-container">
          <h2 className="auction-title">{title}</h2>
          <img src={image} alt={title} className="auction-image" />
        </div>
        <div className="auction-details">
          <div className="auction-description">{description}</div>
          <Countdown targetDate={targetDate} />
          <div className="action-buttons">
            {buttonTexts.map((text, index) => (
              <ActionButton key={index} text={text} />
            ))}
          </div>
        </div>
      </div>
    </section>
  );
};

const AuctionDataPage = () => {
  const { opId } = useParams();
  const [title, setTitle] = useState("");
  const [auctionDatas, setAuctionDatas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAuctionData = async () => {
      setLoading(true);
      try {
        let response;
        if (opId === "1") {
          response = await axios.get(
            "http://localhost:8088/auction/list/live"
          );
          setTitle("Live Auctions");
        } else if (opId === "2") {
          response = await axios.get(
            "http://localhost:8088/auction/list/upcoming"
          );
          setTitle("Upcoming Auctions");
        } else if (opId === "3") {
          response = await axios.get(
            "http://localhost:8088/auction/list/past"
          );
          setTitle("Past Auctions");
        } else {
          response = await axios.get("http://localhost:8088/auction/list");
          setTitle("All Auctions");
        }
        setAuctionDatas(response.data);
      } catch (error) {
        setError(error.message);
      }
      setLoading(false);
    };

    fetchAuctionData();
  }, [opId]);

  if (loading) return <p className="loading-message">Loading...</p>;
  if (error) return <p className="error-message">Error: {error}</p>;

  const createRows = (items, itemsPerRow) => {
    const rows = [];
    for (let i = 0; i < items.length; i += itemsPerRow) {
      rows.push(items.slice(i, i + itemsPerRow));
    }
    return rows;
  };

  const auctionRows = createRows(auctionDatas, 1);

  return (
    <main className="main-content">
      <div className="auction-grid">
        {auctionRows.map((row, rowIndex) => (
          <div className="auction-row" key={rowIndex}>
            {row.map((auction) => (
              <div className="auction" key={auction.auctionId}>
                <AuctionSection
                  title={title}
                  image="https://cdn.builder.io/api/v1/image/assets/TEMP/c6d825cb6d1abfe4027b3895becccb999d6c81f1e7600e5eac0a724d73071989?apiKey=107bd0f933454a5db22e531b7b3a4de4&"
                  description={
                    <>
                      <h3 className="auction-subtitle">{title}</h3>
                      <p className="auction-info">{auction.description}</p>
                      <p className="auction-note">
                        Still open for consignment. If you have jewelry and/or
                        watches that you are interested in consigning, let
                        FORTUNA assist you. Contact us today to receive a
                        complimentary valuation by completing the Free Valuation
                        Form provided below.
                      </p>
                    </>
                  }
                  auctionId={auction.auctionId}
                  buttonTexts={[
                    "Sign Up for Text Message Notifications",
                    "Sign Up for Email Notifications",
                    "Sell With Us",
                  ]}
                />
              </div>
            ))}
          </div>
        ))}
      </div>
    </main>
  );
};

export default AuctionDataPage;
