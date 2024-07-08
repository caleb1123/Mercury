import React, { useEffect, useState } from 'react';
import axios from 'axios';

function JewelryDetails({ jewelryId, requestId, onClose }) {
  const [jewelry, setJewelry] = useState(null);
  const [images, setImages] = useState([]);
  const [preliminaryPrice, setPreliminaryPrice] = useState('');
  const [requestDetails, setRequestDetails] = useState(null);

  useEffect(() => {
    const fetchJewelryDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/jewelry/${jewelryId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setJewelry(response.data);
        console.log("Jewelry Details:", response.data);
      } catch (error) {
        console.error('Error fetching jewelry details:', error);
      }
    };

    const fetchRequestDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/request/id/${requestId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setRequestDetails(response.data);
        setPreliminaryPrice(response.data.preliminaryPrice);
        console.log("Request Details:", response.data);
      } catch (error) {
        console.error('Error fetching request details:', error);
      }
    };

    const fetchJewelryImages = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/jewelryImage/all/${jewelryId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setImages(response.data);
        console.log("Jewelry Images:", response.data);
      } catch (error) {
        console.error('Error fetching jewelry images:', error);
      }
    };

    fetchJewelryDetails();
    fetchRequestDetails();
    fetchJewelryImages();
  }, [jewelryId, requestId]);

  const handlePreliminaryPriceChange = (e) => {
    setPreliminaryPrice(e.target.value);
  };

  const handleSave = async () => {
    try {
      // Update preliminary price
      const updateResponse = await axios.put(
        `http://localhost:8088/request/update/preliminary/${requestId}`,
        {
          requestId: requestId,
          preliminaryPrice: preliminaryPrice,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        }
      );

      // If the preliminary price update is successful, send the email notification
      if (updateResponse.status === 200) {
        const emailResponse = await axios.post(
          `http://localhost:8088/request/sendDeadlineRequest`,
          {
            ...requestDetails,
            preliminaryPrice: preliminaryPrice,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
          }
        );

        if (emailResponse.status === 200) {
          alert('Preliminary price updated and email sent successfully');
        } else {
          alert('Preliminary price updated but failed to send email');
        }
      }
    } catch (error) {
      console.error('Error updating preliminary price:', error.response || error.message);
      if (error.response && error.response.data) {
        alert(`Failed to update preliminary price: ${error.response.data}`);
      } else {
        alert('Failed to update preliminary price');
      }
    }
  };

  if (!jewelry) {
    return <p>Loading...</p>;
  }

  return (
    <div className="jewelry-details">
      <div className="jewelry-details__container">
        <h2>Jewelry Details</h2>
        <p><strong>Request ID:</strong> {requestId}</p>
        <p><strong>ID:</strong> {jewelry.jewelryId}</p>
        <p><strong>Name:</strong> {jewelry.jewelryName}</p>
        <p><strong>Condition:</strong> {jewelry.condition}</p>
        <p><strong>Description:</strong> {jewelry.description}</p>
        <p><strong>Designer:</strong> {jewelry.designer}</p>
        <p><strong>Estimate:</strong> {jewelry.estimate}</p>
        <p><strong>Gemstone:</strong> {jewelry.gemstone}</p>
        <div className="jewelry-details__images">
          {images.map((image, index) => (
            <img key={index} src={image.jewelryImageURL} alt={`${jewelry.jewelryName} ${index + 1}`} />
          ))}
        </div>
        <p><strong>Starting Price:</strong> {jewelry.startingPrice}</p>
        <p><strong>Status:</strong> {jewelry.status ? 'Active' : 'Inactive'}</p>
        <p><strong>Category ID:</strong> {jewelry.jewelryCategoryId}</p>

        <h3>Edit Preliminary Price</h3>
        <input
          type="text"
          value={preliminaryPrice}
          onChange={handlePreliminaryPriceChange}
        />
        <div className="jewelry-details__actions">
          <button onClick={handleSave}>Save</button>
          <button onClick={onClose}>Close</button>
        </div>
      </div>
    </div>
  );
}

export default JewelryDetails;
