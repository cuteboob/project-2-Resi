package network;

import config.Constant;
import elements.Way;
import graph.Coordination;
import graph.Graph;
import graph.NewGraph;
import network.host.Host;

import routing.RoutingAlgorithm;
import simulator.Simulator;
import weightedloadexperiment.ThroughputExperiment;

import java.util.*;

/**
 * Created by Dandoh on 6/27/17.
 */
public class Topology {
    private Graph graph;
    private List<Host> hosts = new ArrayList<Host>();
    private List<Switch> switches = new ArrayList<Switch>();
    private Map<Integer, Host> hostById = new HashMap<Integer, Host>();
    private Map<Integer, Switch> switchById = new HashMap<Integer, Switch>();
    private List<Link> Links;
    private List<Way> ways = new ArrayList<Way>();;
    public Map<Way, List<Integer>> waysUandV = new HashMap<Way, List<Integer>>();;
    
    //ThanhNT 14/10 new property
    public Map<Integer, String> cordOfNodes;
    //Endof ThanhNT 14/10 new property

    
    
    public Topology(Host sourceNode, Switch sw, Host desNode, Way w1, Way w2) {
    	this.hosts = new ArrayList<Host>();
    	this.switches = new ArrayList<Switch>();
    	this.hostById = new HashMap<>();
    	this.switchById = new HashMap<>();
    	this.ways = new ArrayList<Way>();
    	this.hosts.add(sourceNode);
    	this.hostById.put(sourceNode.id, sourceNode);
    	this.hosts.add(desNode);
    	this.hostById.put(desNode.id, desNode);
    	this.switches.add(sw);
    	
    	this.ways.add(w1);
    	this.ways.add(w2);
    }
    
    public Topology(Host sourceNode, Switch sw, Switch sw1, Host desNode, Way w1, Way w2, Way w3) {
    	this.hosts = new ArrayList<Host>();
    	this.switches = new ArrayList<Switch>();
    	this.hostById = new HashMap<>();
    	this.switchById = new HashMap<>();
    	this.ways = new ArrayList<Way>();
    	this.hosts.add(sourceNode);
    	this.hostById.put(sourceNode.id, sourceNode);
    	this.hosts.add(desNode);
    	this.hostById.put(desNode.id, desNode);
    	this.switches.add(sw);
    	this.switchById.put(sw.id, sw);
    	this.switches.add(sw1);
    	this.switchById.put(sw1.id, sw1);
    	
    	this.ways.add(w1);
    	this.ways.add(w2);
    	this.ways.add(w3);
    }
    
    public Topology(Graph graph, RoutingAlgorithm routingAlgorithm) {
        this.graph = graph;
        // construct hosts, switches and links and routing algorithm
        hosts = new ArrayList<>();
        switches = new ArrayList<>();
        hostById = new HashMap<>();
        switchById = new HashMap<>();

        //ThanhNT 14/10 add new statements to init property
        cordOfNodes = new HashMap<>();
        //Endof ThanhNT 14/10 add new statements to init property

        for (int hid : graph.hosts()) {
            Host host = new Host(hid);
            hosts.add(host);
            hostById.put(hid, host);

            //ThanhNT 14/10 add new statements to add new ID of HOST
            cordOfNodes.put(hid, "");
            //Endof ThanhNT 14/10 add new statements to add new ID of HOST
        }

        for (int sid : graph.switches()) {
            Switch sw = new Switch(sid
            		//, routingAlgorithm
            		);
            switches.add(sw);
            switchById.put(sid, sw);

            //ThanhNT 14/10 add new statements to add new ID of switch
            cordOfNodes.put(sid, "");
            //Endof ThanhNT 14/10 add new statements to add new ID of switch
        }


        // link from switch to switch
        Coordination C = new Coordination(graph);
        for (Switch sw : switches) {
            int swid = sw.id;
            for (int nsid : graph.adj(swid)) {
                if (graph.isSwitchVertex(nsid)) {
                    Switch other = switchById.get(nsid);
                    // => ThanhNT set comment to THE following line
                    //if (!other.ports.containsKey(swid)) 
                    {
                        // create new link
                        double x =  C.distanceBetween(sw.id, other.id);
                        System.out.println("Chieu dai leng = " + x + " from: " + sw.id + " to: " + other.id);
                        //double x = 5;
                        Link link = new Link(sw, other, x);
                        Links.add(link);
                        // => ThanhNT set comment to 2 following lines
                        //other.ports.put(swid, link.getPort(other));
                        //sw.ports.put(nsid, link.getPort(sw));
                        //ThanhNT 14/10 add new statements to insert coord of switch
                        cordOfNodes.put(sw.id, C.getCoordOfSwitch(sw.id));
                        cordOfNodes.put(other.id, C.getCoordOfSwitch(other.id));
                        //Endof ThanhNT 14/10 add new statements to insert coord of switch
                    }
                }
            }
        }

        // link from switch to host
        for (Host host : hosts) {
            // get switch
            int nsid = graph.adj(host.id)
                    .get(0);
            Switch csw = switchById.get(nsid);

            // create new link
            Link link = new Link(host, csw, Constant.HOST_TO_SWITCH_LENGTH);
            Links.add(link);
            
            //// add link to both => ThanhNT set comment to 2 following lines
            //host.portToSwitch = link.getPort(host);
            //csw.ports.put(host.id, link.getPort(csw));
            //ThanhNT 14/10 add new statements to insert coord of HOST
            cordOfNodes.put(host.id, C.getCoordOfHost(csw.id, Constant.HOST_TO_SWITCH_LENGTH));
            //Endof ThanhNT 14/10 add new statements to insert coord of HOST
        }
    }
    

	public Topology(NewGraph g) {
		this.graph = g;

        ArrayList<Integer> hostIDs = (ArrayList<Integer>) g.HostIDs;
        ArrayList<Integer> switchIDs = (ArrayList<Integer>) g.SwitchIDs;
        
        for (Integer hid : hostIDs) {
            Host host = new Host(hid);
            hosts.add(host);
            hostById.put(hid, host);
        }

        for (Integer sid : switchIDs) {
            Switch sw = new Switch(sid
            		//, routingAlgorithm
            		);
            switches.add(sw);
            switchById.put(sid, sw);
        }

        for (int i = 0; i < g.getV();i++) {
        	Node node = null;
        	if (switchById.containsKey(i)) {
        		node = switchById.get(i);
        	}
        	if (hostById.containsKey(i)) {
        		node = hostById.get(i);
        	}
        	for (Integer nodeID: g.adj(i)) {
        		if (switchById.containsKey(nodeID)) {
        			Switch sw = switchById.get(nodeID);
            		Link l = new Link(node, sw);
            		Way w = new Way(l);
            		this.ways.add(w);
            		List<Integer> a = new ArrayList<Integer>();
            		a.add(node.id);
            		a.add(sw.id);
            		waysUandV.put(w, a);
        		}
        		if (hostById.containsKey(nodeID)) {
        			Host h = hostById.get(nodeID);
            		Link l = new Link(node, h);
            		Way w = new Way(l);
            		this.ways.add(w);
            		List<Integer> a = new ArrayList<Integer>();
            		a.add(node.id);
            		a.add(h.id);
            		waysUandV.put(w, a);
        		}
        	}
        }
//        Topology.initRouteTable(g, switches);
	}
	
	
	public List<Link> getLink() {
    	return Links;
    }
    
    public Graph getGraph() {
        return graph;
    }

    public List<Host> getHosts() {
        return hosts;
    }
    
    public List<Way> getWays() {
    	return ways;
    }

    public List<Switch> getSwitches() {
        return switches;
    }

    public Host getHostById(int id) {
        return hostById.get(id);
    }
    
    public Switch getSwitchById(int id) {
        return switchById.get(id);
    }

    public void clear() {
        for (Host host : hosts) {
            host.clear();
        }

        for (Switch sw: switches) {
            sw.clear();
        }
    }
    
    public void setSimulator(Simulator sim)
    {
    	for (Host host : hosts) {
            host.physicalLayer.sim = sim;
        }
    	
    	for (Node sw: switches) {
            sw.physicalLayer.sim = sim;
        }


//        for (Switch sw: switches) {
//            sw.physicalLayer.sim = sim;
//        }
        
        sim.network = this;
    }

    public boolean checkDeadlock(){
    	return false;
    }
}
