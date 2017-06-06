
require('respo-ui');
require('./main.css');

var main = require('../target/client.main.js');

if (module.hot) {
  module.hot.accept('../target/client.main.js', function() {
    main = require('../target/client.main.js');
    main.on_jsload_BANG_();
  });
}
