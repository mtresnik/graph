# graph

[![build status](https://github.com/mtresnik/graph/actions/workflows/gradle.yml/badge.svg)](https://github.com/mtresnik/graph/actions/workflows/gradle.yml/)
[![version](https://img.shields.io/badge/version-1.0.0-blue)]()
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://github.com/mtresnik/graph/blob/main/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-green.svg?style=flat-square)](https://makeapullrequest.com)

A novel Graph-Theory and Maze Solution library made for kotlin JVM.
<hr>


## Dependencies
> Please compile against [com.resnik.math:1.0.0](https://github.com/mtresnik/math/packages/1409888) and [com.resnik.intel:1.0.0](https://github.com/mtresnik/intel/packages/1423052) as well as this project.

## Getting Started

> This is a slightly different process to that of [com.resnik.intel](https://github.com/mtresnik/intel/).

## Maven

**~/.m2/settings.xml:**
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">
    ...
  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>
    ...
  <servers>
    <server>
      <id>github</id>
      <username>GITHUB_USERNAME</username>
      <password>GITHUB_PAT</password>
    </server>
  </servers>
</settings>
```

**pom.xml:**
```xml
<repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/mtresnik/math</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>

<repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/mtresnik/intel</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>

<repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/mtresnik/graph</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
...
<dependency>
    <groupId>com.resnik</groupId>
    <artifactId>math</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
    <groupId>com.resnik</groupId>
    <artifactId>intel</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
    <groupId>com.resnik</groupId>
    <artifactId>graph</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Gradle (groovy)

**~/.gradle/gradle.properties:**
```groovy
gpr.user=GITHUB_USERNAME
gpr.token=GITHUB_PAT
```

**build.gradle:**
```groovy
repositories {
    ...
    maven {
        url= uri("https://maven.pkg.github.com/mtresnik/math")
        credentials {
            // Runner stored in env, else stored in ~/.gradle/gradle.properties
            username = System.getenv("USERNAME") ?: findProperty("gpr.user") ?: "<GITHUB_USERNAME>"
            password = System.getenv("TOKEN") ?: findProperty("gpr.token")
        }
    }
    ...
    maven {
        url= uri("https://maven.pkg.github.com/mtresnik/intel")
        credentials {
            // Runner stored in env, else stored in ~/.gradle/gradle.properties
            username = System.getenv("USERNAME") ?: findProperty("gpr.user") ?: "<GITHUB_USERNAME>"
            password = System.getenv("TOKEN") ?: findProperty("gpr.token")
        }
    }
    maven {
        url= uri("https://maven.pkg.github.com/mtresnik/graph")
        credentials {
            // Runner stored in env, else stored in ~/.gradle/gradle.properties
            username = System.getenv("USERNAME") ?: findProperty("gpr.user") ?: "<GITHUB_USERNAME>"
            password = System.getenv("TOKEN") ?: findProperty("gpr.token")
        }
    }
}

dependencies {
    ...
    implementation group: 'com.resnik', name: 'math', version: '1.0.0'
    implementation group: 'com.resnik', name: 'intel', version: '1.0.0'
    implementation group: 'com.resnik', name: 'graph', version: '1.0.0'
    ...
}
```

<hr>

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