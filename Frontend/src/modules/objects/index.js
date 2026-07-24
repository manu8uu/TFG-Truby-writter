import * as actions from './actions';
import * as actionTypes from './actionTypes';
import reducer from './reducer';
import * as selectors from './selectors';

export { default as CreateCharacter } from './components/CreateCharacter';
/*export { default as CharactersHome } from './components/CharactersHome';*/

export default {
    actions,
    actionTypes,
    reducer,
    selectors
};