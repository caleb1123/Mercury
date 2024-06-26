// EditAuction.js
import React, { useState } from 'react';
import { Box, TextField, Button, Typography, Paper } from '@mui/material';
import axios from 'axios';

const EditAuction = ({ auction, onClose, onAuctionUpdated }) => {
  const [startDate, setStartDate] = useState(auction.startDate || '');
  const [endDate, setEndDate] = useState(auction.endDate || '');

  const handleStartDateChange = (e) => {
    setStartDate(e.target.value);
  };

  const handleEndDateChange = (e) => {
    setEndDate(e.target.value);
  };

  const handleSave = async () => {
    try {
      await axios.put(`http://localhost:8088/auction/update/${auction.auctionId}`, {
        ...auction,
        startDate: startDate,
        endDate: endDate,
      }, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      alert('Auction updated successfully');
      onAuctionUpdated();
      onClose();
    } catch (error) {
      console.error('Error updating auction:', error);
      alert('Failed to update auction');
    }
  };

  return (
    <Paper sx={{ padding: 2 }}>
      <Typography variant="h6">Edit Auction</Typography>
      <TextField
        label="Start Date"
        type="datetime-local"
        value={startDate}
        onChange={handleStartDateChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="End Date"
        type="datetime-local"
        value={endDate}
        onChange={handleEndDateChange}
        fullWidth
        margin="normal"
      />
      <Box mt={2}>
        <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
        <Button variant="contained" color="secondary" onClick={onClose} sx={{ ml: 2 }}>Cancel</Button>
      </Box>
    </Paper>
  );
};

export default EditAuction;
