import React, { useState, useEffect } from 'react';
import {
  Grid,
  Typography,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Divider,
  Paper,
  Button,
  TextField,
  Select,
  MenuItem,
  TableContainer,
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  Box,
} from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import JewelryIcon from '@mui/icons-material/Stars';
import AuctionIcon from '@mui/icons-material/Gavel';
import RequestIcon from '@mui/icons-material/Assignment';
import PostIcon from '@mui/icons-material/PostAdd';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import axios from 'axios';

function StaffPage() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [profile, setProfile] = useState({});
  const [jewelryList, setJewelryList] = useState([]);
  const [auctionList, setAuctionList] = useState([]);
  const [requestList, setRequestList] = useState([]);
  const [postList, setPostList] = useState([]);

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    // Fetch data based on selected index
  };

  // Fetch functions for different entities
  const fetchProfile = async () => {
    // Fetch profile data
  };

  const fetchJewelry = async () => {
    // Fetch jewelry data
  };

  const fetchAuctions = async () => {
    // Fetch auction data
  };

  const fetchRequests = async () => {
    // Fetch request data
  };

  const fetchPosts = async () => {
    // Fetch post data
  };

  // Example fetch for profile
  useEffect(() => {
    if (selectedIndex === 0) {
      fetchProfile();
    }
  }, [selectedIndex]);

  return (
    <Grid container sx={{ height: '100vh', backgroundColor: '#000', color: '#fff' }}>
      {/* Sidebar content */}
      <Grid item xs={2} container direction="column" justifyContent="space-between" alignItems="center">
        <Grid item>
          <List>
            <ListItem button selected={selectedIndex === 0} onClick={() => handleListItemClick(0)} sx={{ color: '#fff' }} key="profile">
              <ListItemIcon sx={{ color: '#fff' }}>
                <AccountCircleIcon />
              </ListItemIcon>
              <ListItemText primary="Profile" sx={{ color: '#fff' }} />
            </ListItem>
            <ListItem button selected={selectedIndex === 1} onClick={() => handleListItemClick(1)} sx={{ color: '#fff' }} key="jewelry">
              <ListItemIcon sx={{ color: '#fff' }}>
                <JewelryIcon />
              </ListItemIcon>
              <ListItemText primary="Jewelry" sx={{ color: '#fff' }} />
            </ListItem>
            <ListItem button selected={selectedIndex === 2} onClick={() => handleListItemClick(2)} sx={{ color: '#fff' }} key="auction">
              <ListItemIcon sx={{ color: '#fff' }}>
                <AuctionIcon />
              </ListItemIcon>
              <ListItemText primary="Auctions" sx={{ color: '#fff' }} />
            </ListItem>
            <ListItem button selected={selectedIndex === 3} onClick={() => handleListItemClick(3)} sx={{ color: '#fff' }} key="request">
              <ListItemIcon sx={{ color: '#fff' }}>
                <RequestIcon />
              </ListItemIcon>
              <ListItemText primary="Requests" sx={{ color: '#fff' }} />
            </ListItem>
            <ListItem button selected={selectedIndex === 4} onClick={() => handleListItemClick(4)} sx={{ color: '#fff' }} key="post">
              <ListItemIcon sx={{ color: '#fff' }}>
                <PostIcon />
              </ListItemIcon>
              <ListItemText primary="Posts" sx={{ color: '#fff' }} />
            </ListItem>
          </List>
          <Divider sx={{ backgroundColor: '#fff' }} />
          <List>
            <ListItem button selected={selectedIndex === 5} onClick={() => handleListItemClick(5)} sx={{ color: '#fff' }} key="logout">
              <ListItemIcon sx={{ color: '#fff' }}>
                <ExitToAppIcon />
              </ListItemIcon>
              <ListItemText primary="Logout" sx={{ color: '#fff' }} />
            </ListItem>
          </List>
        </Grid>

        {/* Footer */}
        <Grid item>
          <Typography variant="body2" color="textSecondary" align="center" sx={{ pb: 2 }}>
            © {new Date().getFullYear()} Mercury
          </Typography>
        </Grid>
      </Grid>

      {/* Main content */}
      <Grid item xs={10} sx={{ backgroundColor: '#000', padding: 2 }}>
        <Box
          display="flex"
          justifyContent="center"
          alignItems="center"
          sx={{ height: '100%' }}
        >
          <Box sx={{ width: '80%', backgroundColor: '#fff', padding: 4, borderRadius: 2, color: '#000' }}>
            {selectedIndex === 0 && (
              <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
                <Typography variant="h6">Profile</Typography>
                {/* Profile update form */}
                <TextField label="Full Name" value={profile.full_name} fullWidth margin="normal" />
                <TextField label="Email" value={profile.email} fullWidth margin="normal" />
                <TextField label="Phone" value={profile.phone} fullWidth margin="normal" />
                <Button variant="contained" color="primary">Update Profile</Button>
              </Paper>
            )}
            {selectedIndex === 1 && (
              <React.Fragment>
                <Grid container justifyContent="space-between" sx={{ mb: 2 }}>
                  <TextField
                    label="Search by name"
                    variant="outlined"
                    sx={{ backgroundColor: '#fff', marginRight: 2 }}
                  />
                  <Select
                    displayEmpty
                    sx={{ backgroundColor: '#fff', minWidth: 120 }}
                  >
                    <MenuItem value=""><em>All Categories</em></MenuItem>
                    <MenuItem value={1}>Category 1</MenuItem>
                    <MenuItem value={2}>Category 2</MenuItem>
                  </Select>
                  <Button
                    variant="contained"
                    color="primary"
                  >
                    Add Jewelry
                  </Button>
                </Grid>
                <TableContainer component={Paper} sx={{ backgroundColor: '#fff', p: 2 }}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Category</TableCell>
                        <TableCell>Price</TableCell>
                        <TableCell>Actions</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {jewelryList.map((jewelry) => (
                        <TableRow key={jewelry.id}>
                          <TableCell>{jewelry.id}</TableCell>
                          <TableCell>{jewelry.name}</TableCell>
                          <TableCell>{jewelry.category}</TableCell>
                          <TableCell>{jewelry.price}</TableCell>
                          <TableCell>
                            <Button variant="contained" color="primary">Edit</Button>
                            <Button variant="contained" color="secondary">Delete</Button>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </React.Fragment>
            )}
            {selectedIndex === 2 && (
              <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
                <Typography variant="h6">Auctions</Typography>
                {/* Auction list and actions */}
              </Paper>
            )}
            {selectedIndex === 3 && (
              <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
                <Typography variant="h6">Requests</Typography>
                {/* Request list and actions */}
              </Paper>
            )}
            {selectedIndex === 4 && (
              <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
                <Typography variant="h6">Posts</Typography>
                {/* Post list and actions */}
              </Paper>
            )}
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default StaffPage;
