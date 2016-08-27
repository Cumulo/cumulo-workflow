
Cumulo Worflow
------

> Cumulo apps project template.

Features:

* hot swapping client
* hot swapping server
* declarative programming

Based on [Stack Editor](https://github.com/mvc-works/stack-workflow).

### Usages

Start developing app:

```bash
cd app/
boot dev!
# open browser for editor
open http://repo.cirru.org/stack-editor/target/index.html
```

Start developing server:

```bash
cd server/
npm install

boot repl
# then run `(boot (dev!))` for editor
open http://repo.cirru.org/stack-editor/target/index.html?port=7011

boot dev
# then run `(start-figwheel!)`

# start node.js server
cd target/
node app.js
```

### Build

Build client:

```bash
cd app/
boot build-advanced
```

Build server:

```bash
cd server/
boot build-simple
```

### License

MIT
