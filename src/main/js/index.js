import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import configureStore from '../../store_enhancers/configureStore';
import ItemTable from './ItemTable'
import { getAllItems } from '../../actions/actions'

var ITEMS = [ {name:"Gun", ducatValue:100, platValue:46, message:""} ];

const store = configureStore(ITEMS);

store.dispatch(getAllItems());

class Root extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <ItemTable />
            </Provider>
        )
    }
};

ReactDOM.render(
    <Root />,
    document.getElementById('index')
);