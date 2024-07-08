import React, { useState } from 'react';
import {
  Typography,
  Paper,
  Button,
  TextField,
  Box,
  Container,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
} from '@mui/material';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import EditPostImages from './EditPostImages';

const postCategories = [
  { category_id: 1, category_name: 'AUCTION_INSIGHTS' },
  { category_id: 2, category_name: 'BIDDER_GUIDE' },
  { category_id: 3, category_name: 'SELLER_GUIDE' },
  { category_id: 4, category_name: 'AUCTION_NEWS' },
  { category_id: 5, category_name: 'TREND' },
  { category_id: 6, category_name: 'UPCOMING_AUCTION' },
  { category_id: 7, category_name: 'JEWELRY_CARE' },
  { category_id: 8, category_name: 'BEHIND_THE_SCENES' },
];

function CreatePost() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [postId, setPostId] = useState(null);
  const [isPostCreated, setIsPostCreated] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const token = localStorage.getItem('token');
      if (!token) {
        console.error('No token found');
        return;
      }

      const decodedToken = jwtDecode(token);
      const account_id = decodedToken.account_id;

      const postData = {
        title,
        content,
        categoryId: parseInt(selectedCategory),
        postDate: new Date().toISOString(),
        status: true,
      };

      const postResponse = await axios.post('http://localhost:8088/posts/create', postData, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      const newPostId = postResponse.data.postId;
      setPostId(newPostId);
      setIsPostCreated(true);

      console.log('Post created successfully:', postResponse.data);
    } catch (error) {
      if (error.response) {
        console.error('Error response:', error.response.data);
      } else if (error.request) {
        console.error('Error request:', error.request);
      } else {
        console.error('Error message:', error.message);
      }
      console.error('Error config:', error.config);
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
          <FormControl fullWidth margin="normal" required>
            <InputLabel>Category</InputLabel>
            <Select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
              sx={{ backgroundColor: '#fff', borderRadius: 1 }}
            >
              {postCategories.map((category) => (
                <MenuItem key={category.category_id} value={category.category_id}>
                  {category.category_name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <Button type="submit" variant="contained" color="primary" fullWidth sx={{ backgroundColor: '#1976d2', marginTop: 2 }}>
            Submit Post
          </Button>
        </form>
        {isPostCreated && (
          <EditPostImages postId={postId} onClose={() => setIsPostCreated(false)} />
        )}
      </Paper>
    </Container>
  );
}

export default CreatePost;
