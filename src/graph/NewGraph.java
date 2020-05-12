package graph;

import java.util.ArrayList;
import java.util.List;

import network.Switch;
import network.host.Host;

public class NewGraph extends Graph{
//	protected int V;
//    protected int E;
//    protected List<Integer>[] adj;
	public List<Integer> HostIDs;
	public List<Integer> SwitchIDs;
	
	public NewGraph(int V, List<Integer>[] adj, ArrayList<Integer> HostIDs, ArrayList<Integer> SwitchIDs) {
		this.V = V;
		this.adj = adj;
		this.HostIDs = HostIDs;
		this.SwitchIDs = SwitchIDs;
	}
	
	public void deleteWay(int u, int v) {
        adj[u].remove((Object)v);
	}
	
	public void addWay(int u, int v) {
        adj[u].add(v);
	}
	
	public int getE() {
		return E;
	}
	
	public int getV() {
		return V;
	}

	@Override
	public List<Integer> hosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> switches() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHostVertex(int v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSwitchVertex(int v) {
		// TODO Auto-generated method stub
		return false;
	}
}
