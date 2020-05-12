package states.unidirectionalway;

import elements.Element;
import elements.Way;
import events.NotificationEvent;
import states.State;

public class W2 extends State {
	//•	State W2: the way has no packet but it is unable to transfer.
//	public void act(GenerationEvent ev) {}	//A
//	public void act(LeavingEXBEvent ev) {}	// C
//	public void act(LeavingSourceQueueEvent ev) {} //B
//	public void act(LeavingSwitchEvent ev) {}	//F
//	public void act(MovingInSwitchEvent ev) {}	// E
//	public void act(NotificationEvent ev) {}	// H
//	public void act(ReachingDestinationEvent ev) {}	// G
//	public void act(ReachingENBEvent ev) {}		// D
	public W2() {}
	public W2(Element sq) {
		this.elem = sq;
	}
	
	public void act(NotificationEvent ev) {
		this.elem = elem;
		Way w = (Way) elem;
		w.state = new W0(w);
	}
}
