package states.enb;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import events.Event;
import events.GenerationEvent;
import events.LeavingEXBEvent;
import events.LeavingSourceQueueEvent;
import events.LeavingSwitchEvent;
import events.MovingInSwitchEvent;
import events.NotificationEvent;
import events.ReachingDestinationEvent;
import events.ReachingENBEvent;
import events.TypeD;
import network.Packet;
import states.State;

public class N0 extends State {
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
	public N0() {}
	public N0(Element sq) {
		this.elem = sq;
	}
	public void act (ReachingENBEvent ev) {
		Element elem = this.elem;
		if (ev.EqualsType(TypeD.D2)) {
			elem.state = new N1(elem);
			// chuyen sang trang thai N1;
			// Cap nhat mot so thuoc tinh cua trang thai
			
			// ENB cua (Switch) duoc chuyen them 1 packet, ENB day 
			EntranceBuffer enb = (EntranceBuffer) elem;
			for (int i = 0; i < Constant.QUEUE_SIZE; i++)
				if (enb.allPackets[i]==null) {
					enb.allPackets[i] = ev.p;
					break;
			}
		}
		if (ev.EqualsType(TypeD.D1)) {
			// Link chuyen packet den ENB cua note trung gian tiep
			// theo, ENB chua full
			EntranceBuffer enb = (EntranceBuffer) elem;
			for (int i = 0; i < Constant.QUEUE_SIZE; i++)
				if (enb.allPackets[i]==null) {
					enb.allPackets[i] = ev.p;
					break;
				}
		}
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
		ExitBuffer exb = enb.way.exb;
//		exb.state.act();
		System.out.println(".");
	}
	
//	public void getNextState(Element e) {
//		
//	}
	
}
