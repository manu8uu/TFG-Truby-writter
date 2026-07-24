import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import objects from '../index'; // Ajusta la ruta relativa al index del módulo objects

const CreateCharacter = ({ projectId, onSuccess }) => {
    const dispatch = useDispatch();

    const [name, setName] = useState('');
    const [bio, setBio] = useState('');
    const [mainImageUrl, setMainImageUrl] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        setBackendErrors(null);

        const characterData = {
            name: name.trim(),
            bio: bio.trim(),
            mainImageUrl: mainImageUrl.trim()
        };

        dispatch(
            objects.actions.addCharacter(
                projectId,
                characterData,
                () => {
                    setName('');
                    setBio('');
                    setMainImageUrl('');
                    if (onSuccess) onSuccess();
                },
                (errors) => setBackendErrors(errors)
            )
        );
    };

    return (
        <Form onSubmit={handleSubmit}>
            {backendErrors && (
                <Alert variant="danger" dismissible onClose={() => setBackendErrors(null)}>
                    {backendErrors.message || 'Error al crear el personaje'}
                </Alert>
            )}

            <Form.Group className="mb-3" controlId="characterName">
                <Form.Label className="fw-semibold text-dark">Nombre del Personaje</Form.Label>
                <Form.Control
                    type="text"
                    placeholder="Ej. Sherlock Holmes"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    style={{ borderRadius: '0.75rem' }}
                />
            </Form.Group>

            <Form.Group className="mb-3" controlId="characterBio">
                <Form.Label className="fw-semibold text-dark">Biografía / Descripción</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={3}
                    placeholder="Describe los rasgos clave, motivación, etc."
                    value={bio}
                    onChange={(e) => setBio(e.target.value)}
                    style={{ borderRadius: '0.75rem' }}
                />
            </Form.Group>

            <Form.Group className="mb-4" controlId="characterImage">
                <Form.Label className="fw-semibold text-dark">URL de Imagen (Opcional)</Form.Label>
                <Form.Control
                    type="url"
                    placeholder="https://ejemplo.com/imagen.jpg"
                    value={mainImageUrl}
                    onChange={(e) => setMainImageUrl(e.target.value)}
                    style={{ borderRadius: '0.75rem' }}
                />
            </Form.Group>

            <div className="d-flex justify-content-end gap-2 pt-2 border-top">
                <Button
                    type="submit"
                    className="px-4 py-2 border-0 fw-semibold text-white"
                    style={{
                        borderRadius: '0.75rem',
                        background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)'
                    }}
                >
                    Crear Personaje
                </Button>
            </div>
        </Form>
    );
};

export default CreateCharacter;