import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import "./Category.css";
import Bracelet from './image/image-25@2x.png';
import broochespins from './image/image-26@2x.png';
import cuffin from './image/image-27@2x.png';
import earing from './image/image-28@2x.png';
import loosestone from './image/image-29@2x.png';
import necklace from './image/image-30@2x.png';
import ring from './image/image-31@2x.png';
import watch from './image/image-32@2x.png';
import line from './image/line-3.svg';
import { IconButton } from "@mui/material";
import Avatar from '@mui/material/Avatar';

function Category() {
  const [inputValue, setInputValue] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Check for token in local storage
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  // Handle search input change
  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  // Handle view jewelry list button click
  const handleClick = () => {
    window.location.href = '/ViewJewelryList';
  };

  // Handle logout click
  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    window.location.href = '/';
  };

  return (
    <>
      <div className="Header">
        <div className="UpHeader">
          <NavLink to="/" className="Mercury">MERCURY</NavLink>
          <div className="Login_CreaAccount">
            {isLoggedIn ? (
              <>
                <IconButton onClick={handleLogout} sx={{ color: 'white' }}>
                  <Avatar alt="User Icon" />
                </IconButton>
              </>
            ) : (
              <>
                <NavLink to="/SignUp" className="NavLink_Style">CREATE ACCOUNT</NavLink>
                <NavLink to="/Login" className="LoginStyle">LOGIN</NavLink>
              </>
            )}
          </div>
        </div>
        <div className="Line">
          <img src={line} alt="line" />
        </div>
        <div className="Down_Header">
          <div className="Bar_Home">
            <NavLink to="/Auctions" className="world_bar_style">AUCTIONS</NavLink>
            <NavLink to="/SendRequest" className="world_bar_style">SELL</NavLink>
            <NavLink to="/Result" className="world_bar_style">RESULT</NavLink>
            <NavLink to="/Category" className="world_bar_style">CATEGORY</NavLink>
            <NavLink to="/Blog" className="world_bar_style">BLOG</NavLink>
          </div>
          <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />
        </div>
      </div>
      <div className="Category">
        <div className="PageName_Category">LIST OF CATEGORIES</div>
        <div className="CategoryFrame">
          <div className="Bracelet">
            <img className="image" src={Bracelet} alt="Bracelet" />
            <div className="WordStyle_Category">Bracelet</div>
          </div>
          <div className="brooches-pins">
            <img className="image" src={broochespins} alt="Brooches & Pins" />
            <div className="WordStyle_Category">Brooches & Pins</div>
          </div>
          <div className="Cuffin">
            <img className="image" src={cuffin} alt="Cufflinks, Tie Pins & Tie Clips" />
            <div className="WordStyle_Category">Cufflinks, Tie Pins & Tie Clips</div>
          </div>
          <div className="Earring">
            <img className="image" src={earing} alt="Earrings" />
            <div className="WordStyle_Category">Earrings</div>
          </div>
          <div className="LooseStone">
            <img className="image" src={loosestone} alt="Loose Stones & Beads" />
            <div className="WordStyle_Category">Loose Stones & Beads</div>
          </div>
          <div className="Necklaces">
            <img className="image" src={necklace} alt="Necklaces & Pendants" />
            <div className="WordStyle_Category">Necklaces & Pendants</div>
          </div>
          <div className="Ring">
            <img className="image" src={ring} alt="Rings" />
            <div className="WordStyle_Category">Rings</div>
          </div>
          <div className="Watches">
            <img className="image" src={watch} alt="Watches" />
            <div className="WordStyle_Category">Watches</div>
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

export default Category;
