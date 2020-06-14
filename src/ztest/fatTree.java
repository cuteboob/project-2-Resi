package ztest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import custom.fattree.FatTreeGraph;
import graph.Graph;
import graph.NewGraph;
import network.Switch;
import network.Topology;
import rountingAlgorithm.DijkstrasAlgorithm;
import rountingAlgorithm.MaxFlowTest;
import rountingAlgorithm.rountingAlgorithm;
import weightedloadexperiment.ThroughputExperiment;

public class fatTree {
	public static int dem = 0;
	public static void main(String[] args) {
		FatTreeGraph fatTreeGraph = new FatTreeGraph(4);
		System.out.println(dem);
		List<Integer> HostIDs = fatTreeGraph.hosts();
		List<Integer> SwitchIDs = fatTreeGraph.switches();
		
		Topology network = new Topology(fatTreeGraph);
		List<Integer> sources = new ArrayList<>();
        List<Integer> destination = new ArrayList<>();
        
//        Collections.shuffle(HostIDs);
        
        for (int i = 0; i<HostIDs.size()/2;i++) {
        	sources = HostIDs.subList(0, HostIDs.size() / 2);
        	destination = HostIDs.subList(HostIDs.size() / 2, HostIDs.size());
        }
        
        Map<Integer, Integer> traffic = new HashMap<>();
        
        for (int i=0;i<8;i++) {
        	traffic.put(sources.get(i), destination.get(i));
        }
        
        ThroughputExperiment experiment = new ThroughputExperiment(network);
		
		rountingAlgorithm Dj = new DijkstrasAlgorithm();
		rountingAlgorithm MF = new MaxFlowTest();
		
		experiment.calThroughput(traffic, false, MF);
//		experiment.calThroughput(traffic, false, Dj);
	}
}
