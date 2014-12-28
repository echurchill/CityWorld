package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.ShortChunk;

public class GravelLot extends ConstructLot {

	public GravelLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new GravelLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, ShortChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		generateTailings(generator, chunk, 0, 16, 0, 16);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return 0;
	}

	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}

	protected void generateTailings(CityWorldGenerator generator, RealChunk chunk, int x1, int x2, int z1, int z2) {
		int y = generator.streetLevel;
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				switch (chunkOdds.getRandomInt(6)) {
				case 0:
					chunk.setSlabs(x, x + 1, y, y + 1, z, z + 1, Material.COBBLESTONE, false);
//					chunk.setBlock(x, y - 1, z, Material.COBBLESTONE);
					break;
				case 1:
				case 2:
//					chunk.setBlock(x, y, z, Material.COBBLESTONE);
//					chunk.setBlocks(x, y - 1, y + 1, z, Material.COBBLESTONE);
					chunk.setSlabs(x, x + 1, y + 1, y + 2, z, z + 1, Material.COBBLESTONE, false);
					break;
				case 3:
					chunk.setBlock(x, y, z, Material.COBBLESTONE);
					break;
				default:
//					chunk.setBlock(x, y, z, Material.COBBLESTONE);
//					chunk.setBlocks(x, y - 1, y + 1, z, Material.COBBLESTONE);
					break;
				}
			}
		}
	}
}
