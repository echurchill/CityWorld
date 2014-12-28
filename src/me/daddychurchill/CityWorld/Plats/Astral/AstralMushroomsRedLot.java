package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public class AstralMushroomsRedLot extends AstralMushroomsLot {

	public AstralMushroomsRedLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		
	}

	private Material mushroomMaterial = Material.HUGE_MUSHROOM_2;

	private final static double oddsOfTallMushroom = Odds.oddsSomewhatLikely;
	private final static double oddsOfNarrowMushroom = Odds.oddsSomewhatUnlikely;
	
	@Override
	protected int maxMushrooms() {
		return 3;
	}
	
	@Override
	protected void plantMushroom(CityWorldGenerator generator, WorldBlocks blocks, int blockX, int blockY, int blockZ, int snowY) {
		
		// how tall?
		int heightY = chunkOdds.getRandomInt(Math.min(generator.seaLevel - blockY, maxHeight) - minHeight) + minHeight + snowY;
		
		// nothing here?
		if (blocks.isEmpty(blockX, blockY + snowY + 2, blockZ)) {
		
			// narrow one?
			int width = 3;
			if (chunkOdds.playOdds(oddsOfNarrowMushroom))
				width--;
			
			// start anew
			startMushroom(blocks, blockX, blockY, blockZ, heightY, mushroomMaterial);
			
			// slightly taller one?
			drawMushroomTop(blocks, width - 1);
			if (chunkOdds.playOdds(oddsOfTallMushroom)) {
				drawMushroomSlice(blocks, width);
				drawMushroomSlice(blocks, width);
			} 
			
			// main bit
			drawMushroomSlice(blocks, width);
			drawMushroomSlice(blocks, width + 1);
			
			// if tall enough, add a little more
			if (heightY > (maxHeight - minHeight) / 2 + minHeight) {
				drawMushroomSlice(blocks, width + 1);
				drawMushroomShell(blocks, width);
			}
		}
	}
}
