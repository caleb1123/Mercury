import React from 'react';
import { Paper, Typography, Button, Box, TextField } from '@mui/material';
import axios from 'axios';

function RequestDetails({ request, onSave, onFinalPriceChange, finalPrice, onCancel }) {
  const handleSave = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.put(`http://localhost:8088/request/update/final/${request.requestId}`, {
        requestId: request.requestId, 
        finalPrice: finalPrice,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      alert('Final price updated successfully');
      onSave(); // Notify the parent component to refresh data
    } catch (error) {
      console.error('Error updating final price:', error);
      alert(`Failed to update final price: ${error.response?.data}`);
    }
  };

  return (
    <Paper sx={{ padding: 15, marginTop: 2, width: '100%', maxWidth: '700px', paddingLeft:20}}>
      <Typography variant="h6">Request Details</Typography>
      <Typography><strong>Request ID:</strong> {request.requestId}</Typography>
      <Typography><strong>Request Date:</strong> {request.requestDate}</Typography>
      <Typography><strong>Status:</strong> {request.status}</Typography>
      <Typography><strong>Evaluation Date:</strong> {request.evaluationDate}</Typography>
      <Typography><strong>Delivery Date:</strong> {request.deliveryDate}</Typography>
      <Typography><strong>Preliminary Price:</strong> {request.preliminaryPrice}</Typography>
      <Typography><strong>Final Price:</strong> {request.finalPrice}</Typography>
      <Typography><strong>Seller ID:</strong> {request.sellerId}</Typography>
      <Typography><strong>Jewelry ID:</strong> {request.jewelryId}</Typography>

      <Typography variant="h5" gutterBottom sx={{ marginTop: 4 }}>
        Edit Final Price
      </Typography>
      <TextField 
        label="Final Price"
        value={finalPrice}
        onChange={onFinalPriceChange}
        fullWidth
        sx={{ marginBottom: 2 }}
      />

      <Box mt={2}>
        <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
        <Button variant="contained" color="secondary" onClick={onCancel} sx={{ marginLeft: 2 }}>Cancel</Button>
      </Box>
    </Paper>
  );
}

export default RequestDetails;
