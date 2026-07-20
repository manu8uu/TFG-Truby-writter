import React from 'react';
import { FormattedMessage } from 'react-intl';
import Container from 'react-bootstrap/Container';

const Home = () => (
    <Container className="text-center py-5">
        <h1>
            <FormattedMessage id="project.app.Home.welcome"/>
        </h1>
    </Container>
);

export default Home;