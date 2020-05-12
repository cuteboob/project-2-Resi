package condition;

import network.Node;
import network.Packet;
import states.packet.StateP3;

public class H {
	public boolean satisfy(Node h, Node den, Packet p, long currentTime)  {
		if (p.state instanceof StateP3
			/*&&	den instanceof des node*/
				) {
			return true;
		}
		return false;
	}
}
