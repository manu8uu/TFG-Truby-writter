import React from 'react';
import { Container, Card, Button } from 'react-bootstrap';
import { FormattedMessage } from 'react-intl';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import users from '../index'; 

const UserHome = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = () => {
        dispatch(users.actions.logout());
        navigate('/users/login');
    };

    return (
        <Container className="py-5" style={{ maxWidth: '900px' }}>
            
            <div className="d-flex justify-content-between align-items-center mb-5">
                <div>
                    <h1 className="fw-bold text-dark mb-1" style={{ fontSize: '2.25rem' }}>
                        <FormattedMessage id="project.projects.UserHome.title" defaultMessage="Mis Proyectos" />
                    </h1>
                    <p className="text-muted mb-0 fs-6">
                        <FormattedMessage 
                            id="project.projects.UserHome.subtitle" 
                            defaultMessage="Gestiona tus historias y novelas" 
                        />
                    </p>
                </div>

                <div className="d-flex align-items-center gap-2">
                    <Button 
                        className="px-4 py-2 border-0 text-white fw-semibold shadow-sm d-flex align-items-center gap-2"
                        style={{ 
                            background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)',
                            borderRadius: '0.75rem'
                        }}
                    >
                        <i className="bi bi-plus-lg fs-5"></i>
                        <FormattedMessage id="project.projects.UserHome.newProject" defaultMessage="Nuevo Proyecto" />
                    </Button>

                    <Button 
                        variant="outline-danger"
                        onClick={handleLogout}
                        className="px-3 py-2 fw-semibold d-flex align-items-center gap-2"
                        style={{ 
                            borderRadius: '0.75rem',
                            borderWidth: '1.5px'
                        }}
                        title="Cerrar Sesión"
                    >
                        <i className="bi bi-box-arrow-right fs-5"></i>
                        <span className="d-none d-sm-inline">
                            <FormattedMessage id="project.app.Header.logout" defaultMessage="Cerrar Sesión" />
                        </span>
                    </Button>
                </div>
            </div>

            <Card 
                className="border-0 shadow-sm p-4 position-relative mb-4"
                style={{ 
                    borderRadius: '1.25rem',
                    backgroundColor: '#ffffff',
                    border: '1px solid #e2e8f0'
                }}
            >
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <div className="d-flex align-items-center gap-2 text-muted small fw-medium">
                        <i className="bi bi-folder2-open fs-5 text-primary"></i>
                        <span>Mis Proyectos</span>
                    </div>
                    <Button 
                        variant="link" 
                        className="text-muted p-0 border-0 shadow-none"
                    >
                        <i className="bi bi-x-lg fs-5"></i>
                    </Button>
                </div>

                <div className="mb-4">
                    <h3 className="fw-bold text-dark mb-2 fs-4">
                        Título
                    </h3>
                    <p className="text-secondary small mb-0">
                        Descripción
                    </p>
                </div>

                <div className="d-flex justify-content-between align-items-center pt-3 border-top text-muted small">
                    <div>
                        <span>Última fecha de modificación</span>
                    </div>
                    <div>
                        <span>Número de tramas</span>
                    </div>
                </div>
            </Card>

        </Container>
    );
};

export default UserHome;