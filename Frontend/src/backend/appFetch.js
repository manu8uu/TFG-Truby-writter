const SERVICE_TOKEN_NAME = 'serviceToken';

let networkErrorCallback;
let reauthenticationCallback;

// Avisar si el backend se cae (Mantenemos init por compatibilidad)
export const init = callback => networkErrorCallback = callback;

export const setNetworkErrorCallback = callback => networkErrorCallback = callback;

// Avisar si el token expira (401)
export const setReauthenticationCallback = callback => reauthenticationCallback = callback;

// Guarda el token en el almacenamiento de la sesión bajo serviceToken
export const setServiceToken = serviceToken => sessionStorage.setItem(SERVICE_TOKEN_NAME, serviceToken);

export const getServiceToken = () => sessionStorage.getItem(SERVICE_TOKEN_NAME);

export const removeServiceToken = () => sessionStorage.removeItem(SERVICE_TOKEN_NAME);

// Recibe las cabeceras de la petición y evita procesar como json otras cosas
const isJson = response => {
    const contentType = response.headers.get('content-type');
    return contentType && contentType.indexOf('application/json') !== -1;
}

const getOptions = (method, body) => {
    const options = {};
    options.method = method; // GET, POST, PUT...

    if (body) {
        if (body instanceof FormData) {
            options.body = body;
        } else {
            options.headers = {'Content-Type': 'application/json'};
            options.body = JSON.stringify(body);
        }
    }
    let serviceToken = getServiceToken();
    if (serviceToken) {
        // Inyecta Token
        if (options.headers) {
            options.headers['Authorization'] = `Bearer ${serviceToken}`;
        } else {
            options.headers = {'Authorization': `Bearer ${serviceToken}`};
        }
    }
    return options;
}

export const appFetch = async (method, path, body) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}${path}`, getOptions(method, body));
        const appFetchResponse = {ok: response.ok, payload: null};
        
        if (response.status === 401 && reauthenticationCallback){
            reauthenticationCallback();
            return appFetchResponse;
        }
        
        if (isJson(response)) {
            appFetchResponse.payload = await response.json();
        }
        return appFetchResponse;
    
    } catch (error) {
        if (networkErrorCallback) {
            networkErrorCallback();
        } else {
            console.error("Error de red detectado, pero no hay un callback configurado:", error);
        }
    }
}