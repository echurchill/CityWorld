package me.daddychurchill.CityWorld.Clipboard;

import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class ClipboardConstructLot extends ClipboardLot {

	public ClipboardConstructLot(PlatMap platmap, int chunkX, int chunkZ, Clipboard clip, BlockFace facing, int lotX, int lotZ) {
		super(platmap, chunkX, chunkZ, clip, facing, lotX, lotZ);

	}

	@Override
	public boolean isPlaceableAt(CityWorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inConstructRange(chunkX, chunkZ);
	}
}
