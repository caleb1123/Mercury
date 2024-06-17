import React, { useState, useEffect } from 'react';
import { Grid, Typography, List, ListItem, ListItemIcon, ListItemText, Divider, TableContainer, Table, TableHead, TableBody, TableRow, TableCell, Paper, Box } from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import axios from 'axios';

function Sidebar() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [accounts, setAccounts] = useState([]);

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    if (index === 0) {
      fetchAccounts();
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

      setAccounts(response.data);  
    } catch (error) {
      console.error('Error fetching accounts:', error);
    }
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <Grid container direction="column" justifyContent="space-between" alignItems="center" sx={{ width: '250px', height: '100vh', backgroundColor: '#fff' }}>
        {/* Sidebar content */}
        <Grid item>
          <List>
            <ListItem button selected={selectedIndex === 0} onClick={() => handleListItemClick(0)} sx={{ color: '#000' }}>
              <ListItemIcon sx={{ color: '#000' }}>
                <AccountCircleIcon/>
              </ListItemIcon>
              <ListItemText primary="Account" sx={{ color: '#000' }} />
            </ListItem>
            <ListItem button selected={selectedIndex === 2} onClick={() => handleListItemClick(2)} sx={{ color: '#000' }}>
              <ListItemIcon sx={{ color: '#000' }}>
                <AccountCircleIcon />
              </ListItemIcon>
              <ListItemText primary="Profile" sx={{ color: '#000' }} />
            </ListItem>
          </List>
          <Divider />
          <List>
            <ListItem button selected={selectedIndex === 3} onClick={() => handleListItemClick(3)} sx={{ color: '#000' }}>
              <ListItemIcon sx={{ color: '#000' }}>
                <ExitToAppIcon />
              </ListItemIcon>
              <ListItemText primary="Logout" sx={{ color: '#000' }} />
            </ListItem>
          </List>
        </Grid>

        {/* Footer */}
        <Grid item>
          <Typography variant="body2" color="textSecondary" align="center" sx={{ pb: 2, color: '#000' }}>
            © {new Date().getFullYear()} Mercury
          </Typography>
        </Grid>
      </Grid>

      {/* Account list */}
      <Grid container direction="column" justifyContent="flex-start" alignItems="stretch" sx={{ flexGrow: 1, p: 3 }}>
        {selectedIndex === 0 && (
          <TableContainer component={Paper}>
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
                </TableRow>
              </TableHead>
              <TableBody>
                {accounts.map((account) => (
                  <TableRow key={account.account_id}>
                    <TableCell>{account.account_id}</TableCell>
                    <TableCell>{account.address}</TableCell>
                    <TableCell>{account.dob}</TableCell>
                    <TableCell>{account.email}</TableCell>
                    <TableCell>{account.full_name}</TableCell>
                    <TableCell>{account.phone}</TableCell>
                    <TableCell>{account.sex}</TableCell>
                    <TableCell>{account.status}</TableCell>
                    <TableCell>{account.role_id}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        )}
      </Grid>
    </Box>
  );
}

export default Sidebar;
