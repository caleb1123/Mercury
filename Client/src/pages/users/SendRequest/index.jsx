import React, { useState, useEffect } from "react";
import {
  Paper,
  TextField,
  Button,
  Select,
  MenuItem,
  Typography,
  Dialog,
  DialogActions,
  DialogContentText,
  DialogTitle,
  Grid,
  Box,
  FormControl,
  InputLabel,
} from "@mui/material";
import axios from "axios";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { useNavigate } from "react-router-dom"; // Import useNavigate
import ViewRequests from "./ViewRequests"; // Import the new component

const defaultTheme = createTheme();

const categories = [
  { jewelry_category_id: 1, category_name: "RINGS" },
  { jewelry_category_id: 2, category_name: "BRACELETS" },
  { jewelry_category_id: 3, category_name: "BROOCHES_PINS" },
  { jewelry_category_id: 4, category_name: "CUFFLINKS_TIEPINS_TIECLIPS" },
  { jewelry_category_id: 5, category_name: "EARRINGS" },
  { jewelry_category_id: 6, category_name: "LOOSESTONES_BEADS" },
  { jewelry_category_id: 7, category_name: "NECKLACES_PENDANTS" },
  { jewelry_category_id: 8, category_name: "WATCHES" },
];

export default function SendRequest() {
  const [open, setOpen] = useState(false);
  const [requestsOpen, setRequestsOpen] = useState(false);
  const [newJewelry, setNewJewelry] = useState({
    condition: "",
    description: "",
    designer: "",
    gemstone: "",
    jewelryName: "",
    estimate: 0,
    startingPrice: 0,
    status: false,
    jewelryCategoryId: "",
  });
  const navigate = useNavigate(); // Initialize useNavigate

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/Login"); // Redirect to Login if no token
    }
  }, [navigate]); // Dependency array includes navigate

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewJewelry({
      ...newJewelry,
      [name]: value,
    });
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    window.location.reload();
  };

  const handleAddJewelry = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("User not authenticated");
      }

      const jewelryResponse = await axios.post(
        "http://localhost:8088/jewelry/add",
        newJewelry,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (jewelryResponse.status !== 201) {
        throw new Error("Failed to create jewelry");
      }

      console.log("Jewelry created successfully");

      const requestData = {
        jewelryId: jewelryResponse.data.jewelryId,
        requestDate: new Date().toISOString(),
        status: "PENDING",
        evaluationDate: null,
        preliminaryPrice: 0,
        finalPrice: 0,
      };

      const requestResponse = await axios.post(
        "http://localhost:8088/request/create",
        requestData,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (requestResponse.status !== 201) {
        throw new Error("Failed to create request");
      }

      console.log("Request created successfully");

      handleClickOpen(); // Open success dialog
    } catch (error) {
      console.error(
        "Error creating request:",
        error.response?.data || error.message
      );
    }
  };

  const handleDialogSubmit = (event) => {
    event.preventDefault();
    handleClose();
  };

  const handleViewRequestsOpen = () => {
    setRequestsOpen(true);
  };

  const handleViewRequestsClose = () => {
    setRequestsOpen(false);
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Grid container component="main" sx={{ height: "100vh" }}>
        <CssBaseline />
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            minHeight: "100vh",
            width: "100vw",
            backgroundImage:
              "url(https://i.pinimg.com/564x/41/c0/16/41c01649ba6209717130cab5f07679fb.jpg)",
            backgroundSize: "cover",
            backgroundPosition: "center",
            backgroundRepeat: "no-repeat",
            padding: 2,
          }}
        />
        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
          <Box
            sx={{
              my: 8,
              mx: 4,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Button
              variant="contained"
              color="primary"
              onClick={handleViewRequestsOpen}
              sx={{ mb: 2 }}
            >
              View My Requests
            </Button>
            <Typography component="h1" variant="h2">
              Send Jewelry Valuation Request
            </Typography>
            <br />
            <Paper sx={{ padding: 2, backgroundColor: "#fff", color: "#000" }}>
              <TextField
                label="Condition"
                name="condition"
                value={newJewelry.condition}
                onChange={handleInputChange}
                fullWidth
                margin="normal"
              />
              <TextField
                label="Description"
                name="description"
                value={newJewelry.description}
                onChange={handleInputChange}
                fullWidth
                margin="normal"
              />
              <TextField
                label="Designer"
                name="designer"
                value={newJewelry.designer}
                onChange={handleInputChange}
                fullWidth
                margin="normal"
              />
              <TextField
                label="Gemstone"
                name="gemstone"
                value={newJewelry.gemstone}
                onChange={handleInputChange}
                fullWidth
                margin="normal"
              />
              <TextField
                label="Jewelry Name"
                name="jewelryName"
                value={newJewelry.jewelryName}
                onChange={handleInputChange}
                fullWidth
                margin="normal"
              />
              <FormControl fullWidth margin="normal">
                <InputLabel>Select Category</InputLabel>
                <Select
                  displayEmpty
                  name="jewelryCategoryId"
                  value={newJewelry.jewelryCategoryId}
                  onChange={handleInputChange}
                >
                  <MenuItem value="">
                    <em>Select Category</em>
                  </MenuItem>
                  {categories.map((category) => (
                    <MenuItem
                      key={category.jewelry_category_id}
                      value={category.jewelry_category_id}
                    >
                      {category.category_name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <Button
                variant="contained"
                color="primary"
                onClick={handleAddJewelry}
                sx={{ mt: 2 }}
              >
                Send Request
              </Button>
              <Dialog
                open={open}
                onClose={handleClose}
                PaperProps={{
                  component: "form",
                  onSubmit: handleDialogSubmit,
                }}
              >
                <DialogTitle>Request Sent Successfully</DialogTitle>
                <DialogContentText
                  sx={{
                    textAlign: "center",
                    marginTop: 2,
                  }}
                >
                  Your request has been sent successfully. Please wait for our staff to evaluate your jewelry.
                </DialogContentText>
                <DialogActions>
                  <Button onClick={handleClose}>Close</Button>
                  </DialogActions>
              </Dialog>
              <ViewRequests open={requestsOpen} onClose={handleViewRequestsClose} />
            </Paper>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}
