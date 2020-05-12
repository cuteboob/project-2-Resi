package states;

import elements.Element;
import events.*;
import network.Packet;

public abstract class State {
	public Event ancestorEvent;
	public Element elem;
	public Packet p;
	
	
	public void act() {}
	public void act(GenerationEvent ev) {}
	public void act(LeavingEXBEvent ev) {}
	public void act(LeavingSourceQueueEvent ev) {}
	public void act(LeavingSwitchEvent ev) {}
	public void act(MovingInSwitchEvent ev) {}
	public void act(NotificationEvent ev) {}
	public void act(ReachingDestinationEvent ev) {}
	public void act(ReachingENBEvent ev) {}
	
	public void getNextState(Element e) {}
	
	public void check(Element e) {
		
	}
}