import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import line from "./image/line-3.svg";
import "./Header.css";

const categories = [
  { id: 1, name: "RINGS" },
  { id: 2, name: "BRACELETS" },
  { id: 3, name: "BROOCHES_PINS" },
  { id: 4, name: "CUFFLINKS_TIEPINS_TIECLIPS" },
  { id: 5, name: "EARRINGS" },
  { id: 6, name: "LOOSESTONES_BEADS" },
  { id: 7, name: "NECKLACES_PENDANTS" },
  { id: 8, name: "WATCHES" },
];

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

  const handleCategoryMouseEnter = () => {
    setShowCategories(true);
  };
  const handleAuctionMouseEnter = () => {
    setShowAuctionOp(true);
  };

  const handleCategoryMouseLeave = () => {
    setShowCategories(false);
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
                  <div
                    className="ProfileItem"
                    onClick={() => navigate("/viewprofile")}
                  >
                    View Profile
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
            AUCTIONS
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
          <div
            onMouseEnter={handleCategoryMouseEnter}
            onMouseLeave={handleCategoryMouseLeave}
            className="world_bar_style"
          >
            CATEGORY
            {showCategories && (
              <div
                className="CategoryList"
                onMouseEnter={handleCategoryMouseEnter}
                onMouseLeave={handleCategoryMouseLeave}
              >
                {categories.map((category) => (
                  <div
                    key={category.id}
                    className="CategoryItem"
                    onClick={() => navigate(`/category/${category.id}`)}
                  >
                    {category.name}
                  </div>
                ))}
              </div>
            )}
          </div>
          <NavLink to="/ViewPost" className="world_bar_style">BLOG</NavLink>
        </div>
      </div>
    </div>
  );
};

export default Header;
