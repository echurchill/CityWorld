package me.daddychurchill.CityWorld;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class CityWorldBlockPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk source) {
		int chunkX = source.getX();
		int chunkZ = source.getZ();
		
		// try and find the lot handler for this chunk
		PlatMap platmap = PlatMap.getPlatMap(world, random, chunkX, chunkZ);
		if (platmap != null) {
			
			// calculate the right index
			int platX = chunkX - platmap.X;
			int platZ = chunkZ - platmap.Z;
			
			// see if there is something there yet
			PlatLot platlot = platmap.platLots[platX][platZ];
			if (platlot != null)
				platlot.generateBlocks(platmap, new RealChunk(source), platX, platZ);
		}
	}
}
