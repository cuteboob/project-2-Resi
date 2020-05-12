package condition;


import network.Link;
import network.Packet;
import network.host.Host;
import states.exb.X00;
import states.exb.X01;
import states.exb.X11;
import states.sourcequeue.Sq2;

public class C {
	public boolean satisfy(Host h, Packet p, long currentTime)  {
		if (/* link o trang thai W0 &&*/
				h.physicalLayer.EXBs[0].allPackets[0] == p &&
				(h.physicalLayer.EXBs[0].state instanceof X11
				||h.physicalLayer.EXBs[0].state instanceof X01)) {
			return true;
		}
		return false;
	}
}
