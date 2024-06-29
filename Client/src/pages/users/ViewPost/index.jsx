import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from 'axios';
import line from './image/line-3.svg';
import "./ViewAuction.css";
import {jwtDecode} from 'jwt-decode';

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

const bidIncrements = [
  { price: 0, increment: 1 },
  { price: 20, increment: 5 },
  { price: 100, increment: 10 },
  { price: 250, increment: 25 },
  { price: 500, increment: 50 },
  { price: 1000, increment: 100 },
  { price: 5000, increment: 250 },
  { price: 25000, increment: 500 },
  { price: 50000, increment: 500 },
  { price: 100000, increment: 2500 },
  { price: 250000, increment: 5000 },
  { price: 1000000, increment: 10000 }
];

const getNextBids = (startingPrice) => {
  const nextBids = [];
  let currentPrice = startingPrice;

  for (let i = 0; i < 10; i++) {
    nextBids.push(currentPrice);
    const increment = bidIncrements.find(b => currentPrice < b.price).increment;
    currentPrice += increment;
  }

  return nextBids;
};

function ViewAuction() {
  const { state } = useLocation();
  const { jewelryId } = state || {};
  const [inputValue, setInputValue] = useState('');
  const [jewelry, setJewelry] = useState(null);
  const [auction, setAuction] = useState(null);
  const [bids, setBids] = useState([]);
  const [selectedBid, setSelectedBid] = useState(null);
  const [notification, setNotification] = useState({ type: '', message: '' });
  const [winner, setWinner] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("jewelryId:", jewelryId); // Log the jewelryId
    if (jewelryId) {
      fetchJewelryData();
      fetchAuctionData();
    }
  }, [jewelryId]);

  const fetchJewelryData = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8088/jewelry/${jewelryId}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      setJewelry(response.data);
    } catch (error) {
      console.error('Error fetching jewelry data:', error);
      console.error(error.response ? error.response.data : error.message);
      setNotification({ type: 'error', message: 'Error fetching jewelry data' });
    }
  };

  const fetchAuctionData = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8088/jewelry/${jewelryId}/auction`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      setAuction(response.data);
      fetchBidsData(response.data.auctionId);
    } catch (error) {
      console.error('Error fetching auction data:', error);
      console.error(error.response ? error.response.data : error.message);
      setNotification({ type: 'error', message: `Error fetching auction data: ${error.message}` });
    }
  };

  const fetchBidsData = async (auctionId) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8088/bid/list/${auctionId}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      setBids(response.data);
    } catch (error) {
      console.error('Error fetching bids data:', error);
      console.error(error.response ? error.response.data : error.message);
      setNotification({ type: 'error', message: `Error fetching bids data: ${error.message}` });
    }
  };

  const fetchWinnerData = async (auctionId) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8088/auction/${auctionId}/winner`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      setWinner(response.data);
    } catch (error) {
      console.error('Error fetching winner data:', error);
      console.error(error.response ? error.response.data : error.message);
      setNotification({ type: 'error', message: `Error fetching winner data: ${error.message}` });
    }
  };

  const fetchHighestBid = async (auctionId) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8088/auction/${auctionId}/highestBid`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      return response.data.bidAmount;
    } catch (error) {
      console.error('Error fetching highest bid:', error);
      console.error(error.response ? error.response.data : error.message);
      setNotification({ type: 'error', message: `Error fetching highest bid: ${error.message}` });
    }
  };

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleBidSubmit = async () => {
    try {
      const token = localStorage.getItem('token');

      if (!token) {
        throw new Error('No token found');
      }

      const decodedToken = jwtDecode(token);
      const username = decodedToken.sub;

      const response = await axios.get(`http://localhost:8088/jewelry/${jewelryId}/auction`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      const auctionId = response.data.auctionId;

      if (!auctionId) {
        throw new Error('Auction ID not found');
      }

      await axios.post('http://localhost:8088/bid/create', {
        bidAmount: selectedBid,
        auctionId: auctionId,
      }, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      // Fetch highest bid and update current price
      const highestBid = await fetchHighestBid(auctionId);
      setAuction(prevState => ({ ...prevState, currentPrice: highestBid }));

      setNotification({ type: 'success', message: 'Bid placed successfully!' });
    } catch (error) {
      console.error('Error creating bid:', error);
      console.error(error.response ? error.response.data : error.message);
      const errorMessage = error.response?.data || 'Unknown error occurred';
      setNotification({ type: 'error', message: `Error creating bid: ${errorMessage}` });
    }
  };

  const handleBidChange = (event) => {
    setSelectedBid(event.target.value);
  };

  const handleViewResultClick = () => {
    fetchWinnerData(auction.auctionId);
  };

  if (!jewelry || !auction) {
    return <div>Loading...</div>;
  }

  const nextBids = getNextBids(auction.currentPrice);

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
        {notification.message && (
          <div className={`notification ${notification.type}`}>
            {notification.message}
          </div>
        )}
        <div className="Current_Info">
          <div className="info_data">
            <div>Current Bid</div>
            <div className="info_data_style">${auction.currentPrice}</div>
          </div>
          <div className="info_data">
            <div>Bids</div>
            <div className="info_data_style">{bids.length}</div>
          </div>
          <div className="info_data">
            <div>Start Date</div>
            <div className="info_data_style">{new Date(auction.startDate).toLocaleDateString()}</div>
          </div>
          <div className="info_data">
            <div>End Date</div>
            <div className="info_data_style">{new Date(auction.endDate).toLocaleDateString()}</div>
          </div>
          <div className="info_data">
            <div>Status</div>
            <div className="info_data_style">{auction.status}</div>
          </div>
          <div className="PlaceBidContainer">
            <div className="WordStyle_JewelryInfo">Maximum Bid ($)
              <select name="Bid_List" onChange={handleBidChange}>
                {nextBids.map(bid => (
                  <option key={bid} value={bid}>{bid}</option>
                ))}
              </select>
            </div>
            <button onClick={handleBidSubmit} className="PlaceBidNextButton">Submit Bid</button>
          </div>
          {auction.status === 'Ended' && (
            <div className="ViewResultContainer">
              <button onClick={handleViewResultClick} className="ViewResultButton">View Result</button>
            </div>
          )}
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
            <h4>Bids history ({bids.length})</h4>
            {bids && bids.length > 0 ? (
              bids.map((bid, index) => (
                <div className="BidDetals" key={index}>
                  <div className="BidOwner">{bid.username}</div>
                  <div className="BidTime">{new Date(bid.bidTime).toLocaleTimeString()}</div>
                  <div className="BidPlaced">${bid.bidAmount}</div>
                </div>
              ))
            ) : (
              <div>No bids yet</div>
            )}
          </div>
          {winner && (
            <div className="WinnerInfo">
              <h3>Winner Information</h3>
              <div className="info_WordStyle"><strong>Winner ID:</strong> {winner.winnerId}</div>
              <div className="info_WordStyle"><strong>Username:</strong> {winner.username}</div>
              <div className="info_WordStyle"><strong>Bid Amount:</strong> ${winner.bidAmount}</div>
              <div className="info_WordStyle"><strong>Jewelry ID:</strong> {winner.jewelryId}</div>
              <div className="info_WordStyle"><strong>Jewelry Name:</strong> {winner.jewelryName}</div>
              <div className="info_WordStyle"><strong>Auction ID:</strong> {winner.auctionId}</div>
            </div>
          )}
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
