package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.Clipboard;
import me.daddychurchill.CityWorld.Clipboard.ClipboardLot;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plats.Urban.RoadLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatMap {
	
	// Class Constants
	public static final int Width = 10;
	
	// Instance data
//	public World world;
	public WorldGenerator generator;
	public int originX;
	public int originZ;
	public DataContext context;
	protected PlatLot[][] platLots;
	private int naturalPlats;

	public PlatMap(WorldGenerator generator, ShapeProvider shapeProvider, int originX, int originZ) {
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
	
	public float getNaturePercent() {
		return naturalPlats / (Width * Width);
	}
	
	public Odds getOddsGenerator() {
		return generator.shapeProvider.getMacroOddsGeneratorAt(originX, originZ);
	}
	
	public Odds getChunkOddsGenerator(SupportChunk chunk) {
		return generator.shapeProvider.getMicroOddsGeneratorAt(chunk.chunkX, chunk.chunkZ);
	}
	
	public Odds getChunkOddsGenerator(int chunkX, int chunkZ) {
		return generator.shapeProvider.getMicroOddsGeneratorAt(chunkX, chunkZ);
	}
	
	public void generateChunk(ByteChunk chunk, BiomeGrid biomes) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - originX;
		int platZ = chunk.chunkZ - originZ;
		
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateChunk(generator, this, chunk, biomes, context, platX, platZ);
		}
	}
	
	public void generateBlocks(RealChunk chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - originX;
		int platZ = chunk.chunkZ - originZ;
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
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return platLots[x][z];
		else
			return null;
	}
	
	public PlatLot getMapLot(int chunkX, int chunkZ) throws IndexOutOfBoundsException {
		int platX = chunkX - originX;
		int platZ = chunkZ - originZ;
		
		// range check
		if (platX >= 0 && platX < Width && platZ >= 0 && platZ < Width)
			return platLots[platX][platZ];
		else
			throw new IndexOutOfBoundsException("Location specified is not in this PlatMap");
	}
	
	public boolean isEmptyLot(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return platLots[x][z] == null;
		else
			return true;
	}
	
	public boolean isNaturalLot(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return platLots[x][z] == null || platLots[x][z].style == LotStyle.NATURE;
		else
			return true;
	}
	
	public boolean isStructureLot(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return platLots[x][z] != null && platLots[x][z].style == LotStyle.STRUCTURE;
		else
			return false;
	}
	
	public boolean isExistingRoad(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
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
		if (current == null || current.style != LotStyle.NATURE) {
		
			// place nature
			platLots[x][z] = generator.shapeProvider.createNaturalLot(generator, this, x, z);
			naturalPlats++;
		}
	}
	
	public void paveLot(int x, int z, boolean roundaboutPart) {
		if (generator.settings.inRoadRange(originX + x, originZ + z) && 
			(platLots[x][z] == null || roundaboutPart || platLots[x][z].style != LotStyle.ROAD)) {
			
			// clear it please
			emptyLot(x, z);
			
			// place the lot
			platLots[x][z] = generator.shapeProvider.createRoadLot(generator, this, x, z, roundaboutPart);
		}
	}
	
	public boolean setLot(int x, int z, PlatLot lot) {
		if (lot == null) {
			emptyLot(x, z);
			return true;
		} else {
			boolean result = lot.isPlaceableAt(generator, originX + x, originZ + z);
			if (result) {
				
				// clear it please
				emptyLot(x, z);
				
				// place the lot
				platLots[x][z] = lot;
			} 
			return result;
		}
	}
	
	public void emptyLot(int x, int z) {
		
		// keep track of the nature count
		PlatLot current = platLots[x][z];
		if (current != null && current.style == LotStyle.NATURE)
			naturalPlats--;
		
		// empty this one out
		platLots[x][z] = null;
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
					this.recycleLot(x, z);
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
				if (generator.settings.includeRoundabouts && 
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
		boolean result = HeightInfo.isBuildableAt(generator, (originX + x + deltaX) * SupportChunk.chunksBlockWidth,
									   			 			 (originZ + z + deltaZ) * SupportChunk.chunksBlockWidth);
		
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
		int offsetX = deltaX * SupportChunk.chunksBlockWidth;
		int offsetZ = deltaZ * SupportChunk.chunksBlockWidth;
		
		// where do we test?
		int chunkX = (originX + x) * SupportChunk.chunksBlockWidth;
		int chunkZ = (originZ + z) * SupportChunk.chunksBlockWidth;
		
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
	public void placeSpecificClip(WorldGenerator generator, Odds odds, Clipboard clip) {
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
				
//				generator.reportMessage("Placed " + clip.name + " at " + 
//						((placeX + originX) * SupportChunk.chunksBlockWidth) + 
//						", " + 
//						((placeZ + originZ) * SupportChunk.chunksBlockWidth));

				// put it there
				placeSpecificClip(generator, odds, clip, placeX, placeZ);
				
				// all done
				return;
			}
		}
	}

	public void placeSpecificClip(WorldGenerator generator, Odds odds, Clipboard clip, int placeX, int placeZ) {
		int chunksX = clip.chunkX;
		int chunksZ = clip.chunkZ;
		
		// what way are we facing?
		Direction.Facing facing = odds.getFacing();
		
		// calculate the various template plats
		for (int x = 0; x < chunksX; x++) {
			for (int z = 0; z < chunksZ; z++) {
				setLot(placeX + x, placeZ + z, new ClipboardLot(this, 
																originX + placeX + x, originZ + placeZ + z, 
																clip, facing, x, z));
			}
		}
	}

//	// Added by Sablednah
//	// https://github.com/echurchill/CityWorld/pull/4
//	public PlatLot[][] getPlatLots() {
//		return platLots;
//	}
}
