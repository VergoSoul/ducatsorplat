var path = require('path');
var webpack = require('webpack');

module.exports = {
    devtool: 'eval',
    entry: [ "./src/main/js/index.js" ],
    output: {
        path: __dirname + "/src/main/resources/static",
        filename: "bundle.js"
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                loaders: ['babel-loader'],
                include: path.join(__dirname, 'src')
            },
            { test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/ },
            { test: /\.jsx$/, loader: 'babel-loader', exclude: /node_modules/ }
        ]
    },
};