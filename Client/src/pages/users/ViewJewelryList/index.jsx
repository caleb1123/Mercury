import * as React from "react";
import { useState } from "react";
import line from './image/line-3.svg'
import "./ViewJewelryList.css"
import Bracelet1 from './image/bracelet1.jpeg'
import Bracelet2 from './image/bracelet2.jpeg'
import Bracelet3 from './image/bracelet3.jpeg'
import Bracelet4 from './image/bracelet4.jpeg'
import Bracelet5 from './image/bracelet5.jpeg'
import Bracelet6 from './image/bracelet6.jpeg'
import Bracelet7 from './image/bracelet7.jpeg'
import Bracelet8 from './image/bracelet8.jpeg'
import Bracelet9 from './image/bracelet9.jpeg'

function ViewJewelryList(){
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
                    CATEGORY:
                    <div className="world_bar_style" >BRACELET</div>
                </div>            
            <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />

            </div>
        </div>

        <div className="BraceletList"><h3>BRACELET</h3></div>
        <div className="Bracelet">
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet1} ></img>
                    <div className="JewelryDate_Bracelet">21 May, 2024</div>
                    <div className="JewelryName_Bracelet">CLove's Embrace: 1ct Blue Moissanite Solitaire Bracelet with Silver Hearts</div>
                    <strong>89,30($)</strong>
                </div>
            </div>
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet2} ></img>
                    <div className="JewelryDate_Bracelet">01 June, 2024</div>
                    <div className="JewelryName_Bracelet">Carat Sapphire Gold Plated Silver Bracelet - Gorgeous</div>
                    <strong>211.00($)</strong>
                </div>
            </div>
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet3} ></img>
                    <div className="JewelryDate_Bracelet">01 June, 2024</div>
                    <div className="JewelryName_Bracelet">Carat Sapphire Gold Plated Silver Bracelet - Gorgeous</div>
                    <strong>350.00($)</strong>
                </div>
            </div>
        </div>
        <div className="BraceletList"><h3>BRACELET</h3></div>
        <div className="Bracelet">
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet4} ></img>
                    <div className="JewelryDate_Bracelet">01 June, 2024</div>
                    <div className="JewelryName_Bracelet">Carat Sapphire Gold Plated Silver Bracelet - Gorgeous</div>
                    <strong>300.00($)</strong>
                </div>
            </div>
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet5} ></img>
                    <div className="JewelryDate_Bracelet">09 April, 2024</div>
                    <div className="JewelryName_Bracelet">Natural Baltic Amber - Whole Uncut Original Nugget Bracelet</div>
                    <strong>50.00($)</strong>
                </div>
            </div>
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet6} ></img>
                    <div className="JewelryDate_Bracelet">01 August, 2024</div>
                    <div className="JewelryName_Bracelet">Dendritic Opal / Sterling Silver Bracelet - Superb</div>
                    <strong>765.00($)</strong>
                </div>
            </div>
        </div>
        <div className="BraceletList"><h3>BRACELET</h3></div>
        <div className="Bracelet">
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet7} ></img>
                    <div className="JewelryDate_Bracelet">23 April, 2024</div>
                    <div className="JewelryName_Bracelet">Carats Natural Multi Sapphire fine quality In 925 silver Bracelet</div>
                    <strong>34.00($)</strong>
                </div>
            </div>
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet8} ></img>
                    <div className="JewelryDate_Bracelet">12 May, 2024</div>
                    <div className="JewelryName_Bracelet">CTW LABRADORITE BRACELET IN FINE STERLING SILVER 7 1/2 INCH LENGTH</div>
                    <strong>298.00($)</strong>
                </div>
            </div>
            <div className="ViewBraceletList" >
                <div className="Product">
                    <img className="JewelryImg_Bracelet" src={Bracelet9} ></img>
                    <div className="JewelryDate_Bracelet">23 June, 2024</div>
                    <div className="JewelryName_Bracelet">Blue Lapis Lazuli Beads Barcelet</div>
                    <strong>849.00($)</strong>
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
export default ViewJewelryList;