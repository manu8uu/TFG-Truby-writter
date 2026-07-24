import * as actionTypes from './actionTypes';
import backend from '../../backend';

// --- PERSONAJES ---
const addCharacterCompleted = (character) => ({
    type: actionTypes.ADD_CHARACTER_COMPLETED,
    character
});

export const addCharacter = (projectId, characterData, onSuccess, onErrors) => async (dispatch) => {
    try {
        const response = await backend.objectsService.addCharacter(projectId, characterData);
        const character = (response && typeof response === 'object')
            ? (response.payload || response.items || response.content || response.result || response.data || response)
            : response;

        dispatch(addCharacterCompleted(character));
        if (onSuccess) onSuccess(character);
    } catch (errors) {
        if (onErrors) onErrors(errors);
    }
};

const findCharactersCompleted = (characterSearch) => ({
    type: actionTypes.FIND_CHARACTERS_COMPLETED,
    characterSearch
});

export const findCharactersByFilter = (projectId, text) => async (dispatch) => {
    try {
        const characterSearch = await backend.objectsService.findCharactersByFilter(projectId, text);
        dispatch(findCharactersCompleted(characterSearch));
        return characterSearch;
    } catch (errors) {
        console.error('Error al buscar personajes:', errors);
    }
};

export const deleteCharacter = (characterId, onSuccess) => async (dispatch) => {
    try {
        await backend.objectsService.deleteCharacter(characterId);
        dispatch({ type: actionTypes.DELETE_CHARACTER_COMPLETED, characterId });
        if (onSuccess) onSuccess();
    } catch (errors) {
        console.error('Error al borrar personaje:', errors);
    }
};

const modifyCharacterCompleted = (character) => ({
    type: actionTypes.MODIFY_CHARACTER_COMPLETED,
    character
});

export const modifyCharacter = (characterId, characterData, onSuccess, onErrors) => async (dispatch) => {
    try {
        const response = await backend.objectsService.modifyCharacter(characterId, characterData);
        const character = (response && typeof response === 'object')
            ? (response.payload || response.data || response)
            : response;

        dispatch(modifyCharacterCompleted(character));
        if (onSuccess) onSuccess(character);
    } catch (errors) {
        if (onErrors) onErrors(errors);
    }
};