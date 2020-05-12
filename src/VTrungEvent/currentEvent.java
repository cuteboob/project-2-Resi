package VTrungEvent;

import elements.Element;
import events.Event;
import network.Packet;

public class currentEvent extends Event {
	String type;
	public currentEvent(String type, int pid, int startTime, int source, int destination) {
		super();
		this.pid = pid;
		this.startTime = 0;
		switch (type) {
			case "a": {
				Packet p = new Packet(pid, source, destination, startTime);
				this.endTime = this.startTime;
				break;
			}
			case "b": {
				// xay ra khi hang doi nguon o trang thai Sq2.
				// EXB o trang thai X01 hoac X00
				
			}
			case "c": {
				// đường dẫn ở trạng thái W0
				// P ở đầu EXB
				// EXB ở trạng thái X11 hoặc X01
				
			}
			case "d": {
				// P ở Pe
				// đường dẫn W0
				// nốt tiếp theo là swich
				
				// gói bắt đầu được chuyển
				
				// kết thúc khi gói đến ENB của switch
			}
		}
	}
	
	
}
