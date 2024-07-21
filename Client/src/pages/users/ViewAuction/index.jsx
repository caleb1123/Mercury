import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from 'axios';
import "./ViewAuction.css";
import {jwtDecode} from 'jwt-decode';
import Header from "../Header";
import Footer from '../Footer'



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
  { price: 50000, increment: 500 }
];

const getNextBids = (startingPrice) => {
  const nextBids = [];
  let currentPrice = startingPrice;

  for (let i = 0; i < 10; i++) {
    nextBids.push(currentPrice);
    const incrementObj = bidIncrements.find(b => currentPrice < b.price) || bidIncrements[bidIncrements.length - 1];
    const increment = incrementObj.increment;
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
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false); 
  const [images, setImages] = useState([]);
  const [selectedImage, setSelectedImage] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    console.log("jewelryId:", jewelryId);
    if (jewelryId) {
      fetchJewelryData();
      fetchAuctionData();
      fetchImagesData();
    }
  }, [jewelryId]);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    } else {
      navigate('/login');
    }
    fetchJewelryData();
  }, []);

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
      if (error.response && error.response.status === 401) {
        navigate('/login');
      } else {
        console.error('Error fetching jewelry data:', error);
        console.error(error.response ? error.response.data : error.message);
        setNotification({ type: 'error', message: 'Error fetching jewelry data' });
      }
    }
  };

  const fetchImagesData = async () => {
    try {
      const response = await axios.get(`http://localhost:8088/jewelryImage/all/${jewelryId}`);
      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const formattedImages = response.data.map(image => ({
        ...image,
        jewelryImageURL: image.jewelryImageURL.replace("uc?id=", "uc?export=view&id=")
      }));

      setImages(formattedImages);
      if (formattedImages.length > 0) {
        setSelectedImage(formattedImages[0].jewelryImageURL);
      }
      console.log('Formatted images:', formattedImages);
    } catch (error) {
      console.error('Error fetching images data:', error);
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

      const auctionData = response.data;
      setAuction(auctionData);
      fetchBidsData(auctionData.auctionId);
    } catch (error) {
      if (error.response && error.response.status === 401) {
        navigate('/login');
      } else {
        console.error('Error fetching auction data:', error);
        console.error(error.response ? error.response.data : error.message);
        setNotification({ type: 'error', message: `Error fetching auction data: ${error.message}` });
      }
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

      const bidsData = response.data;
      setBids(bidsData);

      if (bidsData.length > 0) {
        const highestBid = Math.max(...bidsData.map(bid => bid.bidAmount));
        setAuction(prevState => ({ ...prevState, currentPrice: highestBid }));
      } else if (jewelry) {
        setAuction(prevState => ({ ...prevState, currentPrice: jewelry.startingPrice }));
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        navigate('/login');
      } else {
        console.error('Error fetching bids data:', error);
        console.error(error.response ? error.response.data : error.message);
        setNotification({ type: 'error', message: `Error fetching bids data: ${error.message}` });
      }
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
      if (error.response && error.response.status === 401) {
        navigate('/login');
      } else {
        console.error('Error fetching winner data:', error);
        console.error(error.response ? error.response.data : error.message);
        setNotification({ type: 'error', message: `Error fetching winner data: ${error.message}` });
      }
    }
  };

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };
  
  const handleProfileClick = () => {
    navigate('/viewProfile');
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

      const newBid = {
        bidAmount: selectedBid,
        username: username,
        bidTime: new Date().toISOString(),
      };
      setBids(prevBids => [newBid, ...prevBids]);
      setAuction(prevState => ({ ...prevState, currentPrice: selectedBid }));

      setNotification({ type: 'success', message: 'Bid placed successfully!' });
      window.location.reload();
    } catch (error) {
      if (error.response && error.response.status === 401) {
        navigate('/login');
      } else {
        console.error('Error creating bid:', error);
        console.error(error.response ? error.response.data : error.message);
        const errorMessage = error.response?.data || 'Unknown error occurred';
        setNotification({ type: 'error', message: `Error creating bid: ${errorMessage}` });
      }
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
      if (error.response && error.response.status === 401) {
        navigate('/login');
      } else {
        console.error('Error fetching highest bid:', error);
        console.error(error.response ? error.response.data : error.message);
        setNotification({ type: 'error', message: `Error fetching highest bid: ${error.message}` });
      }
    }
  };

  const handleBidChange = (event) => {
    setSelectedBid(event.target.value);
  };

  const handleViewResultClick = () => {
    fetchWinnerData(auction.auctionId);
    setIsModalOpen(true); 
  };

  const closeModal = () => {
    setIsModalOpen(false); 
  };

  const handleThumbnailClick = (url) => {
    setSelectedImage(url);
  };

  if (!jewelry || !auction) {
    return <div>Loading...</div>;
  }

  const nextBids = getNextBids(auction.currentPrice);

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />

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
              <img className="Image" src={selectedImage} alt={jewelry.jewelryName} onError={(e) => { e.target.onerror = null; e.target.src="fallback_image_url_here"; }} />
              <div className="Thumbnails">
                {images.map((image, index) => (
                  <img
                    key={index}
                    className={`Thumbnail ${selectedImage === image.jewelryImageURL ? 'selected' : ''}`}
                    src={image.jewelryImageURL}
                    alt={`${jewelry.jewelryName} ${index + 1}`}
                    onClick={() => handleThumbnailClick(image.jewelryImageURL)}
                    onError={(e) => { e.target.onerror = null; e.target.src="fallback_thumbnail_url_here"; }}
                  />
                ))}
              </div>
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
        </div>
      </div>

      {/* Modal */}
      {isModalOpen && (
        <div className="modal-overlay">
          <div className="modal">
            <button className="close-button" onClick={closeModal}>
              &times;
            </button>
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
      )}

<Footer/>

    </>
  );
}

export default ViewAuction;
