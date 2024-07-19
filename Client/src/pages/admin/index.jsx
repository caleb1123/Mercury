import React, { useState, useEffect } from 'react';
import {
  Grid, Typography, List, ListItem, ListItemIcon, ListItemText, Divider, TableContainer, Table,
  TableHead, TableBody, TableRow, TableCell, Paper, Button, TextField, MenuItem, Select
} from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import AssessmentIcon from '@mui/icons-material/Assessment';
import axios from 'axios';
import AddAccount from './AddAccount';
import EditAccount from './EditAccount';

function Sidebar() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [accounts, setAccounts] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRole, setSelectedRole] = useState('');
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [filteredAccounts, setFilteredAccounts] = useState([]);

  useEffect(() => {
    if (selectedIndex === 0) {
      fetchAccounts();
    }
  }, [selectedIndex]);

  useEffect(() => {
    if (searchTerm) {
      searchAccounts(searchTerm);
    } else {
      setFilteredAccounts(accounts);
    }
  }, [searchTerm, accounts]);

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    if (index === 1) {
      window.location.href = '/'; 
    }
  };

  const fetchAccounts = async () => {
    const token = localStorage.getItem('token');
    try {
      const response = await axios.get('http://localhost:8088/account/list', {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = response.data;
      setAccounts(data);
      setFilteredAccounts(data); // Set filtered accounts initially to all accounts
    } catch (error) {
      console.error('Error fetching accounts:', error);
    }
  };

  const searchAccounts = async (name) => {
    const token = localStorage.getItem('token');
    try {
      const response = await axios.get(`http://localhost:8088/account/search/${name}`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = response.data;
      setFilteredAccounts(data);
    } catch (error) {
      console.error('Error searching accounts:', error);
    }
  };

  const fetchAccountDetails = async (id) => {
    const token = localStorage.getItem('token');
    try {
      const response = await axios.get(`http://localhost:8088/account/id/${id}`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = response.data;
      setSelectedAccount(data);
    } catch (error) {
      console.error('Error fetching account details:', error);
    }
  };

  const handleEditClick = (id) => {
    console.log("Editing account with ID:", id);
    if (id) {
      fetchAccountDetails(id);
      setSelectedIndex(5);
    } else {
      console.error("Account ID is undefined");
    }
  };

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);
  };

  const handleLogout = () => {
    // Remove the token from local storage
    localStorage.removeItem('token');
    // Redirect to the home page
    window.location.href = '/';
  };

  const filteredAndSearchedAccounts = filteredAccounts.filter((account) => {
    return (!selectedRole || account.roleId === selectedRole);
  });

  return (
    <Grid container sx={{ height: '100vh', backgroundColor: '#000', color: '#fff' }}>
      <Grid item xs={2} container direction="column" justifyContent="space-between" alignItems="center">
        <Grid item>
          <List>
            <ListItem button selected={selectedIndex === 0} onClick={() => handleListItemClick(0)} sx={{ color: '#fff' }} key="account">
              <ListItemIcon sx={{ color: '#fff' }}>
                <AccountCircleIcon />
              </ListItemIcon>
              <ListItemText primary="Account" sx={{ color: '#fff' }} />
            </ListItem>
            <ListItem button selected={selectedIndex === 2} onClick={() => handleListItemClick(2)} sx={{ color: '#fff' }} key="profile">
              <ListItemIcon sx={{ color: '#fff' }}>
                <AssessmentIcon />
              </ListItemIcon>
              <ListItemText primary="Report" sx={{ color: '#fff' }} />
            </ListItem>
          </List>
          <Divider sx={{ backgroundColor: '#fff' }} />
          <List>
            <ListItem button selected={selectedIndex === 3} onClick={handleLogout} sx={{ color: '#fff' }} key="logout">
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
        {selectedIndex === 0 && (
          <React.Fragment>
            <Grid container justifyContent="space-between" sx={{ mb: 2 }}>
              <TextField
                label="Search by name"
                variant="outlined"
                value={searchTerm}
                onChange={handleSearchChange}
                sx={{ backgroundColor: '#fff', marginRight: 2 }}
              />
              <Select
                value={selectedRole}
                onChange={handleRoleChange}
                displayEmpty
                sx={{ backgroundColor: '#fff', minWidth: 120 }}
              >
                <MenuItem value=""><em>All Roles</em></MenuItem>
                <MenuItem value={1}>ADMIN</MenuItem>
                <MenuItem value={2}>MANAGER</MenuItem>
                <MenuItem value={3}>STAFF</MenuItem>
                <MenuItem value={4}>USER</MenuItem>
              </Select>
              <Button
                variant="contained"
                color="primary"
                onClick={() => setSelectedIndex(4)}
              >
                Add
              </Button>
            </Grid>
            <TableContainer component={Paper} sx={{ backgroundColor: '#fff', p: 2 }}>
              <Table sx={{ tableLayout: 'fixed', width: '100%' }}>
                <TableHead>
                  <TableRow>
                    <TableCell sx={{ width: '10%' }}>Account ID</TableCell>
                    <TableCell sx={{ width: '15%' }}>Address</TableCell>
                    <TableCell sx={{ width: '10%' }}>Date of Birth</TableCell>
                    <TableCell sx={{ width: '15%' }}>Email</TableCell>
                    <TableCell sx={{ width: '15%' }}>Full Name</TableCell>
                    <TableCell sx={{ width: '10%' }}>Phone</TableCell>
                    <TableCell sx={{ width: '5%' }}>Sex</TableCell>
                    <TableCell sx={{ width: '5%' }}>Status</TableCell>
                    <TableCell sx={{ width: '10%' }}>Role</TableCell>
                    <TableCell sx={{ width: '5%' }}>Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {filteredAndSearchedAccounts.map((account) => (
                    <TableRow key={account.accountId}>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.accountId || 'NULL'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.address || 'NULL'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.dob || 'NULL'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.email || 'NULL'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.fullName || 'NULL'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.phone || 'NULL'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.sex ? 'Female' : 'Male'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>{account.status ? 'Enable' : 'Disable'}</TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>
                        {(() => {
                          switch (account.roleId) {
                            case 1: return 'ADMIN';
                            case 2: return 'MANAGER';
                            case 3: return 'STAFF';
                            case 4: return 'USER';
                            default: return '-';
                          }
                        })()}
                      </TableCell>
                      <TableCell sx={{ wordWrap: 'break-word' }}>
                        <Button
                          variant="outlined"
                          color="primary"
                          onClick={() => handleEditClick(account.accountId)}
                        >
                          Edit
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </React.Fragment>
        )}
        {selectedIndex === 4 && <AddAccount />}
        {selectedIndex === 5 && selectedAccount && (
          <Paper sx={{ padding: 2 }}>
            <EditAccount account={selectedAccount} />
          </Paper>
        )}
      </Grid>
    </Grid>
  );
}

export default Sidebar;
