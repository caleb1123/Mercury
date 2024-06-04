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

const Header = ({ inputValue, handleChange }) => (
  <div className="Header">
    <div className="UpHeader">
      <div className="Mercury">MERCURY</div>
      <div className="Login_CreaAccount">
        CREATE ACCOUNT
        <div className="LoginStyle">LOGIN</div>
      </div>
    </div>
    <div className="Line">
      <img src="line_image_src" alt="line" />
    </div>
    <div className="Down_Header">
      <div className="Bar">
        CATEGORY:
        <div className="world_bar_style">BRACELET</div>
      </div>
      <input
        className="Search"
        type="text"
        value={inputValue}
        onChange={handleChange}
        placeholder="Search"
      />
    </div>
  </div>
);

export default function SendRequest() {
  const [open, setOpen] = React.useState(false);
  const [inputValue, setInputValue] = React.useState("");

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

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Header inputValue={inputValue} handleChange={handleChange} />
      <Box
        sx={{
          height: "100vh",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          backgroundImage:
            "url(https://i.pinimg.com/564x/3c/f4/c8/3cf4c80a3cae6d34690362ba759e2af8.jpg)",
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
          paddingTop: "56px", // Adjust this value based on the height of your header
        }}
      >
        <Grid
          item
          xs={12}
          sm={8}
          md={5}
          component={Paper}
          elevation={6}
          square
          sx={{
            padding: 4,
            width: "100%",
            maxWidth: 500,
            backgroundColor: 'rgba(255, 255, 255, 0.8)', // slightly transparent background for better readability
            borderRadius: 2,
          }}
        >
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Typography component="h1" variant="h5">
              Send Jewelry Valuation Request
            </Typography>
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
      </Box>
    </ThemeProvider>
  );
}
