package states.packet;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.LeavingSwitchEvent;
import events.MovingInSwitchEvent;
import network.Node;
import network.Packet;
import network.Switch;
import network.host.Host;
import simulator.Simulator;
import states.State;
import states.exb.X00;
import states.exb.X01;
import states.exb.X11;
import states.unidirectionalway.W0;

public class StateP5 extends State {
	public Packet p;
	public StateP5(Packet p, ExitBuffer eXB) {
		// TODO Auto-generated constructor stub
		this.p = p;
		this.elem  = eXB;
	}
	//•	State P5: the packet is located at EXB of switch.
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
		System.out.println("P5");
		ExitBuffer EXB = (ExitBuffer) this.elem;
		Way w = EXB.way;
		if (check(EXB)&&(EXB.state instanceof X01||
				EXB.state instanceof X11)&&(w.state instanceof W0)) {
			Event e = new LeavingSwitchEvent(EXB, this.p);		// event F
			e.startTime = EXB.phyLayer.sim.time();
			e.endTime = e.startTime + Constant.SWITCH_CYCLE;	
//			e.endTime = e.startTime;
			EXB.insertEvents(e);
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
	
	public void act(LeavingSwitchEvent ev) {	// F
		// chuyen tu ENB sang EXB
		ExitBuffer EXB = (ExitBuffer) elem;
		Way w = EXB.way;

		this.p.state = new StateP3(this.p, w);
		this.p.state.act();
	}
}
