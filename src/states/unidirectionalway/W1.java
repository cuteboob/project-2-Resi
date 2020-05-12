package states.unidirectionalway;

import elements.Element;
import elements.Way;
import events.GenerationEvent;
import events.ReachingDestinationEvent;
import events.ReachingENBEvent;
import events.TypeD;
import states.State;

public class W1 extends State {
	//•	State W1: the way has a packet.
//	public void act(GenerationEvent ev) {}	//A
//	public void act(LeavingEXBEvent ev) {}	// C
//	public void act(LeavingSourceQueueEvent ev) {} //B
//	public void act(LeavingSwitchEvent ev) {}	//F
//	public void act(MovingInSwitchEvent ev) {}	// E
//	public void act(NotificationEvent ev) {}	// H
//	public void act(ReachingDestinationEvent ev) {}	// G
//	public void act(ReachingENBEvent ev) {}		// D
	public W1() {}
	public W1(Element sq) {
		this.elem = sq;
	}
	
	
	public void act(ReachingENBEvent ev) {
		// Chac la den End Time event moi thuc hien cai nay
		// Tam thoi cho thoi gian bat dau bang 
		// thoi gian ket thuc
		// goi y sua: thay doi ham excuse(), bang endTime moi
		// thuc hien xoa Events
		this.elem = elem;
		Way w = (Way) elem;
		if (ev.EqualsType(TypeD.D1)) {
			w.p = null;	// khong chua goi nua
			w.state = new W0(w);
		}
		if (ev.EqualsType(TypeD.D2)) {
			w.p = null;
			w.state = new W2(w);
		}
	}
	
	public void act(ReachingDestinationEvent ev) {
		this.elem = elem;
		Way w = (Way) elem;
		w.p = null;
		w.state = new W0(w);
	}
}
