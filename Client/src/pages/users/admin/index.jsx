import * as React from 'react';
import { useState } from 'react';
import { Grid, Typography, List, ListItem, ListItemIcon, ListItemText, Divider } from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';
import SettingsIcon from '@mui/icons-material/Settings';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import { TableContainer, Table, TableHead, TableBody, TableRow, TableCell, Paper } from '@mui/material';


function Sidebar() {
  const [selectedIndex, setSelectedIndex] = useState(0);

  const handleListItemClick = (index) => {
    setSelectedIndex(index);
    // Perform any action when a menu item is clicked
  };

  return (
    <Grid container direction="column" justifyContent="space-between" alignItems="center" sx={{ height: '100vh', backgroundColor: '#fff' }}>
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
  );
}

export default Sidebar;
