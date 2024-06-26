import React, { useEffect, useState } from 'react';
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
  Box,
  TableContainer,
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
} from '@mui/material';
import {
  AccountCircle as AccountCircleIcon,
  Stars as JewelryIcon,
  Gavel as AuctionIcon,
  Assignment as RequestIcon,
  ExitToApp as ExitToAppIcon,
  Search as SearchIcon,
} from '@mui/icons-material';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import AddAuction from './AddAuction';
import EditAuction from './EditAuction';

function ManagerPage() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [profile, setProfile] = useState({});
  const [jewelryList, setJewelryList] = useState([]);
  const [auctionList, setAuctionList] = useState([]);
  const [requestList, setRequestList] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [editMode, setEditMode] = useState(false);
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [finalPrice, setFinalPrice] = useState('');
  const [showAddAuction, setShowAddAuction] = useState(false);
  const [showEditAuction, setShowEditAuction] = useState(false);
  const [selectedAuction, setSelectedAuction] = useState(null);

  useEffect(() => {
    if (selectedIndex === 0) {
      fetchProfile();
    } else if (selectedIndex === 1) {
      fetchJewelry();
    } else if (selectedIndex === 2) {
      fetchAuctions();
    } else if (selectedIndex === 3) {
      fetchRequests();
    }
    setShowAddAuction(false); // Hide AddAuction component when other tabs are selected
    setShowEditAuction(false); // Hide EditAuction component when other tabs are selected
  }, [selectedIndex]);

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      const decodedToken = jwtDecode(token);
      const response = await axios.get(`http://localhost:8088/account/myinfor`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setProfile(response.data);
    } catch (error) {
      console.error('Error fetching profile data:', error);
    }
  };

  const fetchJewelry = async () => {
    try {
      const response = await axios.get('http://localhost:8088/jewelry/getAll', {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setJewelryList(response.data);
    } catch (error) {
      console.error('Error fetching jewelry data:', error);
    }
  };

  const fetchAuctions = async () => {
    try {
      const response = await axios.get('http://localhost:8088/auction/list', {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setAuctionList(response.data);
    } catch (error) {
      console.error('Error fetching auction data:', error);
    }
  };

  const fetchRequests = async () => {
    try {
      const response = await axios.get('http://localhost:8088/request/list', {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setRequestList(response.data);
    } catch (error) {
      console.error('Error fetching request data:', error.response?.data || error.message);
    }
  };

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    setEditMode(false);
    setSelectedRequest(null);
    setShowAddAuction(false);
    setShowEditAuction(false);
  };

  const handleSearch = async () => {
    try {
      if (selectedIndex === 1) {
        const response = await axios.get(`http://localhost:8088/jewelry/search?name=${searchQuery}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setJewelryList(response.data);
      } else if (selectedIndex === 2) {
        const response = await axios.get(`http://localhost:8088/auction/search?name=${searchQuery}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setAuctionList(response.data);
      }
    } catch (error) {
      console.error('Error searching:', error);
    }
  };

  const handleEditClick = (request) => {
    setSelectedRequest(request);
    setFinalPrice(request.finalPrice);
    setEditMode(true);
  };

  const handleAuctionEditClick = (auction) => {
    setSelectedAuction(auction);
    setShowEditAuction(true);
  };

  const handleFinalPriceChange = (e) => {
    setFinalPrice(e.target.value);
  };

  const handleSaveFinalPrice = async () => {
    try {
      await axios.put(`http://localhost:8088/request/update/${selectedRequest.requestId}`, {
        ...selectedRequest,
        finalPrice: finalPrice,
      }, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      alert('Final price updated successfully');
      fetchRequests();
      setEditMode(false);
    } catch (error) {
      console.error('Error updating final price:', error);
      alert('Failed to update final price');
    }
  };

  const handleDeleteAuction = async (auctionId) => {
    try {
      await axios.put(`http://localhost:8088/auction/delete/${auctionId}`, null, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      alert('Auction deleted successfully');
      fetchAuctions(); // Refresh the auction list
    } catch (error) {
      console.error('Error deleting auction:', error);
      if (error.response) {
        console.error('Response data:', error.response.data);
        console.error('Response status:', error.response.status);
        console.error('Response headers:', error.response.headers);
      } else if (error.request) {
        console.error('Request data:', error.request);
      } else {
        console.error('Error message:', error.message);
      }
      alert('Failed to delete auction');
    }
  };

  const handleAuctionAdded = () => {
    fetchAuctions();
    setShowAddAuction(false);
  };

  const handleAuctionUpdated = () => {
    fetchAuctions();
    setShowEditAuction(false);
  };

  return (
    <Grid container sx={{ height: '100vh', backgroundColor: '#000', color: '#fff' }}>
      <Grid item xs={2} container direction="column" justifyContent="space-between" alignItems="center">
        <Grid item>
          <List>
            <ListItem button selected={selectedIndex === 0} onClick={() => handleListItemClick(0)} sx={{ color: '#fff' }}>
              <ListItemIcon sx={{ color: '#fff' }}>
                <AccountCircleIcon />
              </ListItemIcon>
              <ListItemText primary="Profile" />
            </ListItem>
            <ListItem button selected={selectedIndex === 1} onClick={() => handleListItemClick(1)} sx={{ color: '#fff' }}>
              <ListItemIcon sx={{ color: '#fff' }}>
                <JewelryIcon />
              </ListItemIcon>
              <ListItemText primary="Jewelry" />
            </ListItem>
            <ListItem button selected={selectedIndex === 2} onClick={() => handleListItemClick(2)} sx={{ color: '#fff' }}>
              <ListItemIcon sx={{ color: '#fff' }}>
                <AuctionIcon />
              </ListItemIcon>
              <ListItemText primary="Auctions" />
            </ListItem>
            <ListItem button selected={selectedIndex === 3} onClick={() => handleListItemClick(3)} sx={{ color: '#fff' }}>
              <ListItemIcon sx={{ color: '#fff' }}>
                <RequestIcon />
              </ListItemIcon>
              <ListItemText primary="Requests" />
            </ListItem>
            <ListItem button selected={selectedIndex === 4} onClick={() => handleListItemClick(4)} sx={{ color: '#fff' }}>
              <ListItemIcon sx={{ color: '#fff' }}>
                <SearchIcon />
              </ListItemIcon>
              <ListItemText primary="Search" />
            </ListItem>
            <Divider sx={{ backgroundColor: '#fff' }} />
            <ListItem button selected={selectedIndex === 5} onClick={() => handleListItemClick(5)} sx={{ color: '#fff' }}>
              <ListItemIcon sx={{ color: '#fff' }}>
                <ExitToAppIcon />
              </ListItemIcon>
              <ListItemText primary="Logout" />
            </ListItem>
          </List>
        </Grid>
        <Grid item>
          <Typography variant="body2" color="textSecondary" align="center" sx={{ pb: 2 }}>
            © {new Date().getFullYear()} Mercury
          </Typography>
        </Grid>
      </Grid>

      <Grid item xs={10} sx={{ backgroundColor: '#000', padding: 2 }}>
        <Box display="flex" justifyContent="center" alignItems="center" sx={{ height: '100%' }}>
          <Box sx={{ width: '80%', backgroundColor: '#fff', padding: 4, borderRadius: 2, color: '#000' }}>
            {selectedIndex === 0 && !showAddAuction && !showEditAuction && (
              <Paper sx={{ padding: 2 }}>
                <Typography variant="h6">Profile</Typography>
                <TextField label="Full Name" value={profile.fullName || ''} fullWidth margin="normal" />
                <TextField label="Email" value={profile.email || ''} fullWidth margin="normal" />
                <TextField label="Phone" value={profile.phone || ''} fullWidth margin="normal" />
                <Button variant="contained" color="primary">Update Profile</Button>
              </Paper>
            )}

            {selectedIndex === 1 && !showAddAuction && !showEditAuction && (
              <React.Fragment>
                <Typography variant="h6">Jewelry</Typography>
                <TableContainer component={Paper} sx={{ marginTop: 2 }}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Designer</TableCell>
                        <TableCell>Category</TableCell>
                        <TableCell>Actions</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {jewelryList.map(jewelry => (
                        <TableRow key={jewelry.jewelryId}>
                          <TableCell>{jewelry.jewelryId}</TableCell>
                          <TableCell>{jewelry.jewelryName}</TableCell>
                          <TableCell>{jewelry.designer}</TableCell>
                          <TableCell>{jewelry.jewelryCategoryId}</TableCell>
                          <TableCell>
                            <Button variant="contained" color="primary">Edit</Button>
                            <Button variant="contained" color="secondary" sx={{ ml: 1 }}>Delete</Button>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </React.Fragment>
            )}

            {selectedIndex === 2 && !showAddAuction && !showEditAuction && (
              <React.Fragment>
                <Typography variant="h6">Auctions</Typography>
                <Button variant="contained" color="primary" onClick={() => setShowAddAuction(true)} sx={{ mb: 2 }}>
                  Add Auction
                </Button>
                <TableContainer component={Paper} sx={{ marginTop: 2 }}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>Auction ID</TableCell>
                        <TableCell>Start Date</TableCell>
                        <TableCell>End Date</TableCell>
                        <TableCell>Current Price</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Jewelry ID</TableCell>
                        <TableCell>Actions</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {auctionList.map(auction => (
                        <TableRow key={auction.auctionId}>
                          <TableCell>{auction.auctionId}</TableCell>
                          <TableCell>{auction.startDate}</TableCell>
                          <TableCell>{auction.endDate}</TableCell>
                          <TableCell>{auction.currentPrice}</TableCell>
                          <TableCell>{auction.status}</TableCell>
                          <TableCell>{auction.jewelryId}</TableCell>
                          <TableCell>
                            <Button variant="contained" color="primary" onClick={() => handleAuctionEditClick(auction)}>Edit</Button>
                            <Button 
                              variant="contained" 
                              color="secondary" 
                              sx={{ ml: 1 }} 
                              onClick={() => handleDeleteAuction(auction.auctionId)}
                            >
                              Delete
                            </Button>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </React.Fragment>
            )}

            {selectedIndex === 3 && !showAddAuction && !showEditAuction && (
              <React.Fragment>
                <Typography variant="h6">Requests</Typography>
                <TableContainer component={Paper} sx={{ marginTop: 2 }}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Preliminary Price</TableCell>
                        <TableCell>Final Price</TableCell>
                        <TableCell>Actions</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {requestList.map(request => (
                        <TableRow key={request.requestId}>
                          <TableCell>{request.requestId}</TableCell>
                          <TableCell>{request.status}</TableCell>
                          <TableCell>{request.preliminaryPrice}</TableCell>
                          <TableCell>{request.finalPrice}</TableCell>
                          <TableCell>
                            <Button variant="contained" color="primary" onClick={() => handleEditClick(request)}>Edit</Button>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </React.Fragment>
            )}

            {selectedIndex === 4 && !showAddAuction && !showEditAuction && (
              <React.Fragment>
                <Typography variant="h6">Search</Typography>
                <Box sx={{ display: 'flex', alignItems: 'center', mt: 2 }}>
                  <TextField
                    label="Search"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    fullWidth
                  />
                  <Button variant="contained" color="primary" onClick={handleSearch} sx={{ ml: 2 }}>
                    Search
                  </Button>
                </Box>
              </React.Fragment>
            )}

            {editMode && selectedIndex === 3 && selectedRequest && !showAddAuction && !showEditAuction && (
              <Paper sx={{ padding: 2, marginTop: 2 }}>
                <Typography variant="h6">Edit Final Price</Typography>
                <TextField
                  label="Final Price"
                  value={finalPrice}
                  onChange={handleFinalPriceChange}
                  fullWidth
                  margin="normal"
                />
                <Box mt={2}>
                  <Button variant="contained" color="primary" onClick={handleSaveFinalPrice}>Save</Button>
                  <Button variant="contained" color="secondary" onClick={() => setEditMode(false)} sx={{ ml: 2 }}>Cancel</Button>
                </Box>
              </Paper>
            )}

            {showAddAuction && (
              <AddAuction
                onClose={() => setShowAddAuction(false)}
                onAuctionAdded={handleAuctionAdded}
              />
            )}

            {showEditAuction && selectedAuction && (
              <EditAuction
                auction={selectedAuction}
                onClose={() => setShowEditAuction(false)}
                onAuctionUpdated={handleAuctionUpdated}
              />
            )}
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default ManagerPage;
