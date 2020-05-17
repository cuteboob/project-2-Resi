package events;

import elements.Element;
import elements.ExitBuffer;
import elements.Way;
import network.Packet;
import simulator.DiscreteEventSimulator;

public class NotificationEvent extends Event {
	//Event dai dien cho su kien loai (H): mot Switch bao cho hang xom cua no rang ENB cua no da trong
	
	// elem o day la EXB
	// Tat ca cac EXB deu se thuc hien act() de kiem tra dieu
	// kien event nay
	public NotificationEvent(Element elem) {
		this.elem = elem;
	}
	
	public void execute() {
		ExitBuffer EXB = (ExitBuffer) elem;

		if (EXB.phyLayer.sim.time() == this.endTime) {
			Way w = EXB.way;
			elem.removeExecutedEvent(this);
			DiscreteEventSimulator sim = (DiscreteEventSimulator) EXB.phyLayer.sim;
			sim.deleteEventFromAllEvent(this);
			EXB.state.act(this);
			w.state.act(this);
			System.out.println("End Event H");
					
		} else if (EXB.phyLayer.sim.time() == this.startTime) {
			System.out.println("Event H");
			this.startTime  = this.endTime;
		}
		
		
	}
}
