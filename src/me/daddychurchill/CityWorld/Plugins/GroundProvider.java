package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class GroundProvider {

	public final static byte stoneId = (byte) Material.STONE.getId();
	
	protected final static double treeOdds = 0.85;
	protected final static double foliageOdds = 0.50;
	
	public byte substratumId;
	public byte subsurfaceId;
	public byte surfaceId;
	public byte fluidId;
	protected Random random;
	
	public abstract Biome generateCrust(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int y, int z, boolean surfaceCaves);
	public abstract void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, int x, double perciseY, int z, boolean includeTrees);
	
	public GroundProvider(WorldGenerator generator, Random random) {
		this.random = random;
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static GroundProvider loadProvider(WorldGenerator generator, Random random) {

		GroundProvider provider = null;
		
//		// need something like PhatLoot but for crust generation
//		provider = CrustProvider_PhatFoliage.loadPhatCrust();
		if (provider == null) {
			
			switch (generator.settings.environment) {
			case NETHER:
				provider = new GroundProvider_Nether(generator, random);
				break;
			case THE_END:
				provider = new GroundProvider_TheEnd(generator, random);
				break;
			case NORMAL:
				provider = new GroundProvider_Normal(generator, random);
				break;
			}
		}
	
		return provider;
	}
	
	protected void generateStratas(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			boolean surfaceCaves) {

		// compute the world block coordinates
		int blockX = chunk.chunkX * chunk.width + x;
		int blockZ = chunk.chunkZ * chunk.width + z;
		
		// stony bits
		for (int y = 2; y < baseY; y++)
			if (lot.isValidStrataY(generator, blockX, y, blockZ) && generator.shapeProvider.notACave(generator, blockX, y, blockZ))
				chunk.setBlock(x, y, z, baseId);

		// aggregate bits
		for (int y = baseY; y < substrateY - 1; y++)
			if (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, y, blockZ))
				chunk.setBlock(x, y, z, substrateId);

		// icing for the cake
		if (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, substrateY, blockZ)) {
			chunk.setBlock(x, substrateY - 1, z, substrateId);
			chunk.setBlock(x, substrateY, z, surfaceId);
		}
	}

	protected void generateStratas(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// a little crust please?
		generateStratas(generator, lot, chunk, x, z, baseId, baseY, substrateId, substrateY, surfaceId, surfaceCaves);

		// cover it up
		for (int y = substrateY + 1; y <= coverY; y++)
			chunk.setBlock(x, y, z, coverId);
	}
	
}
