import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import line from "./image/line-3.svg";
import "./Header.css";

const auction_op = [
  { id: 1, name: "LIVE AUCTIONS" },
  { id: 2, name: "UPCOMING AUCTIONS" },
  { id: 3, name: "PAST AUCTIONS" },
];

const Header = ({ isLoggedIn, handleProfileClick }) => {
  const navigate = useNavigate();
  const [showCategories, setShowCategories] = useState(false);
  const [showAuctionOp, setShowAuctionOp] = useState(false);
  const [showProfileMenu, setShowProfileMenu] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  const handleAuctionMouseEnter = () => {
    setShowAuctionOp(true);
  };

  const handleAuctionMouseLeave = () => {
    setShowAuctionOp(false);
  };

  const handleProfileMouseEnter = () => {
    setShowProfileMenu(true);
  };

  const handleProfileMouseLeave = () => {
    setShowProfileMenu(false);
  };

  const handleClick = () => {
    window.location.href = "/Auctions/all";
  };

  const handleViewResult = () => {
    navigate("/ViewResult");
  };

  return (
    <div className="Header">
      <div className="UpHeader">
        <NavLink to="/" className="Mercury">
          MERCURY
        </NavLink>
        <div className="Login_CreaAccount">
          {isLoggedIn ? (
            <div 
              onMouseEnter={handleProfileMouseEnter}
              onMouseLeave={handleProfileMouseLeave}
              className="ProfileMenu"
            >
              <IconButton onClick={handleProfileClick} sx={{ color: "white" }}>
                <Avatar alt="User Icon" />
              </IconButton>
              {showProfileMenu && (
                <div className="ProfileDropdown">
                  <div className="ProfileItem" onClick={handleViewResult}>
                    View Result
                  </div>
                  <div className="Logout" onClick={handleLogout}>
                    Logout
                  </div>
                </div>
              )}
            </div>
          ) : (
            <NavLink to="/Login" className="LoginStyle">
              LOGIN
            </NavLink>
          )}
        </div>
      </div>
      <div className="Line">
        <img src={line} alt="line" />
      </div>
      <div className="Down_Header">
        <div className="Bar_Home">
          <div
            onMouseEnter={handleAuctionMouseEnter}
            onMouseLeave={handleAuctionMouseLeave}
            className="world_bar_style"
          >
            <div onClick={handleClick}>
              AUCTIONS
            </div>
            {showAuctionOp && (
              <div
                className="AuctionsList"
                onMouseEnter={handleAuctionMouseEnter}
                onMouseLeave={handleAuctionMouseLeave}
              >
                {auction_op.map((category) => (
                  <div
                    key={category.id}
                    className="CategoryItem"
                    onClick={() => navigate(`/Auctions/${category.id}`)}
                  >
                    {category.name}
                  </div>
                ))}
              </div>
            )}
          </div>
          <NavLink to="/SendRequest" className="world_bar_style">
            SELL
          </NavLink>
          <NavLink to="/ViewJewelryList" className="world_bar_style">CATEGORY</NavLink>
          <NavLink to="/ViewPost" className="world_bar_style">BLOG</NavLink>
        </div>
      </div>
    </div>
  );
};

export default Header;
