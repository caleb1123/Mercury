import * as React from 'react';
import { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode'; // Correct import

const defaultTheme = createTheme();

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit">
        Mercury
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

export default function SignIn() {
  const [message, setMessage] = useState('');
  const [otpError, setOtpError] = useState(false);
  const [userName, setUserName] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const userName = data.get('userName');
    const password = data.get('password');
    setUserName(userName);

    try {
      const response = await axios.post('http://localhost:8088/auth/login', {
        userName,
        password,
      });

      if (response.status === 200) {
        const token = response.data.token;
        localStorage.setItem('token', token);
        const decodedToken = jwtDecode(token); // Decode the token

        if (decodedToken.scope === 'ADMIN') {
          window.location.href = '/Admin';
        } else if (decodedToken.scope === 'USER') {
          window.location.href = '/';
        } else if (decodedToken.scope === 'STAFF') {
          window.location.href = '/Staff';
        } else if (decodedToken.scope === 'MANAGER') {
          window.location.href = '/Manager';
        }

        setMessage('Login successfully');
      } else {
        setMessage('Error');
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        // OTP required error handling
        setOtpError(true);
      } else {
        setMessage('Login failed, please try again');
      }
      console.error('Login failed', error);
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Grid container component="main" sx={{ height: '100vh' }}>
        <CssBaseline />
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            minHeight: '100vh',
            width: '100vw',
            backgroundImage: 'url(https://i.pinimg.com/564x/3c/f4/c8/3cf4c80a3cae6d34690362ba759e2af8.jpg)',
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            padding: 2,
          }}
        />
        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
          <Box
            sx={{
              my: 8,
              mx: 4,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}
          >
            <Typography component="h1" variant="h2">
              Welcome to Mercury
            </Typography>
            <br />
            <Typography component="h2" variant="h5">
              Sign in
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="UserName"
                label="UserName"
                name="userName"
                autoComplete="UserName"
                autoFocus
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Sign In
              </Button>
              <Grid container>
                <Grid item xs>
                  <Link href="/ResetPassword" variant="body2">
                    Forgot password?
                  </Link>
                </Grid>
                <Grid item>
                  <Link href="/SignUp" variant="body2">
                    {"Don't have an account? Sign Up"}
                  </Link>
                </Grid>
              </Grid>
              {otpError && (
                <Typography variant="body2" color="error" align="center">
                  OTP is required. Please check your email for the OTP.{' '}
                  <Link href={`/otp-page?userName=${userName}`} variant="body2">
                    Go to OTP page
                  </Link>
                </Typography>
              )}
              <Typography variant="body2" color="error" align="center">
                {message}
              </Typography>
              <Copyright sx={{ mt: 5 }} />
            </Box>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}
