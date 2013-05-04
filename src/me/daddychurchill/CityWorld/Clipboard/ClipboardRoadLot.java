package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.Direction.Facing;

public class ClipboardRoadLot extends ClipboardLot {

	public ClipboardRoadLot(PlatMap platmap, int chunkX, int chunkZ, Clipboard clip, Facing facing, int lotX, int lotZ) {
		super(platmap, chunkX, chunkZ, clip, facing, lotX, lotZ);
		
		style = LotStyle.ROAD;
	}

	@Override
	public boolean isPlaceableAt(WorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inRoadRange(chunkX, chunkZ);
	}
}
