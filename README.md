
Cumulo Worflow
------

> Cumulo apps project template.

Features:

* hot swapping client
* hot swapping server
* declarative programming

### Usages

Start developing app:

```bash
cd app/
boot dev
# open browser
```

Start developing server:

```bash
cd server/
npm install
boot dev
# and...
cd target/
node main.js
```

Start editing code:

```bash
cle app/cirru/src/workflow/ server/cirru/app/workflow_server/
# open http://repo.cirru.org/light-editor
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
