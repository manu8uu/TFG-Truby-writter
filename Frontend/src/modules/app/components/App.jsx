import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';

const App = () => {
    const dispatch = useDispatch();

    useEffect(() => {
        const tryLoginFromServiceToken = async () => {
    try {
        const response = await backend.userService.tryLoginFromServiceToken(
            () => dispatch(users.actions.logout())
        );
        if (response && response.ok) {
            dispatch(users.actions.loginCompleted(response.payload));
        } else {
            // Si el backend responde con error (401, 500, etc.), limpiamos la sesión local
            dispatch(users.actions.logout());
        }
    } catch (error) {
        // En caso de fallo de red o error 500 del servidor
        dispatch(users.actions.logout());
    }
};
    }, [dispatch]);

    return (
        <div className="d-flex flex-column min-vh-100">
            <Header/>
            <Body/>
            <Footer/>
        </div>
    );
}

export default App;