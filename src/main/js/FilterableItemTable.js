import React from 'react';
import PropTypes from 'prop-types'
import ItemTable from './ItemTable'
import { connect } from 'react-redux'

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
            <div>
                <SearchBar/>
                dispatch(requestItems());
                <ItemTable items={this.props.items}/>
            </div>
        );
    }
}

FilterableItemTable.propTypes = {
    items: PropTypes.arrayOf.isRequired
}

const FilterTable = connect(
    this.props = state
)(FilterableItemTable)

export default FilterTable