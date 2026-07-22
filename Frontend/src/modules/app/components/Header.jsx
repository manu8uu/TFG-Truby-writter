import React from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, Link } from 'react-router-dom';
import { Container, Navbar, Button } from 'react-bootstrap';

import users from '../../users';

const Header = () => {
    const navigate = useNavigate();

    const isLoggedIn = useSelector(users.selectors.isLoggedIn); 

    return (
        <Navbar bg="light" className="border-bottom py-3 shadow-sm mb-4">
            <Container className="d-flex justify-content-between align-items-center">
                <Navbar.Brand 
                    as={Link} 
                    to="/" 
                    className="fw-bold text-dark fs-4 m-0 p-0 text-decoration-none"
                >
                    Truby-Writer
                </Navbar.Brand>

                {isLoggedIn && (
                    <Button
                        variant="light"
                        onClick={() => navigate('/users/profile')}
                        className="p-0 border-0 bg-transparent d-flex align-items-center justify-content-center"
                        style={{ width: '40px', height: '40px' }}
                        title="Mi Perfil"
                    >
                        <div 
                            className="d-flex align-items-center justify-content-center rounded-circle border shadow-sm overflow-hidden"
                            style={{ 
                                width: '100%', 
                                height: '100%', 
                                borderColor: '#e2e8f0',
                                backgroundColor: '#ffffff'
                            }}
                        >
                            <img 
                                src="/UsuarioIcono.png"
                                alt="Perfil de usuario" 
                                style={{ 
                                    width: '100%', 
                                    height: '100%', 
                                    objectFit: 'cover' 
                                }} 
                            />
                        </div>
                    </Button>
                )}
            </Container>
        </Navbar>
    );
};

export default Header;