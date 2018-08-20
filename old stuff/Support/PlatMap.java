package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.Clipboard;
import me.daddychurchill.CityWorld.Clipboard.ClipboardLot;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;

import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatMap {
	
	// Class Constants
	public static final int Width = 10;
	
	// Instance data
//	public World world;
	public CityWorldGenerator generator;
	public int originX;
	public int originZ;
	public DataContext context;
	protected PlatLot[][] platLots;
	private float naturalPlats;

	public PlatMap(CityWorldGenerator generator, ShapeProvider shapeProvider, int originX, int originZ) {
		super();
		
		// populate the instance data
//		this.world = generator.getWorld();
		this.generator = generator;
		this.originX = originX;
		this.originZ = originZ;

		// make room for plat data
		platLots = new PlatLot[Width][Width];
		naturalPlats = 0;
		
		// do the deed
		shapeProvider.populateLots(generator, this);
		
		// recycle all the remaining holes
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				if (isEmptyLot(x, z))
					recycleLot(x, z);
			}
		}
	}
	
	public double getNaturePercent() {
		return naturalPlats / (Width * Width) + generator.settings.ruralnessLevel;
	}
	
	public Odds getOddsGenerator() {
		return generator.shapeProvider.getMacroOddsGeneratorAt(originX, originZ);
	}
	
	public Odds getChunkOddsGenerator(SupportBlocks chunk) {
		return generator.shapeProvider.getMicroOddsGeneratorAt(chunk.sectionX, chunk.sectionZ);
	}
	
	public Odds getChunkOddsGenerator(int chunkX, int chunkZ) {
		return generator.shapeProvider.getMicroOddsGeneratorAt(chunkX, chunkZ);
	}
	
	public void generateChunk(InitialBlocks chunk, BiomeGrid biomes) {

		// depending on the platchunk's type render a layer
		int platX = chunk.sectionX - originX;
		int platZ = chunk.sectionZ - originZ;
		
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {
			
//			if (chunk.sectionX != platlot.getChunkX() || chunk.sectionZ != platlot.getChunkZ())
//				generator.reportFormatted("!!!!!1! Wrong chunk [%d, %d] for Platlot [%d, %d]", chunk.sectionX, chunk.sectionZ, platlot.getChunkX(), platlot.getChunkZ());

			// do what we came here for
			platlot.generateChunk(generator, this, chunk, biomes, context, platX, platZ);
		}
	}
	
	public void generateBlocks(RealBlocks chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.sectionX - originX;
		int platZ = chunk.sectionZ - originZ;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateBlocks(generator, this, chunk, context, platX, platZ);
		}
	}
	
	
	public int getNumberOfRoads() {
		int result = 0;
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				if (platLots[x][z] != null && platLots[x][z].style == LotStyle.ROAD)
					result++;
			}
		}
		return result;
	}
	
	public PlatLot getLot(int x, int z) {
		if (inBounds(x, z))
			return platLots[x][z];
		else
			return null;
	}
	
	public PlatLot getMapLot(int chunkX, int chunkZ) throws IndexOutOfBoundsException {
		int platX = chunkX - originX;
		int platZ = chunkZ - originZ;
		
		// range check
		if (inBounds(platX, platZ))
			return platLots[platX][platZ];
		else
			throw new IndexOutOfBoundsException("Location specified is not in this PlatMap");
	}
	
	public boolean inBounds(int x, int z) {
		return x >= 0 && x < Width && z >= 0 && z < Width;
	}
	
	public boolean isEmptyLot(int x, int z) {
		if (inBounds(x, z))
			return platLots[x][z] == null;
		else
			return true;
	}
	
	public boolean isEmptyLots(int x, int z, int width, int length) {
		for (int a = x; a < x + width; a++)
			for (int b = z; b < z + length; b++)
				if (!isEmptyLot(a, b))
					return false;
		return true;
	}
	
	public boolean isInnerReallyEmptyLot(int centerX, int centerZ) {
		if (centerX >= 1 && centerX < Width - 1 && centerZ >= 1 && centerZ < Width - 1) {
			for (int x = centerX - 1; x < centerX + 2; x++) {
				for (int z = centerZ - 1; z < centerZ + 2; z++) {
					if (platLots[x][z] != null)
						return false;
				}
			}
			return true;
		} else
			return false;
	}
	
	public boolean isNaturalLot(int x, int z) {
		if (inBounds(x, z))
			return platLots[x][z] == null || platLots[x][z].style == LotStyle.NATURE;
		else
			return true;
	}
	
	public boolean isStructureLot(int x, int z) {
		if (inBounds(x, z))
			return platLots[x][z] != null && platLots[x][z].style == LotStyle.STRUCTURE;
		else
			return false;
	}
	
	public boolean isExistingRoad(int x, int z) {
		if (inBounds(x, z))
			return isRoad(x, z);
		else
			return false;
	}

	protected boolean isRoad(int x, int z) {
		PlatLot current = platLots[x][z];
		return current != null && (current.style == LotStyle.ROAD || current.style == LotStyle.ROUNDABOUT);
	}
	
	public void recycleLot(int x, int z) {

		// if it is not natural, make it so
		PlatLot current = platLots[x][z];
//		if (current != null && current.getChunkX() == 21 && current.getChunkZ() == -22) {
//			generator.reportMessage("#####>>>>> recycling it");
//			generator.reportMessage(".....>>>>> from = " + current.toString());
//			try {
//				throw new Exception();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else if (this.originX + x == 21 && this.originZ + z == -22) {
//			generator.reportMessage("#####>>>>> recycled it");
//			try {
//				throw new Exception();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		// put nature there
		if (current == null || current.style != LotStyle.NATURE) {
//			if (this.originX + x == 21 && this.originZ + z == -22) 
//				generator.reportMessage(".....>>>>> planting nature");
		
			// place nature
			platLots[x][z] = generator.shapeProvider.createNaturalLot(generator, this, x, z);
			naturalPlats++;
		}
	}
	
	public void paveLot(int x, int z, boolean roundaboutPart) {
		if (generator.settings.inRoadRange(originX + x, originZ + z) && 
			(platLots[x][z] == null || roundaboutPart || platLots[x][z].style != LotStyle.ROAD)) {
			
			// remember the old one
			PlatLot oldLot = platLots[x][z];
			
			// clear it please
			emptyLot(x, z);
			
			// place the lot
			platLots[x][z] = generator.shapeProvider.createRoadLot(generator, this, x, z, roundaboutPart, oldLot);
		}
	}
	
	public boolean setLot(int x, int z, PlatLot lot) {
		if (lot == null) {
			emptyLot(x, z);
			return true;
		} else {
//			if (lot.getChunkX() == 21 && lot.getChunkZ() == -22)
//				generator.reportMessage("#####>>>>> setting it");
			
			boolean result = lot.isPlaceableAt(generator, originX + x, originZ + z);
			if (result) {
				
				// clear it please
				emptyLot(x, z);
				
//				if (lot.getChunkX() == 21 && lot.getChunkZ() == -22) {
//					generator.reportMessage(".....>>>>> set it = " + lot.toString());
//					generator.reportMessage(".....>>>>> average = " + lot.getAverageY());
//					try {
//						throw new Exception();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
				
				// place the lot
				platLots[x][z] = lot;
			} 
			return result;
		}
	}
	
	public void emptyLot(int x, int z) {
		
		// keep track of the nature count
		PlatLot current = platLots[x][z];
		if (current != null) {
			if (current.style == LotStyle.NATURE)
				naturalPlats--;
		
//			if (current.getChunkX() == 21 && current.getChunkZ() == -22) {
//				generator.reportMessage("#####>>>>> emptied it");
//				generator.reportMessage(".    >>>>> was = " + current.toString());
//				generator.reportMessage(".....>>>>> average = " + current.getAverageY());
//				try {
//					throw new Exception();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			
			// empty this one out
			platLots[x][z] = null;
		}
	}
	
	public boolean isTrulyIsolatedLot(int x, int z) {
			
		// check each neighbor to see if it is also trulyIsolated
		for (int lotX = x - 1; lotX < x + 2; lotX++) {
			for (int lotZ = z - 1; lotZ < z + 2; lotZ++) {
				if (lotX != x && lotZ != z) {
					PlatLot neighbor = getLot(lotX, lotZ);
					if (neighbor != null && neighbor.trulyIsolated)
						return false;
				}
			}
		}
			
		// all done
		return true;
	}
	
	public void populateRoads() {
		
		// place the big four
		placeIntersection(RoadLot.PlatMapRoadInset - 1, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(RoadLot.PlatMapRoadInset - 1, Width - RoadLot.PlatMapRoadInset);
		placeIntersection(Width - RoadLot.PlatMapRoadInset, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(Width - RoadLot.PlatMapRoadInset, Width - RoadLot.PlatMapRoadInset);
	}
	
	public void validateRoads() {
		
		// any roads leading out?
		if (!(isRoad(0, RoadLot.PlatMapRoadInset - 1) ||
			  isRoad(0, Width - RoadLot.PlatMapRoadInset) ||
			  isRoad(Width - 1, RoadLot.PlatMapRoadInset - 1) ||
			  isRoad(Width - 1, Width - RoadLot.PlatMapRoadInset) ||
			  isRoad(RoadLot.PlatMapRoadInset - 1, 0) ||
			  isRoad(Width - RoadLot.PlatMapRoadInset, 0) ||
			  isRoad(RoadLot.PlatMapRoadInset - 1, Width - 1) ||
			  isRoad(Width - RoadLot.PlatMapRoadInset, Width - 1))) {
			
			// reclaim all of the silly roads
			for (int x = 0; x < Width; x++) {
				for (int z = 0; z < Width; z++) {
					if (isRoad(x, z))
						recycleLot(x, z);
				}
			}
			
		} else {
			
			//TODO any other validation?
		}
	}
	
	protected void placeIntersection(int x, int z) {
		boolean roadToNorth = false, roadToSouth = false, 
				roadToEast = false, roadToWest = false, 
				roadHere = false;
		
		// is there a road here?
		if (isEmptyLot(x, z)) {
		
			// are there roads from here?
			roadToNorth = isRoadTowards(x, z, 0, -5);
			roadToSouth = isRoadTowards(x, z, 0, 5);
			roadToEast = isRoadTowards(x, z, 5, 0);
			roadToWest = isRoadTowards(x, z, -5, 0);
			
			// is there a need for this intersection?
			if (roadToNorth || roadToSouth || roadToEast || roadToWest) {
			
				// are the odds in favor of a roundabout? AND..
				// are all the surrounding chunks empty (connecting roads shouldn't be there yet)
//				generator.reportMessage("Roundabout? " + generator.settings.includeRoundabouts + ", odds: " + context.oddsOfRoundAbouts + " context: " + context.toString());
				if (generator.settings.includeRoundabouts && 
					generator.settings.inCityRange(originX + x, originZ + z) && 
					generator.shapeProvider.isRoundaboutAt(originX + x, originZ + z, context.oddsOfRoundAbouts) &&
					isEmptyLot(x - 1, z - 1) && isEmptyLot(x - 1, z) &&	isEmptyLot(x - 1, z + 1) &&
					isEmptyLot(x, z - 1) &&	isEmptyLot(x, z + 1) &&
					isEmptyLot(x + 1, z - 1) &&	isEmptyLot(x + 1, z) &&	isEmptyLot(x + 1, z + 1)) {
					
					paveLot(x - 1, z - 1, true);
					paveLot(x - 1, z    , true);
					paveLot(x - 1, z + 1, true);
					
					paveLot(x    , z - 1, true);
					setLot(x, z, generator.shapeProvider.createRoundaboutStatueLot(generator, this, originX + x, originZ + z));
					paveLot(x    , z + 1, true);
			
					paveLot(x + 1, z - 1, true);
					paveLot(x + 1, z    , true);
					paveLot(x + 1, z + 1, true);
				
				// place the intersection then
				} else {
					roadHere = true;
				}
			}
		
		// now figure out if we are within a bridge/tunnel
		} else {
			
			// are there roads from here?
			if (isBridgeTowardsNorth(x, z) &&
				isBridgeTowardsSouth(x, z)) {
				roadToNorth = true;
				roadToSouth = true;
				roadHere = true;
				
			} else if (isBridgeTowardsEast(x, z) && 
					   isBridgeTowardsWest(x, z)) {
				roadToEast = true;
				roadToWest = true;
				roadHere = true;
			}
		}
		
		// now place any remaining roads we need
		if (roadHere)
			paveLot(x, z, false);
		if (roadToNorth) {
			paveLot(x, z - 1, false);
			paveLot(x, z - 2, false);
		}
		if (roadToSouth) {
			paveLot(x, z + 1, false);
			paveLot(x, z + 2, false);
		}
		if (roadToEast) {
			paveLot(x + 1, z, false);
			paveLot(x + 2, z, false);
		}
		if (roadToWest) {
			paveLot(x - 1, z, false);
			paveLot(x - 2, z, false);
		}
	}
	
	private boolean isRoadTowards(int x, int z, int deltaX, int deltaZ) {
		
		// is this a "real" spot?
		boolean result = HeightInfo.isBuildableAt(generator, (originX + x + deltaX) * SupportBlocks.sectionBlockWidth,
									   			 			 (originZ + z + deltaZ) * SupportBlocks.sectionBlockWidth);
		
		// if this isn't a buildable spot, is there a bridge or tunnel that gets us there?
		if (!result)
			result = isBridgeTowards(x, z, deltaX, deltaZ);
		
		// report back
		return result;
	}
	
	public boolean isBridgeTowardsNorth(int x, int z) {
		return isBridgeTowards(x, z, 0, -5);
	}
	
	public boolean isBridgeTowardsSouth(int x, int z) {
		return isBridgeTowards(x, z, 0, 5);
	}
	
	public boolean isBridgeTowardsWest(int x, int z) {
		return isBridgeTowards(x, z, -5, 0);
	}
	
	public boolean isBridgeTowardsEast(int x, int z) {
		return isBridgeTowards(x, z, 5, 0);
	}
	
	private boolean isBridgeTowards(int x, int z, int deltaX, int deltaZ) {
		
		// how far do we go?
		int offsetX = deltaX * SupportBlocks.sectionBlockWidth;
		int offsetZ = deltaZ * SupportBlocks.sectionBlockWidth;
		
		// where do we test?
		int chunkX = (originX + x) * SupportBlocks.sectionBlockWidth;
		int chunkZ = (originZ + z) * SupportBlocks.sectionBlockWidth;
		
		// what is the polarity of this spot
		boolean originPolarity = generator.shapeProvider.getBridgePolarityAt(chunkX, chunkZ);
		boolean currentPolarity = originPolarity;
		
		// short cut things a bit by looking for impossible things (polarity doesn't match the delta values)
		if (originPolarity) {
			if (deltaX != 0)
				return false;
		} else {
			if (deltaZ != 0)
				return false;
		}
		
		// keep searching in the delta direction until polarity shifts
		while (originPolarity == currentPolarity) {
			
			// move it along a bit
			chunkX += offsetX;
			chunkZ += offsetZ;
			
			//TODO should test for a maximum length of bridge/tunnel
			
			// keep going as long it is the same polarity
			currentPolarity = generator.shapeProvider.getBridgePolarityAt(chunkX, chunkZ);
			
			// did we found a "real" spot and the polarity is still the same
			if (currentPolarity == originPolarity && HeightInfo.isBuildableAt(generator, chunkX, chunkZ))
				return true;
		};
		
		// we have failed to find a real bridge/tunnel
		return false;
	}

	private final static int maxPlaceTries = 16;
	public void placeSpecificClip(CityWorldGenerator generator, Odds odds, Clipboard clip) {
		int chunksX = clip.chunkX;
		int chunksZ = clip.chunkZ;
		
		// find a lot that fits into the current platmap
		for (int attempt = 0; attempt < maxPlaceTries; attempt++) {
			int placeX = odds.getRandomInt(PlatMap.Width - chunksX);
			int placeZ = odds.getRandomInt(PlatMap.Width - chunksZ);
			
			// is this space completely empty?
			boolean empty = true;
			for (int x = placeX; x < placeX + chunksX; x++) {
				for (int z = placeZ; z < placeZ + chunksZ; z++) {
					empty = platLots[x][z] == null;
					if (!empty)
						break;
				}
				if (!empty)
					break;
			}
				
			// found one?
			if (empty) {
				
				// put it there
				placeSpecificClip(generator, odds, clip, placeX, placeZ);
				
				// all done
				return;
			}
		}
	}

	public void placeSpecificClip(CityWorldGenerator generator, Odds odds, Clipboard clip, int placeX, int placeZ) {
		int chunksX = clip.chunkX;
		int chunksZ = clip.chunkZ;
		
		// what way are we facing?
		BlockFace facing = odds.getRandomFacing();
		
		// calculate the various template plats
		for (int x = 0; x < chunksX; x++) {
			for (int z = 0; z < chunksZ; z++) {
				setLot(placeX + x, placeZ + z, new ClipboardLot(this, 
																originX + placeX + x, originZ + placeZ + z, 
																clip, facing, x, z));
			}
		}
	}
}
