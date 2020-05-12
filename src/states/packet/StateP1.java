package states.packet;

import states.State;
import states.exb.X00;
import states.exb.X01;
import states.sourcequeue.Sq2;

import java.util.HashMap;
import java.util.Map;

import config.Constant;
import elements.Element;
import elements.ExitBuffer;
import events.Event;
import events.GenerationEvent;
import events.LeavingSourceQueueEvent;
import network.Node;
import network.Packet;
import network.host.SourceQueue;
import simulator.Simulator;

public class StateP1 extends State {
	// • State P1: the packet is generated

	public Packet p;

	// khoi tao StateP1 Packet
	public StateP1(SourceQueue sq) {
		this.elem = sq;
	}

	public StateP1(SourceQueue sq, Packet p) {
		this.elem = sq;
		this.p = p;
	}

	public StateP1(SourceQueue sq, Packet p, GenerationEvent ev) {
		this.elem = sq;
		this.p = p;
		this.ancestorEvent = ev;
	}

//	public Event ancestorEvent;
//	public Element elem;
//	public void act(GenerationEvent ev) {}	//A
//	public void act(LeavingEXBEvent ev) {}	// C
//	public void act(LeavingSourceQueueEvent ev) {} //B
//	public void act(LeavingSwitchEvent ev) {}	//F
//	public void act(MovingInSwitchEvent ev) {}	// E
//	public void act(NotificationEvent ev) {}	// H
//	public void act(ReachingDestinationEvent ev) {}	// G
//	public void act(ReachingENBEvent ev) {}		// D

	/**
	 * Code thay o day la Event A,
	 */
//	@Override
//	public void act(GenerationEvent ev) // Event A;
//	{
//		SourceQueue sQueue = (SourceQueue) elem;
//		if (sQueue == null || this.p == null) {
//			return;
//		}
//		if (sQueue.allPackets == null) {
//			System.out.println("Empty packet in source queue error!\n");
//			return;
//		}
//		if (sQueue.allPackets.get(0) == this.p) {
//			ExitBuffer exb = sQueue.phyLayer.EXBs[0];// Kiem tra xem EXB co cho trong hay khong?
//			// int index = exb.indexOfEmpty();
//			// if(index < Constant.QUEUE_SIZE)
//			// neu EXB con cho trong
//			if (exb.state instanceof X00 || exb.state instanceof X01) {
//				Event e = new LeavingSourceQueueEvent(sQueue, this.p);
//				e.startTime = sQueue.phyLayer.sim.time();
//				e.endTime = e.startTime;
//				e.pid = this.p.id;
//				sQueue.insertEvents(e);// chen them su kien moi vao
//			}
//			/*
//			 * boolean successfullyInserted = exb.insertPacket(this.p);
//			 * if(successfullyInserted) { sQueue.allPackets.remove(0); }
//			 */
//		}
//	}

	/**
	 * Neu qua moi vong lap co Packet bi tac nghen, khong thuc hien event B thi
	 * nhung vong lap sau se thuc hien method **act** khong tham so
	 */
	public void act() {
		if (!p.acting) {
			Simulator.PacketsAct.replace(this.p, false);
			Map<Packet, Boolean> m = Simulator.PacketsAct;
			System.out.println(Simulator.PacketsAct.get(this.p));
			System.out.println("P1");
			SourceQueue sq = (SourceQueue) elem;
			ExitBuffer EXB = sq.phyLayer.EXBs[0];
			if (sq.state instanceof Sq2 && (EXB.state instanceof X01 || EXB.state instanceof X00)) {
				System.out.println(sq.allEvents.size());
				Event e = new LeavingSourceQueueEvent(sq, this.p);
				// Event B
				e.startTime = sq.phyLayer.sim.time();
				e.endTime = e.startTime;
				sq.insertEvents(e);
				System.out.println(sq.allEvents.size());
				System.out.println(sq.allEvents.size());
			}
		}
	}

	/**
	 * Thu sua theo cach minh, sau co the dung code thay
	 */
	public void act(GenerationEvent ev) // Event A;
	{
		this.p.state.act();
	}

	public void act(LeavingSourceQueueEvent ev) { // B chuyen sang
													// state P2
		Element elem = ev.elem;
		SourceQueue sq = (SourceQueue) elem;
		// gia su moi Node chi bao gom 1 EXB, 1 ENB va
		// 1 SourceQueue
		ExitBuffer EXBs = sq.phyLayer.EXBs[0];
		//this.elem = EXBs; // goi dat o EXBs
		
		// goi dat o EXBs
		this.p.state = new StateP2(this.p, EXBs);
		// Thuc hien tiep method act khong tham so cua StateP2
		this.p.state.act();
	}

}
