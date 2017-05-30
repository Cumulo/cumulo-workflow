
var fs = require("fs");
var path = require("path");
var resolve = require("path").resolve;
var webpack = require("webpack");

var nodeModules = {};

fs.readdirSync('node_modules').filter((x) => {
  return ['.bin'].indexOf(x) === -1
}).forEach((mod) => {
  nodeModules[mod] = `commonjs ${mod}`;
});

module.exports = {
  entry: {
    main: ['webpack/hot/poll?1000', './lib/page.js']
  },
  target: 'node',
  devServer: {
    stats: 'errors-only',
    contentBase: resolve(__dirname, 'dist'),
    publicPath: "/"
  },
  output: {
    path: path.join(__dirname, 'dist'),
    filename: "[name].js"
  },
  module: {
    rules: []
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NamedModulesPlugin()
  ],
  externals: nodeModules
};
