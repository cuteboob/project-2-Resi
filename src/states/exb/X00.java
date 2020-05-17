package states.exb;

import java.util.ArrayList;
import java.util.List;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.LeavingSourceQueueEvent;
import events.MovingInSwitchEvent;
import events.NotificationEvent;
import events.ReachingDestinationEvent;
import events.ReachingENBEvent;
import events.TypeB;
import events.TypeD;
import events.TypeE;
import graph.Graph;
import graph.NewGraph;
import network.Topology;
import rountingAlgorithm.DijkstrasAlgorithm;
import simulator.DiscreteEventSimulator;
import states.State;
import states.enb.N0;
import states.enb.N1;
import states.unidirectionalway.W0;
import states.unidirectionalway.W2;
import weightedloadexperiment.ThroughputExperiment;

public class X00 extends State {
	// • State X00: EXB is not full and unable to transfer packet (due to the next
	// ENB is full
	// or this EXB is empty).

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
	public X00() {}
	public X00(Element sq) {
		this.elem = sq;
	}
	
	public void act() {
		//nut A co ket noi den Switch B
		System.out.println("X00");
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		Way w = exb.way;
		EntranceBuffer enb = w.enb;
		if (enb != null &&
				enb.state instanceof N0 &&
				w.state instanceof W2) {
			Event e = new NotificationEvent(exb);
			e.startTime = exb.phyLayer.sim.time();
//			e.endTime = e.startTime;
			e.endTime = e.startTime + Constant.SWITCH_CYCLE;
			exb.insertEvents(e);
			DiscreteEventSimulator sim = (DiscreteEventSimulator) exb.phyLayer.sim;
			sim.allEvents.add(e);
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
//			g.deleteWay(u, v);
//			DijkstrasAlgorithm.Init();
			exb.state = new X10(exb);
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
			// chuyen 1 packet tu ENB den EXB
			// thuc chat la them 1 packet vao EXB
			// xu ly bot ENB o excuse Event
			ExitBuffer exb = (ExitBuffer) elem;
			Topology nw = ThroughputExperiment.network1;
			int u = nw.waysUandV.get(exb.way).get(0);
			int v = nw.waysUandV.get(exb.way).get(1);
			NewGraph g = (NewGraph) nw.getGraph();
//			g.deleteWay(u, v);
//			DijkstrasAlgorithm.Init();
			exb.state = new X10(exb);
			for (int i = 0; i < Constant.QUEUE_SIZE; i++)
				if (exb.allPackets[i] == null) {
					exb.allPackets[i] = ev.p;
					break;
				}
		}
	}

	public void act(ReachingENBEvent ev) {
		Element elem = this.elem;
		if (ev.EqualsType(TypeD.D1)) {
			// bo 1 goi khoi EXB
			// chuyen trang thai sang X01 
			// thuc hien chuyen goi luon (sau nay co the sua)
			ExitBuffer exb = (ExitBuffer) elem;
			
			exb.state = new X01(exb);
			
			for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
				exb.allPackets[i] = exb.allPackets[i + 1];
			}
			exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
		}
		if (ev.EqualsType(TypeD.D2)) {
			// trang thai nay se khong chuyen goi
			// goi event D2 khong thuc hien dieu gi ca
			// DO NOTHING
		}
	}

	public void act(ReachingDestinationEvent ev) {
		// Event G: Link chuyen den goi dich
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		exb.state = new X01(exb);
		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
			exb.allPackets[i] = exb.allPackets[i + 1];
		}
		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
	}

	public void act(NotificationEvent ev) {
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		exb.state = new X01(exb);
	}
//	public void getNextState(Element e) {
//	
//	}
}
