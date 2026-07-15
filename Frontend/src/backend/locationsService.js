import { appFetch } from '../../backend/appFetch';

const BASE_PATH = '/structure';


export const addLocation = async (plotId, locationDto) => {
    return await appFetch('POST', `${BASE_PATH}/plots/${plotId}/createLocation`, locationDto);
};


export const modifyLocation = async (locationId, locationDto) => {
    return await appFetch('PUT', `${BASE_PATH}/modify/${locationId}`, locationDto);
};


export const deleteLocation = async (locationId) => {
    return await appFetch('DELETE', `${BASE_PATH}/delete/${locationId}`);
};


export const findLocationById = async (locationId) => {
    return await appFetch('GET', `${BASE_PATH}/${locationId}`);
};


export const findLocationsByFilter = async (plotId, name) => {
    return await appFetch('GET', `${BASE_PATH}/plots/${plotId}/search?name=${encodeURIComponent(name)}`);
};


export const addLocationPoint = async (locationId, locationPointDto) => {
    return await appFetch('POST', `${BASE_PATH}/${locationId}/createPoint`, locationPointDto);
};


export const modifyLocationPoint = async (locationPointId, locationPointDto) => {
    return await appFetch('PUT', `${BASE_PATH}/points/modify/${locationPointId}`, locationPointDto);
};


export const deleteLocationPoint = async (locationPointId) => {
    return await appFetch('DELETE', `${BASE_PATH}/points/delete/${locationPointId}`);
};


export const findLocationPointById = async (locationPointId) => {
    return await appFetch('GET', `${BASE_PATH}/points/${locationPointId}`);
};