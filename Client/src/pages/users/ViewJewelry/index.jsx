import { memo } from "react";
import { useState } from "react";
import "./ViewJewelry.css";
import pic1 from './image/image-2@2x.png'
import pic2 from './image/image-3@2x.png'
import pic3 from './image/image-4@2x.png'
import line from './image/line-3.svg'


const Popup = ({ isOpen, handleClose, children }) => {
  return (
    isOpen && (
      <div className="popup-overlay">
        <div className="popup-content">
          <button className="close-button" onClick={handleClose}>X</button>
          {children}
        </div>
      </div>
    )
  );
};

const ViewJewelry = () => {
  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const openPopup = () => {
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };
  return (
    <>

      <h1>Header</h1>

      <div className="ViewJewelry">
        <div><h3 className="PageName_ViewJewelry">VIEW JEWELRY</h3></div>
        <div className="Jewelry">
          <div className="ImageFrame">
            <img className="Image" src={pic1} />
            <img className="Image" src={pic2} />
            <img className="Image" src={pic3} />
          </div>
          <div className="info">
            <div className="JewelryName">14k Victorian Pearl & Turquoise Bangle</div>
            <div className="WordStyle_JewelryInfo">
              <span><strong>Estimate</strong></span> : $400-$600<br />
              <span name="StartBid"><strong>Starting Bid</strong></span> : $375
            </div>

            <button onClick={openPopup} className="PlaceBidButton">PLACE BID</button>
              <Popup isOpen={isPopupOpen} handleClose={closePopup}>
              <h2>PLACE BID</h2>
              <p>Enter the highest amount you are willing to bid. We'll bid you incrementally to keep you in the lead. </p>
              <div className="WordStyle_JewelryInfo" >Maximum Bid ($) 
                <span>
                  <select  name="Bid_List">
                    <option>375</option>
                    <option>400</option>
                    <option>425</option>
                    <option>450</option>
                    <option>475</option>
                    <option>500</option>
                    <option>525</option>
                    <option>550</option>
                  </select>
                </span>
              </div>
              <button class="PlaceBidNextButton">NEXT</button>

              </Popup>

            <div className="WordStyle_JewelryInfo"><span><strong>Category</strong></span> : Bracelet</div>
            <img src={line}></img>
            <div className="WordStyle_JewelryInfo"><span><strong>Description</strong></span> </div>
            <div className="WordStyle_JewelryInfo">A late 19th century Victorian Period 14k Yellow Gold bangle bracelet with Turquoise and Pearl. Hinged filigree bracelet with engraved detail and seventeen alternating prong set Pearls and Turquoise stones. Marked 14k at clasp. 10.5 grams total weight. Minor wear. 2 1/2" long. ESTIMATE $400-600</div>
            <img src={line}></img>
            <div className="WordStyle_JewelryInfo"><span><strong>Condition</strong></span> </div>
            <div className="WordStyle_JewelryInfo">Minor wear</div>
            <img src={line}></img>
            <div className="WordStyle_JewelryInfo"><span><strong>Global Shipping</strong></span> </div>
            <div className="WordStyle_JewelryInfo">With customers in over 100 countries, we provide fully insured global shipping, expertly arranged by our team. The shipping costs, determined based on the insured value of the package and its destination, will be calculated post-auction and added to your invoice. Please note, VAT, duties, or any additional charges related to international shipping are not included in these costs and remain the responsibility of the buyer.</div>
            <img src={line}></img>
            <div className="WordStyle_JewelryInfo"><span><strong>Post-Auction Support</strong></span> </div>
            <div className="WordStyle_JewelryInfo">As a full-service auction house, we take pride in the comprehensive range of post-auction services we offer, including ring resizing, stone replacement, and repair work. It's part of our commitment to ensure a seamless transaction and to cater to your needs even after the gavel falls. However, please note that the applicability of certain services may vary depending on the specifics of the lot. If you have any questions or need additional information such as a cost estimate, we encourage you to reach out to us.</div>
            <img src={line}></img>
            <div className="WordStyle_JewelryInfo"><span><strong>Bidding Guidelines</strong></span> </div>
            <div className="WordStyle_JewelryInfo">Please remember that once you have placed a bid on our platform, it cannot be retracted or reduced.</div>
            <img src={line}></img>
            <div className="WordStyle_JewelryInfo"><span><strong>Conditions of Sale</strong></span> </div>
            <div className="WordStyle_JewelryInfo">We encourage all potential bidders to consult our Conditions of Sale for comprehensive details. By placing a bid, you acknowledge that you have read and are bound by these conditions.</div>


          </div>
        </div>


      </div>
      <h1>Footer</h1>

    </>

  );
};
export default memo(ViewJewelry);