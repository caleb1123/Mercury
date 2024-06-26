import { memo, useState, useEffect } from "react";
import "./ViewJewelry.css";
import { NavLink, useParams, useNavigate } from "react-router-dom";
import axios from 'axios';
import line from './image/line-3.svg';
import { jwtDecode } from "jwt-decode";

// Category mapping based on the provided image
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

// Bid increment ranges
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

// Function to calculate the next bids
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

// Popup component for place bid
const Popup = ({ isOpen, handleClose, children }) => {
  return (
    isOpen && (
      <div className="popup-overlay">
        <div className="popup-content">
          <button className="close-button" onClick={handleClose}>X</button>
          {children}
        </div>
      </div>
    )
  );
};

const ViewJewelry = () => {
  const { id } = useParams(); // get the id from the route parameters
  const navigate = useNavigate(); // get the navigate function for navigation
  const [inputValue, setInputValue] = useState('');
  const [jewelry, setJewelry] = useState(null);
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [selectedBid, setSelectedBid] = useState(null); // state to hold the selected bid

  useEffect(() => {
    const fetchJewelryData = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/jewelry/${id}`, {
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
  }, [id]);

  // const getAccountIdFromUsername = async (username, token) => {
  //   try {
  //     const response = await axios.get(`http://localhost:8088/users/username/${username}`, {
  //       headers: {
  //         'Authorization': `Bearer ${token}`
  //       }
  //     });
  //     return response.data.accountId;
  //   } catch (error) {
  //     console.error('Error fetching accountId:', error);
  //     throw new Error('Unable to retrieve accountId', {username});
  //   }
  // };

  const handleClick = async () => {
    try {
      const token = localStorage.getItem('token'); // assuming token is stored in local storage

      if (!token) {
        throw new Error('No token found');
      }

      const decodedToken = jwtDecode(token);
      const username = decodedToken.sub;

      //const accountId = await getAccountIdFromUsername(username, token);

      const response = await axios.get(`http://localhost:8088/jewelry/${id}/auction`, {
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
        // accountId: accountId
      }, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      // Redirect to another page with selectedBid and jewelryId
      navigate('/ViewAuction', { state: { bid: selectedBid, jewelryId: id } });
    } catch (error) {
      console.error('Error creating bid:', error);
      alert('Error creating bid: ' + error.message);
    }
  };

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  const openPopup = () => {
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  const handleBidChange = (event) => {
    setSelectedBid(event.target.value); // update the selected bid
  };

  if (!jewelry) {
    return <div>Loading...</div>;
  }

  // Calculate the min and max estimate
  const estimate = jewelry.estimate;
  const minEstimate = (estimate * 0.85).toFixed(2);
  const maxEstimate = (estimate * 1.275).toFixed(2);

  // Get the next bids based on the starting price
  const nextBids = getNextBids(jewelry.startingPrice);

  return (
    <>
      <div className="Header">
        <div className="UpHeader">
          <NavLink to="/" className="Mercury">MERCURY</NavLink>

          <div className="Login_CreaAccount">
            <NavLink to="./SignUp" className="NavLink_Style">CREATE ACCOUNT</NavLink>
            <NavLink to="./Login" className="LoginStyle">LOGIN</NavLink>
          </div>
        </div>
        <div className="Line">
          <img src={line} alt="line" />
        </div>
        <div className="Down_Header">
          <div className="Bar_Home">
            AUCTIONS
            <NavLink to="./SendRequest" className="world_bar_style">SELL</NavLink>
            <div className="world_bar_style">RESULT</div>
            <NavLink to="./Category" className="world_bar_style">CATEGORY</NavLink>
            <div className="world_bar_style">BLOG</div>
          </div>
          <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />
        </div>
      </div>

      <div className="ViewJewelry">
        <div><h3 className="PageName_ViewJewelry">VIEW JEWELRY</h3></div>
        <div className="Jewelry">
          <div className="ImageFrame">
            <img className="Image" src={jewelry.image} alt={jewelry.jewelryName} />
          </div>
          <div className="info_ViewJewelry">
            <div className="JewelryName">{jewelry.jewelryName}</div>
            <div className="WordStyle_JewelryInfo">
              <span><strong>Estimate</strong></span> : ${minEstimate} - ${maxEstimate}<br />
              <span name="StartBid"><strong>Starting Bid</strong></span> : ${jewelry.startingPrice}
            </div>

            <button onClick={openPopup} className="PlaceBidButton">PLACE BID</button>
            <Popup isOpen={isPopupOpen} handleClose={closePopup}>
              <h2>PLACE BID</h2>
              <p>Enter the highest amount you are willing to bid. We'll bid you incrementally to keep you in the lead. </p>
              <div className="WordStyle_JewelryInfo">Maximum Bid ($)
                <span>
                  <select name="Bid_List" onChange={handleBidChange}>
                    {nextBids.map(bid => (
                      <option key={bid} value={bid}>{bid}</option>
                    ))}
                  </select>
                </span>
              </div>
              <button onClick={handleClick} className="PlaceBidNextButton">NEXT</button>
            </Popup>

            <div className="WordStyle_JewelryInfo"><span><strong>Category</strong></span> : {categoryMapping[jewelry.jewelryCategoryId]}</div>
            <div className="WordStyle_JewelryInfo"><span><strong>Gemstone</strong></span> : {jewelry.gemstone}</div>
            <div className="WordStyle_JewelryInfo"><span><strong>Condition</strong></span> : {jewelry.condition}</div>
            <img src={line} alt="line" />
            <div className="WordStyle_JewelryInfo"><span><strong>Description</strong></span></div>
            <div className="WordStyle_JewelryInfo">{jewelry.description}</div>
            <img src={line} alt="line" />
            <div className="WordStyle_JewelryInfo"><span><strong>Global Shipping</strong></span></div>
            <div className="WordStyle_JewelryInfo">With customers in over 100 countries, we provide fully insured global shipping, expertly arranged by our team. The shipping costs, determined based on the insured value of the package and its destination, will be calculated post-auction and added to your invoice. Please note, VAT, duties, or any additional charges related to international shipping are not included in these costs and remain the responsibility of the buyer.</div>
            <img src={line} alt="line" />
            <div className="WordStyle_JewelryInfo"><span><strong>Post-Auction Support</strong></span></div>
            <div className="WordStyle_JewelryInfo">As a full-service auction house, we take pride in the comprehensive range of post-auction services we offer, including ring resizing, stone replacement, and repair work. It's part of our commitment to ensure a seamless transaction and to cater to your needs even after the gavel falls. However, please note that the applicability of certain services may vary depending on the specifics of the lot. If you have any questions or need additional information such as a cost estimate, we encourage you to reach out to us.</div>
            <img src={line} alt="line" />
            <div className="WordStyle_JewelryInfo"><span><strong>Bidding Guidelines</strong></span></div>
            <div className="WordStyle_JewelryInfo">Please remember that once you have placed a bid on our platform, it cannot be retracted or reduced.</div>
            <img src={line} alt="line" />
            <div className="WordStyle_JewelryInfo"><span><strong>Conditions of Sale</strong></span></div>
            <div className="WordStyle_JewelryInfo">We encourage all potential bidders to consult our Conditions of Sale for comprehensive details. By placing a bid, you acknowledge that you have read and are bound by these conditions.</div>
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
};

export default memo(ViewJewelry);
