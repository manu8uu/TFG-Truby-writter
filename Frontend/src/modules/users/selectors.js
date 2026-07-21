const getModuleState = state => state.users;

export const getUser = state => 
    getModuleState(state)?.user;

export const isLoggedIn = state =>
    getUser(state) !== null && getUser(state) !== undefined;

export const getUserName = state => {
    const u = getUser(state);
    if (!u) return null;
    return u.username || u.userName || null;
};