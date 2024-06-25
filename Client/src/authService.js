// src/services/authService.js
import api from './Api';

const login = async (userName, password) => {
  try {
    const response = await api.post('/auth/login', { userName, password });
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
    }
    return response.data;
  } catch (error) {
    throw new Error('Login failed');
  }
};

const logout = () => {
  localStorage.removeItem('token');
};

export default {
  login,
  logout,
};
