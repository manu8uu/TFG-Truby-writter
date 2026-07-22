import React, { useState } from 'react';
import { Container, Card, Button, Row, Col, Modal } from 'react-bootstrap';
import { FormattedMessage } from 'react-intl';
import { useSelector } from 'react-redux';
import BackLink from '../../common/components/BackLink'; 

import * as selectors from '../selectors';
import ChangePassword from './ChangePassword';

const UserProfile = () => {
    const user = useSelector(selectors.getUser);
    const [showChangePasswordModal, setShowChangePasswordModal] = useState(false);

    const handleOpenModal = () => setShowChangePasswordModal(true);
    const handleCloseModal = () => setShowChangePasswordModal(false);

    if (!user) {
        return (
            <Container className="py-5 text-center" style={{ maxWidth: '600px' }}>
                <Card className="border-0 shadow-sm p-4" style={{ borderRadius: '1.25rem' }}>
                    <i className="bi bi-exclamation-circle text-warning display-4 mb-3"></i>
                    <p className="text-muted mb-0 fw-medium">
                        <FormattedMessage 
                            id="project.users.UserProfile.noUser" 
                            defaultMessage="No hay ningún usuario autenticado." 
                        />
                    </p>
                </Card>
            </Container>
        );
    }

    const userNameDisplay = user.username || user.userName;

    return (
        <Container className="py-5" style={{ maxWidth: '700px' }}>
            
            {/* Encabezado */}
            <div className="mb-4">
                <BackLink/>
                <h1 className="fw-bold text-dark mb-1" style={{ fontSize: '2.25rem' }}>
                    <FormattedMessage id="project.users.UserProfile.title" defaultMessage="Mi Perfil" />
                </h1>
                <p className="text-muted mb-0 fs-6">
                    <FormattedMessage 
                        id="project.users.UserProfile.subtitle" 
                        defaultMessage="Gestiona los datos de tu cuenta de escritor" 
                    />
                </p>
            </div>

            {/* Tarjeta Principal del Perfil */}
            <Card 
                className="border-0 shadow-sm p-4 mb-4"
                style={{ 
                    borderRadius: '1.25rem',
                    backgroundColor: '#ffffff',
                    border: '1px solid #e2e8f0'
                }}
            >
                {/* Cabecera con Avatar/Icono */}
                <div className="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                    <div 
                        className="d-flex align-items-center justify-content-center rounded-circle border shadow-sm"
                        style={{ 
                            width: '64px', 
                            height: '64px', 
                            backgroundColor: '#f8fafc',
                            borderColor: '#e2e8f0'
                        }}
                    >
                        <i className="bi bi-person fs-1 text-dark"></i>
                    </div>
                    <div>
                        <h3 className="fw-bold text-dark mb-0 fs-4">
                            {userNameDisplay}
                        </h3>
                        <span className="text-muted small">
                            {user.email || 'Escritor'}
                        </span>
                    </div>
                </div>

                {/* Datos del usuario */}
                <Row className="g-3 mb-4">
                    <Col xs={12} sm={6}>
                        <div className="p-3 rounded-3" style={{ backgroundColor: '#f8fafc' }}>
                            <small className="text-muted fw-semibold d-block mb-1">
                                <FormattedMessage id="project.users.UserProfile.username" defaultMessage="Nombre de usuario" />
                            </small>
                            <span className="text-dark fw-medium">{userNameDisplay}</span>
                        </div>
                    </Col>

                    {user.email && (
                        <Col xs={12} sm={6}>
                            <div className="p-3 rounded-3" style={{ backgroundColor: '#f8fafc' }}>
                                <small className="text-muted fw-semibold d-block mb-1">
                                    <FormattedMessage id="project.users.UserProfile.email" defaultMessage="Email" />
                                </small>
                                <span className="text-dark fw-medium">{user.email}</span>
                            </div>
                        </Col>
                    )}

                    {user.name && (
                        <Col xs={12}>
                            <div className="p-3 rounded-3" style={{ backgroundColor: '#f8fafc' }}>
                                <small className="text-muted fw-semibold d-block mb-1">
                                    <FormattedMessage id="project.users.UserProfile.fullName" defaultMessage="Nombre" />
                                </small>
                                <span className="text-dark fw-medium">{user.name}</span>
                            </div>
                        </Col>
                    )}
                </Row>

                {/* Botón que activa el PopUp Modal */}
                <div className="d-flex justify-content-end">
                    <Button 
                        onClick={handleOpenModal}
                        className="px-4 py-2 border-0 fw-semibold shadow-sm d-flex align-items-center gap-2"
                        style={{ 
                            borderRadius: '0.75rem',
                            background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)'
                        }}
                    >
                        <i className="bi bi-key fs-5"></i>
                        <FormattedMessage id="project.users.UserProfile.changePassword" defaultMessage="Cambiar contraseña" />
                    </Button>
                </div>
            </Card>

            {/* PopUp Modal de Cambiar Contraseña */}
            <Modal 
                show={showChangePasswordModal} 
                onHide={handleCloseModal}
                centered
                contentClassName="border-0 shadow-lg"
                style={{ borderRadius: '1.25rem' }}
            >
                <Modal.Header closeButton className="border-0 pt-4 px-4 pb-0">
                    <Modal.Title className="fw-bold text-dark d-flex align-items-center gap-2 fs-5">
                        <i className="bi bi-shield-lock text-primary"></i>
                        <FormattedMessage id="project.users.UserProfile.changePasswordTitle" defaultMessage="Actualizar Contraseña" />
                    </Modal.Title>
                </Modal.Header>

                <Modal.Body className="p-4">
                    <ChangePassword onSuccess={handleCloseModal} />
                </Modal.Body>
            </Modal>

        </Container>
    );
};

export default UserProfile;