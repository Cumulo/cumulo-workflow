
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
cd app/
yarn
yarn watch
# with another terminal
yarn shadow-cljs clj-run build.main/html
# open http://localhost:7000
```

Start developing server:

```bash
cd server/
yarn
yarn watch
# another terminal
node target/main.js
```

For editing code, go to `app/` and `server/` and then run `calcit-editor` as editor servers.

### Build

Build client:

```bash
cd app/
yarn build
```

Build server:

```bash
cd server/
yarn build
```

### Workflow

https://github.com/Cumulo/cumulo-workflow

### License

MIT
