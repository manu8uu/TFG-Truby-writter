import * as actions from './actions';
import reducer from './reducer';
import * as selectors from './selectors';
import * as actionTypes from './actionTypes';

export { default as CreateProject } from './components/CreateProject';

export default {
    actions,
    actionTypes,
    reducer,
    selectors
};