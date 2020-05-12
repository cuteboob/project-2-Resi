package states.sourcequeue;


import elements.Element;
import events.GenerationEvent;
import events.LeavingSourceQueueEvent;
import events.TypeB;
import network.Packet;
import network.host.SourceQueue;
import states.State;

public class Sq2 extends State {

	public Sq2() {}
	public Sq2(Element sq) {
		this.elem = sq;
	}

	/**
	 * Code cua thay
	 */
//	//•	State Sq2: source queue is not empty.
//	public Sq2(SourceQueue e)
//	{
//		super(e);
//	}
	
	
	
	
	//•	State Sq2: source queue is not empty.
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
	
	
	public void act (LeavingSourceQueueEvent ev) {
		Element elem = this.elem;
		if (ev.EqualsType(TypeB.B1)) {	// can dieu kien o excuse Event
			// Chuyen 1 packet tu ENB sang EXP	
			SourceQueue sq = (SourceQueue) elem;
			sq.allPackets.remove(0);
			elem.state = new Sq1(sq);
		}
		if (ev.EqualsType(TypeB.B2)) {
			SourceQueue sq = (SourceQueue) elem;
			sq.allPackets.remove(0);
		}
	}
	
	public void act (GenerationEvent ev) {
		Element elem = this.elem;
		SourceQueue sq = (SourceQueue) elem;
		Packet p = sq.dequeue(ev.startTime);
		this.p = p;
	}
	
//	public void getNextState(Element e) {
//		
//	}
	
}
