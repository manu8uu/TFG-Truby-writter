import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import estructure from '../modules/estructure';
import objects from '../modules/objects';


const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    objects: objects.reducer,
    estructure: estructure.reducer,
});

export default rootReducer;
