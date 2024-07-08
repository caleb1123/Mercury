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

const EditJewelry = ({ jewelry, fetchJewelry, setEditMode }) => {
  const [editedJewelry, setEditedJewelry] = useState({ ...jewelry });
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

  const statuses = [
    { status_id: 0, status_name: 'Inactive' },
    { status_id: 1, status_name: 'Active' },
  ];

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedJewelry({
      ...editedJewelry,
      [name]: value,
    });
  };

  const handleFileChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const handleUpdateJewelry = async () => {
    try {
      // Upload image first if a new image file is selected
      if (imageFile) {
        const formData = new FormData();
        formData.append('file', imageFile);
        const imageUploadResponse = await axios.post(`http://localhost:8088/jewelryImage/upload/${editedJewelry.jewelryId}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        editedJewelry.image = imageUploadResponse.data.imageUrl; // Assuming the server returns the image URL in imageUrl field
      }

      // Update jewelry details
      const url = `http://localhost:8088/jewelry/update/${editedJewelry.jewelryId}`;
      await axios.put(url, editedJewelry);
      fetchJewelry(); // Refresh the list
      setEditMode(false); // Exit edit mode
    } catch (error) {
      console.error('Error updating jewelry:', error);
    }
  };

  return (
    <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
      <Typography variant="h6">Edit Jewelry</Typography>
      <TextField
        label="Condition"
        name="condition"
        value={editedJewelry.condition}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Description"
        name="description"
        value={editedJewelry.description}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Designer"
        name="designer"
        value={editedJewelry.designer}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Estimate"
        name="estimate"
        value={editedJewelry.estimate}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Gemstone"
        name="gemstone"
        value={editedJewelry.gemstone}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <input
        accept="image/*"
        type="file"
        onChange={handleFileChange}
        style={{ margin: '20px 0' }}
      />
      <TextField
        label="Jewelry Name"
        name="jewelryName"
        value={editedJewelry.jewelryName}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Starting Price"
        name="startingPrice"
        value={editedJewelry.startingPrice}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <Select
        displayEmpty
        fullWidth
        name="jewelryCategoryId"
        value={editedJewelry.jewelryCategoryId}
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
      <Select
        displayEmpty
        fullWidth
        name="status"
        value={editedJewelry.status}
        onChange={handleInputChange}
        margin="normal"
      >
        <MenuItem value="">
          <em>Select Status</em>
        </MenuItem>
        {statuses.map((status) => (
          <MenuItem key={status.status_id} value={status.status_id}>
            {status.status_name}
          </MenuItem>
        ))}
      </Select>
      <Button variant="contained" color="primary" onClick={handleUpdateJewelry} sx={{ mt: 2 }}>
        Update Jewelry
      </Button>
    </Paper>
  );
};

export default EditJewelry;
