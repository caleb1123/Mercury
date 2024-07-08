import React, { useState } from 'react';
import { NavLink, useNavigate } from "react-router-dom";
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import line from './image/line-3.svg';

// Dữ liệu tĩnh cho các category, bạn có thể thay thế bằng API call để lấy dữ liệu từ backend
const categories = [
  { id: 1, name: "RINGS" },
  { id: 2, name: "BRACELETS" },
  { id: 3, name: "BROOCHES_PINS" },
  { id: 4, name: "CUFFLINKS_TIEPINS_TIECLIPS" },
  { id: 5, name: "EARRINGS" },
  { id: 6, name: "LOOSESTONES_BEADS" },
  { id: 7, name: "NECKLACES_PENDANTS" },
  { id: 8, name: "WATCHES" }
];

const Header = ({ isLoggedIn, handleProfileClick }) => {
  const navigate = useNavigate();
  const [showCategories, setShowCategories] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.href = '/';
  };

  const handleCategoryMouseEnter = () => {
    setShowCategories(true);
  };

  const handleCategoryMouseLeave = () => {
    setShowCategories(false);
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
              <button onClick={handleLogout} className="LogoutButton">Logout</button>
            </>
          ) : (
            <>
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
          <NavLink to="./SendRequest" className="world_bar_style">SELL</NavLink>
          <div 
            onMouseEnter={handleCategoryMouseEnter} 
            onMouseLeave={handleCategoryMouseLeave} 
            className="world_bar_style" 
            style={{ cursor: 'pointer' }}
          >
            CATEGORY
            {showCategories && (
              <div 
                className="CategoryList"
                onMouseEnter={handleCategoryMouseEnter}
                onMouseLeave={handleCategoryMouseLeave}
              >
                {categories.map(category => (
                  <div 
                    key={category.id} 
                    className="CategoryItem"
                    onClick={() => navigate(`/ViewJewelrylist/Category/${category.id}`)}
                  >
                    {category.name}
                  </div>
                ))}
              </div>
            )}
          </div>
          <div className="world_bar_style">BLOG</div>
        </div>
      </div>
    </div>
  );
};

export default Header;
