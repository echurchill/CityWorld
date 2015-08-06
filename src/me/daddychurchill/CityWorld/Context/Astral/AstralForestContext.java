package me.daddychurchill.CityWorld.Context.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralForestCanopyLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralForestFernLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralForestHedgeLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class AstralForestContext extends AstralDataContext {

	public enum ForestStyle { FERN, HEDGE, CANOPY, FRACTAL };
	private ForestStyle style;
	
	public AstralForestContext(CityWorldGenerator generator, ForestStyle style) {
		super(generator);
		
		this.style = style;
	}

	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
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
					int blockX = (originX + x) * SupportBlocks.sectionBlockWidth;
					int blockZ = (originZ + z) * SupportBlocks.sectionBlockWidth;
					
					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.anyEmpties && heights.averageHeight < generator.seaLevel)
						current = generateForestLot(platmap, odds, originX + x, originZ + z, getPopulationOdds(x, z));

					// did current get defined?
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}
	}

	private PlatLot generateForestLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ, double populationChance) {
		switch (style) {
		case FERN:
			return new AstralForestFernLot(platmap, chunkX, chunkZ, populationChance);
		case HEDGE:
			return new AstralForestHedgeLot(platmap, chunkX, chunkZ, populationChance);
		case CANOPY:
		default:
			return new AstralForestCanopyLot(platmap, chunkX, chunkZ, populationChance);
		}
	}

	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Material getMapRepresentation() {
		switch (style) {
		case FERN:
			return Material.LEAVES;
		case HEDGE:
			return Material.LEAVES_2;
		case CANOPY:
		default:
			return Material.LOG;
		}
	}

}
