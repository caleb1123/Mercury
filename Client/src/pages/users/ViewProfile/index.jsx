import { memo } from "react";
import { useState } from "react";
import "./ViewProfile.css";
import avt from './image/Avatar.jpg'
import birthday from './image/vuesaxlinearcake.svg'
import mail from './image/vuesaxlinearsms.svg'
import sex from './image/vuesaxlinearuser.svg'
import address from './image/vuesaxlinearlocation.svg'
import phone from './image/vuesaxlinearcall.svg'
import line from './image/line-3.svg'


function ViewProfile  () {
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
                    AUCTIONS
                    <div className="world_bar_style" >SELL</div>
                    <div className="world_bar_style" >RESULT</div>
                    <div className="world_bar_style" >CATEGORY</div>
                    <div className="world_bar_style" >BLOG</div>


                </div>            
            <input className="Search" type="text" value={inputValue} onChange={handleChange} placeholder="Search" />

            </div>

        </div>

        <div className="ViewProfile">
            <div className="PageName_YourProfile">YOUR PROFILE</div>
            <div className="User">
                <div className="avt">
                    <img src={avt} ></img>
                </div>
                <div className="UserInfo"> 
                    <div className="UserName">NGUYEN MINH TRUNG</div>
                    <div className="OtherInfo">
                        <img src={birthday}/>
                        <div className="WordStyleInfo">18/07/2004</div>
                    </div>
                    <div className="OtherInfo">
                        <img src={mail}/>
                        <div className="WordStyleInfo">nguyenminhtrung18072004@gmail.com</div>
                    </div>
                    <div className="OtherInfo">
                        <img src={sex}/>
                        <div className="WordStyleInfo">Male</div>
                    </div>
                    <div className="OtherInfo">
                        <img src={address}/>
                        <div className="WordStyleInfo">Vinhomes Grand Park</div>
                    </div>
                    <div className="OtherInfo">
                        <img src={phone}/>
                        <div className="WordStyleInfo">+84 745927098</div>
                    </div>
                </div>
            </div>

            <button className="EditProfileButton" >Edit Profile</button>

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
    )
};

export default memo(ViewProfile)