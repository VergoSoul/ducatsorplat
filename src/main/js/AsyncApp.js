import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import {
    selectItems,
    fetchItemsIfNeeded,
    invalidateItems
} from '../../actions/actions'
import filterableItemTable from './FilterableItemTable'

class AsyncApp extends Component {
    constructor(props) {
        super(props)
        this.handleChange = this.handleChange.bind(this)
        this.handleRefreshClick = this.handleRefreshClick.bind(this)
    }

    componentDidMount() {
        const { dispatch } = this.props
        dispatch(fetchItemsIfNeeded())
    }

    componentDidUpdate(prevProps) {
        if (this.props.items !== prevProps.items) {
            const { dispatch } = this.props
            dispatch(fetchItemsIfNeeded())
        }
    }

    handleChange() {
        this.props.dispatch(selectItems())
        this.props.dispatch(fetchItemsIfNeeded())
    }

    handleRefreshClick(e) {
        e.preventDefault()

        const { dispatch } = this.props
        dispatch(invalidateItems())
        dispatch(fetchItemsIfNeeded())
    }

    render() {
        const { items, isFetching, lastUpdated } = this.props
        return (
            <div>
                <p>
                    {lastUpdated &&
                <span>
                    Last updated at {new Date(lastUpdated).toLocaleTimeString()}.{' '}
                </span>}
                    {!isFetching &&
                    <a href="#" onClick={this.handleRefreshClick}>
                        Refresh
                    </a>}
                </p>
                {isFetching && items.length === 0 && <h2>Loading...</h2>}
                {!isFetching && items.length === 0 && <h2>Empty.</h2>}
                {items.length > 0 &&
                <div style={{ opacity: isFetching ? 0.5 : 1 }}>
                    <filterableItemTable items={items}/>
                </div>}
            </div>
        )
    }
}

AsyncApp.propTypes = {
    items: PropTypes.array.isRequired,
    isFetching: PropTypes.bool.isRequired,
    lastUpdated: PropTypes.number,
    dispatch: PropTypes.func.isRequired
}

function mapStateToProps(state) {
    const {
        isFetching,
        lastUpdated,
        items: items
    } = state || {
        isFetching: true,
        items: []
    }

    return {
        items,
        isFetching,
        lastUpdated
    }
}

export default connect(mapStateToProps)(AsyncApp)