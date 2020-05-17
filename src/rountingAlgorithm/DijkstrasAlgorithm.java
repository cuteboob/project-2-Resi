package rountingAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.NewGraph;
import network.Switch;
import network.Topology;
import network.layers.NetworkLayer;
import weightedloadexperiment.ThroughputExperiment;

public class DijkstrasAlgorithm {

	private static final int NO_PARENT = -1;

	// Function that implements Dijkstra's
	// single source shortest path
	// algorithm for a graph represented
	// using adjacency matrix
	// representation
	private static void dijkstra(int[][] adjacencyMatrix, int startVertex, int endVertex) {
		int nVertices = adjacencyMatrix[0].length;

		// shortestDistances[i] will hold the
		// shortest distance from src to i
		int[] shortestDistances = new int[nVertices];

		// added[i] will true if vertex i is
		// included / in shortest path tree
		// or shortest distance from src to
		// i is finalized
		boolean[] added = new boolean[nVertices];

		// Initialize all distances as
		// INFINITE and added[] as false
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}

		// Distance of source vertex from
		// itself is always 0
		shortestDistances[startVertex] = 0;

		// Parent array to store shortest
		// path tree
		int[] parents = new int[nVertices];

		// The starting vertex does not
		// have a parent
		parents[startVertex] = NO_PARENT;

		// Find shortest path for all
		// vertices
		for (int i = 1; i < nVertices; i++) {

			// Pick the minimum distance vertex
			// from the set of vertices not yet
			// processed. nearestVertex is
			// always equal to startNode in
			// first iteration.
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			// Mark the picked vertex as
			// processed
			added[nearestVertex] = true;

			// Update dist value of the
			// adjacent vertices of the
			// picked vertex.
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}

		printSolution(startVertex, shortestDistances, parents, endVertex);
	}

	// A utility function to print
	// the constructed distances
	// array and shortest paths
	public static void printSolution(int startVertex, int[] distances, int[] parents, int endVertex) {

		Topology nw = ThroughputExperiment.network1;
		
		int nVertices = distances.length;
		int vertexIndex = endVertex;
		if (vertexIndex != startVertex) {
//			System.out.print("\n" + startVertex + " -> ");
//			System.out.print(vertexIndex + " \t\t ");
//			System.out.print(distances[vertexIndex] + "\t\t");
			printPath(vertexIndex, parents, startVertex, nw, endVertex);
		}

	}

	// Function to print shortest path
	// from source to currentVertex
	// using parents array
	private static void printPath(int currentVertex, int[] parents, int startVertex, Topology nw, int endVertex) {

		// Base case : Source node has
		// been processed
		if (currentVertex == NO_PARENT) {
			return;
		}
		printPath(parents[currentVertex], parents, startVertex, nw, endVertex);
//		System.out.println("");
		if (parents[currentVertex]>startVertex) {
//			System.out.println(currentVertex + " " + parents[currentVertex]);
			// Switch (parents[currentVertex]) them EXBIndex den switch currentVertex
			nw.getSwitchById(parents[currentVertex]).networkLayer.RoutingTable.put(endVertex, currentVertex);
		}
	}

	// Driver Code
	public static void Init() {

		NewGraph g = (NewGraph) ThroughputExperiment.network1.getGraph();
		Topology nw = ThroughputExperiment.network1;
		List<Switch> switchs = nw.getSwitches();
		for (Switch sw: switchs) {
			sw.networkLayer.RoutingTable = new HashMap<Integer, Integer>();
			// la destination va EXBIndex
		}
		int V = g.getV();
		int[][] adjacencyMatrix = new int[V][V];

		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				adjacencyMatrix[i][j] = 0;
			}
		}
		for (int i = 0; i < V; i++) {
			for (int j : g.adj(i)) {
				adjacencyMatrix[i][j] = 1;
			}
		}
		Map<Integer, Integer> trafficPattern = ThroughputExperiment.trafficPattern;
		for (int i = 0; i < V; i++) {
			if (trafficPattern.containsKey(i)) {
				dijkstra(adjacencyMatrix, i, trafficPattern.get(i));
			}
		}

	}
}