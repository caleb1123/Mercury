import React, { useState, useEffect } from "react";
import { NavLink } from 'react-router-dom';
import { useAuth } from '../../authContext';


const Unauthorized = () => {
  const {user} = useAuth();
  const [page, setPage] = useState('/Login');
  useEffect(() => {
    if (user) {
      switch (user.scope) {
        case 'ADMIN':
          setPage("/Admin");
          break;
        case 'STAFF':
          setPage("/Staff");
          break;
        case 'MANAGER':
          setPage("/manager");
          break;
        case 'USER':
          setPage("/");
          break;
        default:
          setPage("/Login");
      }
    }
  }, [user]);

  return (
    <div>
      <h1>Unauthorized</h1>
      <p>You do not have access to this page.</p>
      <NavLink to={page}>Go to your page</NavLink>
    </div>
  );
};

export default Unauthorized;
