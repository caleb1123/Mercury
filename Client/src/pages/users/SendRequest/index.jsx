import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

const SendRequest = () => {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
};
  const defaultTheme = createTheme();

  export default function SendRequest() {
    const handleSubmit = (event) => {
      event.preventDefault();
      const data = new FormData(event.currentTarget);
      console.log({
        jewelryName: data.get("jewelryName"),
        category: data.get("category"),
        jewelryDescription: data.get("jewelryDescription")
      });
    };
  }

  return (
    <Grid container component="main" sx={{ height: "100vh" }}>
      <CssBaseline />
      <Grid
        item
        xs={false}
        sm={4}
        md={7}
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          minHeight: "100vh",
          width: "100vw",
          backgroundImage:
            "url(https://i.pinimg.com/564x/3c/f4/c8/3cf4c80a3cae6d34690362ba759e2af8.jpg)",
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
          padding: 2,
        }}
      />
      <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
        <Box
          sx={{
            my: 8,
            mx: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography component="h1" variant="h2">
            Send Jewelry Valuation Request
          </Typography>{" "}
          <br></br>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="jewelryName"
              label="Jewelry Name"
              name="jewelryName"
              autoComplete="jewelryName"
              autoFocus
            />
            <form onSubmit={handleSubmit}>
              <Select defaultValue={10} id="category" name="Category">
                <Option>Bracelet</Option>
                <Option>Brooches Pins</Option>
                <Option>Cuffin</Option>
                <Option>Earings</Option>
                <Option>Loose Stone</Option>
                <Option>Necklace</Option>
                <Option>Ring</Option>
                <Option>Watches</Option>
              </Select>
              <TextField
                margin="normal"
                required
                fullWidth
                id="jewelryDescription"
                label="Jewelry Description"
                name="jewelryDescription"
                autoComplete="jewelryDescription"
                autoFocus
              />
              <React.Fragment>
                <Button variant="outlined" onClick={handleClickOpen}>
                  Send Request
                </Button>
                <Dialog
                  open={open}
                  onClose={handleClose}
                  PaperProps={{
                    component: 'form',
                    onSubmit: (event) => {
                      event.preventDefault();
                      const formData = new FormData(event.currentTarget);
                      const formJson = Object.fromEntries(formData.entries());
                      const email = formJson.email;
                      console.log(email);
                      handleClose();
                    },
                  }}
                >
                  <DialogTitle>Successfully</DialogTitle>
                  <DialogContent>
                    <DialogContentText>
                      Please enter your email addresss here. We will send updates occasionally.
                    </DialogContentText>
                    <TextField
                      autoFocus
                      required
                      margin="dense"
                      id="name"
                      name="email"
                      label="Email Address"
                      type="email"
                      fullWidth
                      variant="standard"
                    />
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button type="submit">Submit</Button>
                  </DialogActions>
                </Dialog>
              </React.Fragment>
            </form>
            <Copyright sx={{ mt: 5 }} />
          </Box>
        </Box>
      </Grid>
    </Grid>
  );



