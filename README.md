
Cumulo Worflow
------

> (WIP) Cumulo apps project template.

Features:

* hot swapping client
* hot swapping server
* declarative programming

Based on [Stack Editor](https://github.com/mvc-works/stack-workflow).

### Usages

Start developing app:

```bash
cd app/
yarn
stack-editor
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
stack-editor
# another terminal
yarn watch
# another terminal
node target/main.js
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
