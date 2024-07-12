import React, { useState, useEffect } from "react";
import ContactInfo from "./ContactInfo";
import "./style.css";
import Header from "../Header";
import Footer from "../Footer";
import { useNavigate } from "react-router-dom";

function AboutMercury() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleProfileClick = () => {
    navigate("/viewProfile");
  };
  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <section className="about-mercury">
        <div className="content-wrapper">
          <div className="main-content-ab">
            <div className="left-column">
              <article className="info-box">
                <div className="info-content">
                  <h1 className="title">About MERCURY®</h1>
                  <div className="description-container">
                    <div className="description-content">
                      <div className="text-column">
                        <div className="text-content">
                          <div className="header-text">
                            <span className="large-letter">H</span>
                            <p className="header-description">
                              eadquartered on Fifth <br /> Avenue in New York
                              City,
                            </p>
                          </div>
                          <p className="main-description">
                            MERCURY is the leading boutique <br /> fine jewelry
                            and watch auction <br /> house. MERCURY provides a
                            <br /> secure, transparent, trusted, and <br />{" "}
                            regulated global auction <br /> marketplace to buy
                            and sell fine <br /> jewelry, gemstones, and watches{" "}
                            <br /> to clients in more than 100 <br /> countries.
                          </p>
                        </div>
                      </div>
                      <div className="image-column">
                        <img
                          loading="lazy"
                          src="https://cdn.builder.io/api/v1/image/assets/TEMP/89901ad0699f419c8c1351fc41f0c4a9ebd79391529c981aed94c3bbbae4bc5f?apiKey=107bd0f933454a5db22e531b7b3a4de4&"
                          className="auction-image"
                          alt="MERCURY auction event"
                        />
                      </div>
                    </div>
                  </div>
                  <p className="image-caption">
                    One of MERCURY's recent auctions attended live and online by
                    bidders from <br /> around the world.
                  </p>
                </div>
                <div className="seller-info">
                  <div className="seller-header">
                    <h2 className="seller-title">
                      For our consignors / sellers
                    </h2>
                    <p className="seller-subtitle">
                      , MERCURY provides a means to maximize the value achieved
                      for their
                    </p>
                  </div>
                  <p className="seller-description">
                    jewelry and watches by reaching and encouraging competition
                    among bidders in many countries, <br /> without the upfront
                    risk (in the form of enormous fixed fees) that they might
                    otherwise have to shoulder <br /> when working with one of
                    the "big houses", and with white glove client service that
                    is unparalleled in the <br /> industry and made available to
                    all of our clients—not just a select few.
                  </p>
                  <button className="action-button">Selling At Auction</button>
                </div>
                <div className="buyer-info">
                  <h2 className="buyer-title">For our bidders / buyers</h2>
                  <p className="buyer-subtitle">
                    , MERCURY provides guaranteed authenticity for the jewelry,
                    gemstones, and
                  </p>
                </div>
                <p className="buyer-description">
                  watches that they bid on, detailed and accurate product
                  descriptions and photography that they can <br /> trust and
                  rely on, and unmatched white glove client service to ensure
                  that any questions they have or <br /> information they need
                  is provided.
                </p>
                <button className="action-button">Buying at Auction</button>
                <p className="additional-info">
                  In addition to bi-weekly live and online auctions, MERCURY
                  offers appraisal service arrangements. If you <br /> have an
                  existing collection, estate or trust that is in need of
                  appraisal services, regardless of your location, <br /> our
                  extensive network of professionals can provide you the
                  services you need. To inquire, simply contact <br /> us via
                  our quick contact us form which you can access
                </p>
              </article>
            </div>
            <div className="right-column">
              <ContactInfo />
            </div>
          </div>
        </div>
      </section>
      <Footer />
    </>
  );
}

export default AboutMercury;
