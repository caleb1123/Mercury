import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import axios from 'axios';

const defaultTheme = createTheme();

export default function OtpPage() {
  const [otp, setOtp] = useState('');
  const [message, setMessage] = useState('');
  const [email, setEmail] = useState('');
  const userName = new URLSearchParams(window.location.search).get('userName'); // Get userName from URL
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEmail = async () => {
      try {
        const emailResponse = await axios.get(`http://localhost:8088/account/${userName}`, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
        if (emailResponse.status === 200) {
          setEmail(emailResponse.data.email);
        } else {
          setMessage("Cannot get email from that userName");
        }
      } catch (error) {
        console.error("Error fetching email:", error);
        setMessage("Error fetching email. Please try again.");
      }
    };
    fetchEmail();
  }, [userName]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('http://localhost:8088/auth/signupOTP', {
        email,
        otp,
      }, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        alert('Account activated successfully');
        navigate('/login');
      } else {
        setMessage('Invalid OTP. Please try again.');
      }
    } catch (error) {
      if (error.response && error.response.data) {
        setMessage(error.response.data);
      } else {
        setMessage('Error during sign up. Please try again.');
      }
      console.error('Error during sign up:', error);
    }
  };

  const handleResendOtp = async () => {
    try {
      const response = await axios.post('http://localhost:8088/auth/sendOTPtoActive', { email }, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        alert('OTP has been resent to your email address');
      } else {
        setMessage('Failed to resend OTP. Please try again.');
      }
    } catch (error) {
      console.error('Error during resending OTP:', error);
      setMessage('Error occurred. Please try again.');
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs" sx={{ height: '100vh', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <CssBaseline />
        <Paper elevation={6} sx={{ padding: 4, borderRadius: '8px', textAlign: 'center' }}>
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h4">
            OTP Verification
          </Typography>
          <Typography component="h2" variant="subtitle1" color="text.secondary" sx={{ mt: 2 }}>
            Please enter the OTP sent to your email.
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 4 }}>
            <TextField
              required
              fullWidth
              id="otp"
              label="OTP"
              name="otp"
              autoComplete="one-time-code"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              sx={{ mb: 2 }}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Submit
            </Button>
            <Button
              onClick={handleResendOtp}
              fullWidth
              variant="outlined"
              sx={{ mt: 1, mb: 2 }}
            >
              Resend OTP
            </Button>
            {message && (
              <Typography color="error" variant="body2">
                {message}
              </Typography>
            )}
            <Link href="/login" variant="body2">
              Back to sign in
            </Link>
          </Box>
        </Paper>
      </Container>
    </ThemeProvider>
  );
}
