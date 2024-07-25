import React, { useState, useEffect } from 'react';
import { Box, Button, TextField, Typography, Select, MenuItem } from '@mui/material';
import axios from 'axios';

const PostDetail = ({ post, onClose, handleUpdatePost }) => {
  const [postData, setPostData] = useState(post);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get('http://localhost:8088/categories');
        setCategories(response.data);
      } catch (error) {
        console.error('Error fetching categories:', error);
        setCategories([
          { category_id: 1, category_name: 'AUCTION_INSIGHTS' },
          { category_id: 2, category_name: 'BIDDER_GUIDE' },
          { category_id: 3, category_name: 'SELLER_GUIDE' },
          { category_id: 4, category_name: 'AUCTION_NEWS' },
          { category_id: 5, category_name: 'TREND' },
          { category_id: 6, category_name: 'UPCOMING_AUCTION' },
          { category_id: 7, category_name: 'JEWELRY_CARE' },
          { category_id: 8, category_name: 'BEHIND_THE_SCENES' },
        ]);
      }
    };

    fetchCategories();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPostData({ ...postData, [name]: value });
  };

  const handleStatusChange = (e) => {
    setPostData({ ...postData, status: e.target.value === 'true' });
  };

  const handleCategoryChange = (e) => {
    setPostData({ ...postData, post_category_id: e.target.value });
  };

  const handleSaveChanges = async () => {
    try {
      await axios.put(`http://localhost:8088/posts/update/${postData.postId}`, postData, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      handleUpdatePost(postData);
      alert('Updated successfully!');
      onClose();

    } catch (error) {
      console.error('Error updating post:', error);
    }
  };

  return (
    <Box mt={2} sx={{ width: '100%', marginBottom: '20vh' }}>
      <Typography variant="h6">Post Details</Typography>
      <TextField
        label="Title"
        name="title"
        value={postData.title}
        onChange={handleInputChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Content"
        name="content"
        value={postData.content}
        onChange={handleInputChange}
        fullWidth
        multiline
        rows={4}
        margin="normal"
      />
      <Select
        label="Status"
        name="status"
        value={postData.status ? 'true' : 'false'}
        onChange={handleStatusChange}
        fullWidth
        margin="normal"
      >
        <MenuItem value="true">True</MenuItem>
        <MenuItem value="false">False</MenuItem>
      </Select>
      <Select
        label="Category"
        name="post_category_id"
        value={postData.post_category_id}
        onChange={handleCategoryChange}
        fullWidth
        margin="normal"
      >
        {categories.map((category) => (
          <MenuItem key={category.category_id} value={category.category_id}>
            {category.category_name}
          </MenuItem>
        ))}
      </Select>
      <Button variant="contained" onClick={handleSaveChanges}>Save Changes</Button>
      <Button variant="contained" onClick={onClose} sx={{ ml: 2 }}>Back</Button>
    </Box>
  );
};

export default PostDetail;
