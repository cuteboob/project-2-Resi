package events;

import java.util.ArrayList;

import config.Constant;
import elements.Element;
import elements.ExitBuffer;
import network.Node;
import network.Packet;
import network.host.SourceQueue;
import simulator.DiscreteEventSimulator;
import states.exb.X00;
import states.exb.X01;
import states.exb.X10;
import states.exb.X11;
import states.packet.StateP1;
import states.packet.StateP2;
import states.sourcequeue.Sq1;
import states.sourcequeue.Sq2;

public class LeavingSourceQueueEvent extends Event {
	ArrayList<TypeB> types = new ArrayList<TypeB>();
	// Event dai dien cho su kien loai (B): goi tin roi khoi Source Queue
	
	// Quy uoc elem la diem chua packet
	// elem la SourceQueue
	// chuyen den EXB
	public LeavingSourceQueueEvent(Element elem, Packet p) {
		this.elem = elem;
		this.p = p;
		InitTypeB(elem);
	}

	public void InitTypeB(Element elem) {
		ArrayList<TypeB> ts = new ArrayList<TypeB>();
		ts.add(TypeB.B);
		SourceQueue sq = (SourceQueue) elem;
		if (sq.state instanceof Sq2) {
			if (sq.allPackets.size()==1) {
				ts.add(TypeB.B1);
			}
			if (sq.allPackets.size()>1) {
				ts.add(TypeB.B2);
			}
		}
		ExitBuffer EXB = sq.phyLayer.EXBs[0];
		// co 3 EXB (cach xu li?????)
		// co le lien quan den dinh tuyen
//		if (EXB.state instanceof X00||EXB.state instanceof X01) {
//			ts.add(TypeB.B3);
//		}
//		if (EXB.state instanceof X10||EXB.state instanceof X11) {
//			ts.add(TypeB.B4);
//		}
		
		int dem = 0;
		for (int i=0;i< Constant.QUEUE_SIZE;i++) {
			if (EXB.allPackets[i]!=null) dem++;
		}
			
		//D1: not full
		//D2: full
		
		if (dem+1 != Constant.QUEUE_SIZE) {
			ts.add(TypeB.B3);
		}
		else {
			ts.add(TypeB.B4);
		}
		
		this.types = ts;
	}
	
	public boolean EqualsType(TypeB ts) {
		ArrayList<TypeB> types = this.types;
		for (TypeB type: types) {
			if (type == ts) return true;
		}
		return false;
	}
	/**
	 * Code version 2
	 */
	public void execute() {
		System.out.println("Event B");
		// chuyen tu SourceQueue den EXB cua nut nguon
		elem.removeExecutedEvent(this);
		
		SourceQueue sq = (SourceQueue) elem;
		ExitBuffer EXB = sq.phyLayer.EXBs[0];
		DiscreteEventSimulator sim = (DiscreteEventSimulator) sq.phyLayer.sim;
		sim.deleteEventFromAllEvent(this);
		Packet p = this.p;
		
		// if(elem instanceof SourceQueue)
		// lay Packet p tu SourceQueue
		if (p == null)
			return;
		
		sq.state.act(this);		// o trang thai P2
		// Thuc hien event B1 va B2
		EXB.state.act(this);
		// p o trang thai P1
		
		p.state.act(this);
		
		
	}

	/**
	 * Code version 1
	 */
//	@Override
//	public void execute()
//	{
//		elem.removeExecutedEvent(this);
//		SourceQueue sQueue = (SourceQueue)elem;	// sai, phai sua lai
//		ExitBuffer exb = sQueue.phyLayer.EXBs[0];//Kiem tra xem EXB co cho trong hay khong?
//		//int index = exb.indexOfEmpty();
//		//if(index < Constant.QUEUE_SIZE
//		if(((exb.state instanceof X00 ) || (exb.state instanceof X01))
//				&& (sQueue.state instanceof Sq2)
//				)//neu EXB con cho trong
//		{
//			Packet p = sQueue.allPackets.remove(0);
//			exb.insertPacket(p);
//			p.state = new StateP2();
//			p.state.act(this);
//			
//			int index = exb.indexOfEmpty();
//			if(index == Constant.QUEUE_SIZE)
//			{
//				if(exb.state instanceof X00) { exb.state = new X10(); }
//				if(exb.state instanceof X01) { exb.state = new X11(); }
//			}
//			exb.state.elem = exb;
//			exb.state.act(this);
//			//To be continued...
//			//Event e = new LeavingSourceQueueEvent(sQueue);
//			//e.startTime = sQueue.phyLayer.sim.time();
//			//e.endTime = e.startTime;
//			//e.pid = this.p.id;
//			//sQueue.insertEvents(e);//chen them su kien moi vao
//		}
//	}
}
