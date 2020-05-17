package events;

import config.Constant;
import elements.Element;
import elements.ExitBuffer;
import elements.Way;
import network.Node;
import network.Packet;
import network.host.SourceQueue;
import simulator.DiscreteEventSimulator;

public class LeavingEXBEvent extends Event {
	// Event dai dien cho su kien loai (C): goi tin roi khoi EXB

	public LeavingEXBEvent(Element elem, Packet p) {
		this.elem = elem;
		this.p = p;
		this.pid = p.id;
	}

	/**
	 * Code version 1
	 */
	public void execute() {
		System.out.println("Event C");
		// Loai bo EXB de truyen
		elem.removeExecutedEvent(this);
		ExitBuffer EXB = (ExitBuffer) elem;
		DiscreteEventSimulator sim = (DiscreteEventSimulator) EXB.phyLayer.sim;
		sim.deleteEventFromAllEvent(this);
		
		// EXB co the o trang thai X01 hoac X11
		// tu X01->X00 hoac X11->X00
		
		Way w = EXB.way;
		// tu W0->W1

		
		Packet p = this.p;
		
		// if(elem instanceof SourceQueue)
		// lay Packet p tu SourceQueue
		if (p == null)
			return;
		
		EXB.state.act(this);
		
		// p o trang thai P2
		p.state.act(this);
		
		w.state.act(this);
	}

	/**
	 * Code version 0
	 */
//	@Override
//	public void execute()
//	{
//		elem.removeExecutedEvent(this);
//		//go bo su kien nay ra khoi danh sach cac su kien
//		
//		ExitBuffer exb = (ExitBuffer)elem;
//		//vong lap for thuc hien viec dich chuyen cac goi tin len truoc
//		for(int i = 0; i < Constant.QUEUE_SIZE - 1; i++)
//		{
//			exb.allPackets[i] = exb.allPackets[i + 1];
//		}
//		//slot cuoi cung trong bo dem cua EXB phai la null (khong chua goi tin nao)
//		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
//		
//	}
}
