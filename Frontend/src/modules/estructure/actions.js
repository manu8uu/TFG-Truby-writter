import * as actionTypes from './actionTypes';

export const projectCreated = project => ({
    type: actionTypes.PROJECT_CREATED,
    project
});

export const allProjectsFounded = projects => ({
    type: actionTypes.ALL_PROJECTS_FOUNDED,
    projects
});