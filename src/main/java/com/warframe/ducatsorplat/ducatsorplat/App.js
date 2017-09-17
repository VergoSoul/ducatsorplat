import React from 'react';
import ReactDOM from "react-dom";
import './App.css';

class ItemList extends React.Component{
    render() {
        const items = this.props.items.map(item =>
            <Item key={item.name} item={item}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Item Name</th>
                    <th>Ducat Value</th>
                    <th>Plat Value</th>
                </tr>
                {items}
                </tbody>
            </table>
        )
    }
}

class Item extends React.Component{
    render() {
        return (
            <tr>
                <td>{this.props.item.name}</td>
                {/*<td>{this.props.item.ducatValue}</td>*/}
                {/*<td>{this.props.item.platValue}</td>*/}
            </tr>
        )
    }
}

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {items: []};
    }

    render() {
        return (
            <ItemList items={this.state.items}/>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
);
