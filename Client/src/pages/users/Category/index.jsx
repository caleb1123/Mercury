import * as React from "react";
import "./Category.css";
import Bracelet from './image/image-25@2x.png'
import broochespins from './image/image-26@2x.png'
import cuffin from './image/image-27@2x.png'
import earing from './image/image-28@2x.png'
import loosestone from './image/image-29@2x.png'
import necklace from './image/image-30@2x.png'
import ring from './image/image-31@2x.png'
import watch from './image/image-32@2x.png'



function Category () {
    return(
        <>
        <h1>Category</h1>
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
        <h1>Footer</h1>
        </>
    );
} ;
export default Category;
