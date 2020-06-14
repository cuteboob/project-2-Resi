package ztest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elements.Way;
import network.Link;
import network.Switch;
import network.Topology;
import network.host.DestinationNode;
import network.host.Host;
import rountingAlgorithm.DijkstrasAlgorithm;
import rountingAlgorithm.MaxFlowTest;
import rountingAlgorithm.rountingAlgorithm;
import weightedloadexperiment.ThroughputExperiment;

public class twoSwitchTwoHost {
	public static void main(String[] args) {
		
		    
		double s = Double.MAX_VALUE;
		System.out.println(s);
		Host sourceNode = new Host(100001);
		Switch sw = new Switch(100002);
		Switch sw1 = new Switch(100003);
		Host desNode = new DestinationNode(100004);
		
		Link l1 = new Link(sourceNode, sw);
		Way w1 = new Way(l1);
		
		Link l2 = new Link(sw, sw1);
		Way w2 = new Way(l2);
		
		Link l3 = new Link(sw1, sourceNode);
		Way w3 = new Way(l3);
		
		List<Integer> sources = new ArrayList<>();
        List<Integer> destination = new ArrayList<>();
        
        sources.add(sourceNode.id);
        destination.add(desNode.id);
        
        Map<Integer, Integer> traffic = new HashMap<>();
        traffic.put(sources.get(0), destination.get(0));
        
        Topology network = new Topology(sourceNode, sw, sw1, desNode, w1, w2, w3);
		
		ThroughputExperiment experiment = new ThroughputExperiment(network);
		
		rountingAlgorithm Dj = new DijkstrasAlgorithm();
		rountingAlgorithm MF = new MaxFlowTest();
		
		experiment.calThroughput(traffic, false, MF);
		
	}
}
