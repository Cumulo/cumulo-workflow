
Cumulo Worflow
------

> Cumulo apps project template.

Features:

* hot swapping client
* hot swapping server
* declarative programming

Based on [Calcit Editor](https://github.com/Cirru/calcit-editor).

### Usages

Start developing app:

```bash
yarn
yarn watch
# another terminal
node target/main.js
# with another terminal
yarn shadow-cljs clj-run build.main/page
# open http://localhost:7000
```

For editing code, run `calcit-editor` as editor servers.

### Build

```bash
yarn shadow-cljs clj-run build.main/build
```

### Workflow

https://github.com/Cumulo/cumulo-workflow

### License

MIT
