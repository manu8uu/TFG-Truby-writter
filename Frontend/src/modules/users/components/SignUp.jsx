import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate, Link } from 'react-router-dom';
import { FormattedMessage } from 'react-intl';
import { Card, Form, Button, InputGroup, Row, Col } from 'react-bootstrap';

import Errors from '../../common/components/Errors';
import * as actions from '../actions';

const SignUp = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    
    const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);
    const [backendErrors, setBackendErrors] = useState(null);
    const [formValidated, setFormValidated] = useState(false);

    let form;

    const handleSubmit = event => {
        event.preventDefault();

        if (form.checkValidity()) {
            if (password !== confirmPassword) {
                setPasswordsDoNotMatch(true);
                return;
            }
            
            setPasswordsDoNotMatch(false);
            setBackendErrors(null);

            const userDto = {
                username: userName.trim(),
                password: password,
                firstName: firstName.trim(),
                lastName: lastName.trim(),
                email: email.trim()
            };

            dispatch(
                actions.signUp(
                    userDto,
                    () => navigate('/users/UserHome'),
                    errors => {
                        console.error("Errores devueltos al SignUp:", errors);
                        setBackendErrors(errors);
                    }
                )
            );

        } else {
            setBackendErrors(null);
            setFormValidated(true);
        }
    };

    return (
        <div className="d-flex align-items-center justify-content-center min-vh-100 py-5" 
             style={{ backgroundColor: '#f8fafc' }}>
            
            <div className="w-100" style={{ maxWidth: '520px' }}>
                <Card 
                    className="border-0 p-4 p-sm-5" 
                    style={{ 
                        borderRadius: '1.5rem', 
                        backgroundColor: '#ffffff',
                        boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.05), 0 8px 10px -6px rgba(0, 0, 0, 0.01)',
                        border: '1px solid #f1f5f9'
                    }}
                >
                    <div className="text-center mb-4">
                        <div 
                            className="mx-auto mb-3 d-flex align-items-center justify-content-center text-white rounded-3 shadow-sm"
                            style={{ 
                                width: '52px', 
                                height: '52px',
                                background: 'linear-gradient(135deg, #8b5cf6 0%, #6d28d9 100%)'
                            }}
                        >
                            <i className="bi bi-person-plus-fill fs-4"></i>
                        </div>
                        
                        <h3 className="fw-bold mb-1 text-dark fs-4">
                            <FormattedMessage id="project.users.SignUp.title" defaultMessage="Crear Cuenta" />
                        </h3>
                        <p className="text-muted small mb-0">
                            Únete a Truby-Writer completando el formulario
                        </p>
                    </div>

                    <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>

                    {passwordsDoNotMatch && (
                        <div className="alert alert-danger py-2 small" role="alert">
                            <FormattedMessage id="project.global.validator.passwordsDoNotMatch" defaultMessage="Las contraseñas no coinciden" />
                        </div>
                    )}

                    <Form 
                        ref={node => form = node}
                        noValidate 
                        validated={formValidated} 
                        onSubmit={e => handleSubmit(e)}
                    >
                        <Form.Group className="mb-3" controlId="userName">
                            <Form.Label className="fw-medium text-secondary small mb-1">
                                <FormattedMessage id="project.global.fields.userName" defaultMessage="Nombre de Usuario" />
                            </Form.Label>
                            <InputGroup>
                                <InputGroup.Text className="bg-light border-0 text-muted ps-3" style={{ borderRadius: '0.75rem 0 0 0.75rem' }}>
                                    <i className="bi bi-person"></i>
                                </InputGroup.Text>
                                <Form.Control 
                                    type="text"
                                    placeholder="usuario123"
                                    className="bg-light border-0 py-2.5 fs-6 shadow-none"
                                    style={{ borderRadius: '0 0.75rem 0.75rem 0', color: '#334155' }}
                                    value={userName}
                                    onChange={e => setUserName(e.target.value)}
                                    autoFocus
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id='project.global.validator.required' defaultMessage="Campo obligatorio"/>
                                </Form.Control.Feedback>
                            </InputGroup>
                        </Form.Group>

                        <Row>
                            <Col md={6}>
                                <Form.Group className="mb-3" controlId="firstName">
                                    <Form.Label className="fw-medium text-secondary small mb-1">
                                        <FormattedMessage id="project.global.fields.firstName" defaultMessage="Nombre" />
                                    </Form.Label>
                                    <Form.Control 
                                        type="text"
                                        placeholder="Juan"
                                        className="bg-light border-0 py-2.5 fs-6 shadow-none"
                                        style={{ borderRadius: '0.75rem', color: '#334155' }}
                                        value={firstName}
                                        onChange={e => setFirstName(e.target.value)}
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        <FormattedMessage id='project.global.validator.required' defaultMessage="Campo obligatorio"/>
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Col>

                            <Col md={6}>
                                <Form.Group className="mb-3" controlId="lastName">
                                    <Form.Label className="fw-medium text-secondary small mb-1">
                                        <FormattedMessage id="project.global.fields.lastName" defaultMessage="Apellidos" />
                                    </Form.Label>
                                    <Form.Control 
                                        type="text"
                                        placeholder="Pérez"
                                        className="bg-light border-0 py-2.5 fs-6 shadow-none"
                                        style={{ borderRadius: '0.75rem', color: '#334155' }}
                                        value={lastName}
                                        onChange={e => setLastName(e.target.value)}
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        <FormattedMessage id='project.global.validator.required' defaultMessage="Campo obligatorio"/>
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Col>
                        </Row>

                        <Form.Group className="mb-3" controlId="email">
                            <Form.Label className="fw-medium text-secondary small mb-1">
                                <FormattedMessage id="project.global.fields.email" defaultMessage="Email" />
                            </Form.Label>
                            <InputGroup>
                                <InputGroup.Text className="bg-light border-0 text-muted ps-3" style={{ borderRadius: '0.75rem 0 0 0.75rem' }}>
                                    <i className="bi bi-envelope"></i>
                                </InputGroup.Text>
                                <Form.Control 
                                    type="email"
                                    placeholder="ejemplo@correo.com"
                                    className="bg-light border-0 py-2.5 fs-6 shadow-none"
                                    style={{ borderRadius: '0 0.75rem 0.75rem 0', color: '#334155' }}
                                    value={email}
                                    onChange={e => setEmail(e.target.value)}
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id='project.global.validator.required' defaultMessage="Campo obligatorio"/>
                                </Form.Control.Feedback>
                            </InputGroup>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label className="fw-medium text-secondary small mb-1">
                                <FormattedMessage id="project.global.fields.password" defaultMessage="Contraseña" />
                            </Form.Label>
                            <InputGroup>
                                <InputGroup.Text className="bg-light border-0 text-muted ps-3" style={{ borderRadius: '0.75rem 0 0 0.75rem' }}>
                                    <i className="bi bi-key"></i>
                                </InputGroup.Text>
                                <Form.Control 
                                    type="password"
                                    className="bg-light border-0 py-2.5 fs-6 shadow-none"
                                    style={{ borderRadius: '0 0.75rem 0.75rem 0', color: '#334155' }}
                                    value={password}
                                    onChange={e => setPassword(e.target.value)}
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id='project.global.validator.required' defaultMessage="Campo obligatorio"/>
                                </Form.Control.Feedback>
                            </InputGroup>
                        </Form.Group>

                        <Form.Group className="mb-4" controlId="confirmPassword">
                            <Form.Label className="fw-medium text-secondary small mb-1">
                                <FormattedMessage id="project.users.SignUp.fields.confirmPassword" defaultMessage="Confirmar contraseña" />
                            </Form.Label>
                            <InputGroup>
                                <InputGroup.Text className="bg-light border-0 text-muted ps-3" style={{ borderRadius: '0.75rem 0 0 0.75rem' }}>
                                    <i className="bi bi-shield-check"></i>
                                </InputGroup.Text>
                                <Form.Control 
                                    type="password"
                                    className="bg-light border-0 py-2.5 fs-6 shadow-none"
                                    style={{ borderRadius: '0 0.75rem 0.75rem 0', color: '#334155' }}
                                    value={confirmPassword}
                                    onChange={e => setConfirmPassword(e.target.value)}
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id='project.global.validator.required' defaultMessage="Campo obligatorio"/>
                                </Form.Control.Feedback>
                            </InputGroup>
                        </Form.Group>

                        <Button 
                            type="submit" 
                            className="w-100 py-2.5 border-0 text-white fw-semibold shadow-sm mt-2"
                            style={{ 
                                background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)',
                                borderRadius: '0.75rem',
                                transition: 'all 0.2s ease'
                            }}
                        >
                            <FormattedMessage id="project.users.SignUp.title" defaultMessage="Registrarse"/>
                        </Button>
                    </Form>

                    <hr className="my-4 text-muted opacity-25" />

                    <p className="text-center mb-0 text-muted small">
                        ¿Ya tienes una cuenta?{' '}
                        <Link 
                            to="/users/login" 
                            className="fw-semibold ms-1" 
                            style={{ color: '#7c3aed', textDecoration: 'none' }}
                        >
                            <FormattedMessage id="project.users.Login.title" defaultMessage="Iniciar Sesión"/>
                        </Link>
                    </p>
                </Card>
            </div>
        </div>
    );
};

export default SignUp;