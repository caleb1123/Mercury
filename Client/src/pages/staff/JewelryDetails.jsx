import React, { useEffect, useState } from 'react';
import { Paper, Typography, Button, Box, Grid, TextField } from '@mui/material';
import axios from 'axios';

function JewelryDetails({ jewelryId, requestId, onClose }) {
  const [jewelry, setJewelry] = useState(null);
  const [preliminaryPrice, setPreliminaryPrice] = useState('');

  useEffect(() => {
    const fetchJewelryDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/jewelry/${jewelryId}`, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setJewelry(response.data);
      } catch (error) {
        console.error('Error fetching jewelry details:', error);
      }
    };

    const fetchRequestDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/request/${requestId}`, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setPreliminaryPrice(response.data.preliminary_price);
      } catch (error) {
        console.error('Error fetching request details:', error);
      }
    };

    fetchJewelryDetails();
    fetchRequestDetails();
  }, [jewelryId, requestId]);

  const handlePreliminaryPriceChange = (e) => {
    setPreliminaryPrice(e.target.value);
  };

  const handleSave = async () => {
    try {
      await axios.put(`http://localhost:8088/request/update/${requestId}`, {
        preliminary_price: preliminaryPrice,
      }, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      alert('Preliminary price updated successfully');
    } catch (error) {
      console.error('Error updating preliminary price:', error);
      alert('Failed to update preliminary price');
    }
  };

  if (!jewelry) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Grid container justifyContent="center" alignItems="center" style={{ height: '100vh' }}>
      <Grid item xs={10} md={8} lg={6}>
        <Paper sx={{ padding: 4 }}>
          <Typography variant="h4" gutterBottom>
            Jewelry Details
          </Typography>
          <Typography><strong>ID:</strong> {jewelry.jewelryId}</Typography>
          <Typography><strong>Name:</strong> {jewelry.jewelryName}</Typography>
          <Typography><strong>Condition:</strong> {jewelry.condition}</Typography>
          <Typography><strong>Description:</strong> {jewelry.description}</Typography>
          <Typography><strong>Designer:</strong> {jewelry.designer}</Typography>
          <Typography><strong>Estimate:</strong> {jewelry.estimate}</Typography>
          <Typography><strong>Gemstone:</strong> {jewelry.gemstone}</Typography>
          <Typography><strong>Image:</strong></Typography>
          <img src={jewelry.image} alt={jewelry.jewelryName} style={{ width: '100%', marginBottom: '20px' }} />
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
