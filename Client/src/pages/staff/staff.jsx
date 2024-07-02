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
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import CreatePost from './CreatePost';
import AddJewelry from './AddJewelry';
import EditJewelry from './EditJewelry';
import JewelryDetails from './JewelryDetails';

function StaffPage() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [profile, setProfile] = useState({});
  const [jewelryList, setJewelryList] = useState([]);
  const [filteredJewelryList, setFilteredJewelryList] = useState([]);
  const [auctionList, setAuctionList] = useState([]);
  const [requestList, setRequestList] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [editMode, setEditMode] = useState(false);
  const [addJewelryMode, setAddJewelryMode] = useState(false);
  const [selectedJewelry, setSelectedJewelry] = useState(null);
  const [viewJewelryId, setViewJewelryId] = useState(null);
  const [viewRequestId, setViewRequestId] = useState(null);

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    setEditMode(false);
    setAddJewelryMode(false);
    setViewJewelryId(null);
    setViewRequestId(null);
  };

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      const decodedToken = jwtDecode(token);
      const username = decodedToken.username;

      const response = await axios.get(`http://localhost:8088/account/myinfor`, {
        headers: {
          'Authorization': `Bearer ${token}`,
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
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setJewelryList(response.data);
      setFilteredJewelryList(response.data);
    } catch (error) {
      console.error('Error fetching jewelry data:', error);
    }
  };

  const fetchAuctions = async () => {
    try {
      const response = await axios.get('http://localhost:8088/auction/list', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
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
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setRequestList(response.data);
    } catch (error) {
      console.error('Error fetching request data:', error.response?.data || error.message);
    }
  };

  const fetchPosts = async () => {
    // Fetch post data
  };

  const handleCategoryChange = (event) => {
    const categoryId = event.target.value;
    setSelectedCategory(categoryId);
    if (categoryId === '') {
      setFilteredJewelryList(jewelryList);
    } else {
      const filteredList = jewelryList.filter(jewelry => jewelry.jewelryCategoryId === categoryId);
      setFilteredJewelryList(filteredList);
    }
  };

  const handleEditClick = (jewelry) => {
    setSelectedJewelry(jewelry);
    setEditMode(true);
  };

  const handleDeleteClick = async (jewelry) => {
    try {
      await axios.put(`http://localhost:8088/jewelry/delist/${jewelry.jewelryId}`, { ...jewelry, status: false }, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      fetchJewelry();
    } catch (error) {
      console.error('Error deleting jewelry:', error);
    }
  };

  const handleViewJewelryClick = (jewelryId, requestId) => {
    setViewJewelryId(jewelryId);
    setViewRequestId(requestId);
  };

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
  }, [selectedIndex]);

  const categories = [
    { jewelry_category_id: 1, category_name: 'RINGS' },
    { jewelry_category_id: 2, category_name: 'BRACELETS' },
    { jewelry_category_id: 3, category_name: 'BROOCHES_PINS' },
    { jewelry_category_id: 4, category_name: 'CUFFLINKS_TIEPINS_TIECLIPS' },
    { jewelry_category_id: 5, category_name: 'EARRINGS' },
    { jewelry_category_id: 6, category_name: 'LOOSESTONES_BEADS' },
    { jewelry_category_id: 7, category_name: 'NECKLACES_PENDANTS' },
    { jewelry_category_id: 8, category_name: 'WATCHES' },
  ];

  return (
    <Grid container sx={{ height: '100vh', backgroundColor: '#000', color: '#fff' }}>
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
            <ListItem button selected={selectedIndex === 5} onClick={() => handleListItemClick(5)} sx={{ color: '#fff' }} key="createPost">
              <ListItemIcon sx={{ color: '#fff' }}>
              </ListItemIcon>
              <ListItemText primary="Create Post" sx={{ color: '#fff' }} />
            </ListItem>
          </List>
          <Divider sx={{ backgroundColor: '#fff' }} />
          <List>
            <ListItem button selected={selectedIndex === 6} onClick={() => handleListItemClick(6)} sx={{ color: '#fff' }} key="logout">
              <ListItemIcon sx={{ color: '#fff' }}>
                <ExitToAppIcon />
              </ListItemIcon>
              <ListItemText primary="Logout" sx={{ color: '#fff' }} />
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
                <TextField label="Full Name" value={profile.fullName || ''} fullWidth margin="normal" />
                <TextField label="Email" value={profile.email || ''} fullWidth margin="normal" />
                <TextField label="Phone" value={profile.phone || ''} fullWidth margin="normal" />
                <Button variant="contained" color="primary">Update Profile</Button>
              </Paper>
            )}
            {selectedIndex === 1 && !editMode && !addJewelryMode && !viewJewelryId && (
              <React.Fragment>
                <Box sx={{ mb: 2 }}>
                  <Select
                    displayEmpty
                    fullWidth
                    name="jewelryCategoryId"
                    value={selectedCategory}
                    onChange={handleCategoryChange}
                    sx={{ backgroundColor: '#fff' }}
                  >
                    <MenuItem value=""><em>All Categories</em></MenuItem>
                    {categories.map((category) => (
                      <MenuItem key={category.jewelry_category_id} value={category.jewelry_category_id}>
                        {category.category_name}
                      </MenuItem>
                    ))}
                  </Select>
                </Box>
                <TableContainer component={Paper} sx={{ backgroundColor: '#fff', p: 2 }}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Designer</TableCell>
                        <TableCell>Gemstone</TableCell>
                        <TableCell>Category</TableCell>
                        <TableCell>Price</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Actions</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {filteredJewelryList.map((jewelry) => (
                        <TableRow key={jewelry.jewelryId}>
                          <TableCell>{jewelry.jewelryId}</TableCell>
                          <TableCell>{jewelry.jewelryName}</TableCell>
                          <TableCell>{jewelry.designer}</TableCell>
                          <TableCell>{jewelry.gemstone}</TableCell>
                          <TableCell>{categories.find(category => category.jewelry_category_id === jewelry.jewelryCategoryId)?.category_name}</TableCell>
                          <TableCell>{jewelry.startingPrice}</TableCell>
                          <TableCell>{jewelry.status ? 'Active' : 'Inactive'}</TableCell>
                          <TableCell>
                            <Button variant="contained" color="primary" onClick={() => handleEditClick(jewelry)}>Edit</Button>
                            <Button variant="contained" color="secondary" onClick={() => handleDeleteClick(jewelry)}>Delete</Button>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
                <Button variant="contained" color="primary" onClick={() => setAddJewelryMode(true)} sx={{ mt: 2 }}>
                  Add Jewelry
                </Button>
              </React.Fragment>
            )}
            {selectedIndex === 1 && editMode && selectedJewelry && (
              <EditJewelry jewelry={selectedJewelry} fetchJewelry={fetchJewelry} setEditMode={setEditMode} />
            )}
            {selectedIndex === 1 && addJewelryMode && (
              <AddJewelry fetchJewelry={fetchJewelry} />
            )}
            {selectedIndex === 1 && viewJewelryId && (
              <JewelryDetails jewelryId={viewJewelryId} requestId={viewRequestId} onClose={() => { setViewJewelryId(null); setViewRequestId(null); }} />
            )}
            {selectedIndex === 2 && (
              <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
                <Typography variant="h6">Auctions</Typography>
                <TableContainer component={Paper} sx={{ backgroundColor: '#fff', p: 2 }}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Current Price</TableCell>
                        <TableCell>End Date</TableCell>
                        <TableCell>Start Date</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Winner ID</TableCell>
                        <TableCell>Jewelry ID</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {auctionList.map((auction) => (
                        <TableRow key={auction.auctionId}>
                          <TableCell>{auction.auctionId}</TableCell>
                          <TableCell>{auction.currentPrice}</TableCell>
                          <TableCell>{auction.endDate}</TableCell>
                          <TableCell>{auction.startDate}</TableCell>
                          <TableCell>{auction.status ? 'Active' : 'Inactive'}</TableCell>
                          <TableCell>{auction.winner_id || 'N/A'}</TableCell>
                          <TableCell>{auction.jewelryId}</TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </Paper>
            )}
            {selectedIndex === 3 && !viewJewelryId && (
              <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
                <Typography variant="h6">Requests</Typography>
                <TableContainer component={Paper} sx={{ backgroundColor: '#fff', p: 2 }}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>Request ID</TableCell>
                        <TableCell>Evaluation Date</TableCell>
                        <TableCell>Final Price</TableCell>
                        <TableCell>Preliminary Price</TableCell>
                        <TableCell>Request Date</TableCell>
                        <TableCell>Delivery Date</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Seller ID</TableCell>
                        <TableCell>Jewelry ID</TableCell>
                        <TableCell>Actions</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {requestList.map((request) => (
                        <TableRow key={request.requestId}>
                          <TableCell>{request.requestId}</TableCell>
                          <TableCell>{request.evaluationDate || 'N/A'}</TableCell>
                          <TableCell>{request.finalPrice}</TableCell>
                          <TableCell>{request.preliminaryPrice}</TableCell>
                          <TableCell>{request.requestDate}</TableCell>
                          <TableCell>{request.deliveryDate}</TableCell>
                          <TableCell>{request.status}</TableCell>
                          <TableCell>{request.sellerId}</TableCell>
                          <TableCell>{request.jewelryId}</TableCell>
                          <TableCell>
                            <Button variant="contained" onClick={() => handleViewJewelryClick(request.jewelryId, request.requestId)}>View Jewelry</Button>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </Paper>
            )}
            {selectedIndex === 3 && viewJewelryId && (
              <JewelryDetails jewelryId={viewJewelryId} requestId={viewRequestId} onClose={() => { setViewJewelryId(null); setViewRequestId(null); }} />
            )}
            {selectedIndex === 4 && (
              <AddJewelry fetchJewelry={fetchJewelry} />
            )}
            {selectedIndex === 5 && (
              <Box
                display="flex"
                justifyContent="center"
                alignItems="center"
                sx={{ height: '100%' }}
              >
                <Box sx={{ width: '100vh', backgroundColor: '#fff', padding: 4, borderRadius: 2, color: '#000' }}>
                  <CreatePost />
                </Box>
              </Box>
            )}
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default StaffPage;
