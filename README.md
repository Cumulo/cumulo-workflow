
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
yarn
webpack
export deps=`boot show -c`
lumo -Kc $deps:src/ -i tasks/render.cljs
stack-editor
# with another terminal
boot dev
# open browser for editor
open http://repo.cirru.org/stack-editor/
```

Start developing server:

```bash
cd server/
yarn

export deps=`boot show -c`
port=7011 stack-editor
open http://repo.cirru.org/stack-editor/?port=7011

# with another terminal
export deps=`boot show -c`
lumo -n 6000 -Kvc $deps:src/ -i src/server/main.cljs
```

To remove Lumo caches and reload the files:

```bash
lumo -i tasks/watcher.cljs
```

Read this post for how it works http://clojure-china.org/t/lumo-repl/611

If it fails, you can type in REPL manually:

```clojure
(require '[server.main :as main] :reload)
(main/rm-caches!)
(require '[server.updater.core :as updater] :reload)
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
