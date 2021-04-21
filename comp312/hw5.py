# hw5.py
# Your name
# due: April 20, 11:55pm

from collections import deque # regular queue


class Graph(object):
    """
    An undirected simple graph of n nodes, V = {0, 1, ..., n-1} represented using an adj. list
    """
    def __init__(self, n):
        """
        n: int, a positive integer
        """
        self.n = n # number of nodes
        self.m = 0 # number of edges
        self.node_to_neis = [] # lists that maps every node to its list of neighbors
        for i in range(n):
            self.node_to_neis.append([])

    def number_of_nodes(self):
        return self.n

    def number_of_edges(self):
        return self.m

    def is_edge(self, u, v):
        """
        u, v: int, node labels
        precondition: 0 <= u,v < self.n
        returns: bool, True iff an edge (u,v) exists
        """
        return v in self.node_to_neis[u]

    def add_edge(self, u, v):
        """
        u, v: int, node labels
        precondition: 0 <= u,v < self.n
        returns: None. Adds an undirected (u,v) if u!=v and they are not already connected
        """
        if u != v and not self.is_edge(u, v):
            self.m = self.m + 1
            self.node_to_neis[u].append(v) # add (u,v)
            self.node_to_neis[v].append(u) # add (v,u)

    def remove_edge(self, u, v):
        """
        u, v: int, node labels
        precondition: 0 <= u,v < self.n
        returns: None. Removes the undirected edge (u,v) is it exists
        """
        if self.is_edge(u, v):
            self.m = self.m - 1
            self.node_to_neis[u].remove(v) # remove(u,v)
            self.node_to_neis[v].remove(u) # remove(v,u)

    def get_neighbors(self, u):
        """
        u: int, node label
        precondition: 0 <= u < self.n
        returns: list, a list of nodes v such that (u,v) exists
        """
        return self.node_to_neis[u]

def count_shortest_paths(graph, s):
    """
    graph: Graph
    s: int, source node label
    returns: list, a list xs where xs[i] is the number of shortest paths
    from s to i
    """
    n = graph.number_of_nodes()
    dist = [ -1 for i in range(n) ]
    dist[s] = 1
    Q = deque([s])
    neighborsDict = {} # dictionary for keeping track of neighbors, and number of shortest paths for each neighbor
    while Q:
        v = Q.popleft()
        for w in graph.get_neighbors(v):
            if dist[w] == -1:
                if not neighborsDict.has_key(w): # accumulates shortest paths if multiple nodes have the same neighbor
                    neighborsDict[w] = dist[v]
                else:
                    neighborsDict[w] += dist[v]
        if not Q: # Once the queue is empty the current level has been fully traversed and we can append all the neighbors to the queue
            for (neighbor, numShortPaths) in neighborsDict.items():
                Q.append(neighbor)
                dist[neighbor] = numShortPaths
            neighborsDict = {}
    dist[s] = 0
    return dist
# This algorithm works because if multiple nodes have the same neighbor on the same bredth level of the graph
# then this neighbor can be reached through equal amount of steps from each root node, i.e multiple shortest paths

g = Graph(9)
g.add_edge(0,1)
g.add_edge(0,2)
g.add_edge(0,3)
g.add_edge(2,4)
g.add_edge(3,4)
g.add_edge(4,5)
g.add_edge(5,6)
g.add_edge(5,7)
g.add_edge(6,8)
g.add_edge(7,8)
print(count_shortest_paths(g, 0))


g = Graph(12)
g.add_edge(0,1)
g.add_edge(0,2)
g.add_edge(0,3)
g.add_edge(1,4)
g.add_edge(2,4)
g.add_edge(3,4)
g.add_edge(4,5)
g.add_edge(5,6)
g.add_edge(5,7)
g.add_edge(6,8)
g.add_edge(7,11)
g.add_edge(11,8)
g.add_edge(0,9)
g.add_edge(9,10)
g.add_edge(10,4)
print(count_shortest_paths(g, 0))


class WeightedGraph(object):
    """
    An undirected, weighted, simple graph of n nodes, V = {0, 1, ..., n-1} represented using an adj. list
    """
    def __init__(self, n):
        """
        n: int, a positive integer
        """
        self.n = n # number of nodes
        self.m = 0 # number of edges
        self.node_to_neis = [] # lists that maps every node to its list of neighbors
        for i in range(n):
            self.node_to_neis.append([])

    def number_of_nodes(self):
        return self.n

    def number_of_edges(self):
        return self.m

    def is_edge(self, u, v):
        """
        u, v: int, node labels
        precondition: 0 <= u,v < self.n
        returns: bool, True iff an edge (u,v) exists
        """
        for node,weight in self.node_to_neis[u]:
            if node == v:
                return True
        return False

    def add_edge(self, u, v, w):
        """
        u, v: int, node labels
        w: float, edge weight
        precondition: 0 <= u,v < self.n
        returns: None. Adds an undirected edge (u,v) with weight w if u!=v and they are not already connected
        """

        if u != v and not self.is_edge(u, v):
            self.m = self.m + 1
            self.node_to_neis[u].append((v,w))
            self.node_to_neis[v].append((u,w))

    def increaseNeighbors(self):
        self.node_to_neis.append([])
        self.n += 1

    def remove_edge(self, u, v):
        """
        u, v: int, node labels
        precondition: 0 <= u,v < self.n
        returns: None. Removes the undirected edge (u,v) is it exists
        """
        for node,w in self.node_to_neis[u]:
            if node == v:
                self.node_to_neis[u].remove((v,w))
                self.node_to_neis[v].remove((u,w))
                self.m = self.m - 1

    def get_neighbors(self, u):
        """
        u: int, node label
        precondition: 0 <= u < self.n
        returns: list, a list of nodes v such that (u,v) exists
        """
        return self.node_to_neis[u] # this returns a list of tuples



def weighted_shortest_paths(graph, s):
    """
    graph: WeightedGraph, an undirected weighted graph with positive integer weights
    s: int, source node label
    returns: list, a list of distances, dist[i] = length of weighted shortest path from s to i
    """
    n = graph.number_of_nodes()
    for node in range(0,n):
        currentNeighbors = filter(lambda c: node < c[0] < n, graph.get_neighbors(node))
        # filters through the edges that are in the original graph, and haven't been traversed
        for neighbor in currentNeighbors:
            expand_weight(graph, node, neighbor[0], neighbor[1])

    return BFS(graph, s, n)
    """
    I am measuring the complexity of this solution by the number of nodes traversed in the graph. The first
    part of the problem expands all the edges so all of the edges in the graph have length one. This will go
    through all the nodes in the graph once, plus the extra nodes needed to expand edges so it will have complexity
    O(W) where W is the total weight of the all the edges of the graph. BFS will traverse of a graph of W nodes, hitting
    every node once, so it will have complexity O(W). Overall this algorithm will have O(2W) = O(W) complexity

    """
def expand_weight(graph, u, v, weight):
    if weight == 1:
        return # Doesn't need to expanded
    else:
        n = graph.number_of_nodes()
        lastnode = u
        for i in range(weight-1):
            # Creates a new node, expands the size of the graph by 1, and hooks up previous node to that node
            graph.increaseNeighbors()
            graph.add_edge(lastnode, n+i, 1)
            lastnode = n+i
        # hooks up last new node to the end of the weighted edge, and removes original weighted edge between u and v 
        graph.add_edge(lastnode,v,1)
        graph.remove_edge(u,v)

# BFS implemented in class
def BFS(graph, s, length):
    """
    graph: Graph
    s: int, source node label
    returns: list, a list of distances, dist[i] = length of shortest path from s to i
    """
    n = graph.number_of_nodes()
    dist = [ -1 for i in range(n) ]
    dist[s] = 0
    Q = deque([s])
    while Q:
        v = Q.popleft()
        for w in graph.get_neighbors(v):
            if dist[w[0]] == -1:
                dist[w[0]] = dist[v] + 1
                Q.append(w[0])
    return dist[0:length]

g = WeightedGraph(4)
g.add_edge(0,1,6)
g.add_edge(1,3,1)
g.add_edge(0,2,1)
g.add_edge(2,3,1)
print(weighted_shortest_paths(g, 0))

g = WeightedGraph(4)
g.add_edge(0,1,4)
g.add_edge(1,3,1)
g.add_edge(0,2,4)
g.add_edge(2,3,1)
print(weighted_shortest_paths(g, 0))
