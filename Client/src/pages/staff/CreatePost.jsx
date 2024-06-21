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
  FormControl,
  Container,
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
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Paper sx={{ padding: 4, backgroundColor: '#f5f5f5', color: '#000', boxShadow: 3 }}>
        <Typography variant="h4" gutterBottom>Create a Post</Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            fullWidth
            margin="normal"
            required
            sx={{ backgroundColor: '#fff', borderRadius: 1 }}
          />
          <TextField
            label="Content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            fullWidth
            margin="normal"
            required
            multiline
            rows={6}
            sx={{ backgroundColor: '#fff', borderRadius: 1 }}
          />
          <FormControl fullWidth margin="normal" required sx={{ backgroundColor: '#fff', borderRadius: 1 }}>
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
              <MenuItem value="New Arrivals">New Arrivals</MenuItem>
              <MenuItem value="Upcoming Auctions">Upcoming Auctions</MenuItem>
              <MenuItem value="Auction Results">Auction Results</MenuItem>
              <MenuItem value="Jewelry Care Tips">Jewelry Care Tips</MenuItem>
              <MenuItem value="Promotions">Promotions</MenuItem>
            </Select>
          </FormControl>
          <Box mt={2} mb={2}>
            <input
              accept="image/*,video/*"
              id="contained-button-file"
              multiple
              type="file"
              onChange={handleFileChange}
              style={{ display: 'none' }}
            />
            <label htmlFor="contained-button-file">
              <Button variant="contained" color="primary" component="span" sx={{ backgroundColor: '#1976d2' }}>
                Upload Files
              </Button>
            </label>
          </Box>
          <Button type="submit" variant="contained" color="primary" fullWidth sx={{ backgroundColor: '#1976d2' }}>
            Submit Post
          </Button>
        </form>
      </Paper>
    </Container>
  );
}

export default CreatePost;
