import React from 'react';
import PropTypes from 'prop-types';
import { FormattedMessage } from 'react-intl';
import { Alert } from 'react-bootstrap';

const Errors = ({ errors, onClose }) => {
    if (!errors) {
        return null;
    }

    // 1. Si el backend devuelve directamente un string
    if (typeof errors === 'string') {
        return (
            <Alert variant="danger" dismissible={!!onClose} onClose={onClose} className="my-3 text-start small">
                {errors}
            </Alert>
        );
    }

    // 2. Extraemos posibles estructuras de error habituales
    const globalError = errors.globalError || errors.message || errors.error;
    const fieldErrors = errors.fieldErrors;

    return (
        <Alert variant="danger" dismissible={!!onClose} onClose={onClose} className="my-3 text-start small">
            {/* Error global o mensaje genérico del backend */}
            {globalError && (
                <div>{globalError}</div>
            )}
            
            {/* Lista de errores por campo */}
            {fieldErrors && Array.isArray(fieldErrors) && fieldErrors.length > 0 && (
                <ul className="mb-0 ps-3">
                    {fieldErrors.map((fieldError, index) => (
                        <li key={index}>
                            {fieldError.fieldName ? <strong>{fieldError.fieldName}: </strong> : null}
                            {fieldError.message || fieldError.defaultMessage || fieldError}
                        </li>
                    ))}
                </ul>
            )}

            {/* Caso fallback si el objeto venía vacío o no reconocido */}
            {!globalError && (!fieldErrors || fieldErrors.length === 0) && (
                <div>
                    <FormattedMessage 
                        id="project.global.exceptions.NetworkError" 
                        defaultMessage="Nombre de usuario en uso o datos incorrectos." 
                    />
                </div>
            )}
        </Alert>
    );
};

Errors.propTypes = {
    errors: PropTypes.oneOfType([PropTypes.object, PropTypes.string]),
    onClose: PropTypes.func
};

export default Errors;