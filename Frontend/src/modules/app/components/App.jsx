import React, { useEffect } from 'react'; // <-- Añade "React," aquí
import { useDispatch } from 'react-redux';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';

const App = () => {
    const dispatch = useDispatch();

    useEffect(() => {
        // Bloque de autenticación comentado temporalmente
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