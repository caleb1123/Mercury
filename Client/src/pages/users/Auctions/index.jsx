import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "./Auctions.css";
import Countdown from "./CountDown";
import "./Header";
import Header from "./Header";
import { useEffect, useState } from "react";

const AuctionSection = ({ image, auctionId, buttonTexts }) => {
  const [targetDate, setTargetDate] = useState(null);
  const [jewelryData, setJewelryData] = useState([]);

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

    const fetchJewelryData = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8088/jewelry/${auctionId}/jewelry`
        );
        const data = response.data;
        setJewelryData(data);
      } catch (error) {
        console.error("Error fetching jewelry data:", error);
      }
    };

    fetchJewelryData();
    fetchTargetDate();
  }, [auctionId]);

  if (!targetDate || !jewelryData) return <p>Loading...</p>;

  return (
    <div className="auction-section">
      <div className="auction-content">
        <div className="auction-image-container">
          <h2 className="auction-title">{jewelryData.gemstone}</h2>
          <img
            src={image}
            alt={jewelryData.jewelryName}
            className="auction-image"
          />
          <Countdown targetDate={targetDate} />
        </div>
        <div className="auction-details">
          <div className="auction-description">
            <h3 className="auction-subtitle">{jewelryData.jewelryName}</h3>
            <p className="auction-info">{jewelryData.description}</p>
            <p className="auction-note">
              Still open for consignment. If you have jewelry and/or watches
              that you are interested in consigning, let FORTUNA assist you.
              Contact us today to receive a complimentary valuation by
              completing the Free Valuation Form provided below.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

const AuctionDataPage = () => {
  const { opId } = useParams();
  const [title, setTitle] = useState("");
  const [auctionDatas, setAuctionDatas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [imageUrl, setImageUrl] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLoggedIn(true);
    }

    const fetchJewelryImage = async (id) => {
      try {
        const response = await axios.get(
          `http://localhost:8088/jewelryImage/jewelry/${id}/image`,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (response.status !== 200) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = response.data;
        if (data && data.jewelryImageURL) {
          setImageUrl((prevState) => ({
            ...prevState,
            [id]: data.jewelryImageURL,
          }));
        }
      } catch (error) {
        console.error("Error fetching jewelry image:", error);
      }
    };

    const fetchAuctionData = async () => {
      setLoading(true);
      try {
        let response;
        if (opId === "1") {
          response = await axios.get("http://localhost:8088/auction/list/live");
          setTitle("Live Auctions");
        } else if (opId === "2") {
          response = await axios.get(
            "http://localhost:8088/auction/list/upcoming"
          );
          setTitle("Upcoming Auctions");
        } else if (opId === "3") {
          response = await axios.get(
            "http://localhost:8088/auction/list/Ended"
          );
          setTitle("Past Auctions");
        } else {
          response = await axios.get("http://localhost:8088/auction/list");
          setTitle("All Auctions");
        }
        const data = response.data;
        setAuctionDatas(response.data);
        data.forEach((auction) => fetchJewelryImage(auction.jewelryId));
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

  const handleProfileClick = () => {
    navigate("/viewProfile");
  };

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="main-content">
        <h1 className="section-title">{title}</h1>
        <div className="auction-grid">
          {auctionRows.map((row, rowIndex) => (
            <div className="auction-row" key={rowIndex}>
              {row.map((auction) => (
                <div className="auction" key={auction.auctionId}>
                  <div className="line">
                    <hr />
                  </div>
                  <AuctionSection
                    image={imageUrl[auction.jewelryId]}
                    auctionId={auction.auctionId}
                  />
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default AuctionDataPage;
