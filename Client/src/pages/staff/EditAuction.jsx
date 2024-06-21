import React, { useState, useEffect } from 'react';
import { Paper, TextField, Button } from '@mui/material';
import axios from 'axios';

function EditAuction({ auction, fetchAuctions, setEditMode }) {
  const [updatedAuction, setUpdatedAuction] = useState(auction);

  const handleChange = (e) => {
    setUpdatedAuction({
      ...updatedAuction,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8088/auction/update/${updatedAuction.auctionId}`, updatedAuction);
      fetchAuctions();
      setEditMode(false);
    } catch (error) {
      console.error('Error updating auction:', error);
    }
  };

  return (
    <Paper sx={{ padding: 2 }}>
      <TextField
        label="Current Price"
        name="currentPrice"
        value={updatedAuction.currentPrice}
        onChange={handleChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="End Date"
        name="endDate"
        type="datetime-local"
        value={updatedAuction.endDate}
        onChange={handleChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Start Date"
        name="startDate"
        type="datetime-local"
        value={updatedAuction.startDate}
        onChange={handleChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Jewelry ID"
        name="jewelryId"
        value={updatedAuction.jewelryId}
        onChange={handleChange}
        fullWidth
        margin="normal"
      />
      <Button variant="contained" color="primary" onClick={handleSubmit}>
        Update Auction
      </Button>
    </Paper>
  );
}

export default EditAuction;
