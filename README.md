
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
source tasks/class-path.sh
lumo -Kc $boot_deps:src/ -i tasks/render.cljs
lumo -Kc $boot_deps:src/ -i tasks/server.cljs
# with another terminal
boot dev
# open browser for editor
open http://repo.cirru.org/stack-editor/target/index.html
```

Start developing server:

```bash
cd server/
npm install

source tasks/class-path.sh
lumo -Kc $boot_deps:src/ -i tasks/server.cljs
open http://repo.cirru.org/stack-editor/target/index.html?port=7011

# with another terminal
source tasks/class-path.sh
lumo -n 6000 -Kvc $boot_deps:src/ -i src/workflow_server/main.cljs

# run code from REPL and clear cache to do it
```

To remove Lumo caches and reload the files:

```bash
lumo -i tasks/watcher.cljs
```

Read this post for how it works http://clojure-china.org/t/lumo-repl/611

If it fails, you can type in REPL manually:

```clojure
(require '[workflow-server.main :as main] :reload)
(main/rm-caches!)
(require '[workflow-server.updater.core :as updater] :reload)
(main/on-jsload!)
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
