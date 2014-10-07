package me.daddychurchill.CityWorld.Context.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBrownMushroomsLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralRedMushroomsLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralYellowSpongesLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class AstralMushroomContext extends AstralDataContext {

	public enum MushroomStyle { RED, BROWN, REDBROWN, YELLOW };
	private MushroomStyle style;
	
	public AstralMushroomContext(WorldGenerator generator, MushroomStyle style) {
		super(generator);
		
		this.style = style;
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		//TODO, This doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		Odds odds = platmap.getOddsGenerator();
		
		// is this natural or buildable?
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					// what is the world location of the lot?
					int blockX = (originX + x) * SupportChunk.chunksBlockWidth;
					int blockZ = (originZ + z) * SupportChunk.chunksBlockWidth;
					
					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.anyEmpties && heights.averageHeight < generator.seaLevel)
						current = generateMushroomLot(platmap, odds, originX + x, originZ + z, getPopulationOdds(x, z));

					// did current get defined?
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}
	}

	@Override
	public void validateMap(WorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub

	}
	
	private PlatLot generateMushroomLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ, double populationChance) {
		switch (style) {
		case BROWN:
			return new AstralBrownMushroomsLot(platmap, chunkX, chunkZ, populationChance);
		case RED:
			return new AstralRedMushroomsLot(platmap, chunkX, chunkZ, populationChance);
		case REDBROWN:
			if (odds.flipCoin())
				return new AstralBrownMushroomsLot(platmap, chunkX, chunkZ, populationChance);
			else
				return new AstralRedMushroomsLot(platmap, chunkX, chunkZ, populationChance);
		case YELLOW:
			return new AstralYellowSpongesLot(platmap, chunkX, chunkZ, populationChance);
		}
		
		// just in case
		return null;
	}

	@Override
	public Material getMapRepresentation() {
		switch (style) {
		case YELLOW:
			return Material.SPONGE;
		case BROWN:
		case REDBROWN:
			return Material.HUGE_MUSHROOM_1;
		case RED:
		default:
			return Material.HUGE_MUSHROOM_2;
		}
	}
}
