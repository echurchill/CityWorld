package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public class AstralMushroomsBrownLot extends AstralMushroomsLot {

	public AstralMushroomsBrownLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}

	private Material mushroomMaterial = Material.HUGE_MUSHROOM_1;

	private final static double oddsOfTallMushroom = Odds.oddsSomewhatLikely;
	private final static double oddsOfNarrowMushroom = Odds.oddsSomewhatUnlikely;
	
	@Override
	protected int maxMushrooms() {
		return 4;
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
			if (chunkOdds.playOdds(oddsOfTallMushroom)) {
				drawMushroomTop(blocks, width - 2);
				drawMushroomSlice(blocks, width - 1);
				drawMushroomSlice(blocks, width - 1);
			} else
				drawMushroomTop(blocks, width - 1);
			
			// main bit
			drawMushroomSlice(blocks, width);
			drawMushroomSlice(blocks, width);
			
			// really tall? if so then let's do it again
			if (width > 2 && heightY > (maxHeight - minHeight) / 2 + minHeight) {
				nextMushroomLevel();
				drawMushroomTop(blocks, width - 2);
				drawMushroomSlice(blocks, width - 1);
			}
		}
	}
}
