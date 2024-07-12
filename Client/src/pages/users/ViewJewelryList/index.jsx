import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import "./ViewJewelryList.css";
import Header from "../Header";
import Footer from '../Footer'

const categories = [
  { jewelryCategoryId: 1, category_name: "RINGS" },
  { jewelryCategoryId: 2, category_name: "BRACELETS" },
  { jewelryCategoryId: 3, category_name: "BROOCHES_PINS" },
  { jewelryCategoryId: 4, category_name: "CUFFLINKS_TIEPINS_TIECLIPS" },
  { jewelryCategoryId: 5, category_name: "EARRINGS" },
  { jewelryCategoryId: 6, category_name: "LOOSESTONES_BEADS" },
  { jewelryCategoryId: 7, category_name: "NECKLACES_PENDANTS" },
  { jewelryCategoryId: 8, category_name: "WATCHES" },
];

function ViewJewelryList() {
  const [inputValue, setInputValue] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [jewelryList, setJewelryList] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [searchError, setSearchError] = useState('');
  const [imageUrls, setImageUrls] = useState({});

  const navigate = useNavigate();

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleCategoryChange = (event) => {
    setSelectedCategory(event.target.value);
  };

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  const fetchJewelryData = async () => {
    try {
      const response = await axios.get('http://localhost:8088/jewelry/getAll', {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = response.data;
      setJewelryList(data);
      setSearchError('');
      data.forEach(jewelry => fetchJewelryImage(jewelry.jewelryId));
    } catch (error) {
      console.error('Error fetching jewelry data:', error);
      setSearchError('Error fetching jewelry data');
    }
  };

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
    fetchJewelryData();
  }, []);

  const handleItemClick = (id) => {
    navigate(`/ViewJewelry/${id}`);
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
        setImageUrls(prevState => ({
          ...prevState,
          [id]: data.jewelryImageURL,
        }));
      }
    } catch (error) {
      console.error('Error fetching jewelry image:', error);
    }
  };

  const filteredJewelryList = jewelryList.filter(jewelry => {
    const matchesCategory = selectedCategory ? jewelry.jewelryCategoryId === parseInt(selectedCategory) : true;
    const matchesSearch = jewelry.jewelryName.toLowerCase().includes(inputValue.toLowerCase());
    return jewelry.status === true && matchesCategory && matchesSearch;
  });

  const handleSearch = () => {
    setSearchError('');
  };

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />

      <div className="search-container">
        <input
          type="text"
          value={inputValue}
          onChange={handleChange}
          placeholder="Search jewelry..."
        />
        <select value={selectedCategory} onChange={handleCategoryChange}>
          <option value="">All Categories</option>
          {categories.map((category) => (
            <option key={category.jewelryCategoryId} value={category.jewelryCategoryId}>
              {category.category_name}
            </option>
          ))}
        </select>
      </div>

      {searchError && <div className="error-message">{searchError}</div>}

      <div className="ViewJewelryList">
        {filteredJewelryList.length > 0 ? (
          filteredJewelryList.map((jewelry) => (
            <div key={jewelry.jewelryId} className="jewelry-item">
              <button onClick={() => handleItemClick(jewelry.jewelryId)} className="image-button">
                {imageUrls[jewelry.jewelryId] ? (
                  <img
                    src={imageUrls[jewelry.jewelryId]}
                    alt={jewelry.jewelryName}
                    className="jewelry-image"
                  />
                ) : (
                  <div>Loading image...</div>
                )}
              </button>
              <div className="jewelry-info">
                <h3>{jewelry.jewelryName}</h3>
                <p>Starting Price: ${jewelry.startingPrice}</p>
              </div>
            </div>
          ))
        ) : (
          !searchError && <div>No jewelry available</div>
        )}
      </div>
      <Footer/>
    </>
  );
}

export default ViewJewelryList;
