import React, { memo, useState, useEffect } from "react";
import "./ViewJewelry.css";
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios';
import line from './image/line-3.svg';
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

const ViewJewelry = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [inputValue, setInputValue] = useState('');
  const [jewelry, setJewelry] = useState(null);
  const [images, setImages] = useState([]);
  const [selectedImage, setSelectedImage] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

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

    const fetchImagesData = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/jewelryImage/list/${id}`);
        const data = response.data.filter(img => img.status !== false);
        if (response.status !== 200) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Chuyển đổi liên kết Google Drive thành liên kết trực tiếp
        const formattedImages = data.map(image => ({
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

    fetchJewelryData();
    fetchImagesData();
  }, [id]);

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  const navigateToAuction = () => {
    navigate('/ViewAuction', { state: { jewelryId: id } });
  };

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  const handleThumbnailClick = (url) => {
    setSelectedImage(url);
  };

  if (!jewelry) {
    return <div>Loading...</div>;
  }

  const estimate = jewelry.estimate;
  const minEstimate = (estimate * 0.85).toFixed(2);
  const maxEstimate = (estimate * 1.275).toFixed(2);

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="ViewJewelry">
        <div><h3 className="PageName_ViewJewelry">VIEW JEWELRY</h3></div>
        <div className="Jewelry">
          <div className="ImageFrame">
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
          <div className="info_ViewJewelry">
            <div className="JewelryName">{jewelry.jewelryName}</div>
            <div className="WordStyle_JewelryInfo">
              <span><strong>Estimate</strong></span> : ${minEstimate} - ${maxEstimate}<br />
              <span name="StartBid"><strong>Starting Bid</strong></span> : ${jewelry.startingPrice}
            </div>

            <button onClick={navigateToAuction} className="PlaceBidButton">VIEW AUCTION</button>

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
      <Footer/>
    </>
  );
};

export default memo(ViewJewelry);

