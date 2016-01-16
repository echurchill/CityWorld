package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.material.PoweredRail;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

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
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return blockYs.minHeight;
	}

	@Override
	public int getTopY(CityWorldGenerator generator) {
		return blockYs.maxHeight;
	}
	
	protected boolean getSuperSpecial() {
		return false;
	}
	
	private static int railOffset = 0;
	private static int specialOffset = 7;

	@Override
	public void generateMines(CityWorldGenerator generator, RealBlocks chunk) {
		int y = generator.seaLevel + AstralTownEmptyLot.aboveSeaLevel - 1;
		
		PoweredRail rail = new PoweredRail();
		rail.setPowered(true);
		
		// north/south along where the Nexus is
		if (chunk.getOriginX() == AstralNexusLot.blockX) {
			rail.setDirection(BlockFace.NORTH, false);
			
			// underlayment
			for (int z = 0; z < 16; z++)
				generateOtherbits(chunk, railOffset, y, z, z == specialOffset);
			
			// now the rail itself
			// we do the following weirdness to ensure that power is properly recognized
			try {
				chunk.setDoPhysics(true);
				
				// from powersource to end
				for (int z = specialOffset; z < 16; z++)
					chunk.setBlock(railOffset, y + 1, z, Material.POWERED_RAIL, rail);
				
				// from just before powersource to start
				for (int z = specialOffset - 1; z >= 0; z--)
					chunk.setBlock(railOffset, y + 1, z, Material.POWERED_RAIL, rail);
			} finally {
				chunk.setDoPhysics(false);
			}
		}
		
		// west/east along where the Nexus is
		if (chunk.getOriginZ() == AstralNexusLot.blockZ) {
			rail.setDirection(BlockFace.EAST, false);
			
			// underlayment
			for (int x = 0; x < 16; x++)
				generateOtherbits(chunk, x, y, railOffset, x == specialOffset);

			// now the rail itself
			// we do the following weirdness to ensure that power is properly recognized
			try {
				chunk.setDoPhysics(true);
				
				// from power source to end
				for (int x = specialOffset; x < 16; x++)
					chunk.setBlock(x, y + 1, railOffset, Material.POWERED_RAIL, rail);
				
				// from just before power source to start
				for (int x = specialOffset - 1; x >= 0; x--)
					chunk.setBlock(x, y + 1, railOffset, Material.POWERED_RAIL, rail);
			} finally {
				chunk.setDoPhysics(false);
			}
		}
	}
	
	private void generateOtherbits(RealBlocks chunk, int x, int y, int z, boolean specialPoint) {

		// underlayment
		chunk.setBlock(x, y, z, AstralTownEmptyLot.materialBase);
		if (specialPoint && !getSuperSpecial())
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlocks(x, blockYs.getBlockY(x, z), y, z, Material.QUARTZ_BLOCK);
		
		// need a tunnel?
		if (!chunk.isPartiallyEmpty(x, y + 2, y + 4, z)) {
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 1, Material.AIR);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x, y + 3, z, Material.AIR);
		}
		
		// power!
		if (specialPoint)
			chunk.setBlock(x, y, z, Material.REDSTONE_BLOCK);
		
	}
}
