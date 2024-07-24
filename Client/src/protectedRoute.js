import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './authContext';

const ProtectedRoute = ({ element: Component, notAllowedRoles, ...rest }) => {
  const { user } = useAuth();
  const isNotAllowed = user && notAllowedRoles.includes(user.scope);

  if (isNotAllowed) {
    return <Navigate to="/unauthorized" />;
  }

  return <Component {...rest} />;
};

export default ProtectedRoute;
