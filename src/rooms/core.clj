(ns rooms.core
  (:gen-class))

;; a bunch of pre-generated rooms for testing with

(def red-room {:name "The Red Room" :description "This room is red, exit is 'g'" :exits {"g" "The Green Room"}})
(def green-room {:name "The Green Room" :description "This room is green, exit is 'b'" :exits {"b" "The Blue Room"}})
(def blue-room {:name "The Blue Room" :description "This room is blue, exit is 'r'" :exits {"r" "The Red Room"}})

;;collapses a vector [a b c d] into a hash-map {a b, c d}
(defn map-by-twos [invec]
  (zipmap (take-nth 2 invec) (take-nth 2 (rest invec))))


;; a utility function used to break up the exits nested in "split rooms" by slurp_rooms
(defn exit-splitter [invec]
  (let [exits (nth invec 2)]
    (zipmap [:name :description :exits] [(first invec) (nth invec 1) (map-by-twos (clojure.string/split exits #" % "))])))

;; sucks in a bunch of rooms from a text file and breaks them into a vector of hash-maps
(defn slurp-rooms [input_file]
  (comment "FINISH ME")
  (let [raw_rooms (clojure.string/split-lines (slurp input_file))]
    (let [split_rooms (mapv clojure.string/split raw_rooms (repeatedly (fn [] #" \| ")))]
      (let [split_exits (mapv exit-splitter split_rooms)]
        split_exits))))

;; given a vec of maps with :name fields and an input string, searches the set and returns the map with a name field matching the input string
(defn search-exits [map_vec input current_room]
  (if (= 0 (count map_vec))
    (do
      (println "That's not an Exit!\n")
      current_room)
    (if (= (get (peek map_vec) :name) input)
      (peek map_vec)
      (search-exits (pop map_vec) input current_room))))


(defn exit-search [rooms_manifest exits_map input current_room]
  (let [room_name (get exits_map input)]
    (search-exits rooms_manifest room_name current_room)))

(defn -main
  "I start a loop that shows the player the current room then asks for input"
  []
  (def rooms (slurp-rooms "gloaming-kingdom.txt"))
  (loop [current_room (first rooms)]
    (println (get current_room :name))
    (println (get current_room :description))
    (def input (clojure.string/trim (read-line)))
    (if (= input "exit")
      nil
      (recur (exit-search rooms (get current_room :exits) input current_room)))))

