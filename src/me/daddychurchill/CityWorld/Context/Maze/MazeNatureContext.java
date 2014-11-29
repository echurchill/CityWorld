package me.daddychurchill.CityWorld.Context.Maze;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.NatureContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeNatureLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Plats.Rural.HouseLot;
import me.daddychurchill.CityWorld.Plats.Urban.ConcreteLot;
import me.daddychurchill.CityWorld.Plats.Urban.LibraryBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Plats.Urban.StoreBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.WarehouseBuildingLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class MazeNatureContext extends NatureContext {

	public MazeNatureContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		// random stuff?
		Odds platmapOdds = platmap.getOddsGenerator();
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		
		// make it happen
		switch (1/*platmapOdds.getRandomInt(10)*/) {
		case 1:
			for (int x = 3; x < 7; x++) {
				for (int z = 3; z < 7; z++) {
					PlatLot current = platmap.getLot(x, z);
					if (current == null) {
						
						// what is the world location of the lot?
						int chunkX = originX + x;
						int chunkZ = originZ + z;
						int blockX = chunkX * SupportChunk.chunksBlockWidth;
						int blockZ = chunkZ * SupportChunk.chunksBlockWidth;
						
						// get the height info for this chunk
						heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
						if (!heights.isBuildable()) {
							platmap.setLot(x, z, new ParkLot(platmap, chunkX, chunkZ, 100));
						}
					}
				}
			}
			break;		
		case 2:
			boolean houseMade = false;
			for (int x = 3; x < 7; x++) {
				for (int z = 3; z < 7; z++) {
					PlatLot current = platmap.getLot(x, z);
					if (current == null) {
						
						// what is the world location of the lot?
						int chunkX = originX + x;
						int chunkZ = originZ + z;
						int blockX = chunkX * SupportChunk.chunksBlockWidth;
						int blockZ = chunkZ * SupportChunk.chunksBlockWidth;
						
						// get the height info for this chunk
						heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
						if (!heights.isBuildable()) {
							if (!houseMade && platmapOdds.flipCoin()) {
								houseMade = true;
								platmap.setLot(x, z, new HouseLot(platmap, chunkX, chunkZ));
							} else
								platmap.setLot(x, z, new FarmLot(platmap, chunkX, chunkZ));
						}
					}
				}
			}
			break;			
		case 3:
			for (int x = 3; x < 7; x++) {
				for (int z = 3; z < 7; z++) {
					PlatLot current = platmap.getLot(x, z);
					if (current == null) {
						
						// what is the world location of the lot?
						int chunkX = originX + x;
						int chunkZ = originZ + z;
						int blockX = chunkX * SupportChunk.chunksBlockWidth;
						int blockZ = chunkZ * SupportChunk.chunksBlockWidth;
						
						// get the height info for this chunk
						heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
						if (!heights.isBuildable()) {
							switch (platmapOdds.getRandomInt(6)) {
							case 1:
								platmap.setLot(x, z, new ConcreteLot(platmap, chunkX, chunkZ));
								break;
							case 2:
								platmap.setLot(x, z, new LibraryBuildingLot(platmap, chunkX, chunkZ));
								break;
							case 3:
								platmap.setLot(x, z, new StoreBuildingLot(platmap, chunkX, chunkZ));
								break;
							case 4:
								platmap.setLot(x, z, new WarehouseBuildingLot(platmap, chunkX, chunkZ));
								break;
							case 5:
								platmap.setLot(x, z, new OfficeBuildingLot(platmap, chunkX, chunkZ));
								break;
							default:
								platmap.setLot(x, z, new ParkLot(platmap, chunkX, chunkZ, 100));
								break;
							}
						}
					}
				}
			}
		default:
			// nothing!
		}
		
		// pass on the effort
		super.populateMap(generator, platmap);
	}

	@Override
	public PlatLot createNaturalLot(WorldGenerator generator, PlatMap platmap, int x, int z) {
		return new MazeNatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}
}
