package states.unidirectionalway;

import elements.Element;
import elements.Way;
import events.LeavingEXBEvent;
import events.LeavingSwitchEvent;
import states.State;

public class W0 extends State {
	//•	State W0: the way has no packet and it is able to transfer one.
	
//	public void act(GenerationEvent ev) {}	//A
//	public void act(LeavingEXBEvent ev) {}	// C
//	public void act(LeavingSourceQueueEvent ev) {} //B
//	public void act(LeavingSwitchEvent ev) {}	//F
//	public void act(MovingInSwitchEvent ev) {}	// E
//	public void act(NotificationEvent ev) {}	// H
//	public void act(ReachingDestinationEvent ev) {}	// G
//	public void act(ReachingENBEvent ev) {}		// D
	
	public W0() {}
	public W0(Element sq) {
		this.elem = sq;
	}
	
	public void act(LeavingEXBEvent ev) { // C
		// W0 sang W1 (khong co goi -> co 1 goi)
		this.elem = elem;
		Way w = (Way) elem;
		w.p = ev.p;
		w.state = new W1(w);
	}	
	
	public void act(LeavingSwitchEvent ev) {
		this.elem = elem;
		Way w = (Way) elem;
		w.p = ev.p;
		w.state = new W1(w);
	}
}
