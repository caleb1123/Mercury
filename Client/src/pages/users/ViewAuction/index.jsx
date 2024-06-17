import * as React from "react";
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import axios from 'axios';
import line from './image/line-3.svg';
import "./ViewAuction.css";

// Predefined category mapping
const categoryMapping = {
  1: 'RINGS',
  2: 'BRACELETS',
  3: 'BROOCHES_PINS',
  4: 'CUFFLINKS_TIEPINS_TIECLIPS',
  5: 'EARRINGS',
  6: 'LOOSESTONES_BEADS',
  7: 'NECKLACES_PENDANTS',
  8: 'WATCHES'
};

function ViewAuction() {
  const { state } = useLocation();
  const { jewelryId, bid } = state || {};
  const [inputValue, setInputValue] = useState('');
  const [jewelry, setJewelry] = useState(null);
  const [auction, setAuction] = useState({
    currentBid: 500.0,
    bidCount: 6,
    endDate: '2024-06-09',
    bids: [
      { owner: 'MinhTrung', time: '2024-06-09T11:00:00Z', amount: 400.0 },
      { owner: 'PhuocDuy', time: '2024-06-08T19:00:00Z', amount: 350.0 },
      { owner: 'KhanhVy', time: '2024-06-08T20:00:00Z', amount: 325.0 },
      { owner: 'NgocHan', time: '2024-06-08T21:00:00Z', amount: 300.0 },
      { owner: 'Nam', time: '2024-06-07T00:00:00Z', amount: 200.0 },
      { owner: 'MinhTrung', time: '2024-06-07T00:00:00Z', amount: 100.0 },
    ]
  });

  useEffect(() => {
    if (jewelryId) {
      const fetchJewelryData = async () => {
        try {
          const response = await axios.get(`http://localhost:8088/jewelry/${jewelryId}`, {
            headers: {
              'Content-Type': 'application/json',
            },
          });

          if (response.status !== 200) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }

          setJewelry(response.data);
        } catch (error) {
          console.error('Error fetching jewelry data:', error);
        }
      };

      fetchJewelryData();
    }
  }, [jewelryId]);

  // Handle input change
  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  if (!jewelry) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <div className="Header">
        <div className="UpHeader">
          <div className="Mercury">MERCURY</div>
          <div className="Login_CreaAccount">CREATE ACCOUNT
            <div className="LoginStyle">LOGIN</div>
          </div>
        </div>
        <div className="Line">
          <img src={line} alt="line" />
        </div>
        <div className="Down_Header">
          <div className="Bar_Home">
            CATEGORY
            <div className="world_bar_style">SELL</div>
            <div className="world_bar_style">RESULT</div>
            <div className="world_bar_style">CATEGORY</div>
            <div className="world_bar_style">BLOG</div>
          </div>
          <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />
        </div>
      </div>
      <div className="ViewAuction">
        <div className="PageName_ViewAuction">View Auction</div>
        <div className="Current_Info">
          <div className="info_data">
            Current Bid
            <div className="info_data_style">Bids</div>
            <div className="info_data_style">End Date</div>
          </div>
          <div className="info_data">
            ${auction.currentBid}
            <div className="info_data_style">{auction.bidCount}</div>
            <div className="info_data_style">{new Date(auction.endDate).toLocaleDateString()}</div>
          </div>
        </div>
        <div className="AuctionName">{jewelry.jewelryName}</div>
        <div className="Auction_ViewAuction">
          <div className="Au_info">
            <div className="auction_pic_frame">
              <img src={jewelry.image} className="AuPic" alt="Auction" />
            </div>
            <h4>Details</h4>
            <div className="info_WordStyle">
              <span><strong>Category: </strong></span> {categoryMapping[jewelry.jewelryCategoryId]}
            </div>
            <div className="info_WordStyle">
              <span><strong>Description: </strong></span> {jewelry.description}
            </div>
          </div>
          <div className="BidHistory">
            <h4>Bids history ({auction.bidCount})</h4>
            {auction.bids && auction.bids.length > 0 ? (
              auction.bids.map((bid, index) => (
                <div className="BidDetals" key={index}>
                  <div className="BidOwner">{bid.owner}</div>
                  <div className="BidTime">{new Date(bid.time).toLocaleTimeString()}</div>
                  <div className="BidPlaced">${bid.amount}</div>
                </div>
              ))
            ) : (
              <div>No bids yet</div>
            )}
          </div>
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

export default ViewAuction;
