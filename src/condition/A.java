package condition;

import com.sun.xml.internal.ws.api.message.Packet;

import network.host.Host;

public class A {
	public boolean satisfy(Host h, Packet p, long currentTime)  {
		if (/*traffic tạo gói*/ true && h instanceof Host
				/* la 1 sourceNode */) {
			return true;
		}
		return false;
	}
}
