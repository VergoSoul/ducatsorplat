import { createStore, applyMiddleware } from 'redux';
import rootReducer from '../reducers/reducers';
import thunkMiddleware from 'redux-thunk'
import { createLogger } from 'redux-logger'

const loggerMiddleware = createLogger()

export default function configureStore(initialState) {
    // Note: only Redux >= 3.1.0 supports passing enhancer as third argument.
    // See https://github.com/rackt/redux/releases/tag/v3.1.0
    const store = createStore(rootReducer, initialState, applyMiddleware(thunkMiddleware, loggerMiddleware));
    console.log(store.getState());
    return store;
};