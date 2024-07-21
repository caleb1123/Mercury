import React, { useState, useEffect } from 'react';
import {
  Box,
  Typography,
  Button,
  Grid,
  IconButton,
  Paper,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';

function EditPostImages({ postId, onClose }) {
  const [images, setImages] = useState([]);
  const [newImageFile, setNewImageFile] = useState(null);
  const [noImages, setNoImages] = useState(false);

  useEffect(() => {
    fetchImages();
  }, [postId]);

  const fetchImages = async () => {
    try {
      const response = await axios.get(`http://localhost:8088/postImage/list/${postId}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setImages(response.data);
      setNoImages(response.data.length === 0);
    } catch (error) {
      console.error('Error fetching images:', error);
      setNoImages(true);
    }
  };

  const handleAddImage = async () => {
    if (!newImageFile) return;

    const formData = new FormData();
    formData.append('file', newImageFile);

    try {
      await axios.post(`http://localhost:8088/postImage/upload/${postId}`, formData, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data',
        },
      });
      setNewImageFile(null);
      fetchImages();
    } catch (error) {
      console.error('Error adding image:', error);
    }
  };

  const handleDeleteImage = async (fileId) => {
    console.log('Deleting fileId:', fileId); // Log fileId on console
    try {
      await axios.delete(`http://localhost:8088/postImage/delete/${fileId}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      fetchImages();
    } catch (error) {
      console.error('Error deleting image:', error);
    }
  };

  return (
    <Dialog open onClose={onClose}>
      <DialogTitle>Edit Images</DialogTitle>
      <DialogContent>
        {noImages ? (
          <Typography>There is no image for this post</Typography>
        ) : (
          <Grid container spacing={2}>
            {images.map((image) => (
              <Grid item key={image.postImageId}>
                <Paper sx={{ padding: 2 }}>
                  <img src={image.postImageURL} alt={`Post ${postId}`} style={{ width: '100px', height: '100px' }} />
                  <IconButton onClick={() => handleDeleteImage(image.fileId)}>
                    <DeleteIcon />
                  </IconButton>
                </Paper>
              </Grid>
            ))}
          </Grid>
        )}
        <input
          type="file"
          onChange={(e) => setNewImageFile(e.target.files[0])}
          accept="image/*"
        />
        <Button variant="contained" onClick={handleAddImage}>Add Image</Button>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Close</Button>
      </DialogActions>
    </Dialog>
  );
}

export default EditPostImages;
