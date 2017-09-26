import React from 'react';
import PropTypes from 'prop-types'
import ItemRow from './ItemRow'
import { connect } from 'react-redux'

const ItemTable = ({ items }) => (
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Ducat Value</th>
            <th>Plat Value</th>
            <th>Message</th>
        </tr>
        </thead>
        <tbody>
        {items.map(item =>
            <ItemRow
                item={item} />
        )}
        </tbody>
    </table>
);

ItemTable.propTypes = {
    items: PropTypes.arrayOf( PropTypes.shape({
        name: PropTypes.string.isRequired,
        ducatValue: PropTypes.number.isRequired,
        platValue: PropTypes.number.isRequired,
        message: PropTypes.string.isRequired
    })).isRequired
};

const mapStateToProps = state => ({
    items: state
});

export default connect(
    mapStateToProps
)(ItemTable);