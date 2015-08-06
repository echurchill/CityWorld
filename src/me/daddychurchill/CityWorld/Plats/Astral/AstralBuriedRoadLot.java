package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.DyeColor;
import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class AstralBuriedRoadLot extends AstralBuriedCityLot {
	
	public enum SidewalkStyle { NONE, NORTHSOUTH, EASTWEST, INTERSECTION };
	private SidewalkStyle style;

	public AstralBuriedRoadLot(PlatMap platmap, int chunkX, int chunkZ, SidewalkStyle style) {
		super(platmap, chunkX, chunkZ);

		this.style = style;
	}
	
	private static DyeColor roadColor = DyeColor.BLACK;
	private static Material sidewalkMaterial = Material.DOUBLE_STEP;
	private static double oddsOfLandedSaucer = Odds.oddsEnormouslyUnlikely;

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		if (style == SidewalkStyle.NONE)
			chunk.setClay(0, 16, StreetLevel, StreetLevel + 1, 0, 16, roadColor);
		else {
			chunk.setBlocks(0, 3, StreetLevel, StreetLevel + 1, 0, 3, sidewalkMaterial);
			chunk.setBlocks(13, 16, StreetLevel, StreetLevel + 1, 0, 3, sidewalkMaterial);
			chunk.setBlocks(0, 3, StreetLevel, StreetLevel + 1, 13, 16, sidewalkMaterial);
			chunk.setBlocks(13, 16, StreetLevel, StreetLevel + 1, 13, 16, sidewalkMaterial);

			chunk.setClay(3, 13, StreetLevel, StreetLevel + 1, 3, 13, roadColor);
			
			if (style == SidewalkStyle.NORTHSOUTH) {
				chunk.setBlocks(3, 13, StreetLevel, StreetLevel + 1, 0, 3, sidewalkMaterial);
				chunk.setBlocks(3, 13, StreetLevel, StreetLevel + 1, 13, 16, sidewalkMaterial);
			} else {
				chunk.setClay(3, 13, StreetLevel, StreetLevel + 1, 0, 3, roadColor);
				chunk.setClay(3, 13, StreetLevel, StreetLevel + 1, 13, 16, roadColor);
			}
				
			if (style == SidewalkStyle.EASTWEST) {
				chunk.setBlocks(0, 3, StreetLevel, StreetLevel + 1, 3, 13, sidewalkMaterial);
				chunk.setBlocks(13, 16, StreetLevel, StreetLevel + 1, 3, 13, sidewalkMaterial);
			} else {
				chunk.setClay(0, 3, StreetLevel, StreetLevel + 1, 3, 13, roadColor);
				chunk.setClay(13, 16, StreetLevel, StreetLevel + 1, 3, 13, roadColor);
			}
			
			if (style == SidewalkStyle.INTERSECTION) {
				
				// draw crosswalks
			}
		}
		
		// clear out some space
		chunk.setBlocks(0, 16, StreetLevel + 1, StreetLevel + 4, 0, 16, Material.AIR);
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				if (chunkOdds.flipCoin())
					chunk.setBlock(x, StreetLevel + 4, z, Material.AIR);
			}
		}
		
		// land a saucer?
		if (chunkOdds.playOdds(oddsOfLandedSaucer))
			AstralStructureSaucerLot.drawLandedSaucer(generator, chunk, StreetLevel + 1);
	}

}
