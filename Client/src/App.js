import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './pages/users/authContext';
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
import OTP from './pages/users/Login/OTP';
import Unauthorized from './pages/unauthorized/unauthorized';
import ProtectedRoute from './protectedRoute';

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/Login" element={<SignIn />} />
          <Route path="/SignUp" element={<SignUp />} />
          <Route path="/about-mercury" element={<About />} />
          <Route path="/unauthorized" element={<Unauthorized />} />

          <Route 
          path="/" 
          element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={HomePage} />} 
          />
          <Route path="/Category" 
          element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={Category} />}
          />
          <Route path="/ResetPassword" 
          element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ResetPassword} />}
          />
          <Route
            path="/SendRequest"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={SendRequest} />}
          />
          <Route
            path="/ViewJewelryList"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewJewelryList} />}
          />
          <Route
            path="/ViewJewelry/:id"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewJewelry} />}
          />
          <Route
            path="/ViewAuction"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewAuction} />}
          />
          <Route
            path="/viewProfile"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewProfile} />}
          />
          <Route
            path="/ViewPost"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewPost} />}
          />
          <Route
            path="/Auctions/:opId"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={Auctions} />}
          />
          <Route
            path="/ViewJewelryList/Category/:Cateid"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewJewelryList} />}
          />
          <Route
            path="/ViewPostDetail/:id"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewPostDetail} />}
          />
          <Route
            path="/ViewResult"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={ViewResult} />}
          />
          <Route
            path="/otp-page"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'MANAGER']} element={OTP} />}
          />
          <Route
            path="/Admin"
            element={<ProtectedRoute notAllowedRoles={['STAFF', 'MANAGER', 'USER']} element={Admin} />}
          />
          <Route
            path="/Staff"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'MANAGER', 'USER']} element={StaffPage} />}
          />
          <Route
            path="/manager"
            element={<ProtectedRoute notAllowedRoles={['ADMIN', 'STAFF', 'USER']} element={ManagerPage} />}
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;
