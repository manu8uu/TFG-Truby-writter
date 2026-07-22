import React, { useState, useRef } from 'react';
import { useSelector } from 'react-redux';
import { FormattedMessage } from 'react-intl';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { Errors } from '../../common';
import users from '../../users';
import backend from '../../../backend';

const CreateProject = ({ onSuccess }) => {
    const user = useSelector(users.selectors.getUser); 
    const formRef = useRef(null);

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [formValidated, setFormValidated] = useState(false);
    const [backendErrors, setBackendErrors] = useState(null);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const form = formRef.current;
        const currentUserId = user?.id || user?.userId;

        if (!currentUserId) {
            setBackendErrors({ 
                globalError: "No se encontró un usuario autenticado para crear el proyecto." 
            });
            return;
        }

        if (form && form.checkValidity()) {
            try {
                setBackendErrors(null);
                
                const result = await backend.estructureService.createProject({
                    userId: currentUserId,
                    name: title.trim(), 
                    description: description.trim()
                });

                if (onSuccess) {
                    onSuccess(); 
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
                {/* Título del Proyecto */}
                <Form.Group className="mb-3" controlId="projectTitle">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage 
                            id="project.estructure.CreateProject.fields.title" 
                            defaultMessage="Título del proyecto" 
                        />
                    </Form.Label>
                    <Form.Control 
                        type="text"
                        value={title}
                        onChange={e => setTitle(e.target.value)}
                        placeholder="Ej. El misterio del valle"
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

                {/* Descripción del Proyecto */}
                <Form.Group className="mb-4" controlId="projectDescription">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage 
                            id="project.estructure.CreateProject.fields.description" 
                            defaultMessage="Descripción" 
                        />
                    </Form.Label>
                    <Form.Control 
                        as="textarea"
                        rows={3}
                        value={description}
                        onChange={e => setDescription(e.target.value)}
                        placeholder="Breve sinopsis o notas de la historia..."
                        className="py-2"
                        style={{ borderRadius: '0.5rem', resize: 'none' }}
                    />
                </Form.Group>

                {/* Botón de envío */}
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

export default CreateProject;