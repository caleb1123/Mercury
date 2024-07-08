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
  Select,
  MenuItem,
} from '@mui/material';
import {
  AccountCircle as AccountCircleIcon,
  Stars as JewelryIcon,
  Gavel as AuctionIcon,
  Assignment as RequestIcon,
  ExitToApp as ExitToAppIcon,
} from '@mui/icons-material';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import AddAuction from './AddAuction';
import EditAuction from './EditAuction';
import RequestDetails from './RequestDetails';
import EditJewelry from './EditJewelry';
import AddJewelry from './AddJewelry';
import JewelryDetails from './JewelryDetails';
import EditJewelryImages from './EditJewelryImages';
import { useNavigate } from 'react-router-dom';

function ManagerPage() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [profile, setProfile] = useState({});
  const [jewelryList, setJewelryList] = useState([]);
  const [auctionList, setAuctionList] = useState([]);
  const [requestList, setRequestList] = useState([]);
  const [filteredJewelryList, setFilteredJewelryList] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedStatus, setSelectedStatus] = useState('');
  const [editMode, setEditMode] = useState(false);
  const [selectedJewelry, setSelectedJewelry] = useState(null);
  const [addJewelryMode, setAddJewelryMode] = useState(false);
  const [viewJewelryId, setViewJewelryId] = useState(null);
  const [viewRequestId, setViewRequestId] = useState(null);
  const [editImageMode, setEditImageMode] = useState(false);
  const [selectedJewelryId, setSelectedJewelryId] = useState(null);
  const [username, setUsername] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [finalPrice, setFinalPrice] = useState('');
  const [showAddAuction, setShowAddAuction] = useState(false);
  const [showEditAuction, setShowEditAuction] = useState(false);
  const [selectedAuction, setSelectedAuction] = useState(null);

  const navigate = useNavigate();

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
    setShowAddAuction(false);
    setShowEditAuction(false);
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
      setUsername(response.data.userName);
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
      setFilteredJewelryList(response.data);
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
      filteredList = filteredList.filter((jewelry) => jewelry.jewelryCategoryId === categoryId);
    }
    if (status !== '') {
      filteredList = filteredList.filter((jewelry) => (status === 'active' ? jewelry.status : !jewelry.status));
    }
    setFilteredJewelryList(filteredList);
  };

  const handleEditClick = (jewelry) => {
    setSelectedJewelry(jewelry);
    setEditMode(true);
  };

  const handleDeleteClick = async (jewelry) => {
    try {
      await axios.put(`http://localhost:8088/jewelry/delist/${jewelry.jewelryId}`, { ...jewelry, status: false }, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      fetchJewelry();
    } catch (error) {
      console.error('Error deleting jewelry:', error);
    }
  };

  const handleViewRequestClick = (request) => {
    setSelectedRequest(request);
  };

  const handleEditImageClick = (jewelryId) => {
    setSelectedJewelryId(jewelryId);
    setEditImageMode(true);
  };

  const closeEditImageDialog = () => {
    setEditImageMode(false);
    setSelectedJewelryId(null);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  const updateProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.put(`http://localhost:8088/account/update/${username}`, profile, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      fetchProfile();
      alert('Profile updated successfully!');
    } catch (error) {
      console.error('Error updating profile:', error);
    }
  };

  const handleEditClickRequest = (request) => {
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
      await axios.put(`http://localhost:8088/request/update/final/${selectedRequest.requestId}`, {
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
      fetchAuctions();
    } catch (error) {
      console.error('Error deleting auction:', error);
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
    <Grid container sx={{ height: '100vh', width: '100vw', backgroundColor: '#000', color: '#fff' }}>
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
            <Divider sx={{ backgroundColor: '#fff' }} />
            <ListItem button onClick={handleLogout} sx={{ color: '#fff' }}>
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
          <Box sx={{ width: '100%', maxWidth: '1200px', backgroundColor: '#fff', padding: 4, borderRadius: 2, color: '#000' }}>
            {selectedIndex === 0 && (
              <Paper sx={{ padding: 2 }}>
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
                <TextField label="Role" value={roles.find((role) => role.role_id === profile.roleId)?.role_name || ''} fullWidth margin="normal" disabled />
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
                          <TableCell>{categories.find((category) => category.jewelry_category_id === jewelry.jewelryCategoryId)?.category_name}</TableCell>
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
                      {auctionList.map((auction) => (
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
            {selectedIndex === 3 && !viewRequestId && (
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
                            <Button variant="contained" onClick={() => handleViewRequestClick(request)}>View Request</Button>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </Paper>
            )}
            {selectedIndex === 3 && selectedRequest && (
              <RequestDetails 
                request={selectedRequest} 
                onSave={handleSaveFinalPrice}
                onFinalPriceChange={handleFinalPriceChange}
                finalPrice={finalPrice}
                onCancel={() => setSelectedRequest(null)} 
              />
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
              <Box display="flex" justifyContent="center" alignItems="center" sx={{ height: '100%' }}>
                <RequestDetails
                  request={selectedRequest}
                  onSave={handleSaveFinalPrice}
                  onFinalPriceChange={handleFinalPriceChange}
                  finalPrice={finalPrice}
                  onCancel={() => setEditMode(false)}
                />
              </Box>
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

            {editImageMode && (
              <EditJewelryImages jewelryId={selectedJewelryId} onClose={closeEditImageDialog} />
            )}
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default ManagerPage;
