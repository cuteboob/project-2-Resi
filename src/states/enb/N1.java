package states.enb;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import events.MovingInSwitchEvent;
import events.ReachingENBEvent;
import events.TypeD;
import states.State;

public class N1 extends State {
	//•	State N0: ENB is not full.
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
	public N1() {}
	public N1(Element sq) {
		this.elem = sq;
	}
	public void act (MovingInSwitchEvent ev) {
		Element elem = this.elem;
		// Chuyen 1 packet tu ENB sang EXP
		EntranceBuffer enb = (EntranceBuffer) elem;
		for(int i = 0; i < Constant.QUEUE_SIZE - 1; i++)
		{
			enb.allPackets[i] = enb.allPackets[i + 1];
		}
		enb.allPackets[Constant.QUEUE_SIZE - 1] = null;
		
		elem.state = new N0(enb);
		ExitBuffer exb = enb.way.exb;
		exb.state.act();
	}
	
//	public void getNextState(Element e) {
//		
//	}
}
