package events;

import java.util.ArrayList;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import network.Packet;
import simulator.DiscreteEventSimulator;
import states.enb.N0;
import states.enb.N1;


public class ReachingENBEvent extends Event {
	ArrayList<TypeD> types = new ArrayList<TypeD>();
	//Event dai dien cho su kien loai (D): goi tin den duoc ENB cua nut tiep theo 
	public ReachingENBEvent(Element elem, Packet p)
	{
		this.elem = elem;
		this.p = p;
		this.pid = p.id;
		InitTypeD(elem);
	}
	
	public void InitTypeD(Element elem) {
		ArrayList<TypeD> ts = new ArrayList<TypeD>();
		ts.add(TypeD.D);
		Way w = (Way) elem;
		EntranceBuffer ENB = w.enb;
		int dem = 0;
		for (int i=0;i< Constant.QUEUE_SIZE;i++) {
			if (ENB.allPackets[i]!=null) dem++;
		}
			
		//D1: not full
		//D2: full
		
		if (dem+1 != Constant.QUEUE_SIZE) {
			ts.add(TypeD.D1);
		}
		else {
			ts.add(TypeD.D2);
		}
		
//		if ((dem == Constant.QUEUE_SIZE&& Constant.QUEUE_SIZE == 1) || (dem + 1 == Constant.QUEUE_SIZE && Constant.QUEUE_SIZE != 1))
//		{
//			ts.add(TypeD.D2);
//		}
//		else {
//			//ENB full
//			ts.add(TypeD.D1);
//		}
		this.types = ts;
	}
	
	public boolean EqualsType(TypeD ts) {
		ArrayList<TypeD> types = this.types;
		for (TypeD type: types) {
			if (type == ts) return true;
		}
		return false;
	}
	/**
	 * Code version 1
	 */
	public void execute() {
		Way w = (Way) elem;
		EntranceBuffer ENB = w.enb;
		
		if (ENB.phyLayer.sim.time()==this.endTime) {
			p.acting = false;
			elem.removeExecutedEvent(this);
			DiscreteEventSimulator sim = (DiscreteEventSimulator) ENB.phyLayer.sim;
			sim.deleteEventFromAllEvent(this);
			Packet p = this.p;
			if (p==null)
				return;
			w.state.act(this);
			ENB.state.act(this);
			p.state.act(this);
		}
		else if (ENB.phyLayer.sim.time()==this.startTime) {
			InitTypeD(elem);
			System.out.println("Event D");
			this.startTime = this.endTime;
			p.acting = true;
		}
		
		
		// p o trang thai StateP3 (o trong Way)

		
		
	}
	
	
	/**
	 * Code version 0
	 */
//	public void execute() {
//		elem.removeExecutedEvent(this);
//		//go bo su kien nay ra khoi danh sach cac su kien
//
//		EntranceBuffer enb = (EntranceBuffer)elem;
//		//vong lap for thuc hien viec dich chuyen cac goi tin len truoc
//		for(int i = 0; i < Constant.QUEUE_SIZE - 1; i++)
//		{
//			if (enb.allPackets[i]==null) {
//				enb.allPackets[i]=this.p;
//				break;
//			}
//		}
//		//slot cuoi cung trong bo dem cua EXB phai la null (khong chua goi tin nao)
//		
//	}
}
