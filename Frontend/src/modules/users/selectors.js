const getModuleState = state => state.users;

export const getUser = state => 
    getModuleState(state)?.user;

export const isLoggedIn = state =>
    getUser(state) !== null && getUser(state) !== undefined;

export const getUserName = state => 
    isLoggedIn(state) ? getUser(state).userName : null;
