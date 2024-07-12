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
import  {jwtDecode}  from 'jwt-decode';
import CreatePost from './CreatePost';
import AddJewelry from './AddJewelry';
import EditJewelry from './EditJewelry';
import JewelryDetails from './JewelryDetails';
import EditJewelryImages from './EditJewelryImages'; // Import EditJewelryImages
import { useNavigate } from 'react-router-dom'; // Import useNavigate

function StaffPage() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [profile, setProfile] = useState({});
  const [jewelryList, setJewelryList] = useState([]);
  const [filteredJewelryList, setFilteredJewelryList] = useState([]);
  const [auctionList, setAuctionList] = useState([]);
  const [requestList, setRequestList] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedStatus, setSelectedStatus] = useState(''); // State to manage selected status filter
  const [editMode, setEditMode] = useState(false);
  const [addJewelryMode, setAddJewelryMode] = useState(false);
  const [selectedJewelry, setSelectedJewelry] = useState(null);
  const [viewJewelryId, setViewJewelryId] = useState(null);
  const [viewRequestId, setViewRequestId] = useState(null);
  const [editImageMode, setEditImageMode] = useState(false); // State to manage image editing mode
  const [selectedJewelryId, setSelectedJewelryId] = useState(null); // State to store selected jewelry ID
  const [username, setUsername] = useState(''); // State to store username

  const navigate = useNavigate(); // Initialize navigate

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    setEditMode(false);
    setAddJewelryMode(false);
    setViewJewelryId(null);
    setViewRequestId(null);
    setEditImageMode(false); // Reset edit image mode
  };

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      const decodedToken = jwtDecode(token);

      const response = await axios.get(`https://test-production-532e.up.railway.app/account/myinfor`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      setProfile(response.data);
      setUsername(response.data.userName); // Lấy username từ API

    } catch (error) {
      console.error('Error fetching profile data:', error);
    }
  };

  const fetchJewelry = async () => {
    try {
      const response = await axios.get('https://test-production-532e.up.railway.app/jewelry/getAll', {
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
      const response = await axios.get('https://test-production-532e.up.railway.app/auction/list', {
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
      const response = await axios.get('https://test-production-532e.up.railway.app/request/list', {
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
    filterJewelryList(categoryId, selectedStatus);
  };

  const handleStatusChange = (event) => {
    const status = event.target.value;
    setSelectedStatus(status);
    filterJewelryList(selectedCategory, status);
  };

  const filterJewelryList = (categoryId, status) => {
    let filteredList = jewelryList;
    if (categoryId !== '') {
      filteredList = filteredList.filter(jewelry => jewelry.jewelryCategoryId === categoryId);
    }
    if (status !== '') {
      filteredList = filteredList.filter(jewelry => (status === 'active' ? jewelry.status : !jewelry.status));
    }
    setFilteredJewelryList(filteredList);
  };

  const handleEditClick = (jewelry) => {
    setSelectedJewelry(jewelry);
    setEditMode(true);
  };

  const handleDeleteClick = async (jewelry) => {
    try {
      await axios.put(`https://test-production-532e.up.railway.app/jewelry/delist/${jewelry.jewelryId}`, { ...jewelry, status: false }, {
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

  const handleEditImageClick = (jewelryId) => { // Function to handle edit image button click
    setSelectedJewelryId(jewelryId);
    setEditImageMode(true);
  };

  const closeEditImageDialog = () => { // Function to close the image editing dialog
    setEditImageMode(false);
    setSelectedJewelryId(null);
  };

  const handleLogout = () => { // Function to handle logout
    localStorage.removeItem('token');
    navigate('/'); // Redirect to home page
  };

  const updateProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      const decodedToken = jwtDecode(token);

      await axios.put(`https://test-production-532e.up.railway.app/account/update/${username}`, profile, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      fetchProfile();
      alert('Profile updated successfully!');
    } catch (error) {
      console.error('Error updating profile:', error);
    }
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

  const roles = [
    { role_id: 1, role_name: 'ADMIN' },
    { role_id: 2, role_name: 'MANAGER' },
    { role_id: 3, role_name: 'STAFF' },
    { role_id: 4, role_name: 'USER' },
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
            <ListItem button onClick={handleLogout} sx={{ color: '#fff' }} key="logout">
              <ListItemIcon sx={{ color: '#fff' }}>
                <ExitToAppIcon />
              </ListItemIcon>
              <ListItemText primary="Logout" sx={{ color: '#fff' }} />
            </ListItem>
          </List>
        </Grid>

        <Grid item>
        </Grid>
      </Grid>

      <Grid item xs={10} sx={{ backgroundColor: '#000', padding: 2 }}>
        <Box
          display="flex"
          justifyContent="center"
          alignItems="center"
          sx={{ height: '100%', width: '100%' }} // Full width adjustment
        >
          <Box sx={{ width: '100%', backgroundColor: '#fff', padding: 4, borderRadius: 2, color: '#000' }}>
            {selectedIndex === 0 && (
              <Paper sx={{ padding: 2, backgroundColor: '#fff', color: '#000' }}>
                <Typography variant="h6">Profile</Typography>
                <TextField label="Full Name" value={profile.fullName || ''} fullWidth margin="normal" onChange={(e) => setProfile({ ...profile, fullName: e.target.value })} />
                <TextField label="Email" value={profile.email || ''} fullWidth margin="normal" disabled />
                <TextField label="Phone" value={profile.phone || ''} fullWidth margin="normal" disabled />
                <TextField label="Address" value={profile.address || ''} fullWidth margin="normal" onChange={(e) => setProfile({ ...profile, address: e.target.value })} />
                <TextField label="Date of Birth" type="date" value={profile.dob || ''} fullWidth margin="normal" onChange={(e) => setProfile({ ...profile, dob: e.target.value })} />
                <Select
                  label="Sex"
                  value={profile.sex === true ? 'male' : profile.sex === false ? 'female' : 'other'}
                  fullWidth
                  margin="normal"
                  onChange={(e) => {
                    const value = e.target.value;
                    setProfile({ ...profile, sex: value === 'male' ? true : value === 'female' ? false : null });
                  }}
                >
                  <MenuItem value="male">Male</MenuItem>
                  <MenuItem value="female">Female</MenuItem>
                </Select>

                <TextField label="Role" value={roles.find(role => role.role_id === profile.roleId)?.role_name || ''} fullWidth margin="normal" disabled />
                <Button variant="contained" color="primary" onClick={updateProfile}>Update Profile</Button>
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
                  <Select
                    displayEmpty
                    fullWidth
                    name="status"
                    value={selectedStatus}
                    onChange={handleStatusChange}
                    sx={{ backgroundColor: '#fff', mt: 2 }}
                  >
                    <MenuItem value=""><em>All Status</em></MenuItem>
                    <MenuItem value="active">Active</MenuItem>
                    <MenuItem value="inactive">Inactive</MenuItem>
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
                            <Button variant="contained" onClick={() => handleEditImageClick(jewelry.jewelryId)}>Edit Image</Button>
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
                          <TableCell>{auction.status}</TableCell> 
                          <TableCell>{auction.winnerId || 'N/A'}</TableCell>
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
            {editImageMode && (
              <EditJewelryImages jewelryId={selectedJewelryId} onClose={closeEditImageDialog} />
            )}
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default StaffPage;
