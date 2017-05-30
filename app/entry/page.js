
require('respo-ui');
require('./main.css');

var main = require('../compiled/client.main.js');

if (module.hot) {
  module.hot.accept('../compiled/client.main.js', function() {
    main = require('../compiled/client.main.js');
    main.on_jsload_BANG_();
  });
}
