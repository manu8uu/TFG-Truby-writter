import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate, Outlet } from 'react-router-dom';
import * as usersSelectors from '../../users/selectors';

const ProtectedRoute = () => {
    const isUserLoggedIn = useSelector(usersSelectors.isLoggedIn);

    if (!isUserLoggedIn) {
        return <Navigate to="/users/login" replace />;
    }

    return <Outlet />;
};

export default ProtectedRoute;