package me.daddychurchill.CityWorld.Context.Floating;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.NatureContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plats.Floating.FloatingHouseLot;
import me.daddychurchill.CityWorld.Plats.Floating.FloatingNatureLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class FloatingNatureContext extends NatureContext {

	public FloatingNatureContext(CityWorldGenerator generator) {
		super(generator);
		
		oddsOfIsolatedConstructs = Odds.oddsUnlikely;
	}
	
	@Override
	public PlatLot createNaturalLot(CityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return new FloatingNatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}

	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		super.populateMap(generator, platmap);
		
		//TODO, Nature doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);
		
		// random fluff
		Odds odds = platmap.getOddsGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		
		// is this natural or buildable?
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null || current.style == LotStyle.NATURE) {
//				if (current == null) {
					
					// what is the world location of the lot?
					int blockX = (originX + x) * SupportBlocks.sectionBlockWidth;
					int blockZ = (originZ + z) * SupportBlocks.sectionBlockWidth;
					
					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.isBuildable()) {
						
						// our inner chunks?
						if (x > 0 && x < PlatMap.Width - 1 && z > 0 && z < PlatMap.Width - 1) {
							
							// floating building?
							if (generator.settings.includeHouses) {
								if (shapeProvider.isIsolatedConstructAt(originX + x, originZ + z, oddsOfIsolatedConstructs))
									current = new FloatingHouseLot(platmap, originX + x, originZ + z, 
											shapeProvider.getConstuctMin() + odds.getRandomInt(shapeProvider.getConstuctRange()));
							}
						}
						
						// did current get defined?
						if (current != null)
							platmap.setLot(x, z, current);
						else
							platmap.recycleLot(x, z);
					}
				}
			}
		}
	}
}
