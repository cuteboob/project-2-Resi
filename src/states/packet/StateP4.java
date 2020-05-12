package states.packet;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.MovingInSwitchEvent;
import events.ReachingENBEvent;
import network.Node;
import network.Packet;
import network.Switch;
import network.host.Host;
import network.host.SourceQueue;
import simulator.Simulator;
import states.State;
import states.exb.X00;
import states.exb.X01;
import states.unidirectionalway.W0;

public class StateP4 extends State{
	public Packet p;
	public StateP4(Packet p, EntranceBuffer eNB) {
		// TODO Auto-generated constructor stub
		this.p = p;
		this.elem  = eNB;
	}
	//•	State P4: the packet is located at ENB of switch.
	
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
		System.out.println("P4");
		EntranceBuffer ENB = (EntranceBuffer) this.elem;
		ExitBuffer EXB = ENB.phyLayer.EXBs[0];
		Packet p = this.p;
		if ((check(ENB)) &&(EXB.state instanceof X01||EXB.state instanceof X00)) {
			Event e = new MovingInSwitchEvent(ENB, this.p);
			e.startTime = ENB.phyLayer.sim.time();
//			e.endTime = e.startTime + Constant.SWITCH_CYCLE;
			e.endTime = e.startTime + Constant.SWITCH_CYCLE;
			ENB.insertEvents(e);
		}
		}
	}
	
	public boolean check(EntranceBuffer ENB) {
		Node n = (Node) ENB.phyLayer.node;
		if (n instanceof Host) {
			for (int i=0;i<Constant.QUEUE_SIZE;i++) {
				if (ENB.allPackets[i] == this.p) {
					return true;
				}
			}
		}
		if (n instanceof Switch) {
			for (int i=0;i<Constant.QUEUE_SIZE;i++) {
				if (ENB.allPackets[i] == this.p) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void act(MovingInSwitchEvent ev) {	// E
		// chuyen tu ENB sang EXB
		Element e = this.elem;
		EntranceBuffer ENB = (EntranceBuffer) e;
		ExitBuffer EXB = ENB.phyLayer.EXBs[ev.EXBIndex];
		this.p.state = new StateP5(this.p, EXB);
		this.p.state.act();
	}
}
