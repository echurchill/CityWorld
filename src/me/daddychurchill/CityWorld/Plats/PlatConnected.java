package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;

public abstract class PlatConnected extends PlatLot {

	protected long connectedkey;
	
	public PlatConnected(Random random, PlatMap platmap) {
		super(random);
		
		//TODO while this is relatively safe, I would feel better to have something airtight
		connectedkey = random.nextLong();
	}

	@Override
	public long getConnectedKey() {
		return connectedkey;
	}
	
	@Override
	public boolean makeConnected(Random random, PlatLot relative) {
		if (relative == null)
			return false;
		connectedkey = relative.getConnectedKey();
		return isConnected(relative);
	}
	
	@Override
	public boolean isConnectable(PlatLot relative) {
		if (relative == null)
			return false;
		return getClass().isInstance(relative);
	}
	
	@Override
	public boolean isIsolatedLot(Random random, int oddsOfIsolation) {
		return random.nextInt(oddsOfIsolation) == 0;
	}

	@Override
	public boolean isConnected(PlatLot relative) {
		if (relative == null)
			return false;
		return connectedkey == relative.getConnectedKey();
	}
}
