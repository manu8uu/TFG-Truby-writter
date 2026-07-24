import React, { useState, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { FormattedMessage } from 'react-intl';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { Errors } from '../../common';
import objects from '../index';

const ModifyCharacter = ({ character, onSuccess }) => {
    const dispatch = useDispatch();
    const formRef = useRef(null);

    const [name, setName] = useState(character?.name || '');
    const [bio, setBio] = useState(character?.bio || '');
    const [mainImageUrl, setMainImageUrl] = useState(character?.mainImageUrl || '');
    const [formValidated, setFormValidated] = useState(false);
    const [backendErrors, setBackendErrors] = useState(null);

    const handleSubmit = (event) => {
        event.preventDefault();
        const form = formRef.current;

        if (!character?.id) {
            setBackendErrors({ globalError: "No se encontró el personaje a modificar." });
            return;
        }

        if (form && form.checkValidity()) {
            setBackendErrors(null);

            dispatch(objects.actions.modifyCharacter(
                character.id,
                {
                    name: name.trim(),
                    bio: bio.trim(),
                    mainImageUrl: mainImageUrl.trim()
                },
                (updatedCharacter) => {
                    if (onSuccess) onSuccess(updatedCharacter);
                },
                (errors) => setBackendErrors(errors)
            ));
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
                <Form.Group className="mb-3" controlId="modifyCharacterName">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage
                            id="project.objects.ModifyCharacter.fields.name"
                            defaultMessage="Nombre del personaje"
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

                <Form.Group className="mb-3" controlId="modifyCharacterBio">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage
                            id="project.objects.ModifyCharacter.fields.bio"
                            defaultMessage="Biografía"
                        />
                    </Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        value={bio}
                        onChange={e => setBio(e.target.value)}
                        className="py-2"
                        style={{ borderRadius: '0.5rem', resize: 'none' }}
                    />
                </Form.Group>

                <Form.Group className="mb-4" controlId="modifyCharacterImage">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage
                            id="project.objects.ModifyCharacter.fields.mainImageUrl"
                            defaultMessage="URL de imagen"
                        />
                    </Form.Label>
                    <Form.Control
                        type="text"
                        value={mainImageUrl}
                        onChange={e => setMainImageUrl(e.target.value)}
                        placeholder="https://..."
                        className="py-2"
                        style={{ borderRadius: '0.5rem' }}
                    />
                </Form.Group>

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

export default ModifyCharacter;