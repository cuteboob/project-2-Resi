package network.layers;

import config.Constant;
import elements.*;
import network.Device;
import network.Switch;
import network.host.*;
import simulator.Simulator;
import states.enb.N0;
import states.exb.X01;
import states.sourcequeue.Sq1;

public class PhysicalLayer {
	public ExitBuffer[] EXBs;
	public EntranceBuffer[] ENBs;
	public SourceQueue sq;
	public Simulator sim;
	public Device node;
	
	public PhysicalLayer(Host host)
	{
		EXBs = new ExitBuffer[Constant.QUEUE_SIZE];
		sq = new SourceQueue(host.id);
					// Goi se co id dich va nguon thay vi
					// SourceQueue nhu trong Class
		for (int i=0;i<Constant.QUEUE_SIZE;i++) {
			EXBs[i] = new ExitBuffer();
			EXBs[i].phyLayer = this;
		}
		sq.phyLayer = this;
		this.node = host;
	}

	public PhysicalLayer(Switch switch1) {
		EXBs = new ExitBuffer[Constant.SWITCH_LINK];
		for (int i=0;i<Constant.SWITCH_LINK;i++) {
			EXBs[i] = new ExitBuffer();
			EXBs[i].phyLayer = this;
		}
		ENBs = new EntranceBuffer[Constant.SWITCH_LINK];
		for (int i=0;i<Constant.SWITCH_LINK;i++) {
			ENBs[i] = new EntranceBuffer();
			ENBs[i].phyLayer = this;
		}
		this.node = switch1;
	}
	
	/*public void addLocationOfEvents()
	{
		sim.addLocationOfEvents(node);
	}*/
}
