import React, { useState } from 'react';
import { Grid, TextField, Button, MenuItem, Select, Paper, Typography } from '@mui/material';
import axios from 'axios';

function AddAccount() {
  const [userName, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [roleId, setRoleId] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Basic validation
    if (!userName || !email || !phone || !roleId) {
      setError('All fields are required.');
      return;
    }

    const token = localStorage.getItem('token');

    try {
      const response = await axios.post('http://localhost:8088/account/create', {
        userName,
        email,
        phone,
        roleId: roleId,
      }, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status === 201) {
        alert('Account created successfully');
        setUsername('');
        setEmail('');
        setPhone('');
        setRoleId('');
        setError('');
      } else {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
    } catch (error) {
      console.error('Error creating account:', error);
      setError(error.response?.data || 'Failed to create account');
    }
  };

  return (
    <Grid container justifyContent="center" sx={{ marginTop: 2 }}>
      <Paper sx={{ padding: 4 }}>
        <Typography variant="h6" gutterBottom>Create New Account</Typography>
        {error && <Typography color="error" gutterBottom>{error}</Typography>}
        <form onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="Username"
                variant="outlined"
                value={userName}
                onChange={(e) => setUsername(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="Email"
                variant="outlined"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="Phone"
                variant="outlined"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <Select
                fullWidth
                value={roleId}
                onChange={(e) => setRoleId(e.target.value)}
                displayEmpty
              >
                <MenuItem value=""><em>Select Role</em></MenuItem>
                <MenuItem value={1}>ADMIN</MenuItem>
                <MenuItem value={2}>MANAGER</MenuItem>
                <MenuItem value={3}>STAFF</MenuItem>
                <MenuItem value={4}>USER</MenuItem>
              </Select>
            </Grid>
            <Grid item xs={12}>
              <Button variant="contained" color="primary" type="submit">Create Account</Button>
            </Grid>
          </Grid>
        </form>
      </Paper>
    </Grid>
  );
}

export default AddAccount;
