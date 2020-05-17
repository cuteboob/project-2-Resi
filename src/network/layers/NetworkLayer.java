package network.layers;

import java.util.HashMap;
import java.util.Map;

import config.Constant;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import network.Node;
import network.Packet;
import network.Switch;
import network.Topology;
import weightedloadexperiment.ThroughputExperiment;

public class NetworkLayer {
	public Map<Integer, Integer> RoutingTable;	// La destination va EXBIndex
	public Node node;
	public void controlFlow() {}
	
	public NetworkLayer(Switch switch1) {
		this.node = switch1;
	}
	
	public int route(Packet p) {
		Topology nw = ThroughputExperiment.network1;
		
		int EXBIndex = 0;
		Switch sw = (Switch) node;
		for (int i=0;i<Constant.SWITCH_LINK;i++) {
			if (sw.physicalLayer.EXBs[i].way.to == RoutingTable.get(p.getDestination())) {
				EXBIndex = i;
				break;
			}
		}
		return EXBIndex;
	}
}
