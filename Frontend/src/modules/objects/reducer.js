import { combineReducers } from 'redux';
import * as actionTypes from './actionTypes';

const initialState = {
    characterSearch: null,
};

const characterSearch = (state = initialState.characterSearch, action) => {
    switch (action.type) {
        case actionTypes.FIND_CHARACTERS_COMPLETED: {
            const raw = action.characterSearch;
            const normalized = raw?.payload || raw?.data || raw;
            return normalized;
        }

        case actionTypes.ADD_CHARACTER_COMPLETED:
            if (!state) {
                return { items: [action.character], existMoreItems: false };
            }
            return {
                ...state,
                items: [action.character, ...(state.items || [])]
            };
            
        case actionTypes.MODIFY_CHARACTER_COMPLETED:
             if (!state || !state.items) return state;
            return {
                ...state,
                items: state.items.map(item =>
                    item.id === action.character.id ? action.character : item
                )
            };

        case actionTypes.DELETE_CHARACTER_COMPLETED:
            if (!state || !state.items) return state;
            return {
                ...state,
                items: state.items.filter(item => item.id !== action.characterId)
            };

        case actionTypes.CLEAR_CHARACTERS_SEARCH:
            return null;

        

        default:
            return state;
    }
};

const reducer = combineReducers({
    characterSearch,
});

export default reducer;