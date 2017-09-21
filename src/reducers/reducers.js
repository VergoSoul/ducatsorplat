import * as types from '../constants/actionTypes';

function selectedItems(state = {}, action) {
    switch (action.type) {
        case types.SELECT_ITEMS:
        case types.INVALIDATE_ITEMS:
        case types.RECEIVE_ITEMS:
        case types.REQUEST_ITEMS:
            return Object.assign({}, state, {
                [action.items]: items(state, action)
            })
        default:
            return state
    }
}

function items(
    state = {
        isFetching: false,
        didInvalidate: false,
        items: []
    },
    action
) {
    switch (action.type) {
        case types.INVALIDATE_ITEMS:
            return Object.assign({}, state, {
                didInvalidate: true
            })
        case types.REQUEST_ITEMS:
            return Object.assign({}, state, {
                isFetching: true,
                didInvalidate: false
            })
        case types.RECEIVE_ITEMS:
            return Object.assign({}, state, {
                isFetching: false,
                didInvalidate: false,
                items: action.items,
                lastUpdated: action.receivedAt
            })
        default:
            return state
    }
}

const rootReducer = selectedItems

export default rootReducer