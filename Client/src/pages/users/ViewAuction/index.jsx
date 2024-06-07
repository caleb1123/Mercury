import * as React from "react";
import { useState } from "react";
import line from './image/line-3.svg'
import "./ViewAuction.css"
import AuPic from './image/14k Victorian Pearl & Turquoise Bangle_1.webp'


function ViewAuction() {
    const [inputValue, setInputValue] = useState('');

    // Hàm xử lý sự kiện khi người dùng nhập
    const handleChange = (event) => {
        setInputValue(event.target.value);
    };
    return (
        <>
            <div className="Header">
                <div className="UpHeader">
                    <div className="Mercury">MERCURY</div>
                    <div className="Login_CreaAccount">CREATE ACCOUNT
                        <div className="LoginStyle">LOGIN</div>
                    </div>
                </div>
                <div className="Line">
                    <img src={line} />
                </div>
                <div className="Down_Header">
                    <div className="Bar_Home">
                        {/* <Link href="#" underline="none" >
                            {'CATEGORY'}
                        </Link> */}
                        CATEGORY
                        <div className="world_bar_style" >SELL</div>
                        <div className="world_bar_style" >RESULT</div>
                        <div className="world_bar_style" >CATEGORY</div>
                        <div className="world_bar_style" >BLOG</div>


                    </div>
                    <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />

                </div>

            </div>
            <div className="ViewAuction">
                <div className="PageName_ViewAuction">View Auction</div>
                <div className="Current_Info">
                    <div className="info_data">
                        Current Bid
                        <div className="info_data_style">Bids</div>
                        <div className="info_data_style">End Date</div>
                    </div>
                    <div className="info_data">
                        $ 500.000  
                        <div className="info_data_style">   6</div>
                        <div className="info_data_style">9/June/2024</div>
                    </div>




                </div>
                <div className="AuctionName">14k Victorian Pearl & Turquoise Bangle</div>

                <div className="Auction_ViewAuction">
                    <div className="Au_info"  >
                        <div className="auction_pic_frame">
                            <img src={AuPic} className="AuPic" />
                        </div>
                        <h4>Details</h4>
                        <div className="info_WordStyle">
                            <span><strong>Category: </strong></span> Bracelet
                        </div>
                        <div className="info_WordStyle">
                            <span><strong>Description:  </strong></span> A late 19th century Victorian Period 14k Yellow Gold bangle bracelet with Turquoise and Pearl. Hinged filigree bracelet with engraved detail and seventeen alternating prong set Pearls and Turquoise stones. Marked 14k at clasp. 10.5 grams total weight. Minor wear. 2 1/2" long.
                        </div>
                    </div>
                    <div className="BidHistory">
                        <h4>Bids history (6)</h4>
                        <div className="BidDetals">
                            <div className="BidOwner">MinhTrung</div>
                            <div className="BidTime">11 hours ago</div>
                            <div className="BidPlaced"> $ 400.00</div>
                        </div>
                        <div className="BidDetals">
                            <div className="BidOwner">PhuocDuy</div>
                            <div className="BidTime">19 hours ago</div>
                            <div className="BidPlaced"> $ 350.00</div>
                        </div>
                        <div className="BidDetals">
                            <div className="BidOwner">KhanhVy</div>
                            <div className="BidTime">20 hours ago</div>
                            <div className="BidPlaced"> $ 325.00</div>
                        </div>
                        <div className="BidDetals">
                            <div className="BidOwner">NgocHan</div>
                            <div className="BidTime">21 hours ago</div>
                            <div className="BidPlaced"> $ 300.00</div>
                        </div>
                        <div className="BidDetals">
                            <div className="BidOwner">Nam</div>
                            <div className="BidTime">2 days ago</div>
                            <div className="BidPlaced"> $ 200.00</div>
                        </div>
                        <div className="BidDetals">
                            <div className="BidOwner">MinhTrung</div>
                            <div className="BidTime">2 days ago</div>
                            <div className="BidPlaced"> $ 100.00</div>
                        </div>
                        
                    </div>


                </div>
            </div>
            <div className="Footer">
        <div className="Mercury">MERCURY</div>
        <div className="Footer_Info">
          <div className="Footer_Small" >
            <div className="Footer_Style" >Privacy Policy</div>
            <div className="Footer_Style">How to buy</div>
            <div className="Footer_Style">Modern Slavery </div>
            <div className="Footer_Style">Cookie settings</div>
          </div>
          <div className="Footer_Small" >
            <div className="Footer_Style" >Contacts</div>
            <div className="Footer_Style">Help</div>
            <div className="Footer_Style">About Us </div>
          </div>
          <div className="Footer_Small" >
            <div className="Footer_Style" >Careers</div>
            <div className="Footer_Style">Terms & Conditions</div>
            <div className="Footer_Style">Press </div>
          </div>

        </div>



      </div>


        </>

    );
}
export default ViewAuction;