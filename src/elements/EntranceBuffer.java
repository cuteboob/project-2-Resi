package elements;

import states.enb.N0;

public class EntranceBuffer extends LimitedBuffer {
	public EntranceBuffer()
	{
		state = new N0();
	}
}
