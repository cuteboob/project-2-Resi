package VTrungTest;

import common.Knuth;
import common.StdOut;
import config.Constant;
import custom.fattree.FatTreeGraph;
import custom.fattree.FatTreeRoutingAlgorithm;
import graph.Graph;
import network.Switch;
import network.Topology;
import network.host.Host;
import routing.RoutingAlgorithm;
import routing.RoutingPath;
import weightedloadexperiment.ThroughputExperiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ASimpleNetwork extends Graph {
    private List<Integer> switches;
    private List<Integer> hosts;
    private int k;
    private int N;
    private int r;
    private int test;

    private int nHost;
    private int nSwitch;


    public ASimpleNetwork(int nSwitch, int nHost){
        this.nSwitch = nSwitch;
        this.nHost = nHost;
        this.V = nHost + nSwitch;
        adj = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
//        if (nHost/nSwitch > k) return;

        int hostId = nSwitch;
        while (hostId < V){
            for (int i=0;i<nSwitch;i++) {
                addEdge(i, hostId);
                hostId++;
                if (hostId == V) break;
            }
        }

    }

    public ASimpleNetwork(int nSwitch, int nHost, int test) {
        this.nSwitch = nSwitch;
        this.nHost = nHost;
        this.V = nHost + nSwitch;
        adj = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
        addEdge(1,0);
        addEdge(2,0);
        addEdge(3,0);
        addEdge(0,4);

    }

    public int getnHost(){
        return nHost;
    }

    public int getnSwitch(){
        return nSwitch;
    }


    public ASimpleNetwork(String fileEdge) {
        this.nHost = 0;
        this.nSwitch = 0;
        this.k = 0;
        try (Stream<String> stream = Files.lines(Paths.get(fileEdge))) {
            stream.forEach(line -> {
                if (line.split(" ").length > 2){
                    this.nSwitch = Integer.parseInt(line.split(" ")[0]);
                    this.nHost = Integer.parseInt(line.split(" ")[1]);
                    this.k = Integer.parseInt(line.split(" ")[2]);
                    this.V = nHost + nSwitch;
                    adj = (List<Integer>[]) new List[V];
                    for (int v = 0; v < V; v++) {
                        adj[v] = new ArrayList<>();
                    }
                }
                else {
                    int u = Integer.parseInt(line.split(" ")[0]);
                    int v = Integer.parseInt(line.split(" ")[1]);

                    if (!this.hasEdge(u, v)) {
                        this.addEdge(u, v);
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creatRandomLink(int numMissed[]) {
        Random r = new Random();
        int[] thr = new int[(k * nSwitch - nHost)/2];
        int[] rcv = new int[(k * nSwitch - nHost)/2];
        int thr_index = 0;
        int rcv_index = 0;
        boolean b = true;

        for (int i=0;i<nSwitch;i++){
            for (int j=0;j<numMissed[i];j++){
                if (b){
                    thr[thr_index] = i;
                    thr_index++;
                    b = !b;
                }
                else{
                    rcv[rcv_index] = i;
                    rcv_index++;
                    b = !b;
                }
            }
        }

        int last = rcv.length;;
        for (int i=0;i<thr.length;i++){
            int j = r.nextInt(last);
            int try_time = 0;
            while(try_time<last){
                if ((thr[i]==rcv[j])||(hasEdge(thr[i],rcv[j]))){
                    //                System.out.printf("\nFail (%d,%d)",thr[i],rcv[j]);
                    j = (j+1)%last;
                    try_time++;
                    continue;
                }
                addEdge(thr[i],rcv[j]);
                rcv[j] = rcv[last-1];
                last--;
                break;
            }
        }
    }

    @Override
    public List<Integer> hosts() {
        if (hosts != null) return hosts;

        hosts = new ArrayList<>();
        for (int i = nSwitch; i < V; i++)
            hosts.add(i);

        return hosts;
    }

    @Override
    public List<Integer> switches() {
        if (switches != null) return switches;

        switches= new ArrayList<>();
        for (int i = 0; i < nSwitch; i++)
            switches.add(i);

        return switches;
    }

    @Override
    public boolean isHostVertex(int v) {
        return v >= nSwitch;
    }

    @Override
    public boolean isSwitchVertex(int v) {
        return (v < nSwitch)&&(v>=0);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges \n");
        int sumDegree = 0;

        for (int v = 0; v < V; v++) {
            s.append(String.format("%2d:", v));
            s.append(String.format(" degree = %2d -- ", degree(v)));
            sumDegree += degree(v);
            for (int w : adj[v]) {
                s.append(String.format(" %2d", w));
            }
            s.append("\n");
        }
        s.append("\n");
        s.append(String.format("Average degree = %f", 1.0 * sumDegree / V));
        return s.toString();
    }

    public static void main(String[] args) {
      //  StdOut.println(graph);



        // them o day

            //FatTreeGraph G = new FatTreeGraph(4);
            Graph graph = new ASimpleNetwork(1, 4, 0);
            System.out.println("graph.switches()="+graph.switches());
            System.out.println("graph.hosts()="+graph.hosts());
            System.out.println(graph.toString());
            
            RoutingAlgorithm routingAlgorithm = new RoutingAlgorithm() {
				
				@Override
				public RoutingPath path(int source, int destination) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public int next(int source, int current, int destination) {
					// TODO Auto-generated method stub
					return 0;
				}
			};
            Topology network = new Topology(graph, routingAlgorithm);
            
            List<Switch> sw = network.getSwitches();
            System.out.println(sw.get(0).id);
            ThroughputExperiment experiment = new ThroughputExperiment(network);
            Integer[] hosts = graph.hosts().toArray(new Integer[0]);
            List<Integer> sources = new ArrayList<>();
            List<Integer> destination = new ArrayList<>();
            
            System.out.println(hosts[0]);
            sources.addAll(Arrays.asList(hosts).subList(0, 3));
            destination.addAll(Arrays.asList(hosts).subList(3, 4));
            System.out.println("sources.toString()="+sources.toString());
            System.out.println("destination.toString()="+destination.toString());
            
            Map<Integer, Integer> traffic = new HashMap<>();
            int sizeOfFlow = 3;
            for (int i = 0; i < sizeOfFlow; i++) {
            	traffic.put(sources.get(i), destination.get(0));
            }
            
            experiment.calThroughput(traffic, false);
//      x      RoutingAlgorithm ra = new KShortestPathsRoutingAlgorithm(graph,4);
//     x       Network network = new Network(graph, ra);
//      x      System.out.println(network.getSwitches());
//      x     List<Switch> sw = network.getSwitches();
//      x      System.out.println(sw.get(0).id);
//       x     ThroughputExperiment experiment = new ThroughputExperiment(network);
//       x     Integer[] hosts = graph.hosts().toArray(new Integer[0]);
//      x      List<Integer> sources = new ArrayList<>();
//      x      List<Integer> destination = new ArrayList<>();
//
//     x       System.out.println(hosts[0]);
//
//     x       sources.addAll(Arrays.asList(hosts).subList(0, 3));
//     x       destination.addAll(Arrays.asList(hosts).subList(3, 4));
//
//      x      System.out.println("sources.toString()="+sources.toString());
//      x      System.out.println("destination.toString()="+destination.toString());
//
//      x      Map<Integer, Integer> traffic = new HashMap<>();
//            int sizeOfFlow = 3; //
//
//     x       for (int i = 0; i < sizeOfFlow; i++) {
//     x           traffic.put(sources.get(i), destination.get(0));
//                //traffic.put(destination.get(i), sources.get(i));
//                //StdOut.printf("From source: %d To destination: %d\n", sources.get(i), destination.get(i));
//            }
//
//            experiment.calThroughput(traffic, false);

            

    }
}
