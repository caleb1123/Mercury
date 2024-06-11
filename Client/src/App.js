import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/users/HomePage';
import Category from './pages/users/Category';
import SignIn from './pages/users/Login';
import SignUp from './pages/users/SignUp';
import SendRequest from './pages/users/SendRequest';
import ViewJewelryList from './pages/users/ViewJewelryList';



function App() {
  return (
    <Router>
        <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/Category" element={<Category/>} />
        <Route path="/Login" element={<SignIn/>} />
        <Route path="/SignUp" element={<SignUp/>} />
        <Route path="/SendRequest" element={<SendRequest/>} />
        <Route path="/ViewJewelryList" element={<ViewJewelryList/>}/>


        </Routes>

    </Router>
  );
}

export default App;