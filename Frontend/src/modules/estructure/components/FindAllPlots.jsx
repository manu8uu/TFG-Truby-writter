import React, { useState, useEffect, useCallback } from 'react';
import { Card, Spinner, Button, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

import backend from '../../../backend';
import ModifyPlot from './ModifyPlot';

const STRUCT_FIELDS = [
    { key: 'structWeaknessNeed', label: 'Debilidad / Necesidad' },
    { key: 'structDesire', label: 'Deseo' },
    { key: 'structAdversary', label: 'Adversario' },
    { key: 'structPlan', label: 'Plan' },
    { key: 'structStruggle', label: 'Lucha' },
    { key: 'structSelfRevelation', label: 'Autorrevelación' },
    { key: 'structNewEquilibrium', label: 'Nuevo equilibrio' },
];

const FindAllPlots = ({ projectId, reloadTrigger }) => {
    const navigate = useNavigate();

    const [plots, setPlots] = useState([]);
    const [loading, setLoading] = useState(true);
    const [deletingId, setDeletingId] = useState(null);
    const [editingPlot, setEditingPlot] = useState(null);

    const fetchPlots = useCallback(async () => {
        if (!projectId) {
            setLoading(false);
            return;
        }

        try {
            setLoading(true);
            const response = await backend.estructureService.getAllPlotsByProjectId(projectId);

            let plotList = [];

            if (Array.isArray(response)) {
                plotList = response;
            } else if (response && typeof response === 'object') {
                plotList = response.payload
                    || response.items
                    || response.content
                    || response.plots
                    || response.result
                    || response.data
                    || [];
            }

            setPlots(plotList);
        } catch (error) {
            console.error("Error al obtener las tramas:", error);
            setPlots([]);
        } finally {
            setLoading(false);
        }
    }, [projectId]);

    useEffect(() => {
        fetchPlots();
    }, [fetchPlots, reloadTrigger]);

    const handleDelete = async (e, plotId, plotName) => {
        e.stopPropagation();
        const confirmed = window.confirm(
            `¿Seguro que quieres eliminar "${plotName}"? Esta acción no se puede deshacer.`
        );
        if (!confirmed) return;

        try {
            setDeletingId(plotId);
            await backend.estructureService.deletePlot(plotId);
            setPlots((prev) => prev.filter((p) => p.id !== plotId));
        } catch (error) {
            console.error("Error al eliminar la trama:", error);
            alert("No se pudo eliminar la trama. Inténtalo de nuevo.");
        } finally {
            setDeletingId(null);
        }
    };

    const handlePlotModified = (updatedPlot) => {
        setEditingPlot(null);
        setPlots((prev) => prev.map((p) => (p.id === updatedPlot.id ? updatedPlot : p)));
    };

    const handleOpenPlot = (plot) => {
        navigate(`/projects/${projectId}/plots/${plot.id}`, { state: { plot } });
    };

    if (loading) {
        return (
            <div className="text-center py-5">
                <Spinner animation="border" variant="primary" />
            </div>
        );
    }

    if (!plots || plots.length === 0) {
        return (
            <Card
                className="border-0 shadow-sm p-5 text-center"
                style={{
                    borderRadius: '1.25rem',
                    backgroundColor: '#ffffff',
                    border: '1px solid #e2e8f0'
                }}
            >
                <p className="text-muted mb-0">No tienes tramas creadas aún. ¡Crea la primera!</p>
            </Card>
        );
    }

    return (
        <div>
            {plots.map((plot) => (
                <Card
                    key={plot.id}
                    className="border-0 shadow-sm p-4 position-relative mb-4"
                    style={{
                        borderRadius: '1.25rem',
                        backgroundColor: '#ffffff',
                        border: '1px solid #e2e8f0',
                        cursor: 'pointer'
                    }}
                    onClick={() => handleOpenPlot(plot)}
                >
                    <div className="d-flex justify-content-between align-items-center mb-3">
                        <div className="d-flex align-items-center gap-2 text-muted small fw-medium">
                            <i className="bi bi-diagram-3 fs-5 text-primary"></i>
                        </div>

                        <div className="d-flex align-items-center gap-2">
                            <Button
                                variant="outline-secondary"
                                size="sm"
                                className="d-flex align-items-center gap-1"
                                onClick={(e) => { e.stopPropagation(); setEditingPlot(plot); }}
                            >
                                <i className="bi bi-pencil-square"></i>
                                Editar
                            </Button>

                            <Button
                                variant="outline-danger"
                                size="sm"
                                className="d-flex align-items-center gap-1"
                                disabled={deletingId === plot.id}
                                onClick={(e) => handleDelete(e, plot.id, plot.name)}
                            >
                                {deletingId === plot.id ? (
                                    <Spinner animation="border" size="sm" />
                                ) : (
                                    <>
                                        <i className="bi bi-trash"></i>
                                        Eliminar
                                    </>
                                )}
                            </Button>
                        </div>
                    </div>

                    <div className="mb-3">
                        <h3 className="fw-bold text-dark mb-2 fs-4">
                            {plot.name}
                        </h3>
                        <p className="text-secondary small mb-0">
                            {plot.dramaticSituation || 'Sin situación dramática definida'}
                        </p>
                    </div>

                    {STRUCT_FIELDS.some(field => plot[field.key]) && (
                        <div className="pt-3 border-top">
                            <div className="row g-3">
                                {STRUCT_FIELDS.filter(field => plot[field.key]).map((field) => (
                                    <div className="col-12 col-md-6" key={field.key}>
                                        <p
                                            className="text-uppercase text-muted mb-1"
                                            style={{ fontSize: '0.7rem', letterSpacing: '0.03em' }}
                                        >
                                            {field.label}
                                        </p>
                                        <p className="text-secondary small mb-0">
                                            {plot[field.key]}
                                        </p>
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}
                </Card>
            ))}

            {/* Modal Modificar Trama */}
            <Modal
                show={!!editingPlot}
                onHide={() => setEditingPlot(null)}
                centered
                contentClassName="border-0 shadow-lg"
                style={{ borderRadius: '1.25rem' }}
            >
                <Modal.Header closeButton className="border-0 pt-4 px-4 pb-0">
                    <Modal.Title className="fw-bold text-dark d-flex align-items-center gap-2 fs-5">
                        <i className="bi bi-pencil-square text-primary"></i>
                        Modificar trama
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body className="p-4">
                    {editingPlot && <ModifyPlot plot={editingPlot} onSuccess={handlePlotModified} />}
                </Modal.Body>
            </Modal>
        </div>
    );
};

export default FindAllPlots;