package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.DyeColor;
import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class AstralWhiteSpiresLot extends AstralNatureLot {

	public AstralWhiteSpiresLot(PlatMap platmap, int chunkX, int chunkZ,
			double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}

	private final static int shardWidth = 6;

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		for (int x = 1; x < 16; x = x + shardWidth + 2) {
			for (int z = 1; z < 16; z = z + shardWidth + 2) {
				if (chunkOdds.playOdds(populationChance)) {
					int y = getBlockY(x + 1, z + 1);
					if (y > shardWidth && y < generator.seaLevel - shardWidth)
						generateWhiteShard(generator, chunk, x, y - 1, z);
				}
			}
		}
	}
	
	private int shiftAround(int i) {
		return Math.max(2, Math.min(14, i + chunkOdds.getRandomInt(-1, 3)));
		
	}
	
	private void generateWhiteShard(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z) {
		x = shiftAround(x);
		z = shiftAround(z);
		
		chunk.setBlocks(x, y - 8, y + 6, z, Material.ENDER_STONE);

		chunk.setBlocks(x - 1, y, y + 6, z, Material.ENDER_STONE);
		chunk.setBlocks(x + 1, y, y + 6, z, Material.ENDER_STONE);
		chunk.setBlocks(x, y, y + 6, z - 1, Material.ENDER_STONE);
		chunk.setBlocks(x, y, y + 6, z + 1, Material.ENDER_STONE);
		
		chunk.setBlocks(x - 1, y + 2, y + 6, z - 1, Material.ENDER_STONE);
		chunk.setBlocks(x + 1, y + 2, y + 6, z - 1, Material.ENDER_STONE);
		chunk.setBlocks(x - 1, y + 2, y + 6, z + 1, Material.ENDER_STONE);
		chunk.setBlocks(x + 1, y + 2, y + 6, z + 1, Material.ENDER_STONE);
		
		chunk.setBlocks(x - 2, y + 4, y + 8, z, Material.ENDER_STONE);
		chunk.setBlocks(x + 2, y + 4, y + 8, z, Material.ENDER_STONE);
		chunk.setBlocks(x, y + 4, y + 8, z - 2, Material.ENDER_STONE);
		chunk.setBlocks(x, y + 4, y + 8, z + 2, Material.ENDER_STONE);
		
		chunk.setThinGlass(x - 1, y + 7, z, DyeColor.YELLOW);
		chunk.setThinGlass(x + 1, y + 7, z, DyeColor.YELLOW);
		chunk.setThinGlass(x, y + 7, z - 1, DyeColor.YELLOW);
		chunk.setThinGlass(x, y + 7, z + 1, DyeColor.YELLOW);
		
		chunk.setDoPhysics(true);
		chunk.setBlock(x, y + 7, z, Material.GLOWSTONE);
		chunk.setDoPhysics(false);
	}
	
}
