package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.InitialBlocks;

public class GravelworksLot extends GravelLot {

	public GravelworksLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
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
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		
		switch (chunkOdds.getRandomInt(10)) {
		case 0:
			
			// hole please
			generateBase(generator, chunk);
			generateHole(generator, chunk, chunkOdds.getRandomInt(6, 7), generator.streetLevel - chunkOdds.getRandomInt(5, 48));
			break;
		
		case 1:
		case 2:
			
			// storage
			generateBase(generator, chunk);
			generator.houseProvider.generateShed(generator, chunk, context, chunkOdds, 7, generator.streetLevel + 1, 7, 
					chunkOdds.getRandomInt(2, 2), LootLocation.STONEWORKS, LootLocation.STONEWORKSOUTPUT);
			break;
			
		case 3:
		case 4:
		case 5:
			
			// clear off some space
			generateBase(generator, chunk);
			generateTailings(generator, chunk, 2, 14, 2, 14);
			break;
			
		default:
			
			// create some piles of stuff
			generateBase(generator, chunk);
			generatePile(generator, chunk, 3, 3, 4);
			generatePile(generator, chunk, 3, 9, 4);
			generatePile(generator, chunk, 9, 3, 4);
			generatePile(generator, chunk, 9, 9, 4);
			
			break;
		}
		
	}

}
