import { createSelector } from '@reduxjs/toolkit';

// Array vacío estático para mantener la misma referencia en memoria cuando no hay datos
const EMPTY_ARRAY = [];

const getModuleState = (state) => state.objects || state;

export const getCharacterSearch = (state) => 
    getModuleState(state)?.characterSearch;

export const getCharactersList = createSelector(
    [getCharacterSearch],
    (search) => {
        if (!search) return EMPTY_ARRAY;
        if (Array.isArray(search)) return search;

        // Primer nivel: envoltorio genérico del backend (payload/data)
        const inner = search.payload || search.data || search;
        if (Array.isArray(inner)) return inner;

        // Segundo nivel: estructura tipo Block { items, existMoreItems }
        return inner.items || inner.content || inner.result || EMPTY_ARRAY;
    }
);
/*export const getEventSearch = (state) => 
    getModuleState(state)?.eventSearch;

export const getEventsList = (state) => {
    const search = getEventSearch(state);
    if (!search) return [];
    return Array.isArray(search) ? search : (search.items || search.content || []);
};

export const getLineTime = (state) => 
    getModuleState(state)?.lineTime;*/