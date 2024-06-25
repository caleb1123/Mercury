import React, { useState } from 'react';
import {
  Paper,
  TextField,
  Button,
  Typography,
} from '@mui/material';
import axios from 'axios';

const AddAuction = ({ fetchAuctions }) => {
  const [newAuction, setNewAuction] = useState({
    currentPrice: '',
    endDate: '',
    startDate: '',
    status: true,
    winner_id: '',
    jewelryId: ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewAuction({
      ...newAuction,
      [name]: value,
    });
  };

  const handleAddAuction = async () => {
    try {
      await axios.post('http://localhost:8088/auction/create', newAuction);
      fetchAuctions(); // Refresh the list
      setNewAuction({
        currentPrice: '',
        endDate: '',
        startDate: '',
        status: true,
        winner_id: '',
        jewelryId: ''
      }); // Reset form
    } catch (error) {
      console.error('Error adding auction:', error);
    }
  };

  return (
    <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
      <Typography variant="h6">Add Auction</Typography>
      <TextField
        label="Current Price"
        name="currentPrice"
        value={newAuction.currentPrice}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="End Date"
        name="endDate"
        type="datetime-local"
        value={newAuction.endDate}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Start Date"
        name="startDate"
        type="datetime-local"
        value={newAuction.startDate}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Jewelry ID"
        name="jewelryId"
        value={newAuction.jewelryId}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <Button variant="contained" color="primary" onClick={handleAddAuction} sx={{ mt: 2 }}>
        Add Auction
      </Button>
    </Paper>
  );
};

export default AddAuction;
