import * as actionTypes from './actionTypes';
import backend from '../../backend';

export const signUp = (user, onSuccess, onErrors) => async dispatch => {
    try {
        const response = await backend.userService.signUp(user);

        // appFetch devuelve { ok: true, payload: { serviceToken, userDto } }
        if (response && response.ok && response.payload) {
            // Guardamos en Redux el objeto devuelto en payload
            dispatch(signUpCompleted(response.payload));
            dispatch(loginCompleted(response.payload));
            
            if (onSuccess) onSuccess();
        } else {
            if (onErrors) onErrors(response?.payload || response);
        }
    } catch (error) {
        if (onErrors) onErrors(error);
    }
};

export const signUpCompleted = authenticatedUser => ({
    type: actionTypes.SIGN_UP_COMPLETED,
    authenticatedUser
});

export const login = (userName, password, onSuccess, onErrors) => async dispatch => {
    try {
        const response = await backend.userService.login(userName, password);

        if (response && response.ok && response.payload) {
            dispatch(loginCompleted(response.payload));
            if (onSuccess) onSuccess();
        } else {
            if (onErrors) onErrors(response?.payload || response);
        }
    } catch (error) {
        if (onErrors) onErrors(error);
    }
};

export const loginCompleted = authenticatedUser => ({
    type: actionTypes.LOGIN_COMPLETED,
    authenticatedUser
});

export const logout = () => {
    backend.userService.logout();
    return { type: actionTypes.LOGOUT };
};