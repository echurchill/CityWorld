package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class AstralLot extends IsolatedLot {

	protected double populationChance;
	
	public AstralLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ);
		
		this.populationChance = populationChance;
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
		// TODO Auto-generated method stub

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

	@Override
	public void generateMines(WorldGenerator generator, RealChunk chunk) {
		int y = AstralNexusLot.nexusY1 + 1;
		if (chunk.getOriginX() == AstralNexusLot.blockX) {
			chunk.setBlocks(0, 1, y, 0, 16, Material.QUARTZ_BLOCK);
			chunk.setBlock(0, y, 0, Material.REDSTONE_BLOCK);
			chunk.setBlock(0, y, 8, Material.REDSTONE_BLOCK);
			chunk.setBlocks(0, 1, y + 1, 0, 16, Material.POWERED_RAIL);
			chunk.setBlocks(0, 1, y + 2, y + 4, 0, 16, Material.AIR);
		}
		if (chunk.getOriginZ() == AstralNexusLot.blockZ) {
			chunk.setBlocks(0, 16, AstralNexusLot.nexusY1 + 1, 0, 1, Material.QUARTZ_BLOCK);
			chunk.setBlock(0, y, 0, Material.REDSTONE_BLOCK);
			chunk.setBlock(8, y, 0, Material.REDSTONE_BLOCK);
			chunk.setBlocks(0, 16, y + 1, 0, 1, Material.POWERED_RAIL);
			chunk.setBlocks(0, 16, y + 2, y + 4, 0, 1, Material.AIR);
		}
	}
	
}
