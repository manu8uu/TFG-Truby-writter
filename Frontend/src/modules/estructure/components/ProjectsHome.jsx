import React, { useState } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { Container, Card, Button, Modal } from 'react-bootstrap';

import CreatePlot from './CreatePlot';
import FindAllPlots from './FindAllPlots';
import CreateCharacter from '../../objects/components/CreateCharacter';
import FindAllCharacters from '../../objects/components/FindAllCharacters';

const NAV_ITEMS = [
    { key: 'tramas', label: 'TRAMAS' },
    { key: 'personajes', label: 'PERSONAJES GENERALES' },
];

const ProjectsHome = () => {
    const { projectId } = useParams();
    const location = useLocation();
    const navigate = useNavigate();
    const project = location.state?.project;

    const [activeSection, setActiveSection] = useState('tramas');
    
    // Estados Modales y Recargas
    const [showCreatePlotModal, setShowCreatePlotModal] = useState(false);
    const [showCreateCharacterModal, setShowCreateCharacterModal] = useState(false);
    
    const [reloadPlotsTrigger, setReloadPlotsTrigger] = useState(0);
    const [reloadCharactersTrigger, setReloadCharactersTrigger] = useState(0);

    const handleLogout = () => {
        navigate('/users/logout');
    };

    const handlePlotCreated = () => {
        setShowCreatePlotModal(false);
        setReloadPlotsTrigger(prev => prev + 1);
    };

    const handleCharacterCreated = () => {
        setShowCreateCharacterModal(false);
        setReloadCharactersTrigger(prev => prev + 1);
    };

    return (
        <div className="min-vh-100" style={{ backgroundColor: '#f8fafc' }}>
            <Container className="py-5" style={{ maxWidth: '1100px' }}>

                {/* Cabecera */}
                <div className="d-flex justify-content-between align-items-center mb-4">
                    <h1
                        className="fw-bold text-dark mb-0"
                        style={{ fontSize: '2rem', cursor: 'pointer' }}
                        onClick={() => navigate('/users/UserHome')}
                    >
                        {project?.name || 'Título'}
                    </h1>

                    <div className="d-flex align-items-center gap-3">
                        <i className="bi bi-person-circle text-secondary" style={{ fontSize: '1.5rem' }}></i>
                        <i
                            className="bi bi-box-arrow-right text-secondary"
                            role="button"
                            onClick={handleLogout}
                            style={{ fontSize: '1.5rem', cursor: 'pointer' }}
                            title="Cerrar Sesión"
                        ></i>
                    </div>
                </div>

                <div className="d-flex gap-4">
                    {/* Sidebar */}
                    <Card
                        className="border-0 p-2"
                        style={{
                            borderRadius: '1.25rem',
                            backgroundColor: '#ffffff',
                            boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.05), 0 8px 10px -6px rgba(0, 0, 0, 0.01)',
                            border: '1px solid #f1f5f9',
                            minWidth: '260px',
                            height: 'fit-content'
                        }}
                    >
                        {NAV_ITEMS.map((item) => (
                            <div
                                key={item.key}
                                onClick={() => setActiveSection(item.key)}
                                className="px-3 py-2 small fw-semibold mb-1"
                                style={{
                                    cursor: 'pointer',
                                    borderRadius: '0.75rem',
                                    color: activeSection === item.key ? '#6d28d9' : '#64748b',
                                    backgroundColor: activeSection === item.key ? '#f5f3ff' : 'transparent',
                                    transition: 'all 0.15s ease'
                                }}
                            >
                                {item.label}
                            </div>
                        ))}
                    </Card>

                    {/* Contenido principal */}
                    <div className="flex-grow-1">
                        
                        {/* SECCIÓN TRAMAS */}
                        {activeSection === 'tramas' && (
                            <>
                                <div className="d-flex justify-content-between align-items-center mb-4">
                                    <div>
                                        <h2 className="fw-bold text-dark mb-1" style={{ fontSize: '1.75rem' }}>
                                            Mis Tramas
                                        </h2>
                                        <p className="text-muted mb-0 fs-6">
                                            Gestiona tus tramas narrativas
                                        </p>
                                    </div>

                                    <Button
                                        onClick={() => setShowCreatePlotModal(true)}
                                        className="px-4 py-2 border-0 fw-semibold shadow-sm d-flex align-items-center gap-2 text-white"
                                        style={{
                                            borderRadius: '0.75rem',
                                            background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)'
                                        }}
                                    >
                                        <i className="bi bi-plus-lg fs-5"></i>
                                        Nueva trama
                                    </Button>
                                </div>

                                <FindAllPlots projectId={projectId} reloadTrigger={reloadPlotsTrigger} />
                            </>
                        )}

                        {/* SECCIÓN PERSONAJES GENERALES */}
                        {activeSection === 'personajes' && (
                            <>
                                <div className="d-flex justify-content-between align-items-center mb-4">
                                    <div>
                                        <h2 className="fw-bold text-dark mb-1" style={{ fontSize: '1.75rem' }}>
                                            Personajes Generales
                                        </h2>
                                        <p className="text-muted mb-0 fs-6">
                                            Gestiona los personajes del proyecto
                                        </p>
                                    </div>

                                    <Button
                                        onClick={() => setShowCreateCharacterModal(true)}
                                        className="px-4 py-2 border-0 fw-semibold shadow-sm d-flex align-items-center gap-2 text-white"
                                        style={{
                                            borderRadius: '0.75rem',
                                            background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)'
                                        }}
                                    >
                                        <i className="bi bi-person-plus-fill fs-5"></i>
                                        Nuevo personaje
                                    </Button>
                                </div>

                                <FindAllCharacters projectId={projectId} reloadTrigger={reloadCharactersTrigger} />
                            </>
                        )}

                    </div>
                </div>

                {/* Modal Crear Trama */}
                <Modal
                    show={showCreatePlotModal}
                    onHide={() => setShowCreatePlotModal(false)}
                    centered
                    contentClassName="border-0 shadow-lg"
                    style={{ borderRadius: '1.25rem' }}
                >
                    <Modal.Header closeButton className="border-0 pt-4 px-4 pb-0">
                        <Modal.Title className="fw-bold text-dark d-flex align-items-center gap-2 fs-5">
                            <i className="bi bi-journal-plus text-primary"></i>
                            Nueva trama
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body className="p-4">
                        <CreatePlot projectId={projectId} onSuccess={handlePlotCreated} />
                    </Modal.Body>
                </Modal>

                {/* Modal Crear Personaje */}
                <Modal
                    show={showCreateCharacterModal}
                    onHide={() => setShowCreateCharacterModal(false)}
                    centered
                    contentClassName="border-0 shadow-lg"
                    style={{ borderRadius: '1.25rem' }}
                >
                    <Modal.Header closeButton className="border-0 pt-4 px-4 pb-0">
                        <Modal.Title className="fw-bold text-dark d-flex align-items-center gap-2 fs-5">
                            <i className="bi bi-person-plus text-primary"></i>
                            Nuevo personaje
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body className="p-4">
                        <CreateCharacter projectId={projectId} onSuccess={handleCharacterCreated} />
                    </Modal.Body>
                </Modal>

            </Container>
        </div>
    );
};

export default ProjectsHome;