import React, { useState, useRef } from 'react';
import { useSelector } from 'react-redux';
import { FormattedMessage } from 'react-intl';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { Errors } from '../../common';
import * as selectors from '../selectors';
import backend from '../../../backend';

const ChangePassword = ({ onSuccess }) => {
    const user = useSelector(selectors.getUser);
    const formRef = useRef(null);

    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmNewPassword, setConfirmNewPassword] = useState('');
    const [formValidated, setFormValidated] = useState(false);
    const [backendErrors, setBackendErrors] = useState(null);
    const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);

    const checkConfirmNewPassword = () => {
        if (newPassword !== confirmNewPassword) {
            setPasswordsDoNotMatch(true);
            return false;
        }
        setPasswordsDoNotMatch(false);
        return true;
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const form = formRef.current;

        // Comprobación usando la propiedad 'id' mapeada del UserDto
        if (!user || !user.id) {
            setBackendErrors({ 
                globalError: "El ID de usuario no está disponible en la sesión." 
            });
            return;
        }

        if (form && form.checkValidity() && checkConfirmNewPassword()) {
            try {
                // Llamada a la API enviando user.id
                const response = await backend.userService.changePassword(
                    user.id, 
                    oldPassword, 
                    newPassword
                );

                if (response.ok) {
                    if (onSuccess) {
                        onSuccess(); // Cierra el modal tras el éxito
                    }
                } else {
                    setBackendErrors(response.payload);
                }
            } catch (err) {
                setBackendErrors(err);
            }
        } else {
            setBackendErrors(null);
            setFormValidated(true);
        }
    };

    const handleConfirmNewPasswordChange = (value) => {
        setConfirmNewPassword(value);
        setPasswordsDoNotMatch(false);
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
                {/* Contraseña Actual */}
                <Form.Group className="mb-3" controlId="oldPassword">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage id="project.users.ChangePassword.fields.oldPassword" defaultMessage="Contraseña actual" />
                    </Form.Label>
                    <Form.Control 
                        type="password"
                        value={oldPassword}
                        onChange={e => setOldPassword(e.target.value)}
                        autoFocus
                        autoComplete="current-password"
                        required
                        className="py-2"
                        style={{ borderRadius: '0.5rem' }}
                    />
                    <Form.Control.Feedback type="invalid">
                        <FormattedMessage id='project.global.validator.required' defaultMessage="Este campo es obligatorio" />
                    </Form.Control.Feedback>
                </Form.Group>

                {/* Nueva Contraseña */}
                <Form.Group className="mb-3" controlId="newPassword">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage id="project.users.ChangePassword.fields.newPassword" defaultMessage="Nueva contraseña" />
                    </Form.Label>
                    <Form.Control 
                        type="password"
                        value={newPassword}
                        onChange={e => setNewPassword(e.target.value)}
                        autoComplete="new-password"
                        required
                        className="py-2"
                        style={{ borderRadius: '0.5rem' }}
                    />
                    <Form.Control.Feedback type="invalid">
                        <FormattedMessage id='project.global.validator.required' defaultMessage="Este campo es obligatorio" />
                    </Form.Control.Feedback>
                </Form.Group>

                {/* Confirmar Nueva Contraseña */}
                <Form.Group className="mb-4" controlId="confirmNewPassword">
                    <Form.Label className="small fw-semibold text-secondary">
                        <FormattedMessage id="project.users.SignUp.fields.confirmPassword" defaultMessage="Confirmar contraseña" />
                    </Form.Label>
                    <Form.Control
                        type="password"
                        value={confirmNewPassword}
                        onChange={e => handleConfirmNewPasswordChange(e.target.value)}
                        autoComplete="new-password"
                        isInvalid={passwordsDoNotMatch}
                        required
                        className="py-2"
                        style={{ borderRadius: '0.5rem' }}
                    />
                    <Form.Control.Feedback type="invalid">
                        {passwordsDoNotMatch ? (
                            <FormattedMessage id='project.global.validator.passwordsDoNotMatch' defaultMessage="Las contraseñas no coinciden" />
                        ) : (
                            <FormattedMessage id='project.global.validator.required' defaultMessage="Este campo es obligatorio" />
                        )}
                    </Form.Control.Feedback>
                </Form.Group>

                {/* Botón de envío */}
                <div className="d-flex justify-content-end pt-2">
                    <Button 
                        type="submit" 
                        className="px-4 py-2 border-0 fw-semibold text-white shadow-sm"
                        style={{ 
                            background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)',
                            borderRadius: '0.5rem' 
                        }}
                    >
                        <FormattedMessage id="project.global.buttons.save" defaultMessage="Guardar cambios" />
                    </Button>
                </div>
            </Form>
        </div>
    );
};

export default ChangePassword;