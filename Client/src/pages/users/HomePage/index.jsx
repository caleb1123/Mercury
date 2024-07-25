import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Header from "../Header";
import Footer from "../Footer";
import "./HomePage.css";
import Homebackground from "./image/Website-banner-2-1.png";
import pic1 from "./image/Untitled-2560-×-1703-px-1-1-scaled.jpg";
import pic2 from "./image/pic2.jpg";
import pic3 from "./image/pic3.jpg";
import { useAuth } from "../../../authContext";

const AuctionSection = ({ image, auctionId }) => {
  const [jewelryData, setJewelryData] = useState([]);
  const [currentBid, setCurrentBid] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
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

    const fetchBidsData = async (auctionId) => {
      try {
        const response = await axios.get(`http://localhost:8088/auction/${auctionId}/highestBid`, {
          headers: {
            'Content-Type': 'application/json'
          },
        });

        if (response.status === 200) {
          setCurrentBid(response.data.bidAmount);
        }
      } catch (error) {
        console.error('Error fetching bids data:', error);
        console.error(error.response ? error.response.data : error.message);
      }
    };

    fetchBidsData(auctionId);
    fetchJewelryData();
  }, [auctionId]);

  const handleItemClick = (id) => {
    navigate("/ViewAuction", { state: { jewelryId: id } });
  };

  return (
    <button className="live_Auction" onClick={() => handleItemClick(jewelryData.jewelryId)}>
      <div className="div-55">
        <img
          src={image}
          alt={jewelryData.jewelryName}
          className="img-9"
        />
        <div className="div-56">
          {jewelryData.jewelryName}
        </div>
        <div className="div-57">{currentBid !== 0 ? currentBid : "-"}</div>
        <div className="div-58">SELLING for ${currentBid !== 0 ? currentBid : "-"}</div>
      </div>
    </button>
  );
};

function HomePage() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [liveAuctionDatas, setliveAuctionDatas] = useState([]);
  const [upAuctionDatas, setupAuctionDatas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [imageUrl, setImageUrl] = useState({});
  const navigate = useNavigate();
  const { user } = useAuth();

  useEffect(() => {
    if (user !== null) {
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
        response = await axios.get("http://localhost:8088/auction/list/live");
        const data = response.data.filter(auction => auction.status !== 'Deleted');
        setliveAuctionDatas(data);
        response = await axios.get("http://localhost:8088/auction/list/upcoming");
        const data2 = response.data.filter(auction => auction.status !== 'Deleted');
        setupAuctionDatas(data2);
        data.forEach((auction) => {
          fetchJewelryImage(auction.jewelryId);
        });
        data2.forEach((auction) => {
          fetchJewelryImage(auction.jewelryId);
        });
      } catch (error) {
        console.log(error.response);
      }
      setLoading(false);
    };


    fetchAuctionData();
  }, [user]);

  const createRows = (items, itemsPerRow) => {
    const rows = [];
    for (let i = 0; i < items.length; i += itemsPerRow) {
      rows.push(items.slice(i, i + itemsPerRow));
    }
    return rows;
  };

  const liveAuctionRows = createRows(liveAuctionDatas, 3);
  const upAuctionRows = createRows(upAuctionDatas, 3);

  const handleClick = () => {
    window.location.href = "/ViewJewelryList";
  };

  const handleProfileClick = () => {
    navigate("/viewProfile");
  };

  const handleLearnMore = () => {
    navigate("/about-mercury");
  };

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="HomePage">
        <div className="HomeBackGround">
          <img className="BackGrounPic" src={Homebackground} alt="background" />
          <div className="Introduction">
            <h3>Welcome to Mercury,</h3>
            <p>
              Your premier destination for exclusive online jewelry auctions.
              Discover a curated selection of exquisite pieces, from timeless
              classics to contemporary designs, all available at your
              fingertips. Whether you're a seasoned collector or searching for
              the perfect piece to elevate your style, Mercury offers
              unparalleled access to unique and luxurious jewelry. Join our
              community of discerning buyers and sellers and experience the
              thrill of bidding on high-quality, authenticated items from the
              comfort of your home. Start exploring today and let your next
              treasure find you at Mercury.
            </p>
            <button onClick={handleClick} className="ViewJewelries">
              View Jewelries
            </button>
          </div>
        </div>

        <div className="auction-container">
          <div className="auction-intro">
            <h1>THE LEADING BOUTIQUE JEWELRY AND WATCH AUCTION HOUSE</h1>
            <button onClick={handleLearnMore}>LEARN MORE ABOUT US</button>
          </div>
          <div className="auction-sections">
            <div className="auction-section">
              <img src={pic1} alt="Buying at Auction" />
              <h2>BUYING AT AUCTION</h2>
              <p>
                Bid from anywhere in the world. Auctions are all live + online
                and are held twice per month. Shop and bid on fine, vintage,
                antique, luxury brand jewelry, gemstones, and watches.
              </p>
            </div>
            <div className="auction-section">
              <img src={pic2} alt="Schedule a Preview" />
              <h2>SCHEDULE A PREVIEW</h2>
              <p>
                See something in one of our auctions that you would like to bid
                on? Schedule a private virtual preview to view the lot and talk
                with one of our senior specialists.
              </p>
            </div>
            <div className="auction-section">
              <img src={pic3} alt="Selling at Auction" />
              <h2>SELLING AT AUCTION</h2>
              <p>
                Maximize your results—have bidders in over 100 countries compete
                for your jewelry and watches. No upfront or hidden fees.
                Fully-insured shipping covered by us. If we can't sell for the
                prices you agree to, you owe us nothing.
              </p>
            </div>
          </div>
        </div>
        <div className="auction-title">LIVE AUCTION HIGHLIGHTS</div>
        <div className="div-52">
          {liveAuctionRows.slice(0, 1).map((row, rowIndex) => (
            <div className="auction-row" key={rowIndex}>
              {row.slice(0, 3).map((auction) => (
                <div className="column-6" key={auction.auctionId}>
                  <AuctionSection
                    image={imageUrl[auction.jewelryId]}
                    auctionId={auction.auctionId}
                  />
                </div>
              ))}
            </div>
          ))}
            <a className="show-more" href="/Auctions/1">Show more</a>
          </div>

        <div className="auction-title">UPCOMING AUCTION HIGHLIGHTS</div>
        <div className="div-52">
          {upAuctionRows.slice(0, 1).map((row, rowIndex) => (
            <div className="auction-row" key={rowIndex}>
              {row.slice(0, 3).map((auction) => (
                <div className="column-6" key={auction.auctionId}>
                  <AuctionSection
                    image={imageUrl[auction.jewelryId]}
                    auctionId={auction.auctionId}
                  />
                </div>
              ))}
            </div>
          ))}
            <a className="show-more" href="/Auctions/2">Show more</a>
          </div>
      </div>
      <Footer />
    </>
  );
}

export default HomePage;
