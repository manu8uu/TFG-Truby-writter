import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';
import { IntlProvider } from 'react-intl';
import { configureStore } from '@reduxjs/toolkit';

// Estilos globales de Bootstrap y personalizados
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.css';

// Importamos la aplicación y su reducer desde el módulo app
import { App } from './modules/app';
import appReducer from './modules/app/reducer';

// 1. Configuración de la Store Global con Redux Toolkit
const store = configureStore({
    reducer: {
        app: appReducer
    }
});

// 2. Mensajes de traducción temporales para que no rompa la UI
const tfgMessages = {
    'project.app.Home.welcome': '¡Bienvenido a Truby-Writer!',
    'project.app.Header.locations': 'Localizaciones',
    'project.app.Header.objects': 'Objetos',
    'project.app.Header.login': 'Iniciar Sesión',
    'project.app.Footer.text': 'Universidad de A Coruña - Trabajo Fin de Grado'
};

// 3. Renderizado en el DOM para React 19
const container = document.getElementById('root');
const root = createRoot(container);

root.render(
    <React.StrictMode>
        <Provider store={store}>
            <IntlProvider locale="es" messages={tfgMessages}>
                <Router>
                    <App />
                </Router>
            </IntlProvider>
        </Provider>
    </React.StrictMode>
);