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
  Input,
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
  const [selectedFile, setSelectedFile] = useState(null);
  const [newImageFile, setNewImageFile] = useState(null);
  const [jewelryId, setJewelryId] = useState(null);
  const [images, setImages] = useState([]);
  const [noImages, setNoImages] = useState(false);
  const navigate = useNavigate(); // Initialize useNavigate

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/Login"); // Redirect to Login if no token
    }
  }, [navigate]); // Dependency array includes navigate

  useEffect(() => {
    if (jewelryId) {
      fetchImages();
    }
  }, [jewelryId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewJewelry({
      ...newJewelry,
      [name]: value,
    });
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handleNewImageFileChange = (e) => {
    setNewImageFile(e.target.files[0]);
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
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

      const createdJewelryId = jewelryResponse.data.jewelryId;
      setJewelryId(createdJewelryId);
      console.log("Jewelry created with ID:", createdJewelryId);

      const requestData = {
        jewelryId: createdJewelryId,
        requestDate: new Date().toISOString(),
        status: "PENDING",
        evaluationDate: null,
        preliminaryPrice: 0,
        finalPrice: 0,
      };

      const requestResponse = await axios.post(
        "http://localhost:8088/equest/create",
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

      // Upload the image after the jewelry is created
      await handleUploadImage(createdJewelryId);

      handleClickOpen(); // Open success dialog
    } catch (error) {
      console.error(
        "Error creating request:",
        error.response?.data || error.message
      );
    }
  };

  const handleUploadImage = async (jewelryId) => {
    if (!selectedFile) {
      console.error("No file selected");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);

    try {
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("User not authenticated");
      }

      const response = await axios.post(
        `http://localhost:8088/jewelryImage/upload/${jewelryId}`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      if (response.status !== 200) {
        throw new Error("Failed to upload image");
      }

      console.log("Image uploaded successfully");
    } catch (error) {
      console.error("Error uploading image:", error.response?.data || error.message);
    }
  };

  const handleAddImage = async () => {
    if (!newImageFile) return;

    const formData = new FormData();
    formData.append("file", newImageFile);

    try {
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("User not authenticated");
      }

      const response = await axios.post(
        `http://localhost:8088/jewelryImage/upload/${jewelryId}`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      if (response.status !== 200) {
        throw new Error("Failed to upload image");
      }

      console.log("Image uploaded successfully");
      alert("Image uploaded successfully!");
      setNewImageFile(null);
      fetchImages();
    } catch (error) {
      console.error("Error adding image:", error);
    }
  };

  const handleDeleteImage = async (fileId) => {
    console.log("Deleting fileId:", fileId); 
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("User not authenticated");
      }

      await axios.delete(`http://localhost:8088/jewelryImage/delete/${fileId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      fetchImages();
    } catch (error) {
      console.error("Error deleting image:", error);
    }
  };

  const fetchImages = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("User not authenticated");
      }

      const response = await axios.get(`http://localhost:8088/jewelryImage/all/${jewelryId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setImages(response.data);
      setNoImages(response.data.length === 0);
    } catch (error) {
      console.error("Error fetching images:", error);
      setNoImages(true);
    }
  };

  const handleDialogSubmit = async (event) => {
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
              <Input
                type="file"
                onChange={handleFileChange}
                fullWidth
                margin="normal"
                sx={{ mt: 2 }}
              />
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
                  Your request has been sent successfully. Please wait for our staff to evaluate your jewelry. You can also upload additional images if needed.
                </DialogContentText>
                <DialogActions>
                  <Input
                    type="file"
                    onChange={handleNewImageFileChange}
                    fullWidth
                    margin="normal"
                    sx={{ mt: 2 }}
                  />
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={handleAddImage}
                    sx={{ mt: 2 }}
                  >
                    Upload Additional Image
                  </Button>
                  <Button onClick={handleClose}>Close</Button>
                </DialogActions>
              </Dialog>
              <ViewRequests open={requestsOpen} onClose={handleViewRequestsClose} />
              <Grid container spacing={2} sx={{ mt: 2 }}>
  {images.map((image) => (
    <Grid item xs={6} sm={4} md={3} key={image.jewelryImageId}>
      <Box
        sx={{
          width: "100%",
          paddingBottom: "100%", // This creates a square container
          position: "relative",
        }}
      >
        <img
          src={image.jewelryImageURL}
          alt="Jewelry"
          style={{
            position: "absolute",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            objectFit: "cover",
          }}
        />
        <Button
          variant="contained"
          color="secondary"
          onClick={() => handleDeleteImage(image.fileId)}
          sx={{ mt: 1 }}
          fullWidth
        >
          Delete Image
        </Button>
      </Box>
    </Grid>
  ))}
</Grid>

            </Paper>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}
