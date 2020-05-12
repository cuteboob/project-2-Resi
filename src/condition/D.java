package condition;

import network.Node;
import network.Packet;
import network.Switch;
import network.host.Host;
import states.exb.X01;
import states.exb.X11;
import states.packet.StateP3;

public class D {
	public boolean satisfy(Node h, Node den, Packet p, long currentTime)  {
		if (p.state instanceof StateP3 &&
				/* link o trang thai W0 &&*/
				den instanceof Switch) {
			return true;
		}
		return false;
	}
}
