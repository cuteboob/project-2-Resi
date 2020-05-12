package akhoitao;

import network.Node;
import network.host.Host;
import network.layers.PhysicalLayer;

public class KhoiTaoSourceNode {
	public static void main(String[] args) {
		Host host = new Host(1);
		PhysicalLayer phy = host.physicalLayer;
		if (phy.ENBs!=null) {
			System.out.println("Co ENBs");
		}
		if (phy.EXBs!=null) {
			System.out.println("Co EXBS");
		}
		if (phy.node!=null) {
			// khong co node (node de lam gi?)
			System.out.println("Co node");
		}
		if (phy.sim!=null) {
			// khong co Similator: kha nang la chua viet
			// method lien quan den no
			System.out.println("Co Similator");
		}
		if (phy.sq!=null) {
			// tai sao lai co ca 3 Source Queue, 
			// EXB va ENB
			System.out.println("Co Source Queue");
		}
	}
}
