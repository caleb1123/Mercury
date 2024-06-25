import React from 'react';
import ReactDOM from 'react-dom/client';
import HomePage from './pages/users/HomePage';
import ViewProfile from './pages/users/ViewProfile';
import ViewJewelry from './pages/users/ViewJewelry';
import Category from './pages/users/Category';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import SignIn from './pages/users/Login';
import SignUp from './pages/users/SignUp';
import ResetPassword from './pages/users/ResetPassword';
import ViewJewelryList from './pages/users/ViewJewelryList';
import SendRequest from './pages/users/SendRequest';
import ViewAuction from './pages/users/ViewAuction';
import App from './App';


ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <App />
    </React.StrictMode>,
  )




