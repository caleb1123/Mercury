import * as React from "react";
import { Link } from 'react-router-dom';
import { useState } from "react";
import "./HomePage.css"
import line from './image/line-3.svg'
import Homebackground from './image/BackGround.png'
import Ring from './image/Ring.webp'
import Pendant from './image/Pendant.webp'
import Necklace from './image/Necklace.webp'
import Earing from './image/Earing.webp'
import recommended2 from './image/Recommended2.webp'
import recommended3 from './image/Recommended3.webp'
import Bidnow1 from './image/image-10@2x.png'
import Bidnow2 from './image/image-11@2x.png'
import Bidnow3 from './image/image-12@2x.png'



function HomePage(){
    
    const [inputValue, setInputValue] = useState('');

    // Hàm xử lý sự kiện khi người dùng nhập
    const handleChange = (event) => {
      setInputValue(event.target.value);
    };

    return(
        <>
        <div className="Header">
            <div className="UpHeader">
                <div className="Mercury">MERCURY</div>
                <div className="Login_CreaAccount">CREATE ACCOUNT
                    <div className="LoginStyle">LOGIN</div>
                </div>
            </div>
            <div className="Line">
                <img src={line}/>
            </div>
            <div className="Down_Header">
                <div className="Bar">
                    AUTIONS
                    <div className="world_bar_style" >SELL</div>
                    <div className="world_bar_style" >RESULT</div>
                    <div className="world_bar_style" >CATEGORY</div>
                    <div className="world_bar_style" >BLOG</div>


                </div>            
            <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />

            </div>

        </div>
        <div className="HomePage">
            <div className="HomeBackGround">
                <img className="BackGrounPic" src={Homebackground} />
                <div className="Introduction">
                    <span><h3>Welcome to Mercury,</h3></span> Your premier destination for exclusive online jewelry auctions. Discover a curated selection of exquisite pieces, from timeless classics to contemporary designs, all available at your fingertips. Whether you're a seasoned collector or searching for the perfect piece to elevate your style, Mercury offers unparalleled access to unique and luxurious jewelry. Join our community of discerning buyers and sellers and experience the thrill of bidding on high-quality, authenticated items from the comfort of your home. Start exploring today and let your next treasure find you at Mercury.
                    <div></div>                   
                    <button className="ViewJewelries" >View Jewelries</button>
                </div>
            </div>
            <div className="UpcomingAuctionsTitle"><h3>UPCOMING AUCTIONS</h3></div>
            <div className="UpcomingAuctions">
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={Ring} ></img>
                    <div className="JewelryDate_HomePage_Upcoming">2 days left</div>
                    <div className="JewelryName_HomePage">Circa 1923 Art Deco 1.83 Carat Diamond Engagement Ring in 18K White Gold</div>
                    <strong>2,300($)</strong>
                </div>
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={Pendant} ></img>
                    <div className="JewelryDate_HomePage_Upcoming">3 days left</div>
                    <div className="JewelryName_HomePage">Lovely 14K White Gold and Green Gemstone Pendant</div>
                    <strong>60($)</strong>
                </div>
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={Necklace} ></img>
                    <div className="JewelryDate_HomePage_Upcoming">1 days left</div>
                    <div className="JewelryName_HomePage">ORIGINAL BY STEFAN DINU FABULOUS NECKLACE STATEMENT ONYX AND ROSE QUARTZ</div>
                    <strong>700($)</strong>
                </div>
            </div>
            <div></div>
            <div></div>

            <div className="UpcomingAuctionsTitle"><h3>RECOMMENDED AUCTIONS</h3></div>
            <div className="UpcomingAuctions">
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={Earing} ></img>
                    <div className="JewelryDate_HomePage">Jun 18, 2024 9:00 PM GMT+7</div>
                    <div className="JewelryName_HomePage">Diamond Stud Earrings 2.35 ctw</div>
                    <strong>20,000($)</strong>
                </div>
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={recommended2} ></img>
                    <div className="JewelryDate_HomePage">Jun 06, 2024 5:00 AM GMT+7</div>
                    <div className="JewelryName_HomePage">Tiffany & Co. Double Knot Sterling Silver Cufflinks</div>
                    <strong>25($)</strong>
                </div>
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={recommended3} ></img>
                    <div className="JewelryDate_HomePage">Jun 31, 2024 2:00 PM GMT+7</div>
                    <div className="JewelryName_HomePage">ORIGINAL BY STEFAN DINU FABULOUS NECKLACE STATEMENT ONYX AND ROSE QUARTZ</div>
                    <strong>1,900($)</strong>
                </div>
            </div>

            <div className="UpcomingAuctionsTitle"><h3>Bid now on these items</h3></div>
            <div className="UpcomingAuctions">
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={Bidnow1} ></img>
                    <div className="JewelryName_HomePage">A PLATINUM AND DIAMOND SOLITAIRE</div>
                    <strong>40,000 - 60,000 ($)</strong>
                </div>
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={Bidnow2} ></img>
                    <div className="JewelryName_HomePage">ROLEX. A stainless steel automatic chronograph bracelet watch</div>
                    <strong>12,000 - 18,000 ($)</strong>
                </div>
                <div className="ViewJewelry_HomePage_UpcomingAuctions" >
                    <img className="JewelryImg_HomePage" src={Bidnow3} ></img>
                    <div className="JewelryName_HomePage">VAN CLEEF & ARPELS: A PAIR OF DIAMOND EARRINGS</div>
                    <strong>50,000 - 80,000 ($)</strong>
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
export default HomePage;