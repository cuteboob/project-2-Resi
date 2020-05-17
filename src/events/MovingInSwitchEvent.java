package events;

import java.util.ArrayList;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import network.Node;
import network.Packet;
import network.Switch;
import network.host.Host;
import network.host.SourceQueue;
import simulator.DiscreteEventSimulator;
import states.enb.N0;
import states.enb.N1;
import states.exb.X00;
import states.exb.X01;
import states.exb.X10;
import states.exb.X11;
import states.packet.StateP2;
import states.sourcequeue.Sq2;
import states.unidirectionalway.W0;
import states.unidirectionalway.W1;



public class MovingInSwitchEvent extends Event {
	ArrayList<TypeE> types = new ArrayList<TypeE>();
	// Event dai dien cho su kien loai (E): goi tin roi khoi ENB cua Switch de sang
	// EXB
	// Elem la ENB
	public int EXBIndex;
	public MovingInSwitchEvent(Element elem, Packet p) {
		this.elem = elem;
		this.p = p;
		this.pid = p.id;
	}
	
	public void InitTypeE(Element elem, int EXBIndex) {
		ArrayList<TypeE> ts = new ArrayList<TypeE>();
		ts.add(TypeE.E);
		EntranceBuffer ENB = (EntranceBuffer) elem;
		
		ExitBuffer EXB = ENB.phyLayer.EXBs[EXBIndex];
		
//		if (EXB.state instanceof X00||
//				EXB.state instanceof X01) {
//			//ENB not full
//			ts.add(TypeE.E1);
//		}
//		if (EXB.state instanceof X10||
//				EXB.state instanceof X11) {
//			//ENB full
//			ts.add(TypeE.E2);
//		}
		
		int dem = 0;
		for (int i=0;i< Constant.QUEUE_SIZE;i++) {
			if (EXB.allPackets[i]!=null) dem++;
		}
			
		//D1: not full
		//D2: full
		
		if (dem+1 != Constant.QUEUE_SIZE) {
			ts.add(TypeE.E1);
		}
		else {
			ts.add(TypeE.E2);
		}
		
		this.types = ts;
	}
	
	public boolean EqualsType(TypeE ts) {
		ArrayList<TypeE> types = this.types;
		for (TypeE type: types) {
			if (type == ts) return true;
		}
		return false;
	}
	
	/**
	 * Code version 1
	 */
	// elem la ENB
	public void execute() {
		EntranceBuffer ENB = (EntranceBuffer) elem;

		if (ENB.phyLayer.sim.time() == this.endTime) {
			p.acting = false;
			elem.removeExecutedEvent(this);
			DiscreteEventSimulator sim = (DiscreteEventSimulator) ENB.phyLayer.sim;
			sim.deleteEventFromAllEvent(this);
			ExitBuffer EXB = null;
			int EXBIndex = 0;
			if (ENB.phyLayer.node instanceof Host) {
				EXB = ENB.phyLayer.EXBs[0];
				InitTypeE(elem, 0);
			}
			else {
				Switch sw = (Switch)ENB.phyLayer.node;
				EXBIndex = sw.networkLayer.route(p);
				this.EXBIndex = EXBIndex;
				EXB = ENB.phyLayer.EXBs[EXBIndex];
				InitTypeE(elem, EXBIndex);
			}
			Packet p = this.p;
			
			
			if (p==null)
				return;
			
			Way w = EXB.way;
			
			if (EXB.state instanceof X00 || EXB.state instanceof X01) {
				ENB.state.act(this);
				EXB.state.act(this);
				System.out.println("EXB Index: " + EXBIndex);
			// p o trang thai StateP (o trong Way)
				p.state.act(this);
//				System.out.println("_");
			}
		}

		else if (ENB.phyLayer.sim.time() == this.startTime) {
			System.out.println("Event E, packet id: " + p.id);
			this.startTime = this.endTime;
			p.acting = true;
		}
		}
	
	/**
	 * Code version 0
	 */
//	@Override
//	public void execute() {
//		elem.removeExecutedEvent(this);
//		ExitBuffer exb = (ExitBuffer) elem;// Kiem tra xem EXB co cho trong hay khong?
//		EntranceBuffer enb = (EntranceBuffer) elem;
//		// int index = exb.indexOfEmpty();
//		// if(index < Constant.QUEUE_SIZE
//		// vong lap for thuc hien viec dich chuyen cac goi tin len truoc
//		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
//			enb.allPackets[i] = enb.allPackets[i + 1];
//		}
//		// slot cuoi cung trong bo dem cua ENB phai la null (khong chua goi tin nao)
//		enb.allPackets[Constant.QUEUE_SIZE - 1] = null;
//
//		for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
//			if (exb.allPackets[i] == null) {
//				exb.allPackets[i] = enb.allPackets[0];
//				break;
//			}
//		}
//	}
}
