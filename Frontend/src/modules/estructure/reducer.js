import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = { projects: [] };

const projects = (state = initialState.projects, action) => {
    switch (action.type) {
        case actionTypes.PROJECT_CREATED:
            return [...state, action.project];

        case actionTypes.ALL_PROJECTS_FOUNDED:
            return action.projects;
            
        default:
            return state;
    }
};

const reducer = combineReducers({ projects });
export default reducer;