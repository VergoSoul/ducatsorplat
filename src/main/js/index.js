import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import configureStore from '../../store_enhancers/configureStore';
import asyncApp from './AsyncApp'
import filterableItemTable from './FilterableItemTable'
import DevTools from '../../store_enhancers/devTools';

var ITEMS = [ {name:"Gun", ducatValue:100, platValue:46} ];

const store = configureStore(ITEMS);

class Root extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <asyncApp />
            </Provider>
        )
    }
}

class ItemRow extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.item.name}</td>
                <td>{this.props.item.ducatValue}</td>
                <td>{this.props.item.platValue}</td>
            </tr>
        );
    }
}

class ItemTable extends React.Component {
    render() {
        var rows = [];
        if(this.props.items && this.props.items.length > 0) {
            this.props.items.forEach(function(item) {
                rows.push(<ItemRow item={item} key={item.name}/>);
            });
        }

        return (
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Ducat Value</th>
                    <th>Plat Value</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
        );
    }
}

class SearchBar extends React.Component {
    render() {
        return (
            <form>
                <input type="text" placeholder="Search..." />
            </form>
        );
    }
}

class FilterableItemTable extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <div>
                    <SearchBar/>
                    <ItemTable items={this.props.items}/>
                    <DevTools />
                </div>
            </Provider>
        );
    }
}

ReactDOM.render(
    <FilterableItemTable />,
    document.getElementById('index')
);