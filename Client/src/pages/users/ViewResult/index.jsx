import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ViewResult.css';
import Header from "../Header";

function ViewResult() {
  const [auctionData, setAuctionData] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [error, setError] = useState('');
  const [paymentStatus, setPaymentStatus] = useState({});
  const [userName, setUserName] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
      fetchUserInfo(token);
    }
  }, []);

  const fetchUserInfo = async (token) => {
    try {
      const response = await fetch('http://localhost:8088/account/myinfor', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      if (response.ok) {
        const data = await response.json();
        setUserName(data.userName); // Giả sử API trả về một đối tượng có thuộc tính userName
        fetchAuctionData(token, data.userName); // Gọi fetchAuctionData sau khi đã có userName
      } else {
        console.error('Failed to fetch user info:', response.statusText);
      }
    } catch (error) {
      console.error('Error fetching user info:', error);
    }
  };

  const fetchAuctionData = async (token, userName) => {
    try {
      const response = await fetch('http://localhost:8088/auction/thisUser/wonAuctions', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      if (response.ok) {
        const data = await response.json();
        setAuctionData(data);

        // Fetch payment status for each auction
        data.forEach(auction => checkPaymentStatus(auction.auctionId, userName));
      } else {
        console.error('Failed to fetch auction data:', response.statusText);
      }
    } catch (error) {
      console.error('Error fetching auction data:', error);
    }
  };

  const checkPaymentStatus = async (auctionId, userName) => {
    try {
      const response = await fetch('http://localhost:8088/payment/vn-pay', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          bankCode: 'NCB',
          auctionId: auctionId,
          userName: userName
        })
      });
  
      const data = await response.json(); // Đảm bảo biến data được khai báo và nhận dữ liệu từ API
      console.log('Payment API response:', data); // Log dữ liệu trả về từ API
  
      if (response.ok) {
        if (data.code === 200 && data.data && data.data.paymentUrl) {
          console.log('Payment link:', data.data.paymentUrl); // Log link thanh toán
          setPaymentStatus(prevStatus => ({
            ...prevStatus,
            [auctionId]: { paid: false, link: data.data.paymentUrl }
          }));
        } else {
          console.error('Failed to retrieve payment URL:', data.message);
        }
      } else if (response.status === 400) {
        console.log('Payment already completed for auction ID:', auctionId);
        setPaymentStatus(prevStatus => ({
          ...prevStatus,
          [auctionId]: { paid: true, message: 'Payment is already completed' }
        }));
      } else {
        console.error('Failed to check payment status:', response.statusText);
      }
  
      // // Kiểm tra trạng thái thanh toán từ response và chuyển hướng
      // if (data.data && data.data.message === 'FAILED') {
      //   navigate('/PaymentFailed');
      // } else {
      //   navigate('/PaymentSuccess');
      // }
    } catch (error) {
      console.error('Error checking payment status:', error);
      setPaymentStatus(prevStatus => ({
        ...prevStatus,
        [auctionId]: { paid: true, message: 'Payment is already completed' }
      }));
    }
  };
  

  const handlePaymentClick = (auctionId) => {
    const paymentInfo = paymentStatus[auctionId];
    if (paymentInfo && !paymentInfo.paid && paymentInfo.link) {
      console.log('Navigating to payment link:', paymentInfo.link); // Log link thanh toán
      window.location.href = paymentInfo.link;
    }
  };

  const logAuctionInfo = (auctionId, userName) => {
    console.log(`Auction ID: ${auctionId}, Username: ${userName}`);
    handlePaymentClick(auctionId);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  if (error) {
    return <div>{error}</div>;
  }

  if (!auctionData.length) {
    return <div><h1>You have not won any items yet</h1></div>;
  }

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="containerResult">
        <div className="result">
          <h2>Won Auctions</h2>
          <div className="result-details">
            <div className="Congra"><h1>Congratulations !!!! You have won this item </h1></div>
            {auctionData.map((auction) => (
              <div key={auction.auctionId} className="auction-item">
                <p><strong>Auction ID:</strong> {auction.auctionId}</p>
                <p><strong>Start Date:</strong> {auction.startDate}</p>
                <p><strong>End Date:</strong> {auction.endDate}</p>
                <p><strong>Current Price:</strong> {auction.currentPrice}</p>
                <p><strong>Status:</strong> {auction.status}</p>
                <p><strong>Jewelry ID:</strong> {auction.jewelryId}</p>
                {paymentStatus[auction.auctionId] && !paymentStatus[auction.auctionId].paid && (
                  <button onClick={() => logAuctionInfo(auction.auctionId, userName)}>Pay Now</button>
                )}
                {paymentStatus[auction.auctionId] && paymentStatus[auction.auctionId].paid && (
                  <p className="completed-payment">{paymentStatus[auction.auctionId].message}</p>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}

export default ViewResult;
