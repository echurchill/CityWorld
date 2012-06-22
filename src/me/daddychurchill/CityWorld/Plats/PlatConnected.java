package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.PlatMap;

public abstract class PlatConnected extends PlatLot {

	protected long connectedkey;
	
	public PlatConnected(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		connectedkey = platmap.generator.getConnectionKey();
	}

	@Override
	public long getConnectedKey() {
		return connectedkey;
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
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
	public boolean isConnected(PlatLot relative) {
		if (relative == null)
			return false;
		return connectedkey == relative.getConnectedKey();
	}
}
