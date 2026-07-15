import {appFetch, setServiceToken, getServiceToken, removeServiceToken, setReauthenticationCallback, setNetworkErrorCallback} from './appFetch';

const BASE_PATH = '/users';


export const singUp = async (userDto, reauthenticationCallback) => {
    const response = await appFetch('POST', `${BASE_PATH}/signup`, userDto);
    if (response.ok && response.payload) {
        setServiceToken(response.payload.serviceToken);
        setReauthenticationCallback(reauthenticationCallback);
    }
    return response;
}

export const login = async (userName, password, reauthenticationCallback) => {
    const response = await appFetch('POST', `${BASE_PATH}/login`, {userName, password});
    if (response.ok && response.payload) {
        setServiceToken(response.payload.serviceToken);
        setReauthenticationCallback(reauthenticationCallback);
    }
    return response;
}

export const tryLoginFromServiceToken = async (reauthenticationCallback) => {
    const serviceToken = getServiceToken();
    if (!serviceToken) {
        return {ok: false, payload: null};
    }
    setReauthenticationCallback(reauthenticationCallback);

    const response = await appFetch('POST', `${BASE_PATH}/loginFromServiceToken`);

    if (response.ok) {
        return response;
    } else {
        removeServiceToken();
        return { ok: false, payload: null };
    }
}

export const logout = () => removeServiceToken();

export const changePassword = async (userDto, reauthenticationCallback) => {
    const response = await appFetch('PUT', `${BASE_PATH}/changePassword`, userDto);
    if (response.ok) {
        setReauthenticationCallback(reauthenticationCallback);
    }
    return response;
}

export const searchUsersByFilter = async (blocked, filter) =>{
    let queryParams = '';
    const params = [];
    
    if (blocked!== undefined && blocked !== null) {
        params.push(`blocked=${blocked}`);
    }
    if (filter){
        params.push(`filter=${encodeURIComponent(filter)}`);
    }
    if(params.length > 0){
        queryParams = '?' + params.join('&');
    }

    return await appFetch('GET', `${BASE_PATH}/search${queryParams}`);
}

export const blockUser = async (userId) =>{
    return await appFetch('POST', `${BASE_PATH}/${userId}/block`);
}

export const unblockUser = async (userId) => {
    return await appFetch('POST', `${BASE_PATH}/${userId}/unblock`);
};

export const findUserByUsername = async (username) => {
    return await appFetch('GET', `${BASE_PATH}/username/${encodeURIComponent(username)}`);
};





















































