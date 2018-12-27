package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class ConnectedLot extends PlatLot {

	protected long connectedkey;

	public ConnectedLot(PlatMap platmap, int chunkX, int chunkZ) {
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

	@Override
	public PlatLot validateLot(PlatMap platmap, int platX, int platZ) {
		return null;
	}

}
