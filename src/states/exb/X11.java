package states.exb;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.LeavingEXBEvent;
import events.LeavingSwitchEvent;
import events.NotificationEvent;
import graph.NewGraph;
import network.Topology;
import rountingAlgorithm.DijkstrasAlgorithm;
import states.State;
import states.enb.N0;
import states.unidirectionalway.W0;
import states.unidirectionalway.W2;
import weightedloadexperiment.ThroughputExperiment;

public class X11 extends State {
	//•	State X11: EXB is full and able to transfer packet.
	public X11() {}
	public X11(Element sq) {
		this.elem = sq;
	}
	public void act() {
		//nut A co ket noi den Switch B
		System.out.println("X11");
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		Way w = exb.way;
		EntranceBuffer enb = w.enb;
		if (enb != null &&
				enb.state instanceof N0 &&
				w.state instanceof W2) {
			Event e = new NotificationEvent(exb);
			e.startTime = exb.phyLayer.sim.time();
			e.endTime = e.startTime + Constant.SWITCH_CYCLE;
			exb.insertEvents(e);
		}
	}
	
	public void act(LeavingEXBEvent ev) {
		// Event C
		// EXB loai bo 1 goi de truyen (xay ra o sourceNode)
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		EntranceBuffer enb = exb.way.enb;
		int dem = 0;
		for (int i=0;i< Constant.QUEUE_SIZE;i++) {
			if (enb.allPackets[i]!=null) dem++;
		}
		if (dem + 1 == Constant.QUEUE_SIZE) {
			Topology nw = ThroughputExperiment.network1;
			int u = nw.waysUandV.get(exb.way).get(0);
			int v = nw.waysUandV.get(exb.way).get(1);
			NewGraph g = (NewGraph) nw.getGraph();
			g.addWay(u, v);
			DijkstrasAlgorithm.Init();
			exb.state = new X00(exb);
		}
		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
			exb.allPackets[i] = exb.allPackets[i + 1];
		}
		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
	}
	
	public void act(LeavingSwitchEvent ev) {
		// Event F
		// EXB loai bo 1 goi de truyen (xay ra o Switch)
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		int dem = 0;
		EntranceBuffer enb = exb.way.enb;
		if (enb!=null) {
			for (int i=0;i< Constant.QUEUE_SIZE;i++) {
				if (enb.allPackets[i]!=null) dem++;
			}
			if (dem + 1 == Constant.QUEUE_SIZE) {
				Topology nw = ThroughputExperiment.network1;
				int u = nw.waysUandV.get(exb.way).get(0);
				int v = nw.waysUandV.get(exb.way).get(1);
				NewGraph g = (NewGraph) nw.getGraph();
				g.addWay(u, v);
				DijkstrasAlgorithm.Init();
				exb.state = new X00(exb);
			}
			}
		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
			exb.allPackets[i] = exb.allPackets[i + 1];
		}
		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
	}
}
