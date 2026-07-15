import { appFetch } from '../../backend/appFetch';

const BASE_PATH = '/structure';


export const createProject = async (dto) => {
    return await appFetch('POST', `${BASE_PATH}/projects/createProject`, dto);
};

export const getProject = async (id) => {
    return await appFetch('GET', `${BASE_PATH}/projects/${id}`);
};

export const deleteProject = async (id) => {
    return await appFetch('DELETE', `${BASE_PATH}/projects/deleteProject/${id}`);
};

export const addPlot = async (projectId, dto) => {
    return await appFetch('POST', `${BASE_PATH}/projects/${projectId}/createPlot`, dto);
};

export const modifyPlot = async (plotId, dto) => {
    return await appFetch('PUT', `${BASE_PATH}/plots/modify/${plotId}`, dto);
};

export const deletePlot = async (plotId) => {
    return await appFetch('DELETE', `${BASE_PATH}/plots/delete/${plotId}`);
};

export const findPlotById = async (plotId) => {
    return await appFetch('GET', `${BASE_PATH}/plots/${plotId}`);
};

export const addPremise = async (plotId, dto) => {
    return await appFetch('POST', `${BASE_PATH}/plots/${plotId}/createPremise`, dto);
};

export const modifyPremise = async (premiseId, dto) => {
    return await appFetch('PUT', `${BASE_PATH}/premises/modify/${premiseId}`, dto);
};

export const deletePremise = async (premiseId) => {
    return await appFetch('DELETE', `${BASE_PATH}/premises/delete/${premiseId}`);
};

export const findPremiseById = async (premiseId) => {
    return await appFetch('GET', `${BASE_PATH}/premises/findPremise/${premiseId}`);
};

export const findPremisesByFilterName = async (plotId, name) => {
    return await appFetch('GET', `${BASE_PATH}/plots/${plotId}/premises/search?name=${encodeURIComponent(name)}`);
};

export const addCharacterToNetwork = async (plotId, dto) => {
    return await appFetch('POST', `${BASE_PATH}/plots/${plotId}/network/addCharacter`, dto);
};

export const modifyCharacterRoleInNetwork = async (plotId, characterId, dto) => {
    return await appFetch('PUT', `${BASE_PATH}/plots/${plotId}/network/modifyCharacter/${characterId}`, dto);
};

export const deleteCharacterFromNetwork = async (plotId, characterId) => {
    return await appFetch('DELETE', `${BASE_PATH}/plots/${plotId}/network/characters/deleteCharacter/${characterId}`);
};

export const addRelationshipToNetwork = async (plotId, dto) => {
    return await appFetch('POST', `${BASE_PATH}/plots/${plotId}/network/relationships`, dto);
};

export const modifyRelationship = async (plotId, dto) => {
    return await appFetch('PUT', `${BASE_PATH}/plots/${plotId}/network/relationships`, dto);
};

export const deleteRelationship = async (plotId, characterId1, characterId2) => {
    const params = `?characterId1=${characterId1}&characterId2=${characterId2}`;
    return await appFetch('DELETE', `${BASE_PATH}/plots/${plotId}/network/relationships${params}`);
};