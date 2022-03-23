# graph

A novel Graph-Theory and Maze Solution library made for kotlin JVM.

# Mazes

## Maze Generation

### Minimum Spanning Trees (MST's)

| Prim's Algorithm (40x40) | Kruskal's Algorithm (40x40) | 
| -------- |-------- |
| <img src="https://i.imgur.com/vIca2nz.png" width="300">     |<img src="https://i.imgur.com/PTpO31E.png" width="300">     |

The obvious benefit to MST's is that every node can be reached from any other node, meaning all produced mazes are consistent (able to be solved).

| "Recursive" Subdivision (40x40) | Aldous-Broder Algorithm |
| -------- |-------- |
| <img src="https://i.imgur.com/EPrUePe.png" width="300">     | <img src="https://i.imgur.com/2pDalGG.png" width="300">     |

**Recursive** is in quotes here because the actual process of generating the Maze uses Depth First Search in a single method rather than programatically calling itself.

The Aldous-Broder Algorithm is slightly modified such that neighboring frontiers are connected. This produces a more uniform spanning tree.

> WIP : Recursive DFS for Maze Generation


## Maze To Graph Conversions

| Initial Maze | Graph Representation |
| -------- | -------- |
| <img src="https://i.imgur.com/sz2smzf.png" width="300"> | <img src="https://i.imgur.com/KS33Nej.png" width="300"> |

```kotlin 
val maze : Maze = // Somehow generated maze...
val graph = MazeToGraphProvider(maze).build()
```

The resulting graph represents the initial maze, where a `MazeCell` is represented by a `Vertex` and a `MazeBorder` determines the `Edge`'s.

| Maze Solution | Graph Solution |
| -------- | -------- |
| <img src="https://i.imgur.com/IkCbjGr.png" width="300"> | <img src="https://i.imgur.com/vW6NIsA.png" width="300"> |

All `GraphAlgorithms` can be used on `Graphs` and all `Mazes` can be converted into `Graphs`.

## Traversals

### Path Finding
* Breadth-First Search
* Depth-First Search
* Dijkstra
* A* Search

### Minimum Spanning Trees (MST)

| Kruskal's Algorithm | Prim's Algorithm |
| -------- | -------- |
| <img src="https://i.imgur.com/wgCCtWD.png" width="300">     | <img src="https://i.imgur.com/zQy0Kmh.png" width="300">     | 

> Generated using `PartiallyConnectedGraph` with V=20 and (E/V)<=10

In general, the `MST` algorithms accept a base `Graph` and return a cloned `Graph` representing the Tree. (Formally this is written as G \ T)

### Traveling Salesperson Problem (TSP)

* Brute Force Search - O(n!) **uses recursion**
* Permutation Search - O(n!) **linear search**
* Random Search - O(N * (|V| + |E|))
* Greedy Search (sub optimal) - O(|V| + |E|)
* Greedy Twice-Around (uses Prim's MST) - O(|V|^2 + MST)
* Two-Opt (reduces edge cross over)

## Serialization

Vertices, Edges, Paths, and Graphs are both `Clonable` and `Serializable`.

Saving an `Identifyable` Item to an `ItemizedLongStorable` will set an auto-incementing ID (long) to the Item and store it within its internal collection. In general, there is `VertexStorage`, `EdgeStorage`, `PathStorage`, and `GraphStorage`.

```kotlin 
/*
 * Where xyzStorage could be: 
 * VertexStorage, 
 * EdgeStorage, 
 * PathStorage, or GraphStorage
 * */
val outputStream = ByteArrayOutputStream()
xyzStorage.writeTo(outputStream)

// or to file...
val parent = File("PATH/TO/GRAPH/STORAGE")
xyzStorage.saveFromParent(parent)
```

### VertexStorage

#### Usage
```kotlin 
val vertexStorage = graph.storage.vertexStorage
// or
val vertexStorage2 = VertexStorage()
vertexStorage2.save(v1)
vertexStorage2.save(v2)
// ...
vertexStorage2.save(vn)

vertexStorageX.forEach{ vertex -> doSomething(vertex) }
```

```
header v size 3 
header v bbox 0.1 1.0 11.0 2.0 
v 1 0.5 1.0 | 1 5 11
v 2 0.1 2.0
v 3 11.0 2.0 | 12
```

### EdgeStorage

#### Usage
```kotlin 
val edgeStorage = graph.storage.edgeStorage
// or
val vertexStorage = VertexStorage()
val edgeStorage2 = EdgeStorage(vertexStorage)
vertexStorage.save(v1)
vertexStorage.save(v2)
val edge1 = Edge(v1, v2)
edgeStorage2.save(edge1)

edgeStorageX.forEach { edge -> doSomething(edge) }
```

```
header e size 3 
e 1 1 2 0.0
e 2 2 3 0.0
e 3 3 1 0.0
```

### GraphStorage

```
header g v PATH\TO\VERTICES\file.rgv
header g e PATH\TO\EDGES\file.rge 
header g t PATH\TO\PATHS\file.rgt 
```

## Graph Providers
```kotlin
val graphProvider = BoundedGraphProvider(bbox, width, height)
val graph = graphProvider.build()

// Prune input by 20%
val prunedProvider = RandomPruneGraphProvider(graph, 0.20)

// Uses a cloned graph for pruning
val pruned1 = prunedProvider.build()
val pruned2 = prunedProvider.build()
// ...
val prunedN = prunedProvider.build()
```


## Nearest Neighbor

```kotlin 
val vertexStorage = graph.storage.vertexStorage

// O(|V|)
val start = vertexStorage.nearestNeighbor(ArrayPoint(0.5, 1.0))
val dest = vertexStorage.nearestNeighbor(ArrayPoint(10.5, -20))

// kNN checks : O(k*|V|)
val closest3 = vertexStorage.kNearestNeighbors(ArrayPoint(3.0, 4.0), 6)

```