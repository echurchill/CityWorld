package me.daddychurchill.CityWorld.Plats;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class NatureLot extends IsolatedLot {

	public NatureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new NatureLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return 0;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.seaLevel + generator.landRange;
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		generateSurface(generator, chunk, true);
		
		generateEntities(generator, chunk);
	}
	
	private final static int magicSeaSpawnY = 62;
	
	protected void generateEntities(CityWorldGenerator generator, RealBlocks chunk) {
		int x = chunkOdds.getRandomInt(1, 14);
		int z = chunkOdds.getRandomInt(1, 14);
			
		// in the water?
		if (chunk.isWater(x, magicSeaSpawnY, z)) {
			generator.spawnProvider.spawnSeaAnimals(generator, chunk, chunkOdds, x, magicSeaSpawnY, z);
		} else {
			int y = getBlockY(x, z);
			y = chunk.findFirstEmptyAbove(x, y, z, getTopY(generator));
			int count = chunkOdds.getRandomInt(1, 3);
			if (!chunk.isWater(x, y - 1, z))
				generator.spawnProvider.spawnVagrants(generator, chunk, chunkOdds, x, y, z, count);
		}
	}

}
