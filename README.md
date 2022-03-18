# graph

A novel graph and maze solution library made for kotlin JVM.

## Graph Algorithms

* Breadth-First Search
* Depth-First Search
* Dijkstra
* A* Search

## Serialization

Resnik Graphs can be Serialized and Cloned.

Saving an Identifyable Item to an ItemizedLongStorable will set an auto-incementing ID (long) to the Item and store it within its internal collection.

### Vertices
```
header v size 3 
header v bbox 0.1 1.0 11.0 2.0 
v 1 0.5 1.0 | 1 5 11
v 2 0.1 2.0
v 3 11.0 2.0 | 12
```

### Edges
```
header e size 3 
e 1 1 2 0.0
e 2 2 3 0.0
e 3 3 1 0.0
```

### Graph
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