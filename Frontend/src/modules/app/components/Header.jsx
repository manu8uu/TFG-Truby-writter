import React from 'react';
import { FormattedMessage } from 'react-intl';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';

const Header = () => {
    const userName = null; 

    return (
        <Navbar bg="light" expand="lg" className="border-bottom">
            <Container>
                {/* El Navbar.Brand actúa como enlace a la raíz */}
                <Navbar.Brand as={Link} to="/">Tryby-Writer</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        {/* Enlaces públicos del catálogo/estructura */}
                        <Nav.Link as={Link} to="/locations">
                            <FormattedMessage id="project.app.Header.locations"/>
                        </Nav.Link>
                        <Nav.Link as={Link} to="/objects">
                            <FormattedMessage id="project.app.Header.objects"/>
                        </Nav.Link>
                    </Nav>
                    
                    <Nav className="ms-auto">
                        {userName ? (
                            <Nav.Link as={Link} to="/users/update-profile">
                                {userName}
                            </Nav.Link>
                        ) : (
                            <Nav.Link as={Link} to="/users/login">
                                <FormattedMessage id="project.app.Header.login"/>
                            </Nav.Link>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;