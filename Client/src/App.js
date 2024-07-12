import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/users/HomePage';
import Category from './pages/users/Category';
import SignIn from './pages/users/Login';
import SignUp from './pages/users/SignUp';
import SendRequest from './pages/users/SendRequest';
import ViewJewelryList from './pages/users/ViewJewelryList';
import Admin from './pages/admin';
import ViewJewelry from './pages/users/ViewJewelry';
import ViewAuction from './pages/users/ViewAuction';
import StaffPage from './pages/staff/staff';
import ViewProfile from './pages/users/ViewProfile';
import ManagerPage from './pages/manager/manager';
import ViewPost from './pages/users/ViewPost';
import Auctions from './pages/users/Auctions';
import About from './pages/users/About/AboutMercury';
import ResetPassword from './pages/users/ResetPassword';
import ViewPostDetail from './pages/users/ViewPostDetail';
import ViewResult from './pages/users/ViewResult';


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
        <Route path="/Admin" element={<Admin/>}/>
        <Route path="/ViewJewelry/:id" element={<ViewJewelry/>} />
        <Route path="/ViewAuction" element={<ViewAuction/>} />
        <Route path="/Staff" element={<StaffPage/>} />
        <Route path="/viewProfile" element={<ViewProfile/>} />
        <Route path="/manager" element={<ManagerPage/>} />
        <Route path="/ViewPost" element={<ViewPost/>} />
        <Route path="/Auctions/:opId" element={<Auctions/>} />
        <Route path="/ResetPassword" element={<ResetPassword/>} />
        <Route path='/ViewJewelryList/Category/:Cateid' element={<ViewJewelryList/>}/>
        <Route path="/about-mercury" element={<About/>} />
        <Route path="/ViewPostDetail/:id" element={<ViewPostDetail/>} />
        <Route path="/ViewResult" element={<ViewResult/>} />
        </Routes>

    </Router>
  );
}

export default App;