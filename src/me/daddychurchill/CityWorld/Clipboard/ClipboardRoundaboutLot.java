package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.Direction.Facing;

public class ClipboardRoundaboutLot extends ClipboardRoadLot {

	public ClipboardRoundaboutLot(PlatMap platmap, int chunkX, int chunkZ, Clipboard clip, Facing facing, int lotX, int lotZ) {
		super(platmap, chunkX, chunkZ, clip, facing, lotX, lotZ);
		
		style = LotStyle.ROUNDABOUT;
	}

}
