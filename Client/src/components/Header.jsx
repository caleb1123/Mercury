import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import IconButton from '@mui/material/IconButton';
import Avatar from '@mui/material/Avatar';
import line from '../users/HomePage/image/line-3.svg';
import '/FPT/ki5/SWP391/code/Mercury/Client/src/pages/users/HomePage/HomePage.css'; // Import the CSS file

function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    window.location.href = '/';
  };

  return (
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
          AUCTIONS
          <NavLink to="/SendRequest" className="world_bar_style">SELL</NavLink>
          <div className="world_bar_style">RESULT</div>
          <NavLink to="/Category" className="world_bar_style">CATEGORY</NavLink>
          <div className="world_bar_style">BLOG</div>
        </div>
        <input className="Search" type="text" placeholder="Search" />
      </div>
    </div>
  );
}

export default Header;
