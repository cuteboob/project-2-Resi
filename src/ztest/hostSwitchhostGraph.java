package ztest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.NewGraph;
import network.Topology;
import weightedloadexperiment.ThroughputExperiment;

public class hostSwitchhostGraph {
	public static void main(String[] args) {
		int V = 3;
		ArrayList<Integer> HostIDs = new ArrayList<Integer>();
		ArrayList<Integer> SwitchIDs = new ArrayList<Integer>();
		HostIDs.add(0);
		SwitchIDs.add(1);
		HostIDs.add(2);
		List<Integer>[] adj = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
        
        NewGraph g = new NewGraph(V, adj, HostIDs, SwitchIDs);
        
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        
        
        Topology network = new Topology(g);
		List<Integer> sources = new ArrayList<>();
        List<Integer> destination = new ArrayList<>();
        
        sources.add(HostIDs.get(0));
        destination.add(HostIDs.get(1));
        
        Map<Integer, Integer> traffic = new HashMap<>();
        traffic.put(sources.get(0), destination.get(0));
		
		ThroughputExperiment experiment = new ThroughputExperiment(network);
		
		experiment.calThroughput(traffic, false);
	}
}
