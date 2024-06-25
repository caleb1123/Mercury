import React, { useState, useEffect } from 'react';
import { Grid, Typography, List, ListItem, ListItemIcon, ListItemText, Divider, TableContainer, Table, TableHead, TableBody, TableRow, TableCell, Paper, Button, TextField, MenuItem, Select } from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import AssessmentIcon from '@mui/icons-material/Assessment';
import axios from 'axios';

function Sidebar() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [accounts, setAccounts] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRole, setSelectedRole] = useState('');

  useEffect(() => {
    if (selectedIndex === 0) {
      fetchAccounts();
    }
  }, [selectedIndex]);

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    if (index === 1) {
      window.location.href = '/'; // Chuyển về trang chủ, bạn có thể điều chỉnh đường dẫn tùy ý
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
      console.log(data);  
    } catch (error) {
      console.error('Error fetching accounts:', error);
    }
  };

  const filteredAccounts = accounts.filter((account) => {
    return (
      (!searchTerm || account.full_name.toLowerCase().includes(searchTerm.toLowerCase())) &&
      (!selectedRole || account.role_id === selectedRole)
    );
  });

  return (
    <Grid container sx={{ height: '100vh', backgroundColor: '#000', color: '#fff' }}>
      {/* Sidebar content */}
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
            <ListItem button selected={selectedIndex === 3} onClick={() => handleListItemClick(3)} sx={{ color: '#fff' }} key="logout">
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
        {/* Account list */}
        {selectedIndex === 0 && (
          <React.Fragment>
            <Grid container justifyContent="space-between" sx={{ mb: 2 }}>
              <TextField
                label="Search by name"
                variant="outlined"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                sx={{ backgroundColor: '#fff', marginRight: 2 }}
              />
              <Select
                value={selectedRole}
                onChange={(e) => setSelectedRole(e.target.value)}
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
                onClick={() => {
                  window.location.href = '/add-account'; 
                }}
              >
                Add
              </Button>
            </Grid>
            <TableContainer component={Paper} sx={{ backgroundColor: '#fff', p: 2 }}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Account ID</TableCell>
                    <TableCell>Address</TableCell>
                    <TableCell>Date of Birth</TableCell>
                    <TableCell>Email</TableCell>
                    <TableCell>Full Name</TableCell>
                    <TableCell>Phone</TableCell>
                    <TableCell>Sex</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell>Role ID</TableCell>
                    <TableCell>Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {filteredAccounts.map((account) => (
                    <TableRow key={account.account_id}>
                      <TableCell>{account.accountId || 'NULL'}</TableCell>
                      <TableCell>{account.address || 'NULL'}</TableCell>
                      <TableCell>{account.dob || 'NULL'}</TableCell>
                      <TableCell>{account.email || 'NULL'}</TableCell>
                      <TableCell>{account.fullName || 'NULL'}</TableCell>
                      <TableCell>{account.phone || 'NULL'}</TableCell>
                      <TableCell>{account.sex ? 'Female' : 'Male'}</TableCell>
                      <TableCell>{account.status ? 'Enable' : 'Disable'}</TableCell>
                      <TableCell>
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
                      <TableCell>
                        <Button
                          variant="outlined"
                          color="primary"
                          onClick={() => {
                            window.location.href = `/edit-account/${account.account_id}`;
                          }}
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
      </Grid>
    </Grid>
  );
}

export default Sidebar;
