import * as React from "react";
import { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import axios from 'axios';
import "./ViewJewelryList.css";
import line from './image/line-3.svg';
import Header from "./Header";

function ViewJewelryList() {
  const [inputValue, setInputValue] = useState('');
  const [jewelryList, setJewelryList] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const navigate = useNavigate();


  // Handle input change
  const handleChange = (event) => {
    setInputValue(event.target.value);
  };


  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  // Fetch jewelry data from API
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
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
      } catch (error) {
        console.error('Error fetching jewelry data:', error);
      }
    };

    fetchJewelryData();
  }, []);

  const handleItemClick = (id) => {
    navigate(`/ViewJewelry/${id}`);
  };

  // Filter jewelry list based on status
  const filteredJewelryList = jewelryList.filter(jewelry => jewelry.status === true);

  return (
    <>
            <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />


      <div className="ViewJewelryList">
        {filteredJewelryList.map((jewelry) => (
          <div key={jewelry.jewelryId} className="jewelry-item">
            <button onClick={() => handleItemClick(jewelry.jewelryId)} className="image-button">
              <img src={jewelry.image} alt={jewelry.jewelryName} className="jewelry-image" />
            </button>
            <div className="jewelry-info">
              <h3>{jewelry.jewelryName}</h3>
              <p>Starting Price: ${jewelry.startingPrice}</p>
            </div>
          </div>
        ))}
      </div>


      <div className="Footer">
        <div className="Footer_style">© MERCURY AUCTION LLC 2024</div>
      </div>
    </>
  );
}

export default ViewJewelryList;
