package simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import events.Event;
import network.Device;
import network.Packet;
import network.Switch;
import network.Topology;
import network.host.Host;

public abstract class Simulator {
	protected double currentTime = 0;
    protected boolean stopped = true;
    protected boolean simulating = false;
	public static Map<Packet, Boolean> PacketsAct;
    
    public Topology network;
    
    public double time() {
        return this.currentTime;
    }

    public void init() {
        this.currentTime = 0;
        this.stopped = false;
        this.simulating = false;
    }
    
    public boolean isSimulating() {
        return this.simulating;
    }

    public boolean isStopped() {
        return this.stopped;
    }
    
    public void stop() {
        this.stopped = true;
    }
    
    public void start () {
    
    }
    
    
    
    /*public void addLocationOfEvents(Device device)
    {
    	if(device instanceof Host)
    	{
    		addLocation((Host)device);
    	}
    	if(device instanceof Switch)
    	{
    		
    	}
    }
    
    public void addLocation(Host host)
    {
    	
    }*/
    
}
