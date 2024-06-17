import * as React from "react";
import Link from '@mui/material/Link';
import { useState } from "react";
import "./ViewJewelryList.css"
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
import { NavLink } from "react-router-dom";
import { useHistory } from 'react-router-dom';



function ViewJewelryList() {

    const [inputValue, setInputValue] = useState('');

    // Hàm xử lý sự kiện khi người dùng nhập
    const handleChange = (event) => {
        setInputValue(event.target.value);
    };

    const handleClick = () => {
        window.location.href = '/ViewJewelryList';
      };

    return (
        <>
            <div className="Header">
                <div className="UpHeader">
                    <NavLink to ="./" className="Mercury" >MERCURY</NavLink>
                    
                    <div className="Login_CreaAccount">
                        <NavLink to ="./SignUp" className="NavLink_Style"  >CREATE ACCOUNT</NavLink>
                        <NavLink to ="./Login" className="LoginStyle"   >LOGIN</NavLink>
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
                        AUCTIONS
                        <NavLink to ="./SendRequest" className="world_bar_style" >SELL</NavLink>
                        <div className="world_bar_style" >RESULT</div>
                        {/* <div className="world_bar_style" >CATEGORY</div> */}
                        <NavLink to ="./Category" className="world_bar_style" >CATEGORY</NavLink>
                        <div className="world_bar_style" >BLOG</div>


                    </div>
                    <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />

                </div>

            </div>
            <div className="ViewJewelryList">

                
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
export default ViewJewelryList;