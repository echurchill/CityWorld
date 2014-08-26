package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralCrystalSpiresLot extends AstralNatureLot {

	public AstralCrystalSpiresLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {

	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		double oddsOfSpire = Odds.oddsPrettyLikely;
		double heightPercent = 1.0;
		if (platX == 0 || platZ == 0 || platX == PlatMap.Width - 1 || platZ == PlatMap.Width - 1) {
			oddsOfSpire = Odds.oddsSomewhatLikely;
			heightPercent = 0.5;
		}

		for (int x = 0; x < 16; x = x + 2) {
			for (int z = 0; z < 16; z = z + 2) {
				if (chunkOdds.playOdds(oddsOfSpire)) {
					//TODO stagger them a bit?
					int y = getBlockY(x, z);
					if (y > 4 && y < generator.seaLevel) {
						int topY = chunkOdds.getRandomInt(Math.max(2, NoiseGenerator.floor((generator.seaLevel - y) * heightPercent))) + y;
						chunk.setBlocks(x, y - 3, topY, z, Material.GLASS);
						chunk.setBlock(x, topY, z, Material.THIN_GLASS);
					}
				}
			}
		}
	}
		
	@Override
	public int getBottomY(WorldGenerator generator) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTopY(WorldGenerator generator) {
		// TODO Auto-generated method stub
		return 0;
	}

}
