package states.packet;

import config.Constant;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.LeavingEXBEvent;
import events.LeavingSourceQueueEvent;
import network.Node;
import network.Packet;
import network.Switch;
import network.host.Host;
import network.host.SourceQueue;
import simulator.DiscreteEventSimulator;
import simulator.Simulator;
import states.State;
import states.exb.X00;
import states.exb.X01;
import states.exb.X11;
import states.sourcequeue.Sq2;
import states.unidirectionalway.W0;

public class StateP2 extends State {
	public Packet p;
	
	public StateP2(Packet p, ExitBuffer eXBs) {
		this.p = p;
		this.elem = eXBs;
	}
	//•	State P2: the packet is located at EXB of the source node.

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
	public void act() {
		if (!p.acting) {
		Simulator.PacketsAct.replace(p, false);
		System.out.println("P2");
		ExitBuffer EXB = (ExitBuffer) elem;
		Way w = EXB.way;
		if (w.state instanceof W0&& check(EXB)
				&& (EXB.state instanceof X11 || EXB.state instanceof X01)) {
			Event e = new LeavingEXBEvent(EXB, this.p);
			e.startTime = EXB.phyLayer.sim.time();
			e.endTime = e.startTime;
			EXB.insertEvents(e);
			DiscreteEventSimulator sim = (DiscreteEventSimulator) EXB.phyLayer.sim;
			sim.allEvents.add(e);
		}
		}
	}
	
	public boolean check(ExitBuffer EXB) {
		Node n = (Node) EXB.phyLayer.node;
		if (n instanceof Host) {
			for (int i=0;i<Constant.QUEUE_SIZE;i++) {
				if (EXB.allPackets[i] == this.p) {
					return true;
				}
			}
		}
		if (n instanceof Switch) {
			for (int i=0;i<Constant.QUEUE_SIZE;i++) {
				if (EXB.allPackets[i] == this.p) {
					return true;
				}
			}
		}
		return false;
	}
	public void act(LeavingEXBEvent ev) { // C
		// chuyen tu P2 sang P3 : goi duoc di chuyen tren 
		// 1 unidirectional way
		ExitBuffer EXB = (ExitBuffer) ev.elem;
		Way w = EXB.way;
//		w.p = ev.p;
		this.p.state = new StateP3(this.p, w);
		this.p.state.act();
	}	
	
}
