import * as React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import "./ViewJewelryList.css";
import Header from "./Header";

function ViewJewelryList() {
  const [inputValue, setInputValue] = useState('');
  const [jewelryList, setJewelryList] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [searchError, setSearchError] = useState('');

  const navigate = useNavigate();

  // Handle input change
  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  // Fetch jewelry data from API
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

  // Filter jewelry list based on status
  const filteredJewelryList = jewelryList.filter(jewelry => jewelry.status === true);

  // Handle search
  const handleSearch = async () => {
    if (!inputValue.trim()) {
      // If input is empty, fetch all jewelry data
      fetchJewelryData();
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8088/jewelry/search/${inputValue}`, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = response.data;
      if (data.length === 0) {
        setSearchError('Jewelry not found');
        setJewelryList([]);
      } else {
        setJewelryList(data);
        setSearchError('');
      }
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setSearchError('Jewelry not found');
        setJewelryList([]);
      } else {
        console.error('Error searching jewelry data:', error);
        setSearchError('Error searching jewelry data');
      }
    }
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
        <button onClick={handleSearch}>Search</button>
      </div>

      {searchError && <div className="error-message">{searchError}</div>}

      <div className="ViewJewelryList">
        {filteredJewelryList.length > 0 ? (
          filteredJewelryList.map((jewelry) => (
            <div key={jewelry.jewelryId} className="jewelry-item">
              <button onClick={() => handleItemClick(jewelry.jewelryId)} className="image-button">
                <img src={jewelry.image} alt={jewelry.jewelryName} className="jewelry-image" />
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

      <div className="Footer">
        <div className="Footer_style">© MERCURY AUCTION LLC 2024</div>
      </div>
    </>
  );
}

export default ViewJewelryList;
