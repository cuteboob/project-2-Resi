package elements;

import java.util.ArrayList;

import config.Constant;
import events.Event;
import network.Link;
import network.Packet;
import network.Switch;
import network.host.Host;
import states.unidirectionalway.W0;

public class Way extends Element {
	public ArrayList<Event> allEvents = new ArrayList<Event>();
	public int from;
	public int to;
	public Link link;
	public Packet p;
	public ExitBuffer exb;
	public EntranceBuffer enb;

	public Way(Link link) {
		this.link = link;
		from = link.u.id;
		to = link.v.id;
		this.state = new W0();
	}

	public void InitWay() {
		
		for (int i = 0; i < Constant.HOST_LINK; i++) {
			if (link.u instanceof Host) {
			if (null == link.u.physicalLayer.EXBs[i].way) {
				link.u.physicalLayer.EXBs[i].way = this;
				exb = link.u.physicalLayer.EXBs[i];
				break;
				}
			}
		}
		for (int i = 0; i < Constant.SWITCH_LINK; i++) {
			if (link.u instanceof Switch) {
			if (null == link.u.physicalLayer.EXBs[i].way) {
				link.u.physicalLayer.EXBs[i].way = this;
				exb = link.u.physicalLayer.EXBs[i];
				break;
			}
			}
		}
		
		for (int i = 0; i < Constant.SWITCH_LINK; i++) {
				if (link.v instanceof Switch) {
					if (null == link.v.physicalLayer.ENBs[i].way) {
						link.v.physicalLayer.ENBs[i].way = this;
						enb = link.v.physicalLayer.ENBs[i];
					break;
				}
			}
		}
	}

	public void insertEvents(Event ev) {
		double endTime = ev.endTime;
		int i;
		if (allEvents == null) {
			allEvents = new ArrayList<Event>();
			allEvents.add(ev);
			return;
		}
		if (allEvents.size() == 0) {
			allEvents.add(ev);
			return;
		}
		boolean found = false;
		for (i = 0; i < allEvents.size() && !found; i++) {
			if (allEvents.get(i).endTime > endTime) {
				found = true;
				break;
			}
		}
		allEvents.add(i, ev);
		updateSoonestEndTime();
	}

	public void removeExecutedEvent(Event ev) {
		this.allEvents.remove(ev);
	}
}
