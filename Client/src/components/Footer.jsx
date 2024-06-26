import React from "react";
import './HomePage.css'; // Import the CSS file

function Footer() {
  return (
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
  );
}

export default Footer;
