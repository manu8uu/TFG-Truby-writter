import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FormattedMessage } from 'react-intl';

const BackLink = () => {
    const navigate = useNavigate();

    return (
        <button 
            type="button"
            onClick={() => navigate(-1)}
            className="btn btn-link p-0 text-decoration-none text-secondary fw-medium d-inline-flex align-items-center gap-2 mb-3 border-0 bg-transparent shadow-none"
            style={{ fontSize: '0.95rem' }}
        >
            <i className="bi bi-arrow-left fs-5"></i>
            <FormattedMessage 
                id="project.global.buttons.back" 
                defaultMessage="Volver atrás" 
            />
        </button>
    );
};

export default BackLink;