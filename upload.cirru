
{} (:host |cumulo.org)
  :uploads $ []
    {} (:from |dist/*) (:to |/web-assets/cdn/cumulo-workflow/)
    {} (:from |dist/{index.html,manifest.json}) (:to |/web-assets/repo/Cumulo/workflow/)
    {} (:from |dist/{server.js,package.json}) (:to |/servers/workflow/)
