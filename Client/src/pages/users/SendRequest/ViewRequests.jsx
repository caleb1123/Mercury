import React, { useState, useEffect } from "react";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Typography,
  Button,
  Box,
} from "@mui/material";
import axios from "axios";

export default function ViewRequests({ open, onClose }) {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    if (open) {
      fetchRequests();
    }
  }, [open]);

  const fetchRequests = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("User not authenticated");
      }

      const response = await axios.get("http://localhost:8088/request/list/thisuser", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error("Failed to fetch requests");
      }

      setRequests(response.data);
    } catch (error) {
      console.error("Error fetching requests:", error.response?.data || error.message);
    }
  };

  return (
    <Dialog
      open={open}
      onClose={onClose}
      maxWidth="md" // Increase dialog width
      fullWidth
    >
      <DialogTitle>My Requests</DialogTitle>
      <DialogContent>
        {requests.length > 0 ? (
          requests.map((request) => (
            <Box key={request.id} sx={{ mb: 3, p: 2, border: '1px solid #ccc', borderRadius: 2 }}>
              <Typography variant="body1">
                <strong>Request ID:</strong> {request.requestId}
              </Typography>
              <Typography variant="body1" sx={{ fontWeight: 'bold', color: 'red' }}>
                <strong>Status:</strong> {request.status}
              </Typography>
              <Typography variant="body1">
                <strong>Request Date:</strong> {request.requestDate}
              </Typography>
              <Typography variant="body1">
                <strong>Evaluation Date:</strong> {request.evaluationDate}
              </Typography>
              <Typography variant="body1">
                <strong>Delivery Date:</strong> {request.deliveryDate}
              </Typography>
              <Typography variant="body1">
                <strong>Preliminary Price:</strong> {request.preliminaryPrice}
              </Typography>
              <Typography variant="body1">
                <strong>Final Price:</strong> {request.finalPrice}
              </Typography>
            </Box>
          ))
        ) : (
          <Typography variant="body1">No requests found</Typography>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Close</Button>
      </DialogActions>
    </Dialog>
  );
}
