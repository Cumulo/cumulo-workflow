
path = require 'path'
ExtractTextPlugin = require 'extract-text-webpack-plugin'

module.exports =
  entry:
    style: 'respo-ui'
  output:
    path: path.join __dirname, './target/'
    filename: '[name].js'
  module:
    rules: [
      test: /\.css$/, loader: ExtractTextPlugin.extract
        fallback: 'style-loader', use: 'css-loader'
    ,
      test: /\.(eot|svg|ttf|woff2?)(\?.+)?$/, loader: 'url-loader'
      query: {limit: 100, name: 'fonts/[name].[ext]'}
    ]
  plugins: [
    new ExtractTextPlugin("[name].css")
  ]
