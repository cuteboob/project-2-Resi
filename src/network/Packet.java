package network;

//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import config.Constant;
import elements.Element;
import network.host.SourceQueue;
import states.packet.StateP1;

import java.util.List;

/**
 * Created by Dandoh on 6/27/17.
 */
public class Packet extends Element{
    //public int id;
	
	// can dau vao node source, dau ra node destination
    private int source;
    private int destination;
    private int size;
    
    public boolean acting = false;
    
    private double startTime;
    private double endTime;
    public int nHop = 0;

    private List<Integer> predeterminedPath;
    public void setPredeterminedPath(List<Integer> predeterminedPath) {
        this.predeterminedPath = predeterminedPath;
    }

    public Packet(int id, int source, int destination, double startTime) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.size = Constant.PACKET_SIZE;
        SourceQueue sq = new SourceQueue(source); // se sua
        this.state = new StateP1(sq); // se sua
        this.startTime = startTime;
        this.endTime = -1;
    }

    public Packet(int id, SourceQueue sq, double startTime) {
        this.id = id;
        this.size = Constant.PACKET_SIZE;
        this.state = new StateP1(sq);
        this.startTime = startTime;
        this.endTime = -1;
    }
    
    public Packet(int id, Packet p, double startTime) {
        this.id = id;
        this.source = p.getSource();
        this.destination = p.getDestination();
        this.size = p.getSize();
        SourceQueue sq = new SourceQueue(source); // se sua
        this.state = new StateP1(sq); // se sua
        this.startTime = startTime;
        this.endTime = -1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }
    
    public void setDestination(int destination) {
    	this.destination = destination;
    }
    
    public int getSize() {
        return size;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public double timeTravel() {
        return endTime - startTime;
    }

    public boolean isTransmitted() {
        return endTime > startTime;
    }
}
