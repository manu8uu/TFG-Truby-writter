import React, { useState, useRef } from 'react';
import { FormattedMessage } from 'react-intl';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { Errors } from '../../common';
import backend from '../../../backend';

const STRUCT_FIELDS = [
    { key: 'structWeaknessNeed', label: 'Debilidad / Necesidad' },
    { key: 'structDesire', label: 'Deseo' },
    { key: 'structAdversary', label: 'Adversario' },
    { key: 'structPlan', label: 'Plan' },
    { key: 'structStruggle', label: 'Lucha' },
    { key: 'structSelfRevelation', label: 'Autorrevelación' },
    { key: 'structNewEquilibrium', label: 'Nuevo equilibrio' },
];

const ModifyPlot = ({ plot, onSuccess }) => {
    const formRef = useRef(null);

    const [name, setName] = useState(plot?.name || '');
    const [dramaticSituation, setDramaticSituation] = useState(plot?.dramaticSituation || '');
    const [structFields, setStructFields] = useState(() =>
        STRUCT_FIELDS.reduce((acc, field) => {
            acc[field.key] = plot?.[field.key] || '';
            return acc;
        }, {})
    );
    const [formValidated, setFormValidated] = useState(false);
    const [backendErrors, setBackendErrors] = useState(null);

    const handleStructFieldChange = (key, value) => {
        setStructFields((prev) => ({ ...prev, [key]: value }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const form = formRef.current;

        if (!plot?.id) {
            setBackendErrors({ globalError: "No se encontró la trama a modificar." });
            return;
        }

        if (form && form.checkValidity()) {
            try {
                setBackendErrors(null);

                const dto = {
                    name: name.trim(),
                    dramaticSituation: dramaticSituation.trim(),
                    ...Object.fromEntries(
                        STRUCT_FIELDS.map(field => [field.key, structFields[field.key].trim()])
                    )
                };

                const response = await backend.estructureService.modifyPlot(plot.id, dto);

                // La respuesta puede venir envuelta (payload/items/content/...) según el endpoint;
                // desenvolvemos para asegurarnos de pasar el objeto plot real a onSuccess.
                const updatedPlot = (response && typeof response === 'object')
                    ? (response.payload || response.items || response.content || response.result || response.data || response)
                    : response;

                if (onSuccess) {
                    onSuccess(updatedPlot);
                }

            } catch (error) {
                setBackendErrors(error);
            }
        } else {
            setBackendErrors(null);
            setFormValidated(true);
        }
    };

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)} />

            <Form
                ref={formRef}
                noValidate
                validated={formValidated}
                onSubmit={handleSubmit}
            >
                <Form.Group className="mb-3" controlId="modifyPlotTitle">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage
                            id="project.estructure.ModifyPlot.fields.title"
                            defaultMessage="Título de la trama"
                        />
                    </Form.Label>
                    <Form.Control
                        type="text"
                        value={name}
                        onChange={e => setName(e.target.value)}
                        autoFocus
                        required
                        className="py-2"
                        style={{ borderRadius: '0.5rem' }}
                    />
                    <Form.Control.Feedback type="invalid">
                        <FormattedMessage
                            id="project.global.validator.required"
                            defaultMessage="Este campo es obligatorio"
                        />
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-3" controlId="modifyPlotDramaticSituation">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage
                            id="project.estructure.ModifyPlot.fields.dramaticSituation"
                            defaultMessage="Situación dramática"
                        />
                    </Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={2}
                        value={dramaticSituation}
                        onChange={e => setDramaticSituation(e.target.value)}
                        className="py-2"
                        style={{ borderRadius: '0.5rem', resize: 'none' }}
                    />
                </Form.Group>

                <hr className="my-4" />

                <p className="small fw-semibold text-secondary mb-3">
                    Estructura narrativa
                </p>

                {STRUCT_FIELDS.map((field) => (
                    <Form.Group className="mb-3" controlId={`modifyPlot-${field.key}`} key={field.key}>
                        <Form.Label className="small fw-semibold text-secondary">
                            {field.label}
                        </Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={2}
                            value={structFields[field.key]}
                            onChange={e => handleStructFieldChange(field.key, e.target.value)}
                            className="py-2"
                            style={{ borderRadius: '0.5rem', resize: 'none' }}
                        />
                    </Form.Group>
                ))}

                <div className="d-flex justify-content-end pt-2">
                    <Button
                        type="submit"
                        className="px-4 py-2 border-0 fw-semibold text-white shadow-sm d-flex align-items-center gap-2"
                        style={{
                            background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)',
                            borderRadius: '0.5rem'
                        }}
                    >
                        <i className="bi bi-check-lg fs-5"></i>
                        <FormattedMessage
                            id="project.global.buttons.save"
                            defaultMessage="Guardar cambios"
                        />
                    </Button>
                </div>
            </Form>
        </div>
    );
};

export default ModifyPlot;