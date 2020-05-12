package events;

import elements.*;
import network.Node;
import network.Packet;

public abstract class Event {
	public long pid; //packet ID
	public Packet p;
	public double startTime;
	public double endTime;
	
	public Element elem;

	
	public void execute()
	{}
}
