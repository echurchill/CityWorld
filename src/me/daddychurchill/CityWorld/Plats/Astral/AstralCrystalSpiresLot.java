package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class AstralCrystalSpiresLot extends AstralNatureLot {

	public AstralCrystalSpiresLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		for (int x = 0; x < 16; x = x + 2) {
			for (int z = 0; z < 16; z = z + 2) {
				if (chunkOdds.playOdds(populationChance)) {
					
					//TODO stagger them a bit?
					int y = getBlockY(x, z);
					if (y > 4 && y < generator.seaLevel) {
						int topY = chunkOdds.getRandomInt(Math.max(2, NoiseGenerator.floor((generator.seaLevel - y) * populationChance))) + y;
						chunk.setBlocks(x, y - 3, topY, z, Material.GLASS);
						chunk.setBlock(x, topY, z, Material.THIN_GLASS);
					}
				}
			}
		}
	}
}
