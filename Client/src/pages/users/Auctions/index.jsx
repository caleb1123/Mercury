import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "./Auctions.css";
import Countdown from "./CountDown";
import "./Header";
import Header from "./Header";
import { useEffect, useState } from "react";

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
        setJewelryData(response.data);
        console.log('Jewelry Data:', response.data);
      } catch (error) {
        console.error('Error fetching jewelry data:', error);
      }
    }
    fetchJewelryData();
    fetchTargetDate();
  }, [auctionId]);

  if (!targetDate || !jewelryData) return <p>Loading...</p>;

  return (
    <section className="auction-section">
      <div className="auction-content">
        <div className="auction-image-container">
          <h2 className="auction-title">{title}</h2>
          <img src={image} alt={title} className="auction-image"/>
        </div>
        <div className="auction-details">
          <div className="auction-description">
          <h3 className="auction-subtitle">{jewelryData.gemstone}</h3>
                        <p className="auction-info">{jewelryData.description}</p>
                        <p className="auction-note">
                          Still open for consignment. If you have jewelry and/or
                          watches that you are interested in consigning, let
                          FORTUNA assist you. Contact us today to receive a
                          complimentary valuation by completing the Free
                          Valuation Form provided below.
                        </p>
          </div>
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

function AuctionDataPage() {
  const { opId } = useParams();
  const [title, setTitle] = useState("");
  const [auctionDatas, setAuctionDatas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [imageUrl, setImageUrl] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
    
    const fetchAuctionData = async () => {
      setLoading(true);
      try {
        let response;
        if (opId === "1") {
          response = await axios.get("http://localhost:8088/auction/list/live");
          setTitle("Live Auctions");
        } else if (opId === "2") {
          response = await axios.get("http://localhost:8088/auction/list/upcoming");
          setTitle("Upcoming Auctions");
        } else if (opId === "3") {
          response = await axios.get("http://localhost:8088/auction/list/past");
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

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  const fetchJewelryImage = async (id) => {
    try {
      const response = await axios.get(`http://localhost:8088/jewelryImage/jewelry/${id}/image`, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = response.data;
      if (data && data.jewelryImageURL) {
        setImageUrl(prevState => ({
          ...prevState,
          [id]: data.jewelryImageURL,
        }));
      }
    } catch (error) {
      console.error('Error fetching jewelry image:', error);
    }
  };


  return (
    <>
    <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick}/>
      <main className="main-content">
        <div className="auction-grid">
          {auctionRows.map((row, rowIndex) => (
            <div className="auction-row" key={rowIndex}>
              {row.map((auction) => (
                <div className="auction" key={auction.auctionId}>
                  <AuctionSection
                    title={title}
                    image={imageUrl[auction.jewelryId]}
                    description={
                      <>
                        <h3 className="auction-subtitle">{title}</h3>
                        <p className="auction-info">{auction.description}</p>
                        <p className="auction-note">
                          Still open for consignment. If you have jewelry and/or
                          watches that you are interested in consigning, let
                          FORTUNA assist you. Contact us today to receive a
                          complimentary valuation by completing the Free
                          Valuation Form provided below.
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
    </>
  );
};

export default AuctionDataPage;
