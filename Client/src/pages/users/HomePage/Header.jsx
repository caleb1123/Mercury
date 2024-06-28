import React from 'react';
import { NavLink, useNavigate } from "react-router-dom";
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import line from './image/line-3.svg';

const Header = ({ isLoggedIn, handleProfileClick }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.href = '/';
  };

  return (
    <div className="Header">
      <div className="UpHeader">
        <NavLink to="/" className="Mercury">MERCURY</NavLink>
        <div className="Login_CreaAccount">
          {isLoggedIn ? (
            <>
              <IconButton onClick={handleProfileClick} sx={{ color: 'white' }}>
                <Avatar alt="User Icon" />
              </IconButton>
            </>
          ) : (
            <>
              <NavLink to="./SignUp" className="NavLink_Style">CREATE ACCOUNT</NavLink>
              <NavLink to="./Login" className="LoginStyle">LOGIN</NavLink>
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
          <NavLink to="./SendRequest" className="world_bar_style">SELL</NavLink>
          <div className="world_bar_style">RESULT</div>
          <NavLink to="./Category" className="world_bar_style">CATEGORY</NavLink>
          <div className="world_bar_style">BLOG</div>
        </div>
        <input className="Search" type="text" placeholder="Search" />
      </div>
    </div>
  );
};

export default Header;
