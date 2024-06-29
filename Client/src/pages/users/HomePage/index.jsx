import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import Header from './Header';
import "./HomePage.css";
import line from './image/line-3.svg';
import Homebackground from './image/Website-banner-2-1.png';
import pic1 from './image/Untitled-2560-×-1703-px-1-1-scaled.jpg'
import pic2 from './image/pic2.jpg'
import pic3 from './image/pic3.jpg'



function HomePage() {
  const [inputValue, setInputValue] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [upcomingAuctions, setUpcomingAuctions] = useState([]);
  const [recommendedAuctions, setRecommendedAuctions] = useState([]);
  const [bidNowItems, setBidNowItems] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }

    fetch('http://localhost:8088/auctions/upcoming')
      .then(response => response.json())
      .then(data => setUpcomingAuctions(data))
      .catch(error => console.error('Error fetching upcoming auctions:', error));

    fetch('http://localhost:8088/auctions/recommended')
      .then(response => response.json())
      .then(data => setRecommendedAuctions(data))
      .catch(error => console.error('Error fetching recommended auctions:', error));

    fetch('http://localhost:8088/auctions/bidnow')
      .then(response => response.json())
      .then(data => setBidNowItems(data))
      .catch(error => console.error('Error fetching bid now items:', error));
  }, []);

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleClick = () => {
    window.location.href = '/ViewJewelryList';
  };

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="HomePage">
        <div className="HomeBackGround">
          <img className="BackGrounPic" src={Homebackground} alt="background" />
          <div className="Introduction">
            <h3>Welcome to Mercury,</h3>
            <p>Your premier destination for exclusive online jewelry auctions. Discover a curated selection of exquisite pieces, from timeless classics to contemporary designs, all available at your fingertips. Whether you're a seasoned collector or searching for the perfect piece to elevate your style, Mercury offers unparalleled access to unique and luxurious jewelry. Join our community of discerning buyers and sellers and experience the thrill of bidding on high-quality, authenticated items from the comfort of your home. Start exploring today and let your next treasure find you at Mercury.</p>
            <button onClick={handleClick} className="ViewJewelries">View Jewelries</button>
          </div>
        </div>

        <div className="auction-container">
          <div className="auction-intro">
            <h1>THE LEADING BOUTIQUE JEWELRY AND WATCH AUCTION HOUSE</h1>
            <button>LEARN MORE ABOUT US</button>
          </div>
          <div className="auction-sections">
            <div className="auction-section">
              <img src={pic1} alt="Buying at Auction" />
              <h2>BUYING AT AUCTION</h2>
              <p>
                Bid from anywhere in the world. Auctions are all live + online and are held twice per month. Shop and bid on fine, vintage, antique, luxury brand jewelry, gemstones, and watches.
              </p>
            </div>
            <div className="auction-section">
              <img src={pic2} alt="Schedule a Preview" />
              <h2>SCHEDULE A PREVIEW</h2>
              <p>
                See something in one of our auctions that you would like to bid on? Schedule a private virtual preview to view the lot and talk with one of our senior specialists.
              </p>
            </div>
            <div className="auction-section">
              <img src={pic3} alt="Selling at Auction" />
              <h2>SELLING AT AUCTION</h2>
              <p>
                Maximize your results—have bidders in over 100 countries compete for your jewelry and watches. No upfront or hidden fees. Fully-insured shipping covered by us. If we can't sell for the prices you agree to, you owe us nothing.
              </p>
            </div>
          </div>
        </div>

        <div className="UpcomingAuctionsTitle"><h3>UPCOMING AUCTIONS</h3></div>
        <div className="UpcomingAuctions">
          {upcomingAuctions.map((auction) => (
            <div key={auction.id} className="ViewJewelry_HomePage_UpcomingAuctions">
              <img className="JewelryImg_HomePage" src={auction.image} alt={auction.name} />
              <div className="JewelryDate_HomePage_Upcoming">{auction.daysLeft} days left</div>
              <div className="JewelryName_HomePage">{auction.name}</div>
              <strong>{auction.price}($)</strong>
            </div>
          ))}
        </div>
        <div className="UpcomingAuctionsTitle"><h3>RECOMMENDED AUCTIONS</h3></div>
        <div className="UpcomingAuctions">
          {recommendedAuctions.map((auction) => (
            <div key={auction.id} className="ViewJewelry_HomePage_UpcomingAuctions">
              <img className="JewelryImg_HomePage" src={auction.image} alt={auction.name} />
              <div className="JewelryDate_HomePage">{new Date(auction.date).toLocaleString()}</div>
              <div className="JewelryName_HomePage">{auction.name}</div>
              <strong>{auction.price}($)</strong>
            </div>
          ))}
        </div>
        <div className="UpcomingAuctionsTitle"><h3>Bid now on these items</h3></div>
        <div className="UpcomingAuctions">
          {bidNowItems.map((item) => (
            <div key={item.id} className="ViewJewelry_HomePage_UpcomingAuctions">
              <img className="JewelryImg_HomePage" src={item.image} alt={item.name} />
              <div className="JewelryName_HomePage">{item.name}</div>
              <strong>{item.priceRange}</strong>
            </div>
          ))}
        </div>
      </div>
      <div className="Footer">
        <div className="Mercury">MERCURY</div>
        <div className="Footer_Info">
          <div className="Footer_Small">
            <div className="Footer_Style">Privacy Policy</div>
            <div className="Footer_Style">How to buy</div>
            <div className="Footer_Style">Modern Slavery</div>
            <div className="Footer_Style">Cookie settings</div>
          </div>
          <div className="Footer_Small">
            <div className="Footer_Style">Contacts</div>
            <div className="Footer_Style">Help</div>
            <div className="Footer_Style">About Us</div>
          </div>
          <div className="Footer_Small">
            <div className="Footer_Style">Careers</div>
            <div className="Footer_Style">Terms & Conditions</div>
            <div className="Footer_Style">Press</div>
          </div>
        </div>
      </div>
    </>
  );
}

export default HomePage;
