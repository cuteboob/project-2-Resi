package states.exb;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.LeavingEXBEvent;
import events.LeavingSourceQueueEvent;
import events.LeavingSwitchEvent;
import events.MovingInSwitchEvent;
import events.NotificationEvent;
import events.ReachingDestinationEvent;
import events.ReachingENBEvent;
import events.TypeB;
import events.TypeD;
import events.TypeE;
import simulator.DiscreteEventSimulator;
import states.State;
import states.enb.N0;
import states.enb.N1;
import states.unidirectionalway.W0;
import states.unidirectionalway.W2;

public class X10 extends State {
	// • State X10: EXB is full and unable to transfer packet (due to the next ENB
	// is full).
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
	public X10() {}
	public X10(Element sq) {
		this.elem = sq;
	}
	public void act() {
		//nut A co ket noi den Switch B
		System.out.println("X10");
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
			DiscreteEventSimulator sim = (DiscreteEventSimulator) exb.phyLayer.sim;
			sim.allEvents.add(e);
		}
	}
	
	public void act (ReachingENBEvent ev) {
		Element elem = this.elem;
		if (ev.EqualsType(TypeD.D1)) {
			// bo 1 goi khoi EXB
			// chuyen trang thai sang X11 ( day va co the chuyen goi)
			// thuc hien chuyen goi luon (sau nay co the sua)
			ExitBuffer exb = (ExitBuffer) elem;
			exb.state = new X11(exb);
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


	public void act(LeavingEXBEvent ev) {
		// Event C
		// EXB loai bo 1 goi de truyen (xay ra o sourceNode)
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
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
		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
			exb.allPackets[i] = exb.allPackets[i + 1];
		}
		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
	}
	
	public void act(ReachingDestinationEvent ev) {
		// Event G: Link chuyen den goi dich
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		exb.state = new X11(exb);
		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
			exb.allPackets[i] = exb.allPackets[i + 1];
		}
		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
	}

	public void act(NotificationEvent ev) {
		Element elem = this.elem;
		ExitBuffer exb = (ExitBuffer) elem;
		exb.state = new X11(exb);
	}
//	public void getNextState(Element e) {
//	
//	}
}
