package condition;

import network.Node;
import network.Packet;
import network.Switch;
import states.exb.X01;
import states.exb.X11;
import states.packet.StateP3;

public class G {
	public boolean satisfy(Node h, Node den, Packet p, long currentTime)  {
		if (p.state instanceof StateP3
			/*&&	den instanceof des node*/
				) {
			return true;
		}
		return false;
	}
}
