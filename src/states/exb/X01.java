package states.exb;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import states.State;
import states.enb.N0;
import states.unidirectionalway.W0;
import states.unidirectionalway.W2;
import weightedloadexperiment.ThroughputExperiment;
import events.*;
import graph.NewGraph;
import network.Switch;
import network.Topology;
import rountingAlgorithm.DijkstrasAlgorithm;

public class X01 extends State {
	// • State X01: EXB is not full and able to transfer packet.
	/**
	 * Code thay
	 * 
	 */
//	@Override
//	public void act(LeavingSourceQueueEvent ev)
//	{
//		ExitBuffer exb = (ExitBuffer)elem;
//		if(exb.allPackets[0] != null)
//		{
//			Event leavingEXB = new LeavingEXBEvent(exb, exb.allPackets[0]);
//			exb.insertEvents(leavingEXB);
//		}
//	}

	/**
	 * Code tu viet
	 * 
	 */

//	public Event ancestorEvent;
//	public Element elem;
//	public void act(GenerationEvent ev) {}	//A
//	public void act(LeavingEXBEvent ev) {}	// C
//	public void act(LeavingSourceQueueEvent ev) {} //B
//	public void act(LeavingSwitchEvent ev) {}	//F
//	public void act(MovingInSwitchEvent ev) {}	// E
//	public void act(NotificationEvent ev) {}	// H
//	public void act(ReachingDestinationEvent ev) {}	// G
//	public void act(ReachingENBEvent ev) {}		// D
	public X01() {}
	public X01(Element sq) {
		this.elem = sq;
	}
	public void act() {
		//nut A co ket noi den Switch B
		System.out.println("X01");
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		Way w = exb.way;
		if (w.link.v instanceof Switch && w.enb.state instanceof N0 && w.state instanceof W2) {
			Event e = new NotificationEvent(exb);
			e.startTime = exb.phyLayer.sim.time();
			e.endTime = e.startTime + Constant.SWITCH_CYCLE;
			exb.insertEvents(e);
		}
	}
	
	public void act(LeavingSourceQueueEvent ev) {
		Element elem = this.elem;
		if (ev.EqualsType(TypeB.B3)) {
			// chuyen 1 packet tu source queue den EXB
			// thuc chat la them 1 packet vao EXB
			// xu ly bot source queue o excuse Event
			ExitBuffer exb = (ExitBuffer) elem;
			for (int i = 0; i < Constant.QUEUE_SIZE; i++)
				if (exb.allPackets[i] == null) {
					exb.allPackets[i] = ev.p;
					break;
				}
		}
		if (ev.EqualsType(TypeB.B4)) {
			ExitBuffer exb = (ExitBuffer) elem;
			Topology nw = ThroughputExperiment.network1;
			int u = nw.waysUandV.get(exb.way).get(0);
			int v = nw.waysUandV.get(exb.way).get(1);
			NewGraph g = (NewGraph) nw.getGraph();
			g.deleteWay(u, v);
			DijkstrasAlgorithm.Init();
			exb.state = new X11(exb);
			for (int i = 0; i < Constant.QUEUE_SIZE; i++)
				if (exb.allPackets[i] == null) {
					exb.allPackets[i] = ev.p;
					break;
				}
		}
	}

	public void act(MovingInSwitchEvent ev) {
		Element elem = this.elem;
		if (ev.EqualsType(TypeE.E1)) {
			// chuyen 1 packet tu source queue den EXB
			// thuc chat la them 1 packet vao EXB
			// xu ly bot ENB o excuse Event
			ExitBuffer exb = (ExitBuffer) elem;
			for (int i = 0; i < Constant.QUEUE_SIZE; i++)
				if (exb.allPackets[i] == null) {
					exb.allPackets[i] = ev.p;
					break;
				}
		}
		if (ev.EqualsType(TypeE.E2)) {
			// chuyen 1 packet tu source queue den EXB
			// EXB day
			ExitBuffer exb = (ExitBuffer) elem;
			Topology nw = ThroughputExperiment.network1;
			int u = nw.waysUandV.get(exb.way).get(0);
			int v = nw.waysUandV.get(exb.way).get(1);
			NewGraph g = (NewGraph) nw.getGraph();
			g.deleteWay(u, v);
			DijkstrasAlgorithm.Init();
			exb.state = new X11(exb);
			for (int i = 0; i < Constant.QUEUE_SIZE; i++)
				if (exb.allPackets[i] == null) {
					exb.allPackets[i] = ev.p;
					break;
				}
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
			exb.state = new X00(exb);
		}
		}
		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
			exb.allPackets[i] = exb.allPackets[i + 1];
		}
		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
	}
	
//	public void getNextState(Element e) {
//	
//	}
}
