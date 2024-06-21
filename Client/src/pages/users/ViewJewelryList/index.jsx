import * as React from "react";
import { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import axios from 'axios';
import "./ViewJewelryList.css";
import line from './image/line-3.svg';

function ViewJewelryList() {
  const [inputValue, setInputValue] = useState('');
  const [jewelryList, setJewelryList] = useState([]);
  const navigate = useNavigate();

  // Handle input change
  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  // Fetch jewelry data from API
  useEffect(() => {
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

      <div className="ViewJewelryList">
        {jewelryList.map((jewelry) => (
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

export default ViewJewelryList;
