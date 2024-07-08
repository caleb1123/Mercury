import React, { useEffect, useState } from 'react';
import { Paper, Typography, Button, Box, Grid, TextField } from '@mui/material';
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
        const response = await axios.get(`http://localhost:8088/jewelryImage/list/${jewelryId}`, {
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
    return <Typography>Loading...</Typography>;
  }

  return (
    <Grid container justifyContent="center" alignItems="center" sx={{ height: '100vh', width: '100%' }}>
      <Grid item xs={12} md={10} lg={8}>
        <Paper sx={{ padding: 4, width: '100%' }}>
          <Typography variant="h4" gutterBottom>
            Jewelry Details
          </Typography>
          <Typography><strong>Request ID:</strong> {requestId}</Typography>
          <Typography><strong>ID:</strong> {jewelry.jewelryId}</Typography>
          <Typography><strong>Name:</strong> {jewelry.jewelryName}</Typography>
          <Typography><strong>Condition:</strong> {jewelry.condition}</Typography>
          <Typography><strong>Description:</strong> {jewelry.description}</Typography>
          <Typography><strong>Designer:</strong> {jewelry.designer}</Typography>
          <Typography><strong>Estimate:</strong> {jewelry.estimate}</Typography>
          <Typography><strong>Gemstone:</strong> {jewelry.gemstone}</Typography>
          <Typography><strong>Images:</strong></Typography>
          <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, marginBottom: 2 }}>
            {images.map((image, index) => (
              <img key={index} src={image.jewelryImageURL} alt={`${jewelry.jewelryName} ${index + 1}`} style={{ width: '100%', maxWidth: '200px', borderRadius: '10px' }} />
            ))}
          </Box>
          <Typography><strong>Starting Price:</strong> {jewelry.startingPrice}</Typography>
          <Typography><strong>Status:</strong> {jewelry.status ? 'Active' : 'Inactive'}</Typography>
          <Typography><strong>Category ID:</strong> {jewelry.jewelryCategoryId}</Typography>

          <Typography variant="h5" gutterBottom sx={{ marginTop: 4 }}>
            Edit Preliminary Price
          </Typography>
          <TextField 
            label="Preliminary Price"
            value={preliminaryPrice} 
            onChange={handlePreliminaryPriceChange} 
            fullWidth 
            sx={{ marginBottom: 2 }}
          />

          <Box mt={2}>
            <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
            <Button variant="contained" color="secondary" onClick={onClose} sx={{ marginLeft: 2 }}>Close</Button>
          </Box>
        </Paper>
      </Grid>
    </Grid>
  );
}

export default JewelryDetails;
