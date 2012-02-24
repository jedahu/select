# Synopsis

Designing a page with HTML and CSS is fairly straigtforward and iteration time
is fast. Add in a non-HTML templating system and each design change must be
tested by running the app, or at least the template system. This can mean the
designer is relying on the programmer to keep them up-to-date with variable
names for the template.

If the template system consists of selectors bound to functions that return DOM
nodes, the designer can create plain HTML for the programmer to use. In this
scenario the programmer simply replaces parts of the design while taking care to
preserve its ids and classes.

Such a system requires only a simple API.

<pre class='brush: clojure'>
;; select/fill: [root-selector selector-fn-map & opts]

(requre 'select)

(def data {:title "Hello"})

(def selector-map
  {".title" (fn [matched-node]
              (let [node (. matched-node cloneNode)]
                (set! (. node -innerText)
                      (:title data))))})

(defn css-query [root-node query]
  (. root (querySelectorAll query)))

(select/fill js/document selector-map :selector-fn css-query)
</pre>

Full documentation at <http://jedahu.github.com/select/>
