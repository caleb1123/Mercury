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



const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<HomePage/>);



