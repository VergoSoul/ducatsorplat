import React from 'react';
import PropTypes from 'prop-types'

const ItemRow = ({ item }) => (
    <tr>
        <td>{item.name}</td>
        <td>{item.ducatValue}</td>
        <td>{item.platValue}</td>
    </tr>
);

ItemRow.propTypes = {
    item: PropTypes.shape({
        name: PropTypes.string.isRequired,
        ducatValue: PropTypes.number.isRequired,
        platValue: PropTypes.number.isRequired
    }).isRequired
};

export default ItemRow;