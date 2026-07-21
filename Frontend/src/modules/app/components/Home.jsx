import React from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { FormattedMessage } from 'react-intl';
import { Container, Card, Button } from 'react-bootstrap';

import * as usersSelectors from '../../users/selectors';

const Home = () => {
    const isLoggedIn = useSelector(usersSelectors.isLoggedIn);
    const user = useSelector(usersSelectors.getUser);

    return (
        <div 
            className="d-flex align-items-center justify-content-center min-vh-100 py-5" 
            style={{ backgroundColor: '#f8fafc' }}
        >
            <Container style={{ maxWidth: '520px' }}>
                {!isLoggedIn ? (
                    /* LANDING PÚBLICA MINIMALISTA */
                    <Card 
                        className="border-0 text-center p-4 p-sm-5"
                        style={{ 
                            borderRadius: '1.5rem', 
                            backgroundColor: '#ffffff',
                            boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.05), 0 8px 10px -6px rgba(0, 0, 0, 0.01)',
                            border: '1px solid #f1f5f9'
                        }}
                    >
                        <Card.Body className="p-0">
                            
                            {/* 1. Imagen Central */}
                            <div className="mb-4 text-center">
                                <img 
                                    src="/Home.png" 
                                    alt="Truby-Writer" 
                                    className="img-fluid rounded-3"
                                    style={{ 
                                        maxHeight: '260px', 
                                        width: 'auto',
                                        boxShadow: '0 10px 20px rgba(0, 0, 0, 0.08)'
                                    }}
                                />
                            </div>

                            {/* 2. Texto Breve */}
                            <h3 className="fw-bold text-dark mb-2">
                                Truby-Writer
                            </h3>
                            <p className="text-muted fs-6 mb-4 px-2">
                                Diseña y organiza la estructura narrativa de tus historias.
                            </p>

                            {/* 3. Opciones únicas: Iniciar Sesión o Registrarse */}
                            <div className="d-grid gap-2 col-11 mx-auto">
                                <Button 
                                    as={Link} 
                                    to="/users/signup" 
                                    className="py-2.5 border-0 text-white fw-semibold shadow-sm"
                                    style={{ 
                                        background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)',
                                        borderRadius: '0.75rem',
                                        fontSize: '0.95rem'
                                    }}
                                >
                                    <FormattedMessage id="project.users.SignUp.title" defaultMessage="Registrarse" />
                                </Button>

                                <Button 
                                    as={Link} 
                                    to="/users/login" 
                                    variant="light"
                                    className="py-2.5 fw-semibold border text-secondary"
                                    style={{ 
                                        borderRadius: '0.75rem',
                                        fontSize: '0.95rem',
                                        backgroundColor: '#ffffff',
                                        borderColor: '#e2e8f0'
                                    }}
                                >
                                    <FormattedMessage id="project.users.Login.title" defaultMessage="Iniciar Sesión" />
                                </Button>
                            </div>
                        </Card.Body>
                    </Card>
                ) : (
                    /* DOK / PANEL SI YA ESTÁ LOGUEADO */
                    <Card 
                        className="border-0 p-4 p-sm-5 text-center"
                        style={{ 
                            borderRadius: '1.5rem', 
                            backgroundColor: '#ffffff',
                            boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.05)',
                            border: '1px solid #f1f5f9'
                        }}
                    >
                        <Card.Body className="p-0">
                            <h3 className="fw-bold text-dark mb-2">
                                ¡Hola, {user?.userName || 'Escritor'}! 👋
                            </h3>
                            <p className="text-muted mb-4">
                                Has iniciado sesión correctamente.
                            </p>

                            <div className="d-grid gap-2">
                                <Button 
                                    as={Link} 
                                    to="/locations" 
                                    className="py-2.5 border-0 text-white fw-semibold"
                                    style={{ 
                                        background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)',
                                        borderRadius: '0.75rem' 
                                    }}
                                >
                                    Ir a Localizaciones
                                </Button>
                            </div>
                        </Card.Body>
                    </Card>
                )}
            </Container>
        </div>
    );
};

export default Home;