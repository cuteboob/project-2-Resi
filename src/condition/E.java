package condition;

import network.Node;
import network.Packet;
import network.Switch;
import states.exb.X00;
import states.exb.X01;
import states.packet.StateP3;

public class E {
	public boolean satisfy(Node h, Node den, Packet p, long currentTime)  {
		if (h.physicalLayer.ENBs[0].allPackets[0] == p &&
				(den.physicalLayer.EXBs[0].state instanceof X01||
				den.physicalLayer.EXBs[0].state instanceof X00) 
				/*&&Switch chon goi P de di chuyen */) {
			return true;
		}
		return false;
	}
}
