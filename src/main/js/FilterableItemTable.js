import React from 'react';
import DevTools from '../../store_enhancers/devTools';
import PropTypes from 'prop-types'

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
        this.props.items.forEach(function(item) {
            rows.push(<ItemRow item={item} key={item.name} />);
        });

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

export default class FilterableItemTable extends React.Component {
    render() {
        return (
            <div>
                <SearchBar/>
                <ItemTable items={this.props.items}/>
                <DevTools />
            </div>
        );
    }
}

FilterableItemTable.propTypes = {
    items: PropTypes.array.isRequired
}