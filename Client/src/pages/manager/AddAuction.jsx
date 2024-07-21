import React, { useState, useEffect } from 'react';
import { Box, Button, TextField, Typography, Paper, Grid, MenuItem, Select, FormControl, InputLabel } from '@mui/material';
import axios from 'axios';

const AddAuction = ({ onClose, onAuctionAdded }) => {
  const [auctionData, setAuctionData] = useState({
    startDate: '',
    endDate: '',
    currentPrice: '',
    status: '',
    jewelryId: '',
  });
  const [jewelryList, setJewelryList] = useState([]);

  useEffect(() => {
    fetchAvailableJewelry();
  }, []);

  const fetchAvailableJewelry = async () => {
    try {
      const response = await axios.get('http://localhost:8088/jewelry/list/available', {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      const filteredJewelry = response.data.filter(jewelry => jewelry.status === true);
      setJewelryList(filteredJewelry);
    } catch (error) {
      console.error('Error fetching available jewelry:', error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAuctionData((prev) => ({
      ...prev,
      [name]: value,
    }));

    if (name === 'jewelryId') {
      const selectedJewelry = jewelryList.find(jewelry => jewelry.jewelryId === parseInt(value, 10));
      if (selectedJewelry) {
        setAuctionData((prev) => ({
          ...prev,
          currentPrice: selectedJewelry.startingPrice,
        }));
      }
    }
  };

  const handleSubmit = async () => {
    console.log('Auction data to be submitted:', auctionData);
    try {
      const response = await axios.post('http://localhost:8088/auction/create', auctionData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      alert('Auction created successfully');
      onAuctionAdded();
      onClose();
    } catch (error) {
      console.error('Error creating auction:', error);
      alert('Failed to create auction');
    }
  };

  return (
    <Paper sx={{ padding: 2 }}>
      <Typography variant="h6">Add New Auction</Typography>
      <Grid container spacing={2} sx={{ marginTop: 2 }}>
        <Grid item xs={12}>
          <TextField
            label="Start Date"
            name="startDate"
            type="datetime-local"
            variant="outlined"
            fullWidth
            value={auctionData.startDate}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="End Date"
            name="endDate"
            type="datetime-local"
            variant="outlined"
            fullWidth
            value={auctionData.endDate}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControl fullWidth variant="outlined">
            <InputLabel>Jewelry</InputLabel>
            <Select
              label="Jewelry"
              name="jewelryId"
              value={auctionData.jewelryId}
              onChange={handleChange}
            >
              {jewelryList.map((jewelry) => (
                <MenuItem key={jewelry.jewelryId} value={jewelry.jewelryId}>
                  {jewelry.jewelryName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'space-between' }}>
          <Button variant="contained" color="primary" onClick={handleSubmit}>
            Add Auction
          </Button>
          <Button variant="contained" color="secondary" onClick={onClose}>
            Cancel
          </Button>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default AddAuction;
