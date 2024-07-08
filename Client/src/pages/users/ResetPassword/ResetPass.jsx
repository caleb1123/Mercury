import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import axios from 'axios';

const defaultTheme = createTheme();

export default function ConfirmResetPassword() {
  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const otp = data.get('otp');
    const password = data.get('password');
    const confirmPassword = data.get('confirmPassword');

    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8088/auth/reset-password', { otp, password }, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        alert('Password reset successful');
        window.location.href = '/login';
      } else {
        alert('Failed to reset password');
      }
    } catch (error) {
      console.error('Error during password reset:', error);
      alert('Error occurred, please try again');
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <div className='Middle'>
        <style jsx global>{`
          body, html {
            height: 100%;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #f0f0f0;
          }

          .Middle {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
          }
        `}</style>
        <Container component="main" maxWidth="xs" sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <CssBaseline />
          <Paper elevation={6} sx={{ padding: 4, borderRadius: '8px', textAlign: 'center' }}>
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h4">
              Reset Password
            </Typography>
            <Typography component="h2" variant="subtitle1" color="text.secondary" sx={{ mt: 2 }}>
              Please enter the OTP sent to your email and your new password.
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 4 }}>
              <TextField
                required
                fullWidth
                id="otp"
                label="OTP"
                name="otp"
                autoComplete="one-time-code"
                sx={{ mb: 2 }}
              />
              <TextField
                required
                fullWidth
                name="password"
                label="New Password"
                type="password"
                id="password"
                autoComplete="new-password"
                sx={{ mb: 2 }}
              />
              <TextField
                required
                fullWidth
                name="confirmPassword"
                label="Confirm New Password"
                type="password"
                id="confirmPassword"
                autoComplete="new-password"
                sx={{ mb: 2 }}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Reset Password
              </Button>
              <Link href="/login" variant="body2">
                Back to sign in
              </Link>
            </Box>
          </Paper>
        </Container>
      </div>
    </ThemeProvider>
  );
}
