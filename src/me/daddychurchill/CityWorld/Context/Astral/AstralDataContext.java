package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBuildingLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralEmptyLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class AstralDataContext extends DataContext {

	public AstralDataContext(WorldGenerator generator) {
		super(generator);

		schematicFamily = SchematicFamily.ASTRAL;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		// let the user add their stuff first, then plug any remaining holes with our stuff
		mapsSchematics.populate(generator, platmap);
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		
		// random fluff!
		Odds platmapOdds = platmap.getOddsGenerator();
	
		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					// what is the world location of the lot?
					int blockX = (originX + x) * SupportChunk.chunksBlockWidth;
					int blockZ = (originZ + z) * SupportChunk.chunksBlockWidth;
					
					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (heights.averageHeight < generator.seaLevel - 8) {
						
						// what to build?
						current = getBuilding(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z);

					}
					// remember what we did
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}

		// validate each lot
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current != null) {
					PlatLot replacement = current.validateLot(platmap, x, z);
					if (replacement != null)
						platmap.setLot(x, z, replacement);
				}
			}
		}
	}

	@Override
	public void validateMap(WorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub

	}

	protected PlatLot getBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		switch (odds.getRandomInt(2)) {
		case 1:
			return new AstralEmptyLot(platmap, chunkX, chunkZ);
//		case 2:
//			return new StoreBuildingLot(platmap, chunkX, chunkZ);
//		case 3:
//			return new LibraryBuildingLot(platmap, chunkX, chunkZ);
//		case 4:
//			return new ApartmentBuildingLot(platmap, chunkX, chunkZ);
//		case 5:
//			return new BankBuildingLot(platmap, chunkX, chunkZ);
//		case 6:
//			return new FactoryBuildingLot(platmap, chunkX, chunkZ);
//		case 7:
//			return new BlaBlaBuildingLot(platmap, chunkX, chunkZ);
		default:
			return new AstralBuildingLot(platmap, chunkX, chunkZ);
			}
	}
}
