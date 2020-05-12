package condition;

import network.Node;
import network.Packet;
import network.Switch;
import states.exb.X00;
import states.exb.X01;
import states.exb.X11;

public class F {
	public boolean satisfy(Node h, Node den, Packet p, long currentTime)  {
		if (h.physicalLayer.EXBs[0].allPackets[0] != null&&
				(h.physicalLayer.EXBs[0].state instanceof X01||
				h.physicalLayer.EXBs[0].state instanceof X11)&&
				/* link o trang thai W0 */
				h instanceof Switch) {
			return true;
		}
		return false;
	}
}
