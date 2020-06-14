package ztest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.Graph;
import graph.NewGraph;
import network.Switch;
import network.Topology;
import rountingAlgorithm.DijkstrasAlgorithm;
import rountingAlgorithm.MaxFlowTest;
import rountingAlgorithm.rountingAlgorithm;
import weightedloadexperiment.ThroughputExperiment;

public class finalNetwork {
	public static ArrayList<Double> ketqua  = new ArrayList<Double>(); 
	
	/**
	 * MF là MaxFlow cải thiện từ DijkstrasAlgorithm
	 * Dj là DijkstrasAlgorithm (định tuyến lựa chọn đường đi ngắn nhất)
	 */
	public static String ThuatToan = "MF";
	public static Integer LanLap = 20;
	
	public static void main(String[] args) {
	for (int z=0;z<finalNetwork.LanLap;z++) {
		int V = 36;
		ArrayList<Integer> HostIDs = new ArrayList<Integer>();
		ArrayList<Integer> SwitchIDs = new ArrayList<Integer>();
		for (int i=1;i<=16;i++) {
			HostIDs.add(i-1);
		}
		
		for (int i=17;i<=36;i++) {
			SwitchIDs.add(i-1);
		}
		
		List<Integer>[] adj = (List<Integer>[]) new List[V];
        for (int v = 1; v <= V; v++) {
            adj[v-1] = new ArrayList<Integer>();
        }
		
		NewGraph g = new NewGraph(V, adj, HostIDs, SwitchIDs);
		for (int i=1;i<=16;i++) {
			g.addEdge(i-1, (i+1)/2 + 16-1);
		}
		
		g.addEdge(17-1, 26-1);
		g.addEdge(18-1, 25-1);
		g.addEdge(19-1, 28-1);
		g.addEdge(20-1, 27-1);
		g.addEdge(21-1, 30-1);
		g.addEdge(22-1, 29-1);
		g.addEdge(23-1, 32-1);
		g.addEdge(24-1, 31-1);
		g.addEdge(17-1, 25-1);
		g.addEdge(18-1, 26-1);
		g.addEdge(19-1, 27-1);
		g.addEdge(20-1, 28-1);
		g.addEdge(21-1, 29-1);
		g.addEdge(22-1, 30-1);
		g.addEdge(23-1, 31-1);
		g.addEdge(24-1, 32-1);
		
		//33
		g.addEdge(33-1, 25-1);
		g.addEdge(33-1, 27-1);
		g.addEdge(33-1, 29-1);
		g.addEdge(33-1, 31-1);
		
		//34
		g.addEdge(34-1, 25-1);
		g.addEdge(34-1, 27-1);
		g.addEdge(34-1, 29-1);
		g.addEdge(34-1, 31-1);
		
		//35
		g.addEdge(35-1, 26-1);
		g.addEdge(35-1, 28-1);
		g.addEdge(35-1, 30-1);
		g.addEdge(35-1, 32-1);
		
		//36
		g.addEdge(36-1, 26-1);
		g.addEdge(36-1, 28-1);
		g.addEdge(36-1, 30-1);
		g.addEdge(36-1, 32-1);
		
		Topology network = new Topology(g);
		List<Integer> sources = new ArrayList<>();
        List<Integer> destination = new ArrayList<>();
        
        Collections.shuffle(HostIDs);
        
        for (int i = 0; i<HostIDs.size()/2;i++) {
        	sources = HostIDs.subList(0, HostIDs.size() / 2);
        	destination = HostIDs.subList(HostIDs.size() / 2, HostIDs.size());
        }
        
        Map<Integer, Integer> traffic = new HashMap<>();
        
        for (int i=0;i<8;i++) {
        	traffic.put(sources.get(i), destination.get(i));
        }
		
		ThroughputExperiment experiment = new ThroughputExperiment(network);
		rountingAlgorithm rA = new rountingAlgorithm();
		switch (ThuatToan) {
		case "MF":
			rA = new DijkstrasAlgorithm();
			break;
		case "Dj":
			rA = new MaxFlowTest();
			break;
		}
		
		// Dj, MF là thuật toán định tuyến
		experiment.calThroughput(traffic, false, rA);
	}
		System.out.println(finalNetwork.ketqua);
	}
}
