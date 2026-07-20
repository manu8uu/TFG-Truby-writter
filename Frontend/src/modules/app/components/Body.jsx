import React from 'react';
import Container from 'react-bootstrap/Container';
import { Route, Routes } from 'react-router-dom';
import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';

const Body = () => (
    <Container className="py-4 flex-grow-1">
        {/* Componente para interceptar y pintar alertas o errores globales */}
        <AppGlobalComponents />
        
        <Routes>
            {/* Ruta por defecto que carga la pantalla de inicio */}
            <Route path="/" element={<Home />} />
            
            {/* 
              Dejamos los huecos preparados para los futuros módulos. 
              Por ahora, si entras a estas URLs no pintarán nada hasta que 
              importemos sus respectivos componentes de negocio.
            */}
            <Route path="/locations/*" element={<div>Locations Module</div>} />
            <Route path="/objects/*" element={<div>Objects Module</div>} />
            <Route path="/users/*" element={<div>Users Module</div>} />
            
            {/* Ruta comodín por si el usuario introduce una URL que no existe */}
            <Route path="*" element={<Home />} />
        </Routes>
    </Container>
);

export default Body;