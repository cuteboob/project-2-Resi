package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xpath.internal.operations.Bool;

import common.StdOut;
import config.Constant;
import elements.Element;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.GenerationEvent;
import events.LeavingEXBEvent;
import events.LeavingSourceQueueEvent;
import events.LeavingSwitchEvent;
import events.MovingInSwitchEvent;
import events.NotificationEvent;
import events.ReachingDestinationEvent;
import events.ReachingENBEvent;
import network.Link;
import network.Packet;
import network.Switch;
import network.Topology;
import network.host.DestinationNode;
import network.host.Host;
import network.host.SourceQueue;
import states.enb.N0;
import states.enb.N1;
import states.exb.X01;
import states.unidirectionalway.W0;

public class DiscreteEventSimulator extends Simulator {
	public int numReceived = 0; // x
	public long receivedPacket[]; // x
	public int numSent = 0; // x
	public int numLoss = 0; // x
	public long totalPacketTime = 0; // x
	public int numEvent = 0; // x
	private boolean isLimit; // x
	private double timeLimit; // x
	private boolean verbose; // x
	public long totalHop = 0; // x
	public ArrayList<Event> currentEvents = new ArrayList<Event>();
	// them o day
	public ArrayList<Event> allEvents = new ArrayList<Event>();
	public ArrayList<Event> newEvents = new ArrayList<Event>();

	public DiscreteEventSimulator(boolean isLimit, double timeLimit, boolean verbose) {
		super();
		this.isLimit = isLimit;
		this.verbose = verbose;
		this.timeLimit = timeLimit;
		this.receivedPacket = new long[(int) (timeLimit / Constant.EXPERIMENT_INTERVAL + 1)];
	} // x

	public double getTime() {
		return currentTime;
	} // x

	public double getTimeLimit() {
		return timeLimit;
	} // x

	public void start(Map<Integer, Integer> trafficPattern) {
		InitStateAndWay();
//		allEvents = selectAllEvents();
		int count = 0;
		while (currentTime <= timeLimit) {
			System.out.println("CurrentTime: " + currentTime);
			InitPacketAct();

//			System.out.println(currentTime);
			selectCurrentEvents(currentTime); // can them event link va tat ca event ENB, EXB

			currentEvents = selectCurrentEvents(currentTime);

			if (currentTime == 1000000) {
				int dem = 0;
//				for (Way w: this.network.getWays()) {
//					dem++;
//					if (!(w.state instanceof W0)) {
//						System.out.println("dem");
//					}
//				}
//				for (Host h: this.network.getHosts()) {
//					dem++;
//					for (int i= 0; i<Constant.HOST_LINK;i++) {
//						if (!(h.physicalLayer.EXBs[i].state instanceof X01)) {
//							System.out.println("dem");
//						}
//					}
//				}
				for (Switch sw: this.network.getSwitches()) {
					dem++;
					for (int i= 0; i<Constant.SWITCH_LINK;i++) {
						if (!(sw.physicalLayer.EXBs[i].state instanceof X01)) {
							System.out.println(sw.id);
						}
					}
				}
				
//				for (Switch sw: this.network.getSwitches()) {
//					dem++;
//					for (int i= 0; i<Constant.SWITCH_LINK;i++) {
//						if (!(sw.physicalLayer.ENBs[i].state instanceof N0)) {
//							System.out.println(dem);
//						}
//					}
//				}
				System.out.println(".");
			}
//			System.out.println("Tong so Events la: " + allEvents.size());
//			
//			System.out.println("Tong so Events la: " + currentEvents.size());
			System.out.println(currentEvents.size());
			for (Event e : currentEvents) { // ok
				e.execute();
				// execute se thuc hien updateState luon
			}

			// UpdateState(currentEvents); // thieu ham nay

			// allElement = allElement/currentEvents
//			deleteAllEventsFromList(currentEvents); // ok

////    		//Loc ra tat ca cac event sap ket thuc o cac thiet bi
//    		addCurrentEventsFromDevices(currentTime);
//    		//
			
			

			ElemUpdateEvent(currentTime);
			
//			deleteAllEventsFromList(currentEvents);
			
//			allEvents = selectAllEvents();

			currentTime = minEndTimeOffAllEvents(); // ok
			System.out.println(allEvents.size());
			if (currentTime <= Constant.HOST_DELAY * count && currentTime >= Constant.HOST_DELAY * (count-1)) {
				count++;
	        	for (Integer source : trafficPattern.keySet()) {
	                Integer destination = trafficPattern.get(source);
	                network.getHostById(source).generatePacket(destination, Constant.HOST_DELAY * count);
	            }
	        	System.out.println(count);
	        }
			System.out.println(allEvents.size());
			System.out.println("hello");
			// Ham EventGenerator se su dung traffic de tao them goi
			// cong them tat ca Events trong network

			// thieu ham nay

//			joinAllEventsFromList(newEvents); // ok

//    		currentTime = selectNextCurrentTime(currentTime);
		}
	}

	private ArrayList<Event> selectAllEvents() {
		// TODO Auto-generated method stub
		ArrayList<Event> alEvents = new ArrayList<Event>();

		List<Host> allHosts = this.network.getHosts();
		List<Way> allWays = this.network.getWays();
		List<Switch> allSwitchs = this.network.getSwitches();

		for (Host host : allHosts) {
			addEventsFromList(alEvents, host.physicalLayer.sq.allEvents);
			for (int i = 0; i < Constant.HOST_LINK; i++)
				addEventsFromList(alEvents, host.physicalLayer.EXBs[i].allEvents);// add events of EXB of hosts
		}

		for (Switch sw : allSwitchs) {
			for (int i = 0; i < Constant.SWITCH_LINK; i++) {
				addEventsFromList(alEvents, sw.physicalLayer.ENBs[i].allEvents);// add events of EXB of hosts
				addEventsFromList(alEvents, sw.physicalLayer.EXBs[i].allEvents);// add events of EXB of hosts
			}
		}
		for (Way w : allWays) {
			addEventsFromList(alEvents, w.allEvents);
		}

		return alEvents;
	}

	private void InitPacketAct() {
		// TODO Auto-generated method stub
//		Simulator.PacketsAct.put(key, value);
//		Simulator.PacketsAct.get(key);
		Map<Packet, Boolean> m = new HashMap<>();
//		System.out.println("m: " + m);
		ArrayList<Event> alEvents = new ArrayList<Event>();

		List<Host> allHosts = this.network.getHosts();
		List<Way> allWays = this.network.getWays();
		List<Switch> allSwitchs = this.network.getSwitches();

		for (Host h : allHosts) {
			ArrayList<Packet> allPackets;
			allPackets = h.physicalLayer.sq.allPackets;
			for (Packet p : allPackets) {
				m.put(p, true);
			}
			for (int j = 0; j < Constant.HOST_LINK; j++) {
				ExitBuffer exb = h.physicalLayer.EXBs[j];
				for (int i = 0; i < Constant.QUEUE_SIZE; i++) {
					if (exb.allPackets[i] != null) {
						m.put(exb.allPackets[i], true);
					}
				}
			}
		}

		for (Switch sw : allSwitchs) {
			ArrayList<Packet> allPackets;
			for (int j = 0; j < Constant.SWITCH_LINK; j++) {
				EntranceBuffer enb = sw.physicalLayer.ENBs[j];
				for (int i = 0; i < Constant.QUEUE_SIZE; i++) {
					if (enb.allPackets[i] != null) {
						m.put(enb.allPackets[i], true);
					}
				}
			}
			for (int j = 0; j < Constant.SWITCH_LINK; j++) {
				ExitBuffer exb = sw.physicalLayer.EXBs[j];
				for (int i = 0; i < Constant.QUEUE_SIZE; i++) {
					if (exb.allPackets[i] != null) {
						m.put(exb.allPackets[i], true);
					}
				}
			}
		}

		for (Way w : allWays) {
			m.put(w.p, true);
		}
		Simulator.PacketsAct = m;
	}

	private void InitStateAndWay() {
		ArrayList<Event> alEvents = new ArrayList<Event>();
		List<Host> allHosts = this.network.getHosts();
		List<Way> allWays = this.network.getWays();
		List<Switch> allSwitchs = this.network.getSwitches();

		for (Host host : allHosts) {
			host.physicalLayer.sq.state.elem = host.physicalLayer.sq;

			SourceQueue sq1 = host.physicalLayer.sq;
			SourceQueue sq2 = (SourceQueue) host.physicalLayer.sq.state.elem;
			for (int i = 0; i < Constant.HOST_LINK; i++) {
				host.physicalLayer.EXBs[i].state.elem = host.physicalLayer.EXBs[i];
			}
		}

		for (Switch sw : allSwitchs) {
			for (int i = 0; i < Constant.SWITCH_LINK; i++) {
				sw.physicalLayer.ENBs[i].state.elem = sw.physicalLayer.ENBs[i];
				sw.physicalLayer.EXBs[i].state.elem = sw.physicalLayer.EXBs[i];
			}
		}
		System.out.println("Size allWays: " + allWays.size());
		for (Way w : allWays) {
			w.InitWay();
			w.state.elem = w;
		}
	}

	public void ElemUpdateEvent(double currentTime) {
		ArrayList<Event> alEvents = new ArrayList<Event>();

		List<Host> allHosts = this.network.getHosts();
		List<Way> allWays = this.network.getWays();
		List<Switch> allSwitchs = this.network.getSwitches();

		for (Host h : allHosts) {
			ArrayList<Packet> allPackets;
			allPackets = h.physicalLayer.sq.allPackets;
			for (Packet p : allPackets) {
//				if (!(Simulator.PacketsAct.containsKey(p)))
//					p.state.act();
				if (Simulator.PacketsAct.containsKey(p) && Simulator.PacketsAct.get(p))
				{
					p.state.act();
//					System.out.println(".");
				}
			}
			for (int j = 0; j < Constant.HOST_LINK; j++) {
				ExitBuffer exb = h.physicalLayer.EXBs[j];
				for (int i = 0; i < Constant.QUEUE_SIZE; i++) {
					if (exb.allPackets[i] != null) {
//					if (!(Simulator.PacketsAct.containsKey(exb.allPackets[i])))
//							exb.allPackets[i].state.act();
						Map<Packet, Boolean> s = Simulator.PacketsAct;
						if (Simulator.PacketsAct.containsKey(exb.allPackets[i])
								&& Simulator.PacketsAct.get(exb.allPackets[i]))
						{
							exb.allPackets[i].state.act();
//							System.out.println(".");
						}
					}
				}
			}
		}

		for (Switch sw : allSwitchs) {
			for (int j = 0; j < Constant.SWITCH_LINK; j++) {
				EntranceBuffer enb = sw.physicalLayer.ENBs[j];
				for (int i = 0; i < Constant.QUEUE_SIZE; i++) {
					if (enb.allPackets[i] != null) {
//					if (!(Simulator.PacketsAct.containsKey(enb.allPackets[i])))
//						enb.allPackets[i].state.act();
						System.out.println("ID goi tin: " + enb.allPackets[i].id);
						if (Simulator.PacketsAct.containsKey(enb.allPackets[i])
								&& Simulator.PacketsAct.get(enb.allPackets[i])
								&& !enb.allPackets[i].acting
								)
						{
							
							enb.allPackets[i].state.act();
						}
					}
				}
			}
			for (int j = 0; j < Constant.SWITCH_LINK; j++) {
				ExitBuffer exb = sw.physicalLayer.EXBs[j];
				for (int i = 0; i < Constant.QUEUE_SIZE; i++) {
					if (exb.allPackets[i] != null) {
//					if (!(Simulator.PacketsAct.containsKey(exb.allPackets[i])))
//						exb.allPackets[i].state.act();
						if (Simulator.PacketsAct.containsKey(exb.allPackets[i])
								&& Simulator.PacketsAct.get(exb.allPackets[i]))
						{
							exb.allPackets[i].state.act();
//							System.out.println(".");
						}
					}
				}
			}

		}

		for (Way w : allWays) {
			if (w.p != null)
//				if (!(Simulator.PacketsAct.containsKey(w.p)))
//					w.p.state.act();
				if (Simulator.PacketsAct.containsKey(w.p) && Simulator.PacketsAct.get(w.p))
				{
					w.p.state.act();
//					System.out.println(".");
				}
		}

	}

	public boolean isVerbose() {
		return verbose;
	} // x

	public void log(String message) {
		if (this.verbose) {
			StdOut.printf("At %d: %s\n", (long) this.getTime(), message);
		}
	} // x

	public ArrayList<Event> EventGenerator(long currentTime) {
		List<Host> allHosts = this.network.getHosts();
		return null;
	}

	public void addAllEventsFromDevices(long currentTime) {
		// ArrayList<Event> allEvents = new ArrayList<Event>();
		List<Host> allHosts = this.network.getHosts();
		for (Host host : allHosts) {
			// soonestEndTime will be updated later as events are executed

			// con packet nua
			if (host.physicalLayer.sq.soonestEndTime == currentTime) {
				addAllEventsFromList(host.physicalLayer.sq.allEvents);
			}
			// soonestEndTime will be updated later as events are executed
			if (host.physicalLayer.EXBs[0].soonestEndTime == currentTime) {
				addAllEventsFromList(host.physicalLayer.EXBs[0].allEvents);// add events of EXB of hosts
			}
		}

		// Temporarily set comment, will be set to normal later
		/*
		 * List<Switch> allSwitches = this.network.getSwitches(); for(Switch aSwitch :
		 * allSwitches) { for(int i = 0; i < aSwitch.numPorts; i++) { //soonestEndTime
		 * will be updated later as events are executed
		 * if(aSwitch.physicalLayer.ENBs[i].soonestEndTime == currentTime) {
		 * addCurrentEvetsFromList(aSwitch.physicalLayer.ENBs[i].allEvents); }
		 * //soonestEndTime will be updated later as events are executed
		 * if(aSwitch.physicalLayer.EXBs[i].soonestEndTime == currentTime) {
		 * addCurrentEvetsFromList(aSwitch.physicalLayer.EXBs[i].allEvents);//add events
		 * of EXB of hosts } } }
		 */

	}

	// source co san
	public void addCurrentEventsFromDevices(long currentTime) {
		// ArrayList<Event> allEvents = new ArrayList<Event>();
		List<Host> allHosts = this.network.getHosts();
		for (Host host : allHosts) {
			// soonestEndTime will be updated later as events are executed
			if (host.physicalLayer.sq.soonestEndTime == currentTime) {
				addCurrentEvetsFromList(host.physicalLayer.sq.allEvents);
			}
			// soonestEndTime will be updated later as events are executed
			if (host.physicalLayer.EXBs[0].soonestEndTime == currentTime) {
				addCurrentEvetsFromList(host.physicalLayer.EXBs[0].allEvents);// add events of EXB of hosts
			}

			// them code
		}

		// Temporarily set comment, will be set to normal later
		/*
		 * List<Switch> allSwitches = this.network.getSwitches(); for(Switch aSwitch :
		 * allSwitches) { for(int i = 0; i < aSwitch.numPorts; i++) { //soonestEndTime
		 * will be updated later as events are executed
		 * if(aSwitch.physicalLayer.ENBs[i].soonestEndTime == currentTime) {
		 * addCurrentEvetsFromList(aSwitch.physicalLayer.ENBs[i].allEvents); }
		 * //soonestEndTime will be updated later as events are executed
		 * if(aSwitch.physicalLayer.EXBs[i].soonestEndTime == currentTime) {
		 * addCurrentEvetsFromList(aSwitch.physicalLayer.EXBs[i].allEvents);//add events
		 * of EXB of hosts } } }
		 */

	}

	// them vao
	public void addCurrentEvetsFromList(ArrayList<Event> allEvents) {
		if (allEvents != null) {
			if (allEvents.size() > 0) {
				for (Event e : allEvents) {
					if (e.endTime == this.currentTime) {
						this.currentEvents.add(e);
					}
				}
			}
		}
	}

	public void addAllEventsFromList(ArrayList<Event> allEvents) {
		if (allEvents != null) {
			if (allEvents.size() > 0) {
				for (Event e : allEvents) {
					if (e.endTime == this.currentTime) {
						this.allEvents.add(e);
					}
				}
			}
		}
	}

	public void addEventsFromList(ArrayList<Event> alEvents, ArrayList<Event> allEvents) {
		if (allEvents != null) {
			if (allEvents.size() > 0) {
				for (Event e : allEvents) {
					alEvents.add(e);
				}
			}
		}
	}

	public void joinAllEventsFromList(ArrayList<Event> currentEvents) {
		for (Event e : this.allEvents) {
			boolean check = true;
			for (Event ei : currentEvents) {
				if (e == ei)
					check = false;
			}
			if (check)
				this.allEvents.add(e);
		}
	}

	public void deleteEventFromAllEvent(Event e) {
		for (int i = 0; i < this.allEvents.size(); i++) {
				if (this.allEvents.get(i) == e) {
					this.allEvents.remove(i);
				}
			}
	}

	public double selectNextCurrentTime(long currentTime) {
		double result = Double.MAX_VALUE;
		List<Host> allHosts = this.network.getHosts();
		for (Host host : allHosts) {
			if (result > host.physicalLayer.sq.soonestEndTime && host.physicalLayer.sq.soonestEndTime >= currentTime) {
				result = host.physicalLayer.sq.soonestEndTime;
			}

			if (result > host.physicalLayer.EXBs[0].soonestEndTime
					&& host.physicalLayer.EXBs[0].soonestEndTime >= currentTime) {
				result = host.physicalLayer.EXBs[0].soonestEndTime;
			}
		}

		// Temporarily set comment, will be set to normal later
		/*
		 * List<Switch> allSwitches = this.network.getSwitches(); for(Switch aSwitch :
		 * allSwitches) { if(result > aSwitch.physicalLayer.sq.soonestEndTime &&
		 * aSwitch.physicalLayer.sq.soonestEndTime >= currentTime ) { result =
		 * aSwitch.physicalLayer.sq.soonestEndTime; } }
		 */
		return result;

	}

	public double minEndTimeOffAllEvents() {
		double minimum = Double.MAX_VALUE;
		for (Event e : allEvents) {
			minimum = Math.min(e.startTime, minimum);
		}
		return minimum;
	}

	public ArrayList<Event> selectCurrentEvents(double currentTime) {
//		// ArrayList<Event> allEvents = new ArrayList<Event>();
//		List<Host> allHosts = this.network.getHosts();
//		List<Way> allWays = this.network.getWays();
//		List<Switch> allSwitchs = this.network.getSwitches();
		ArrayList<Event> alEvent = new ArrayList<Event>();
//		for (Host host : allHosts) {
////			// soonestEndTime will be updated later as events are executed
////			if (host.physicalLayer.sq.soonestEndTime == currentTime) {
////				addAllEventsFromList(host.physicalLayer.sq.allEvents);
////			}
////			// soonestEndTime will be updated later as events are executed
////			if (host.physicalLayer.EXBs[0].soonestEndTime == currentTime) {
////				addAllEventsFromList(host.physicalLayer.EXBs[0].allEvents);// add events of EXB of hosts
////			}
//
//			for (Event e : host.physicalLayer.sq.allEvents) {
//				if (e.startTime == currentTime) {
//					alEvent.add(e);
//				}
//			}
//
//			for (int i = 0; i < Constant.HOST_LINK; i++) {
//				for (Event e : host.physicalLayer.EXBs[i].allEvents) {
//					if (e.startTime == currentTime) {
//						alEvent.add(e);
//					}
//				}
//			}
//		}
//
//		for (Switch sw : allSwitchs) {
//			// soonestEndTime will be updated later as events are executed
//			// soonestEndTime will be updated later as events are executed
////			if (sw.physicalLayer.EXBs[0].soonestEndTime == currentTime) {
////				addAllEventsFromList(sw.physicalLayer.ENBs[0].allEvents);// add events of EXB of hosts
////			}
////			// can them EnB, Link
////			if (sw.physicalLayer.ENBs[0].soonestEndTime == currentTime) {
////				addAllEventsFromList(sw.physicalLayer.EXBs[0].allEvents);// add events of EXB of hosts
////			}
//			for (int i = 0; i < Constant.SWITCH_LINK; i++) {
//				for (Event e : sw.physicalLayer.ENBs[i].allEvents) {
//					if (e.startTime == currentTime) {
//						alEvent.add(e);
//					}
//				}
//			}
//			for (int i = 0; i < Constant.SWITCH_LINK; i++) {
//				for (Event e : sw.physicalLayer.EXBs[i].allEvents) {
//					if (e.startTime == currentTime) {
//						alEvent.add(e);
//					}
//				}
//			}
//		}
//
//		for (Way w : allWays) {
//			// dieu kien de current time;
//			for (Event e : w.allEvents) {
//				if (e.startTime == currentTime) {
//					alEvent.add(e);
//				}
//			}
//		}
		for (Event e: allEvents) {
			if (e.startTime == currentTime) {
				alEvent.add(e);
			}
		}
		
		return alEvent;
	}
}
