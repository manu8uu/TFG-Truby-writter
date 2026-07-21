import React from 'react';
import { Container, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <Navbar bg="light" className="border-bottom py-3 shadow-sm mb-4">
            <Container className="justify-content-center justify-content-sm-start">
                <Navbar.Brand 
                    as={Link} 
                    to="/" 
                    className="fw-bold text-dark fs-4 m-0 p-0 text-decoration-none"
                >
                    Truby-Writer
                </Navbar.Brand>
            </Container>
        </Navbar>
    );
};

export default Header;