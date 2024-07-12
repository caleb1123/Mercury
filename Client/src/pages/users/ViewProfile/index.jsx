import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ViewProfile.css';
import Header from "../Header";

function ViewProfile() {
  const [userData, setUserData] = useState(null);
  const [editData, setEditData] = useState({
    fullName: '',
    address: '',
    dob: '',
    password: '',
    sex: ''
  });
  const [isEditing, setIsEditing] = useState(false);
  const [error, setError] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
    
    const fetchUserData = async () => {
      try {
        const response = await fetch('http://localhost:8088/account/myinfor', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        if (response.ok) {
          const data = await response.json();
          setUserData(data);
          setEditData({
            fullName: data.fullName || '',
            address: data.address || '',
            dob: data.dob || '',
            password: data.password || '',
            sex: data.sex !== null ? (data.sex ? 'Male' : 'Female') : ''
          });
          console.log('Fetched user data:', data); // Log user data
        } else {
          console.error('Failed to fetch user data:', response.statusText);
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchUserData();
  }, []);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEditData({
      ...editData,
      [name]: value
    });
  };

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    setError(null); // Reset error state
    try {
      console.log('Updating user:', userData.userName); // Log userName before update
      const response = await fetch(`http://localhost:8088/account/update/${userData.userName}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({
          fullName: editData.fullName || null,
          address: editData.address || null,
          dob: editData.dob || null,
          password: editData.password || null,
          sex: editData.sex === 'Male' ? true : (editData.sex === 'Female' ? false : null)
        })
      });

      const text = await response.text(); // Read the response body as text
      console.log('Response text:', text); // Log response text

      if (response.ok) {
        if (text) {
          const updatedData = JSON.parse(text); // Parse the JSON if it exists
          setUserData(updatedData);
        }
        alert('Profile updated successfully');
        setIsEditing(false);
        window.location.reload(); // Reload the page after successful update

      } else {
        const errorText = await response.text();
        setError(`Failed to update profile: ${errorText}`);
      }
    } catch (error) {
      console.error('Error updating user data:', error);
      setError('Error updating user data');
    }
  };

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  const handleProfileClick = () => {
    navigate('/viewProfile');
  };

  if (!userData) {
    return <div>Loading...</div>;
  }

  return (
    
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />

    
    <div className="containerProfile">
      <div className="profile">
        <h2>User Profile</h2>
        <div className="profile-details">
          <p><strong>Account ID:</strong> {userData.accountId}</p>
          <p><strong>Full Name:</strong> {userData.fullName}</p>
          <p><strong>User Name:</strong> {userData.userName}</p>
          <p><strong>Address:</strong> {userData.address}</p>
          <p><strong>Date of Birth:</strong> {userData.dob}</p>
          <p><strong>Email:</strong> {userData.email}</p>
          <p><strong>Sex:</strong> {userData.sex ? 'Male' : 'Female'}</p>
          <p><strong>Phone:</strong> {userData.phone}</p>
          <p><strong>Status:</strong> {userData.status ? 'Active' : 'Inactive'}</p>
          <p><strong>Role ID:</strong> {userData.roleId}</p>
        </div>
        {!isEditing && <button onClick={handleEditClick} className="edit-button">Edit Profile</button>}
        {isEditing && (
          <form onSubmit={handleFormSubmit} className="edit-profile-form">
            <h3>Edit Profile</h3>
            {error && <div className="error-message">{error}</div>}
            <div className="form-field">
              <label>Full Name:</label>
              <input type="text" name="fullName" value={editData.fullName} onChange={handleInputChange} />
            </div>
            <div className="form-field">
              <label>Address:</label>
              <input type="text" name="address" value={editData.address} onChange={handleInputChange} />
            </div>
            <div className="form-field">
              <label>Date of Birth:</label>
              <input type="date" name="dob" value={editData.dob} onChange={handleInputChange} />
            </div>
            <div className="form-field">
              <label>Password:</label>
              <input type="password" name="password" value={editData.passwword} onChange={handleInputChange} />
            </div>
            <div className="form-field">
              <label>Sex:</label>
              <select name="sex" value={editData.sex} onChange={handleInputChange}>
                <option value="">Select</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </select>
            </div>
            <button type="submit" className="save-button">Save Changes</button>
          </form>
        )}
        <button onClick={handleLogout} className="logout-button">Logout</button>
      </div>
    </div>

    </>
  );
}

export default ViewProfile;
