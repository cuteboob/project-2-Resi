package states.sourcequeue;

import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import events.Event;
import events.GenerationEvent;
import events.MovingInSwitchEvent;
import events.ReachingENBEvent;
import events.TypeD;
import network.Packet;
import network.host.SourceQueue;
import states.State;
import states.enb.N1;


public class Sq1 extends State {

/**
 * 
 * @author Code cua thay
 *
 */
	public Sq1() {}
	public Sq1(Element sq) {
		this.elem = sq;
	}
//	//•	State Sq1: source queue is empty.
//	public Sq1(SourceQueue e)
//	{
//		this.elem = e;
//	}
//	
//	/**
//	 * Phuong thuc act dung de goi khi ma mot phan tu thay doi trang thai
//	 * O day, phan tu Source queue khi o trang thai Sq1 thi
//	 * no se kiem tra xem danh sach cac su kien (sap xay ra) co
//	 * su kien sinh goi tin tiep theo chua?
//	 * Neu chua se tao ra su kien nay. Thoi diem xay ra su kien nay la
//	 * tuong lai (mot Constant.HOST_DELAY nua)
//	 */
//	public void act()
//	{
//		SourceQueue sQueue = (SourceQueue)elem;
//		if(notYetAddGenerationEvent(sQueue))//Kiem tra xem Source Queue da co event tao goi tin moi chua?
//		{
//			Event e = new GenerationEvent(elem);
//			e.startTime = (long)sQueue.getNextPacketTime();
//			e.endTime = e.startTime;
//			sQueue.insertEvents(e);//ma nguon cu dung pthuc add la khong dung
//		}
//	}
//	
//	public boolean notYetAddGenerationEvent(SourceQueue sQueue)
//	//Kiem tra xem Source Queue da co event tao goi tin moi chua?
//	{
//		long nextPacketTime = (long)sQueue.getNextPacketTime();
//		for(int i = 0; i < sQueue.allEvents.size(); i++)
//		{
//			if(sQueue.allEvents.get(i) instanceof GenerationEvent)
//			{
//				if(((GenerationEvent)sQueue.allEvents.get(i)).startTime 
//						== nextPacketTime)
//				{
//					return false;
//				}
//			}
//		}
//		return true;
//	}
	
	

	/**
	 * 
	 * @author Code sua
	 *
	 */
	//•	State Sq1: source queue is empty.
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

	
	public void act (GenerationEvent ev) {
		Element elem = this.elem;
		SourceQueue sq = (SourceQueue) elem;
//		sq.allPackets.add(ev.p);	// vao truoc ra truoc (se thuc hien
//									// remove(0)
		elem.state = new Sq2(sq);
		Packet p = sq.dequeue(ev.startTime);
		
		sq.state.p = p;
	}
	
	
//	public void getNextState(Element e) {
//		
//	}
}
