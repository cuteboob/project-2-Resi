package network.host;

import config.Constant;
import events.Event;
import events.GenerationEvent;
import network.Link;
import network.Node;
import network.layers.PhysicalLayer;
import simulator.DiscreteEventSimulator;



/**
 * Created by Dandoh on 6/27/17.
 */
public class Host extends Node {
	public Host(int id) {
	     super(id);
	     this.physicalLayer = new PhysicalLayer(this);
	}
   @Override
    public void clear() {

    }
   
   
   // Tap trung sua ham nay la duoc
   public void generatePacket(int destination) 
   {
	   if(this.physicalLayer.sq == null)
		   this.physicalLayer.sq = new SourceQueue(this.id, destination);
	   else
		   this.physicalLayer.sq.setDestionationID(destination);
	   Event ev = new GenerationEvent(this.physicalLayer.sq);
	   ev.startTime = this.physicalLayer.sim.time();
	   ev.endTime = ev.startTime;
	   this.physicalLayer.sq.insertEvents(ev);
   }
   
   public void generatePacket(Integer destination, int a) {
		 if(this.physicalLayer.sq == null)
			   this.physicalLayer.sq = new SourceQueue(this.id, destination);
		   else
			   this.physicalLayer.sq.setDestionationID(destination);
		   Event ev = new GenerationEvent(this.physicalLayer.sq, destination);
		   DiscreteEventSimulator sim = (DiscreteEventSimulator) this.physicalLayer.sq.phyLayer.sim;
		   sim.allEvents.add(ev);
		   ev.startTime = a;
		   ev.endTime = ev.startTime;
		   this.physicalLayer.sq.insertEvents(ev);
	}
   
   public void receive()
   {
	   
   }

}