
var main = require('../compiled/server.main');

main.init();
var app = main.start();

if (module.hot) {
  module.hot.accept('../compiled/server.main', function() {
    // stop currently running version
    main.stop(app);
    main = require('../compiled/server.main');
    app = main.start();
  });
}
