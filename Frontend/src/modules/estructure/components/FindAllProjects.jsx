import React, { useState, useEffect, useCallback } from 'react';
import { Card, Spinner, Button } from 'react-bootstrap';
import { useSelector } from 'react-redux';

import users from '../../users';
import backend from '../../../backend';

const FindAllProjects = ({ reloadTrigger }) => {
    const user = useSelector(users.selectors.getUser);
    const userId = user?.id || user?.userId;

    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [deletingId, setDeletingId] = useState(null);

    const fetchProjects = useCallback(async () => {
        if (!userId) {
            setLoading(false);
            return;
        }

        try {
            setLoading(true);
            const response = await backend.estructureService.getAllProjectsByUserId(userId);

            let projectList = [];

            if (Array.isArray(response)) {
                projectList = response;
            } else if (response && typeof response === 'object') {
                projectList = response.payload
                    || response.items 
                    || response.content 
                    || response.projects 
                    || response.result 
                    || response.data 
                    || [];
            }

            setProjects(projectList);
        } catch (error) {
            console.error("Error al obtener los proyectos:", error);
            setProjects([]);
        } finally {
            setLoading(false);
        }
    }, [userId]);

    useEffect(() => {
        fetchProjects();
    }, [fetchProjects, reloadTrigger]);

    const handleDelete = async (projectId, projectName) => {
        const confirmed = window.confirm(
            `¿Seguro que quieres eliminar "${projectName}"? Esta acción no se puede deshacer.`
        );
        if (!confirmed) return;

        try {
            setDeletingId(projectId);
            await backend.estructureService.deleteProject(projectId);
            // Actualización optimista: quitamos el proyecto sin volver a pedir toda la lista
            setProjects((prev) => prev.filter((p) => p.id !== projectId));
        } catch (error) {
            console.error("Error al eliminar el proyecto:", error);
            alert("No se pudo eliminar el proyecto. Inténtalo de nuevo.");
        } finally {
            setDeletingId(null);
        }
    };

    if (loading) {
        return (
            <div className="text-center py-5">
                <Spinner animation="border" variant="primary" />
            </div>
        );
    }

    if (!projects || projects.length === 0) {
        return (
            <Card className="border-0 shadow-sm p-5 text-center" style={{ borderRadius: '1.25rem' }}>
                <p className="text-muted mb-0">No tienes proyectos creados aún. ¡Crea el primero!</p>
            </Card>
        );
    }

    return (
        <div>
            {projects.map((project) => (
                <Card 
                    key={project.id}
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
                        </div>

                        <Button
                            variant="outline-danger"
                            size="sm"
                            className="d-flex align-items-center gap-1"
                            disabled={deletingId === project.id}
                            onClick={() => handleDelete(project.id, project.name)}
                        >
                            {deletingId === project.id ? (
                                <Spinner animation="border" size="sm" />
                            ) : (
                                <>
                                    <i className="bi bi-trash"></i>
                                    Eliminar
                                </>
                            )}
                        </Button>
                    </div>

                    <div className="mb-4">
                        <h3 className="fw-bold text-dark mb-2 fs-4">
                            {project.name}
                        </h3>
                        <p className="text-secondary small mb-0">
                            {project.description || 'Sin descripción'}
                        </p>
                    </div>

                    <div className="d-flex justify-content-between align-items-center pt-3 border-top text-muted small">
                        <div>
                            <span>
                                Última modificación: {project.modifiedAt ? new Date(project.modifiedAt).toLocaleDateString() : 'N/A'}
                            </span>
                        </div>
                    </div>
                </Card>
            ))}
        </div>
    );
};

export default FindAllProjects;