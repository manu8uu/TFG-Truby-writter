import React from 'react';
import { useSelector } from 'react-redux';
import Container from 'react-bootstrap/Container';
import { Route, Routes } from 'react-router-dom';
import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import users, { Login, SignUp, Logout, ChangePassword, UserHome, UserProfile } from '../../users';
import estructure, { CreateProject, ProjectsHome } from '../../estructure';

const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    
    return (
    <Container className="py-4 flex-grow-1">
        {}
        <AppGlobalComponents />
        <Routes>
            <Route path="/" element={<Home />} />
            {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
            {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
            {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
            {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
            {loggedIn && <Route path="/users/UserHome" element={<UserHome/>}/>}
            {loggedIn && <Route path="/users/profile" element={<UserProfile/>}/>}
            
            {/* PROJECTS */}
            {loggedIn && <Route path="/projects/createProject" element={<CreateProject/>}/>}
            {loggedIn && <Route path="/projects/:projectId" element={<ProjectsHome/>}/>}

        </Routes>
    </Container>
    );
};

export default Body;