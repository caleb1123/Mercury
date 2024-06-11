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
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";

const defaultTheme = createTheme();

export default function SendRequest() {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      jewelryName: data.get("jewelryName"),
      category: data.get("category"),
      jewelryDescription: data.get("jewelryDescription"),
    });
  };

  return (
    <ThemeProvider theme={defaultTheme}>
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
              "url(https://i.pinimg.com/564x/41/c0/16/41c01649ba6209717130cab5f07679fb.jpg)",
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
            </Typography>
            <br />
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
              <Select
                defaultValue=""
                id="category"
                name="category"
                fullWidth
                required
                displayEmpty
                sx={{ mt: 2, mb: 2 }}
              >
                <MenuItem value="" disabled>
                  Select Category
                </MenuItem>
                <MenuItem value="Bracelet">Bracelet</MenuItem>
                <MenuItem value="Brooches Pins">Brooches Pins</MenuItem>
                <MenuItem value="Cuffin">Cuffin</MenuItem>
                <MenuItem value="Earrings">Earrings</MenuItem>
                <MenuItem value="Loose Stone">Loose Stone</MenuItem>
                <MenuItem value="Necklace">Necklace</MenuItem>
                <MenuItem value="Ring">Ring</MenuItem>
                <MenuItem value="Watches">Watches</MenuItem>
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
              <Button
                variant="outlined"
                onClick={handleClickOpen}
                sx={{ mt: 2 }}
              >
                Send Request
              </Button>
              <Dialog
                open={open}
                onClose={handleClose}
                PaperProps={{
                  component: "form",
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
                    Please enter your email address here. We will send updates occasionally.
                  </DialogContentText>
                  <TextField
                    autoFocus
                    required
                    margin="dense"
                    id="email"
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
            </Box>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}