package states.packet;

import config.Constant;
import elements.Element;
import elements.Way;
import simulator.DiscreteEventSimulator;
import states.State;

public class StateP6 extends State {
	//•	State P6: the packet is received by the destination node.
	public StateP6(Element elem) {
		System.out.println("P6");
		System.out.println("Goi tin da den dich");
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
