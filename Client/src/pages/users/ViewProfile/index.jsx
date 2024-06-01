import { memo } from "react";
import "./ViewProfile.css";
import avt from './image/Avatar.jpg'
import birthday from './image/vuesaxlinearcake.svg'
import mail from './image/vuesaxlinearsms.svg'
import sex from './image/vuesaxlinearuser.svg'
import address from './image/vuesaxlinearlocation.svg'
import phone from './image/vuesaxlinearcall.svg'

const ViewProfile = () =>{
    return(
        <>
        <h1>Header</h1>
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
        <h1>Footer</h1>

        </>
    )
};

export default memo(ViewProfile)