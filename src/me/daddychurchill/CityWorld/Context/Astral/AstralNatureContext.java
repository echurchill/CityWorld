package me.daddychurchill.CityWorld.Context.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralNatureLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralNatureContext extends AstralDataContext {
	
	public AstralNatureContext(CityWorldGenerator generator) {
		super(generator);
		
		oddsOfIsolatedConstructs = Odds.oddsPrettyUnlikely;
	}
	
	@Override
	public PlatLot createNaturalLot(CityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return new AstralNatureLot(platmap, platmap.originX + x, platmap.originZ + z, Odds.oddsAlwaysGoingToHappen);
	}
	
	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		
		//TODO, This doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);
		
//		// random fluff
//		ShapeProvider shapeProvider = generator.shapeProvider;
//		
//		// only one to a customer
//		boolean saucerPlaced = false;
//		
//		// where it all begins
//		int originX = platmap.originX;
//		int originZ = platmap.originZ;
//		
//		// is this natural or buildable?
//		for (int x = 0; x < PlatMap.Width; x++) {
//			for (int z = 0; z < PlatMap.Width; z++) {
//				if (!saucerPlaced) {
//				
//					// found a hole in the world?
//					PlatLot current = platmap.getLot(x, z);
//					if (current == null) {
//						
//						// what is the world location of the lot?
//						int blockX = (originX + x) * SupportChunk.chunksBlockWidth;
//						int blockZ = (originZ + z) * SupportChunk.chunksBlockWidth;
//						
//						// get the height info for this chunk
//						HeightInfo heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
//						if (shapeProvider.isIsolatedConstructAt(originX + x, originZ + z, oddsOfIsolatedConstructs)) {
//							
//							// abandoned saucer?
//							if (!saucerPlaced && heights.isSortaOnLevel(generator.seaLevel)) {
//								current = new AstralSaucerLot(platmap, originX + x, originZ + z);
//								saucerPlaced = true;
//							}
//						}
//		
//						// did current get defined?
//						if (current != null)
//							platmap.setLot(x, z, current);
//					}
//				}
//			}
//		}
	}

	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Material getMapRepresentation() {
		return Material.GLASS;
	}

}
