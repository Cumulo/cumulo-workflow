
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
mkdir -p target/
npm install
webpack
boot dev!
# open browser for editor
open http://repo.cirru.org/stack-editor/target/index.html
```

Start developing server:

```bash
cd server/
npm install

boot editor!
open http://repo.cirru.org/stack-editor/target/index.html?port=7011

export boot_deps=`boot show -c`
lumo -Kc $boot_deps:src/

# run code from REPL and clear cache to do it
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

### Workflow

https://github.com/Cumulo/cumulo-workflow

### License

MIT
