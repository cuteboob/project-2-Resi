package states.packet;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.LeavingEXBEvent;
import events.ReachingDestinationEvent;
import events.ReachingENBEvent;
import network.Node;
import network.Packet;
import network.host.SourceQueue;
import simulator.Simulator;
import states.State;
import states.exb.X01;
import states.exb.X11;
import states.unidirectionalway.W0;

public class StateP3 extends State {
	public Packet p;

	public StateP3(Packet p, Way w) {
		// TODO Auto-generated constructor stub
		this.p = p;
		this.elem = w;
	}
	//•	State P3: the packet is moved in a unidirectional way.
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
		System.out.println("P3");
		Way w = (Way) elem;
		SourceQueue sq = w.link.v.physicalLayer.sq;
		
		Packet p = this.p;
		// Dieu kien event G
		if (p.state instanceof StateP3
					// nut tiep theo la dich
					// tam thoi cho ca 3 sq, ENB va EXB = null
				 && sq != null
				) {
			Event e = new ReachingDestinationEvent(w, this.p);
			e.startTime = sq.phyLayer.sim.time();
			e.endTime = e.startTime + Constant.HOST_TO_SWITCH_LENGTH / Constant.PROPAGATION_VELOCITY
					+ Constant.PACKET_SIZE / Constant.LINK_BANDWIDTH;
			w.insertEvents(e);
		}
		else if (sq==null) {
			EntranceBuffer ENB = w.link.v.physicalLayer.ENBs[0];
			if (p.state instanceof StateP3 && w.state instanceof W0 && 
					sq == null	//nut tiep theo la Switch
								// (chua sua phan khoi tao Node)
				) {
				Event e = new ReachingENBEvent(w, this.p);
				e.startTime = ENB.phyLayer.sim.time();
//				e.endTime = e.startTime; // + thoi gian chuyen goi
				e.endTime = e.startTime + Constant.HOST_TO_SWITCH_LENGTH / Constant.PROPAGATION_VELOCITY
								+ Constant.PACKET_SIZE / Constant.LINK_BANDWIDTH;
				w.insertEvents(e);
			}
		}
		}
		
	}
	
	public void act(ReachingENBEvent ev) {		// D
		// chuyen tu trang thai P3 ( tren Way) sang
		// P4 (ENB cua switch)
		// Evemnt D: Chuyen packet tu Way den ENB
		Element e = this.elem;
		Way w = (Way) e;
		Node v = w.link.v; // node dich
		EntranceBuffer ENB = v.physicalLayer.ENBs[0];
//		w.p = null;
		
		this.p.state = new StateP4(this.p, ENB);
		this.p.state.act();
	}
	
	public void act(ReachingDestinationEvent ev) {	// G
		// goi duoc nhan boi nut dich
		this.p.state = new StateP6(elem);	// chuyen den node dich
	}	
	
}
