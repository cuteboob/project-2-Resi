package states.packet;

import config.Constant;
import elements.Element;
import elements.Way;
import network.Packet;
import simulator.DiscreteEventSimulator;
import states.State;

public class StateP6 extends State {
	//•	State P6: the packet is received by the destination node.
	public static int dendich = 0;
	public StateP6(Element elem, Packet p) {
		dendich++;
		System.out.println("P6");
		System.out.println("Goi tin " + p.id + " da den, so den dich: " + dendich);
		System.out.println("____________________");
		Way w = (Way) elem;
		DiscreteEventSimulator sim = (DiscreteEventSimulator) w.link.v.physicalLayer.sim;
		try {
			sim.receivedPacket[(int) (sim.getTime() / Constant.EXPERIMENT_INTERVAL + 1)]++;
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
