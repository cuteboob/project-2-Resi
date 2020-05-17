package events;

import elements.Element;
import elements.ExitBuffer;
import network.host.*;
import simulator.DiscreteEventSimulator;
import simulator.Simulator;
import network.Packet;
import states.exb.X00;
import states.exb.X01;
import states.packet.*;
import states.sourcequeue.Sq1;
import states.sourcequeue.Sq2;

public class GenerationEvent extends Event {
	//Event dai dien cho su kien loai (A): goi tin duoc sinh ra
	public int numSent = 0;		// ???????
	public int destination;
	public static int paid = 0;
	// sua lai bang host thay vi elem
	
	// truyen them int destination
	public GenerationEvent(Element elem, int destination) {
		this.elem = elem;
		this.destination = destination;
	}
	
	public GenerationEvent(Element elem) {
		this.elem = elem;
	}
	
	public GenerationEvent(Element elem, Packet p)
	{
		this.elem = elem;
		this.p = p;
		// tao packet o lenh excute, goi event.p luon
	}
	
	@Override
	// lien quan den CBR, sau mot khoang thoi gian se tu 
	// dong thuc thi
	// (Mau traffic toc do khong doi)
	public void execute()
	{
		//if(elem instanceof SourceQueue)
		{
			System.out.println("Event A");
			SourceQueue sq = (SourceQueue)elem;
			elem.removeExecutedEvent(this);	// khi thuc thi xoa event nay
			DiscreteEventSimulator sim = (DiscreteEventSimulator) sq.phyLayer.sim;
			sim.deleteEventFromAllEvent(this);
			
			
			
			
			// co the la Sq1 hoac Sq2
			if (sq.allPackets.size()<=10)
			 {
				sq.state.act(this);
				
				Packet p = sq.state.p;
							// lay Packet p tu SourceQueue
				
				if(p == null) return;
				paid++;
							// neu SourceQueue khong co packet
				p.setId(paid);	// numSent nay de lam gi???

				this.pid = p.id;
				p.setDestination(this.destination);

				
				p.state = new StateP1(sq, p);
						// PacketP1 o trong SourceQueue
				Simulator.PacketsAct.put(p,true);
				p.state.act(this);
			}
			
			
			
			/*
			 * Code cua thay o cho nay co thuc hien
			 * method act cua state nay
			 */
//			p.state.act(this);
			
			/*
			 * Thu khong thuc hien state nay
			 * Kiem tra dieu kien o Execute Event
			 */
//			ExitBuffer EXB = ((SourceQueue)elem).phyLayer.EXBs[0];
//			SourceQueue sq = (SourceQueue)elem;
			
			
			// phai dem vao act boi vi neu trong Event se phai thuc
			// hien 1 chuoi lien tuc, khong bi tac nghen
			// (copy cai ben duoi vao State
			// tiep theo thuc hien p.state.act(this);
//			if (sq.state instanceof Sq2 && 
//					(EXB.state instanceof X01 || EXB.state instanceof X00)) {
//				Event e = new LeavingSourceQueueEvent(sq, p);
//				e.startTime = sq.phyLayer.sim.time();
//				e.endTime = e.startTime;
//				sq.insertEvents(e);    // chen them su kien 
//				// moi vao SourceQueue
//			}
			

			
			/**
			 * Code thay
			 * chua biet co y nghia gi nen xoa tam
			 */
//			if(elem.state instanceof Sq1)//it means that elem is an instance of SourceQueue 
//			{
//				
////				elem.state = new Sq2((SourceQueue)elem);
//				/**
//				 * Code sua
//				 */
//				elem.state = new Sq2();
//				elem.state.act(this);
//			}
			
			
		}
	}
}
