
Cumulo Worflow
------

> (WIP) Cumulo apps project template.

Features:

* hot swapping client
* hot swapping server
* declarative programming

Based on [Cumulo Editor](https://github.com/mvc-works/coworkflow).

### Usages

Start developing app:

```bash
cd app/
yarn
cumulo-editor
# with another terminal
yarn watch
# with another terminal
yarn dev
# open http://localhost:8080
```

Start developing server:

```bash
cd server/
yarn
cumulo-editor
# another terminal
yarn watch
# another terminal
node --inspect target/main.js
```

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
