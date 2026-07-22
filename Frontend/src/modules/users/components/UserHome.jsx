import React, { useState } from 'react';
import { Container, Button, Modal } from 'react-bootstrap';
import { FormattedMessage } from 'react-intl';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import users from '../index'; 
import CreateProject from '../../estructure/components/CreateProject';
import FindAllProjects from '../../estructure/components/FindAllProjects';

const UserHome = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    // Contador que cambia cada vez que se crea un proyecto
    const [reloadKey, setReloadKey] = useState(0);

    const [showCreateProjectModal, setShowCreateProjectModal] = useState(false);
    const handleOpenModal = () => setShowCreateProjectModal(true);
    const handleCloseModal = () => setShowCreateProjectModal(false);

    const handleLogout = () => {
        dispatch(users.actions.logout());
        navigate('/users/login');
    };

    // Al crear un proyecto, incrementamos la key para forzar la recarga en FindAllProjects
    const handleProjectCreated = () => {
        handleCloseModal();
        setReloadKey(prev => prev + 1);
    };

    return (
        <Container className="py-5" style={{ maxWidth: '900px' }}>
            
            {/* Cabecera */}
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
                        onClick={handleOpenModal}
                        className="px-4 py-2 border-0 fw-semibold shadow-sm d-flex align-items-center gap-2 text-white"
                        style={{ 
                            borderRadius: '0.75rem',
                            background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)'
                        }}
                    >
                        <i className="bi bi-plus-lg fs-5"></i>
                        <FormattedMessage id="project.users.UserHome.newProject" defaultMessage="Nuevo Proyecto" />
                    </Button>

                    <Button 
                        variant="outline-danger"
                        onClick={handleLogout}
                        className="px-3 py-2 fw-semibold d-flex align-items-center gap-2"
                        style={{ borderRadius: '0.75rem', borderWidth: '1.5px' }}
                        title="Cerrar Sesión"
                    >
                        <i className="bi bi-box-arrow-right fs-5"></i>
                        <span className="d-none d-sm-inline">
                            <FormattedMessage id="project.app.Header.logout" defaultMessage="Cerrar Sesión" />
                        </span>
                    </Button>
                </div>
            </div>

            {/* Le pasamos reloadKey como prop para refrescar cuando cambie */}
            <FindAllProjects reloadTrigger={reloadKey} />

            {/* Modal para Crear Proyecto */}
            <Modal 
                show={showCreateProjectModal} 
                onHide={handleCloseModal}
                centered
                contentClassName="border-0 shadow-lg"
                style={{ borderRadius: '1.25rem' }}
            >
                <Modal.Header closeButton className="border-0 pt-4 px-4 pb-0">
                    <Modal.Title className="fw-bold text-dark d-flex align-items-center gap-2 fs-5">
                        <i className="bi bi-journal-plus text-primary"></i>
                        <FormattedMessage id="project.projects.CreateProject.title" defaultMessage="Nuevo Proyecto" />
                    </Modal.Title>
                </Modal.Header>

                <Modal.Body className="p-4">
                    <CreateProject onSuccess={handleProjectCreated} />
                </Modal.Body>
            </Modal>

        </Container>
    );
};

export default UserHome;