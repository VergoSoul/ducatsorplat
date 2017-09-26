import request from 'superagent'
import * as types from '../constants/actionTypes'

const receiveItems = items => ({
    type: types.RECEIVE_ITEMS,
    items: items
});

var ITEMS = [ {name:"Gun", ducatValue:100, platValue:46, message:""}, {name:"Gun2", ducatValue:50, platValue:100, message:""} ];

export const getAllItems = () => dispatch => {
    request
        .get('/data')
        .end(function (err, res) {
            if (err || !res.ok) {
                alert('Oh no! error' + err.message);
                dispatch(receiveItems(ITEMS))
            } else {
                //alert('yay got ' + JSON.stringify(res.body));
                dispatch(receiveItems(res.body));
            }
        })
};