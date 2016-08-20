
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
# open browser
open http://repo.cirru.org/stack-editor/target/index.html
```

Start developing server:

```bash
cd server/
npm install
boot dev!
# and...
cd target/
node main.js
# open another browser
open http://repo.cirru.org/stack-editor/target/index.html?port=7011
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
