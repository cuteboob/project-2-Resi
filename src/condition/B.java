package condition;

import com.sun.xml.internal.ws.api.message.Packet;

import network.host.Host;
import states.exb.X00;
import states.exb.X01;
import states.sourcequeue.Sq2;

public class B {
	// can set trang thai cho cac EXB truoc
	public boolean satisfy(Host h, Packet p, long currentTime)  {
		if (h.physicalLayer.sq.state instanceof Sq2 &&
				(h.physicalLayer.EXBs[0].state instanceof X01
				||h.physicalLayer.EXBs[0].state instanceof X00)
				/*&& h la source node */ ) {
			return true;
		}
		return false;
	}
}
