import React, { useState, useEffect } from 'react';
import { Grid, TextField, Button, Select, MenuItem, InputLabel, FormControl, Box } from '@mui/material';
import axios from 'axios';

const EditAccount = ({ account }) => {
  const [formData, setFormData] = useState({
    accountId: account.accountId || '',
    fullName: account.fullName || '',
    userName: account.userName || '',
    address: account.address || '',
    dob: account.dob || '',
    email: account.email || '',
    sex: account.sex !== null ? account.sex : '',
    phone: account.phone || '',
    status: account.status !== null ? account.status : '',
    roleId: account.roleId !== null ? account.roleId : '',
  });

  useEffect(() => {
    setFormData({
      accountId: account.accountId || '',
      fullName: account.fullName || '',
      userName: account.userName || '',
      address: account.address || '',
      dob: account.dob || '',
      email: account.email || '',
      sex: account.sex !== null ? account.sex : '',
      phone: account.phone || '',
      status: account.status !== null ? account.status : '',
      roleId: account.roleId !== null ? account.roleId : '',
    });
  }, [account]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSave = async () => {
    const token = localStorage.getItem('token');
    console.log("Username for Update:", formData.userName);
    console.log("Request Payload:", JSON.stringify(formData, null, 2));

    try {
      const response = await axios.put(`http://localhost:8088/account/update/${formData.userName}`, formData, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      alert('Account updated successfully');
    } catch (error) {
      console.error('Error updating account:', error);
    }
  };

  return (
    <Box sx={{ p: 3, backgroundColor: '#fff' }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <TextField
            label="Account ID"
            name="accountId"
            variant="outlined"
            value={formData.accountId}
            InputProps={{
              readOnly: true,
            }}
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Full Name"
            name="fullName"
            variant="outlined"
            value={formData.fullName}
            onChange={handleChange}
            fullWidth
            sx={{ mb: 2 }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Username"
            name="userName"
            variant="outlined"
            value={formData.userName}
            InputProps={{
              readOnly: true,
            }}
            fullWidth
            sx={{ mb: 2 }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Address"
            name="address"
            variant="outlined"
            value={formData.address}
            onChange={handleChange}
            fullWidth
            sx={{ mb: 2 }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Date of Birth"
            name="dob"
            type="date"
            variant="outlined"
            value={formData.dob}
            onChange={handleChange}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
            sx={{ mb: 2 }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Email"
            name="email"
            variant="outlined"
            value={formData.email}
            InputProps={{
              readOnly: true,
            }}
            fullWidth
            sx={{ mb: 2 }}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Sex</InputLabel>
            <Select
              label="Sex"
              name="sex"
              value={formData.sex}
              onChange={handleChange}
            >
              <MenuItem value={true}>Female</MenuItem>
              <MenuItem value={false}>Male</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Phone"
            name="phone"
            variant="outlined"
            value={formData.phone}
            onChange={handleChange}
            fullWidth
            sx={{ mb: 2 }}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Status</InputLabel>
            <Select
              label="Status"
              name="status"
              value={formData.status}
              onChange={handleChange}
            >
              <MenuItem value={true}>Enable</MenuItem>
              <MenuItem value={false}>Disable</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12}>
          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Role</InputLabel>
            <Select
              label="Role"
              name="roleId"
              value={formData.roleId}
              onChange={handleChange}
            >
              <MenuItem value={1}>ADMIN</MenuItem>
              <MenuItem value={2}>MANAGER</MenuItem>
              <MenuItem value={3}>STAFF</MenuItem>
              <MenuItem value={4}>USER</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12}>
          <Button variant="contained" color="primary" onClick={handleSave}>
            Save
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
};

export default EditAccount;
