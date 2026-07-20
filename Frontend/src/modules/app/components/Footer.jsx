import React from 'react';
import { FormattedMessage } from 'react-intl';

const Footer = () => (
    <footer className="bg-dark text-white text-center py-3 mt-auto">
        <div className="container">
            <p className="mb-0">
                &copy; {new Date().getFullYear()} Truby-Writer. <FormattedMessage id="footer.rights" defaultMessage="All rights reserved." />
            </p>
        </div>
    </footer>
);

export default Footer;