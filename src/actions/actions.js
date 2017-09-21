import fetch from 'isomorphic-fetch'
import * as types from '../constants/actionTypes'

export function selectItems() {
    return {
        type: types.SELECT_ITEMS,
    }
}

export function invalidateItems() {
    return {
        type: types.INVALIDATE_ITEMS,
    }
}

function requestItems() {
    return {
        type: types.REQUEST_ITEMS,
    }
}

function receiveItems(json) {
    return {
        type: types.RECEIVE_ITEMS,
        items: json.data.children.map(child => child.data),
        receivedAt: Date.now()
    }
}

function fetchItems() {
    return dispatch => {
        dispatch(requestItems())
        return fetch('https://www.reddit.com/r/gamedeals.json')
            .then(response => response.json())
            .then(json => dispatch(receiveItems(json)))
    }
}

function shouldFetchItems(state) {
    const items = state.items
    if (!items) {
        return true
    } else if (items.isFetching) {
        return false
    } else {
        return items.didInvalidate
    }
}

export function fetchItemsIfNeeded() {
    return (dispatch, getState) => {
        if (shouldFetchItems(getState())) {
            return dispatch(fetchItems())
        }
    }
}