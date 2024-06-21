import React, { useState } from 'react';
import {
  Grid,
  Typography,
  Paper,
  Button,
  TextField,
  Select,
  MenuItem,
  Box,
  InputLabel,
  FormControl
} from '@mui/material';
import axios from 'axios';

function CreatePost() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [category, setCategory] = useState('');
  const [files, setFiles] = useState([]);

  const handleFileChange = (event) => {
    setFiles(event.target.files);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append('title', title);
    formData.append('content', content);
    formData.append('category', category);
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }

    try {
      const response = await axios.post('http://localhost:8088/posts/create', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      console.log('Post created successfully:', response.data);
    } catch (error) {
      console.error('Error creating post:', error);
    }
  };

  return (
    <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
      <Typography variant="h6">Create a Post</Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          fullWidth
          margin="normal"
          required
        />
        <TextField
          label="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          fullWidth
          margin="normal"
          required
          multiline
          rows={4}
        />
        <FormControl fullWidth margin="normal" required>
          <InputLabel id="category-label">Post Category</InputLabel>
          <Select
            labelId="category-label"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            label="Post Category"
          >
            <MenuItem value="Instruction">Instruction</MenuItem>
            <MenuItem value="Announcement">Announcement</MenuItem>
            <MenuItem value="Update">Update</MenuItem>
          </Select>
        </FormControl>
        <Box mt={2} mb={2}>
          <input
            accept="image/*,video/*"
            id="contained-button-file"
            multiple
            type="file"
            onChange={handleFileChange}
          />
        </Box>
        <Button type="submit" variant="contained" color="primary">
          Submit post
        </Button>
      </form>
    </Paper>
  );
}

export default CreatePost;
