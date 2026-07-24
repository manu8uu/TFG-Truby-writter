import React, { useState, useEffect, useCallback, useRef } from 'react';
import { Card, Spinner, Button, Modal, Form, InputGroup } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import objects from '../index';
import ModifyCharacter from './ModifyCharacter';

const FindAllCharacters = ({ projectId, reloadTrigger }) => {
    const dispatch = useDispatch();
    const characters = useSelector(objects.selectors.getCharactersList);
    const [loading, setLoading] = useState(true);
    const [deletingId, setDeletingId] = useState(null);
    const [editingCharacter, setEditingCharacter] = useState(null);
    const [searchText, setSearchText] = useState('');

    const debounceRef = useRef(null);

    const fetchCharacters = useCallback(async (text = '') => {
        if (!projectId) return;
        try {
            setLoading(true);
            await dispatch(objects.actions.findCharactersByFilter(projectId, text));
        } catch (error) {
            console.error("Error al cargar personajes:", error);
        } finally {
            setLoading(false);
        }
    }, [dispatch, projectId]);

    // Carga inicial y recarga forzada (creación/reloadTrigger) - respeta el texto actual
    useEffect(() => {
        fetchCharacters(searchText);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [fetchCharacters, reloadTrigger]);

    const handleSearchChange = (e) => {
        const value = e.target.value;
        setSearchText(value);

        if (debounceRef.current) {
            clearTimeout(debounceRef.current);
        }

        debounceRef.current = setTimeout(() => {
            fetchCharacters(value);
        }, 350);
    };

    useEffect(() => {
        return () => {
            if (debounceRef.current) clearTimeout(debounceRef.current);
        };
    }, []);

    const handleDelete = (e, characterId, characterName) => {
        e.stopPropagation();
        const confirmed = window.confirm(
            `¿Seguro que quieres eliminar "${characterName}"? Esta acción no se puede deshacer.`
        );
        if (!confirmed) return;

        setDeletingId(characterId);
        dispatch(objects.actions.deleteCharacter(characterId, () => {
            setDeletingId(null);
            fetchCharacters(searchText);
        }));
    };

    const handleCharacterModified = () => {
        setEditingCharacter(null);
    };

    return (
        <div>
            {/* Barra de búsqueda */}
            <InputGroup className="mb-4">
                <InputGroup.Text
                    className="bg-white border-end-0"
                    style={{ borderRadius: '0.75rem 0 0 0.75rem', borderColor: '#e2e8f0' }}
                >
                    <i className="bi bi-search text-muted"></i>
                </InputGroup.Text>
                <Form.Control
                    type="text"
                    placeholder="Buscar personaje por nombre..."
                    value={searchText}
                    onChange={handleSearchChange}
                    className="border-start-0 py-2"
                    style={{ borderRadius: '0 0.75rem 0.75rem 0', borderColor: '#e2e8f0', boxShadow: 'none' }}
                />
            </InputGroup>

            {loading ? (
                <div className="text-center py-5">
                    <Spinner animation="border" variant="primary" />
                </div>
            ) : !characters || characters.length === 0 ? (
                <Card
                    className="border-0 shadow-sm p-5 text-center"
                    style={{
                        borderRadius: '1.25rem',
                        backgroundColor: '#ffffff',
                        border: '1px solid #e2e8f0'
                    }}
                >
                    <p className="text-muted mb-0">
                        {searchText
                            ? `No se encontraron personajes que coincidan con "${searchText}".`
                            : 'No tienes personajes creados aún. ¡Crea el primero!'}
                    </p>
                </Card>
            ) : (
                characters.map((character) => (
                    <Card
                        key={character.id}
                        className="border-0 shadow-sm p-4 position-relative mb-4"
                        style={{
                            borderRadius: '1.25rem',
                            backgroundColor: '#ffffff',
                            border: '1px solid #e2e8f0'
                        }}
                    >
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <div className="d-flex align-items-center gap-2 text-muted small fw-medium">
                                <i className="bi bi-person-badge fs-5 text-primary"></i>
                            </div>

                            <div className="d-flex align-items-center gap-2">
                                <Button
                                    variant="outline-secondary"
                                    size="sm"
                                    className="d-flex align-items-center gap-1"
                                    onClick={(e) => { e.stopPropagation(); setEditingCharacter(character); }}
                                >
                                    <i className="bi bi-pencil-square"></i>
                                    Editar
                                </Button>

                                <Button
                                    variant="outline-danger"
                                    size="sm"
                                    className="d-flex align-items-center gap-1"
                                    disabled={deletingId === character.id}
                                    onClick={(e) => handleDelete(e, character.id, character.name)}
                                >
                                    {deletingId === character.id ? (
                                        <Spinner animation="border" size="sm" />
                                    ) : (
                                        <>
                                            <i className="bi bi-trash"></i>
                                            Eliminar
                                        </>
                                    )}
                                </Button>
                            </div>
                        </div>

                        <div className="d-flex align-items-center gap-3">
                            {character.mainImageUrl ? (
                                <img
                                    src={character.mainImageUrl}
                                    alt={character.name}
                                    className="rounded-circle object-fit-cover"
                                    style={{ width: '56px', height: '56px' }}
                                />
                            ) : (
                                <div
                                    className="rounded-circle d-flex align-items-center justify-content-center fw-bold text-white"
                                    style={{
                                        width: '56px',
                                        height: '56px',
                                        fontSize: '1.1rem',
                                        background: 'linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%)'
                                    }}
                                >
                                    {character.name?.charAt(0).toUpperCase()}
                                </div>
                            )}

                            <div>
                                <h3 className="fw-bold text-dark mb-1 fs-4">
                                    {character.name}
                                </h3>
                                <p className="text-secondary small mb-0">
                                    {character.bio || 'Sin biografía'}
                                </p>
                            </div>
                        </div>
                    </Card>
                ))
            )}

            {/* Modal Modificar Personaje */}
            <Modal
                show={!!editingCharacter}
                onHide={() => setEditingCharacter(null)}
                centered
                contentClassName="border-0 shadow-lg"
                style={{ borderRadius: '1.25rem' }}
            >
                <Modal.Header closeButton className="border-0 pt-4 px-4 pb-0">
                    <Modal.Title className="fw-bold text-dark d-flex align-items-center gap-2 fs-5">
                        <i className="bi bi-pencil-square text-primary"></i>
                        Modificar personaje
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body className="p-4">
                    {editingCharacter && (
                        <ModifyCharacter character={editingCharacter} onSuccess={handleCharacterModified} />
                    )}
                </Modal.Body>
            </Modal>
        </div>
    );
};

export default FindAllCharacters;