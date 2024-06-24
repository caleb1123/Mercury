import React, { useState, useEffect } from 'react';
import {
  Typography,
  Paper,
  Button,
  TextField,
  Box,
  Container,
} from '@mui/material';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

function CreatePost() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [accountId, setAccountId] = useState(null);
  const defaultCategory = 5;

  useEffect(() => {
    // const fetchAccountId = () => {
    //   try {
    //     const token = localStorage.getItem('token');
    //     if (token) {
    //       const decodedToken = jwtDecode(token);
    //       if (decodedToken && decodedToken.account_id) {
    //         setAccountId(decodedToken.account_id);  // Adjust according to the token's structure
    //       } else {
    //         console.error('Decoded token does not contain account_id');
    //       }
    //     } else {
    //       console.error('No token found in localStorage');
    //     }
    //   } catch (error) {
    //     console.error('Error decoding token:', error);
    //   }
    // };

    //fetchAccountId();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    // if (!accountId) {
    //   console.error('Account ID not available');
    //   return;
    // }

    const postData = {
      title,
      content,
      category: defaultCategory,
      //account_id: accountId,
    };

    try {
      const response = await axios.post('http://localhost:8088/posts/create', postData, {
        headers: {
          'Content-Type': 'application/json'
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
          <Box mt={2} mb={2}>
            <Typography variant="body2">Category ID: {defaultCategory}</Typography>
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
