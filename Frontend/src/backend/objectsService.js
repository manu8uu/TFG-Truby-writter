import { appFetch } from './appFetch';

const BASE_PATH = '/structure';

export const addCharacter = async (projectId, characterDto) => {
    return await appFetch('POST', `${BASE_PATH}/projects/${projectId}/createCharacter`, characterDto);
};

export const modifyCharacter = async (characterId, characterDto) => {
    return await appFetch('PUT', `${BASE_PATH}/characters/modify/${characterId}`, characterDto);
};

export const deleteCharacter = async (characterId) => {
    return await appFetch('DELETE', `${BASE_PATH}/characters/delete/${characterId}`);
};

export const getCharacterById = async (characterId) => {
    return await appFetch('GET', `${BASE_PATH}/characters/${characterId}`);
};

export const findCharactersByFilter = async (projectId, text) => {
    return await appFetch('GET', `${BASE_PATH}/projects/${projectId}/characters/search?text=${encodeURIComponent(text)}`);
};

export const findCharacterByName = async (projectId, name) => {
    return await appFetch('GET', `${BASE_PATH}/projects/${projectId}/characters/byName?name=${encodeURIComponent(name)}`);
};

export const addEvent = async (timelineId, eventDto) => {
    return await appFetch('POST', `${BASE_PATH}/plots/${timelineId}/createEvent`, eventDto);
};

export const modifyEvent = async (eventId, eventDto) => {
    return await appFetch('PUT', `${BASE_PATH}/events/modify/${eventId}`, eventDto);
};

export const deleteEvent = async (eventId) => {
    return await appFetch('DELETE', `${BASE_PATH}/events/delete/${eventId}`);
};

export const findEventsByName = async (timelineId, title) => {
    return await appFetch('GET', `${BASE_PATH}/plots/${timelineId}/events/search?title=${encodeURIComponent(title)}`);
};

export const getLineTimeByPlot = async (plotId) => {
    return await appFetch('GET', `${BASE_PATH}/plots/${plotId}/lineTime`);
};