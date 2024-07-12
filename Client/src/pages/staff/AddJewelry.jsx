import React, { useState } from 'react';
import {
  Paper,
  TextField,
  Button,
  Select,
  MenuItem,
  Typography,
} from '@mui/material';
import axios from 'axios';

const AddJewelry = ({ fetchJewelry }) => {
  const [newJewelry, setNewJewelry] = useState({
    condition: '',
    description: '',
    designer: '',
    estimate: '',
    gemstone: '',
    image: '',
    jewelryName: '',
    startingPrice: '',
    status: true,
    jewelryCategoryId: ''
  });

  const [imageFile, setImageFile] = useState(null);

  const categories = [
    { jewelry_category_id: 1, category_name: 'RINGS' },
    { jewelry_category_id: 2, category_name: 'BRACELETS' },
    { jewelry_category_id: 3, category_name: 'BROOCHES_PINS' },
    { jewelry_category_id: 4, category_name: 'CUFFLINKS_TIEPINS_TIECLIPS' },
    { jewelry_category_id: 5, category_name: 'EARRINGS' },
    { jewelry_category_id: 6, category_name: 'LOOSESTONES_BEADS' },
    { jewelry_category_id: 7, category_name: 'NECKLACES_PENDANTS' },
    { jewelry_category_id: 8, category_name: 'WATCHES' },
  ];

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewJewelry({
      ...newJewelry,
      [name]: value,
    });
  };

  const handleFileChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const handleAddJewelry = async () => {
    try {
      // Upload image first
      if (imageFile) {
        const formData = new FormData();
        formData.append('file', imageFile);
        const imageUploadResponse = await axios.post('http://localhost:8088/jewelryImage/upload/101', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        newJewelry.image = imageUploadResponse.data.imageUrl; // Assuming the server returns the image URL in imageUrl field
      }

      // Add jewelry details
      const response = await axios.post('http://localhost:8088/jewelry/add', newJewelry);
      console.log('Server response:', response);
      fetchJewelry(); // Refresh the list
      setNewJewelry({
        condition: '',
        description: '',
        designer: '',
        estimate: '',
        gemstone: '',
        image: '',
        jewelryName: '',
        startingPrice: '',
        status: true,
        jewelryCategoryId: ''
      }); // Reset form
      setImageFile(null); // Reset image file
    } catch (error) {
      console.error('Error adding jewelry:', error.response ? error.response.data : error.message);
    }
  };

  return (
    <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
      <Typography variant="h6">Add Jewelry</Typography>
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
        label="Estimate"
        name="estimate"
        value={newJewelry.estimate}
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
      {/* <input
        accept="image/*"
        type="file"
        onChange={handleFileChange}
        style={{ margin: '20px 0' }}
      /> */}
      <TextField
        label="Jewelry Name"
        name="jewelryName"
        value={newJewelry.jewelryName}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Starting Price"
        name="startingPrice"
        value={newJewelry.startingPrice}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <Select
        displayEmpty
        fullWidth
        name="jewelryCategoryId"
        value={newJewelry.jewelryCategoryId}
        onChange={handleInputChange}
        margin="normal"
      >
        <MenuItem value="">
          <em>Select Category</em>
        </MenuItem>
        {categories.map((category) => (
          <MenuItem key={category.jewelry_category_id} value={category.jewelry_category_id}>
            {category.category_name}
          </MenuItem>
        ))}
      </Select>
      <Button variant="contained" color="primary" onClick={handleAddJewelry} sx={{ mt: 2 }}>
        Add Jewelry
      </Button>
    </Paper>
  );
};

export default AddJewelry;
