package akhoitao;

import network.Packet;

public class KhoiTaoPacket {
	public static void main(String[] args) {
		Packet p = new Packet(2000001, 1, 1000001, 0);
		System.out.println(p);
	}
}
