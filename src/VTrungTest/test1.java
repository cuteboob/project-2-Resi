package VTrungTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import VTrungEvent.currentEvent;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import events.Event;
import events.GenerationEvent;
import graph.Graph;
import network.Link;
import network.Packet;
import network.Switch;
import network.Topology;
import network.host.Host;
import network.host.SourceQueue;
import routing.RoutingAlgorithm;
import routing.RoutingPath;
import weightedloadexperiment.ThroughputExperiment;

public class test1 {
	public static void main(String[] args) {
		// Host(id: 1)
		Host A = new Host(0);
		Host B = new Host(1);
		Host C = new Host(2);
		Host E = new Host(4);
		
		// Switch(id: 3)
		Switch D = new Switch(3);
		
		
		// SourceQueue(sourceID: 10)
		SourceQueue SA = A.physicalLayer.sq;
		SourceQueue SB = B.physicalLayer.sq;
		SourceQueue SC = C.physicalLayer.sq;
		
		// ExitBuffer();
		A.physicalLayer.EXBs = new ExitBuffer[1];
		B.physicalLayer.EXBs = new ExitBuffer[1];
		C.physicalLayer.EXBs = new ExitBuffer[1];
		D.physicalLayer.EXBs = new ExitBuffer[1];
		ExitBuffer XA = new ExitBuffer();
		ExitBuffer XB = new ExitBuffer();
		ExitBuffer XC = new ExitBuffer();
		ExitBuffer XDE = new ExitBuffer();
		A.physicalLayer.EXBs[0] = XA;
		B.physicalLayer.EXBs[0] = XB;
		C.physicalLayer.EXBs[0] = XC;
		D.physicalLayer.EXBs[0] = XDE;
		
		// EntranceBuffer
		D.physicalLayer.ENBs = new EntranceBuffer[3];
		EntranceBuffer NAD = new EntranceBuffer();
		EntranceBuffer NBD = new EntranceBuffer();
		EntranceBuffer NCD = new EntranceBuffer();
		D.physicalLayer.ENBs[0] = NAD;
		D.physicalLayer.ENBs[0] = NBD;
		D.physicalLayer.ENBs[0] = NCD;
		
		Link WAD = new Link(A,D);
		Link WBD = new Link(B,D);
		Link WCD = new Link(C,D);
		Link WDE = new Link(D,E);
		
		List<Event> allEvents = new ArrayList<>();
		List<Event> newEvents = new ArrayList<>();
		List<Event> currentEvents = new ArrayList<>();
		// Su kien loai A: GenerationEvent
		Packet pg1 = new Packet(100, A.id, D.id, 0);
		Packet pg2 = new Packet(200, B.id, D.id, 0);
		Packet pg3 = new Packet(300, C.id, D.id, 0);
		Packet pg4 = new Packet(400, A.id, D.id, 50);
		Packet pg5 = new Packet(500, B.id, D.id, 50);
		Packet pg6 = new Packet(600, C.id, D.id, 50);
		Event a_P1_0 = new GenerationEvent(pg1);
		Event a_P2_0 = new GenerationEvent(pg2);
		Event a_P3_0 = new GenerationEvent(pg3);
		Event a_P4_0 = new GenerationEvent(pg1);
		Event a_P5_0 = new GenerationEvent(pg2);
		Event a_P6_0 = new GenerationEvent(pg3);
		newEvents.add(a_P1_0);
		newEvents.add(a_P1_0);
		newEvents.add(a_P3_0);
		newEvents.add(a_P4_0);
		newEvents.add(a_P5_0);
		newEvents.add(a_P6_0);
		allEvents.addAll(newEvents);
		
		int SimulationTime = 10000;
		int CurrentTime = 0;
		while (CurrentTime<=SimulationTime) {
			currentEvents = selectCurrentEvents(allEvents);
		}
//		
//		
//		// ExitBuffer();
//		ExitBuffer XA = new ExitBuffer();
//		ExitBuffer XB = new ExitBuffer();
//		ExitBuffer XC = new ExitBuffer();
//		ExitBuffer XDE = new ExitBuffer();
//		
//		// EntranceBuffer
//		EntranceBuffer NAD = new EntranceBuffer();
//		EntranceBuffer NBD = new EntranceBuffer();
//		EntranceBuffer NCD = new EntranceBuffer();
//		
//		// Link(Node: A, Node: D)
//		Link WAD = new Link(A,D);
//		Link WBD = new Link(B,D);
//		Link WCD = new Link(C,D);
//		Link WDE = new Link(D,E);
//		
//		List <Event> currentEvents = new ArrayList<Event>();
//		List <Event> newEvents = new ArrayList<Event>();
//		
//		
//		
//		// hang 1, time = 0;
//		currentEvent a_P1_0 = new currentEvent(a, 10, 0);
//		currentEvent a_P2_0 = new currentEvent(a, 20, 0);
//		currentEvent a_P3_0 = new currentEvent(a, 30, 0);
//		
//		newEvent b_P1_0 = new newEvent(b, 10, 0);
//		newEvent b_P2_0 = new newEvent(b, 20, 0);
//		newEvent b_P3_0 = new newEvent(b, 30, 0);
//		
//		
//
//		// hang 2, time = 0;
//		currentEvent b1_P1_0 = new currentEvent(b1, 10, 0);
//		currentEvent b1_P2_0 = new currentEvent(b1, 20, 0);
//		currentEvent b1_P3_0 = new currentEvent(b1, 30, 0);
//		
//		newEvent c_P1_0 = new newEvent(c, 10, 0);
//		newEvent c_P2_0 = new newEvent(c, 20, 0);
//		newEvent c_P3_0 = new newEvent(c, 30, 0);
//		
//		
//		// hang 3, time = 0;
//		currentEvent c_P1_0 = new currentEvent(c, 10, 0);
//		currentEvent c_P2_0 = new currentEvent(c, 20, 0);
//		currentEvent c_P3_0 = new currentEvent(c, 30, 0);
//		
//		newEvent d_P1_0 = new newEvent(d, 10, 0);
//		newEvent d_P2_0 = new newEvent(d, 20, 0);
//		newEvent d_P3_0 = new newEvent(d, 30, 0);
//		
//		
//		// hang 4, time = 15;
//		currentEvent d1_P1_0 = new currentEvent(d1, 10, 0);
//		currentEvent d1_P2_0 = new currentEvent(d1, 20, 0);
//		currentEvent d1_P3_0 = new currentEvent(d1, 30, 0);
//		
//		newEvent e1_P1_0 = new newEvent(e1, 10, 0);
//		
//		
//		// hang 5, time = 20;
//		currentEvent e1_P1_0 = new currentEvent(e1, 10, 0);
//		
//		newEvent f_P1_0 = new newEvent(f, 10, 0);
//		newEvent e1_P2_0 = new newEvent(e1, 20, 0);
//		
//		// hang 6, time = 25;
//		
//		
//		List<Integer> sources = new ArrayList<>();
//        List<Integer> destination = new ArrayList<>();
//		// 
	}
}
