import React, { useState } from 'react';
import { Box, Button, TextField, Typography, Select, MenuItem } from '@mui/material';
import axios from 'axios';

const PostDetail = ({ post, onClose, handleUpdatePost }) => {
  const [postData, setPostData] = useState(post);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPostData({ ...postData, [name]: value });
  };

  const handleStatusChange = (e) => {
    setPostData({ ...postData, status: e.target.value === 'true' });
  };

  const handleSaveChanges = async () => {
    try {
      await axios.put(`http://localhost:8088/posts/update/${postData.postId}`, postData, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      handleUpdatePost(postData);
      onClose();
    } catch (error) {
      console.error('Error updating post:', error);
    }
  };

  return (
    <Box mt={2} sx={{ width: '100%' }}>
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
      <Button variant="contained" onClick={handleSaveChanges}>Save Changes</Button>
      <Button variant="contained" onClick={onClose} sx={{ ml: 2 }}>Back</Button>
    </Box>
  );
};

export default PostDetail;
