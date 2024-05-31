import * as React from "react";
import { useState } from "react";
import "./Category.css";
import Bracelet from './image/image-25@2x.png'
import broochespins from './image/image-26@2x.png'
import cuffin from './image/image-27@2x.png'
import earing from './image/image-28@2x.png'
import loosestone from './image/image-29@2x.png'
import necklace from './image/image-30@2x.png'
import ring from './image/image-31@2x.png'
import watch from './image/image-32@2x.png'
import line from './image/line-3.svg'




function Category() {
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
            <div className="Category">
                <div className="PageName_Category">LIST OF CATEGORIES</div>
                <div className="CategoryFrame" >
                    <div className="Bracelet">
                        <img className="image" src={Bracelet} />
                        <div className="WordStyle_Category">Bracelet</div>
                    </div>
                    <div className="brooches-pins">
                        <img className="image" src={broochespins} />
                        <div className="WordStyle_Category">Brooches & Pins</div>
                    </div>
                    <div className="Cuffin">
                        <img className="image" src={cuffin} />
                        <div className="WordStyle_Category">Cufflinks, Tie Pins & Tie Clips</div>
                    </div>
                    <div className="Earring">
                        <img className="image" src={earing} />
                        <div className="WordStyle_Category">Earrings</div>
                    </div>
                    <div className="LooseStone">
                        <img className="image" src={loosestone} />
                        <div className="WordStyle_Category">Loose Stones & Beads</div>
                    </div>
                    <div className="Necklaces">
                        <img className="image" src={necklace} />
                        <div className="WordStyle_Category">Necklaces & Pendants</div>
                    </div>
                    <div className="Ring">
                        <img className="image" src={ring} />
                        <div className="WordStyle_Category">Rings</div>
                    </div>
                    <div className="Watches">
                        <img className="image" src={watch} />
                        <div className="WordStyle_Category">Watches</div>
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
};
export default Category;
