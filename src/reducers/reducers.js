import * as types from '../constants/actionTypes';

function items(state = {}, action) {
    switch (action.type) {
        case types.RECEIVE_ITEMS:
            return action.items;
        default:
            return state;
    }
}

const rootReducer = items

export default rootReducer