package events;

import config.Constant;
import elements.Element;
import elements.ExitBuffer;
import elements.Way;
import network.Node;
import network.Packet;
import simulator.DiscreteEventSimulator;
import states.exb.X01;
import states.exb.X11;
import states.unidirectionalway.W0;

public class LeavingSwitchEvent extends Event {
	//Event dai dien cho su kien loai (F): goi tin roi khoi EXB cua Switch de di len tren LINK
	
	public LeavingSwitchEvent(Element elem, Packet p)
	{
		this.elem = elem;
		this.p = p;
		this.pid = p.id;
	}
	/**
	 * Code version 1
	 */
	public void execute() {
		ExitBuffer EXB = (ExitBuffer) elem;
		Way w = EXB.way;

		if (EXB.phyLayer.sim.time() == this.endTime) {
			p.acting = false;
			elem.removeExecutedEvent(this);
			DiscreteEventSimulator sim = (DiscreteEventSimulator) EXB.phyLayer.sim;
			sim.deleteEventFromAllEvent(this);
			
//			F: Nếu EXB co goi, EXB ở trạng thái X01 hoặc X11 và unidirectional way(link)
//			ở trạng thái W0. 
//			Sự kiện xảy ra ở Switch, bắt đầu khi P nằm top EXB và kết thúc sau SWITCH_CYCLE
			
			// EXB co the o trang thai X01 hoac X11
			// tu X01->X00 hoac X11->X00
			Packet p = this.p;
			
			// if(elem instanceof SourceQueue)
			// lay Packet p tu SourceQueue
			if (p == null)
				return;
			
				// p o trang thai P3
			p.state.act(this);
				
			EXB.state.act(this);
				// tu W0->W1
			w.state.act(this);
		}
		
		else if (EXB.phyLayer.sim.time() == this.startTime) {
			System.out.println("Event F");
			this.startTime = this.endTime;
			p.acting = true;
		}
		
		// Loai bo Packet o EXB de truyen
		
		// Tam thoi coi Start time = End Time
		
		
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
//	}
}
