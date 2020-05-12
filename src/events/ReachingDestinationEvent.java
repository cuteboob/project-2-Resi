package events;

import config.Constant;
import elements.Element;
import elements.ExitBuffer;
import elements.Way;
import network.Packet;

public class ReachingDestinationEvent extends Event {
	//Event dai dien cho su kien loai (G): goi tin den duoc nut dich
	// elem o day la Way
	public ReachingDestinationEvent(Element elem, Packet p)
	{
		this.elem = elem;
		this.p = p;
		this.pid = p.id;
	}
	
	/**
	 * Code version 1
	 */
	public void execute()
	{
		Way w = (Way) elem;
		if (w.link.u.physicalLayer.sim.time() == this.endTime) {
			p.acting = false;
			elem.removeExecutedEvent(this);
			
			w.state.act(this);
			ExitBuffer exb = w.exb;
			exb.state.act(this);
			Packet p = this.p;
			if (p==null)
				return;
			
			// p o trang thai StateP3 (o trong Way)
			p.state.act(this);
		}
		else if (w.link.u.physicalLayer.sim.time() == this.startTime) {
			System.out.println("Event G");
			this.startTime = this.endTime;
			p.acting = true;
		}
		
	}
	
	/**
	 * Code version 0
	 */
//	@Override
//	public void execute()
//	{
//		elem.removeExecutedEvent(this);
//		//go bo su kien nay ra khoi danh sach cac su kien
//		ExitBuffer exb = (ExitBuffer)elem;
//		//vong lap for thuc hien viec dich chuyen cac goi tin len truoc
//		for(int i = 0; i < Constant.QUEUE_SIZE - 1; i++)
//		{
//			exb.allPackets[i] = exb.allPackets[i + 1];
//		}
//		//slot cuoi cung trong bo dem cua EXB phai la null (khong chua goi tin nao)
//		exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
//	}
}
