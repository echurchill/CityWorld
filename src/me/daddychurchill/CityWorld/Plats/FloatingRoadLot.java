package me.daddychurchill.CityWorld.Plats;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingRoads;

public class FloatingRoadLot extends RoadLot {

	public FloatingRoadLot(PlatMap platmap, int chunkX, int chunkZ,	long globalconnectionkey, boolean roundaboutPart) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, roundaboutPart);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.streetLevel - 1;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {

		// where do we start
		int pavementLevel = generator.streetLevel;
		int sidewalkLevel = pavementLevel + 1;
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// draw pavement and clear out a bit
		chunk.setLayer(pavementLevel - 1, bridgeEdgeId);
		chunk.setLayer(pavementLevel, pavementId);
		chunk.setLayer(sidewalkLevel, airId);
		
		// sidewalk corners
		chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
		chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, chunk.width - sidewalkWidth, chunk.width, sidewalkId);
		chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
		chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkLevel + 1, chunk.width - sidewalkWidth, chunk.width, sidewalkId);
		
		// sidewalk edges
		if (!roads.toWest())
			chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkId);
		if (!roads.toEast())
			chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkLevel + 1, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkId);
		if (!roads.toNorth())
			chunk.setBlocks(sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
		if (!roads.toSouth())
			chunk.setBlocks(sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, chunk.width - sidewalkWidth, chunk.width, sidewalkId);
		
		// crosswalks?
		if (cityRoad && !generator.settings.includeWoolRoads) {
			calculateCrosswalks(roads);
			
			// draw the crosswalk bits
			if (crosswalkNorth)
				generateNSCrosswalk(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, 0, sidewalkWidth);
			if (crosswalkSouth)
				generateNSCrosswalk(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, chunk.width - sidewalkWidth, chunk.width);
			if (crosswalkWest)
				generateWECrosswalk(chunk, 0, sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (crosswalkEast)
				generateWECrosswalk(chunk, chunk.width - sidewalkWidth, chunk.width, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);
		}
			
		// round things out
		if (!roads.toWest() && roads.toEast() && !roads.toNorth() && roads.toSouth())
			generateRoundedOut(generator, context, chunk, sidewalkWidth, sidewalkWidth, 
					false, false);
		if (!roads.toWest() && roads.toEast() && roads.toNorth() && !roads.toSouth())
			generateRoundedOut(generator, context, chunk, sidewalkWidth, chunk.width - sidewalkWidth - 4, 
					false, true);
		if (roads.toWest() && !roads.toEast() && !roads.toNorth() && roads.toSouth())
			generateRoundedOut(generator, context, chunk, chunk.width - sidewalkWidth - 4, sidewalkWidth, 
					true, false);
		if (roads.toWest() && !roads.toEast() && roads.toNorth() && !roads.toSouth())
			generateRoundedOut(generator, context, chunk, chunk.width - sidewalkWidth - 4, chunk.width - sidewalkWidth - 4, 
					true, true);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {

		// where do we start
		int pavementLevel = generator.streetLevel;
		int sidewalkLevel = pavementLevel + 1;
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// crosswalks?
		if (generator.settings.includeWoolRoads) {
			calculateCrosswalks(roads);
			
			// center bit
			chunk.setBlocks(sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, pavementMat, pavementColor, false);
		
			// finally draw the crosswalks
			generateNSCrosswalk(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, 0, sidewalkWidth, crosswalkNorth);
			generateNSCrosswalk(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, chunk.width - sidewalkWidth, chunk.width, crosswalkSouth);
			generateWECrosswalk(chunk, 0, sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkWest);
			generateWECrosswalk(chunk, chunk.width - sidewalkWidth, chunk.width, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkEast);
		}
		
		// decay please
		if (generator.settings.includeDecayedRoads) {

			// center bit
			decayRoad(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			
			// road to the whatever
			if (roads.toNorth())
				decayRoad(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, 0, sidewalkWidth);
			if (roads.toSouth())
				decayRoad(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, chunk.width - sidewalkWidth, chunk.width);
			if (roads.toWest())
				decayRoad(chunk, 0, sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (roads.toEast())
				decayRoad(chunk, chunk.width - sidewalkWidth, chunk.width, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);

			// sidewalk corners
			decaySidewalk(chunk, 0, sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
			decaySidewalk(chunk, 0, sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			decaySidewalk(chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, 0, sidewalkWidth);
			decaySidewalk(chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			
			// sidewalk edges
			if (!roads.toWest())
				decaySidewalk(chunk, 0, sidewalkWidth, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (!roads.toEast())
				decaySidewalk(chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (!roads.toNorth())
				decaySidewalk(chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
			if (!roads.toSouth())
				decaySidewalk(chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			
		}
		
		// light posts
		if (cityRoad) {
			boolean lightPostNW = generateLightPost(generator, chunk, context, sidewalkLevel, sidewalkWidth - 1, sidewalkWidth - 1);
			boolean lightPostSE = generateLightPost(generator, chunk, context, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width - sidewalkWidth);
			
			// put signs up?
			if (generator.settings.includeNamedRoads) {
				
				// if we haven't calculated crosswalks yet do so
				calculateCrosswalks(roads);
				
				// add the signs
				if (lightPostNW && (crosswalkNorth || crosswalkWest))
					generateStreetSign(generator, chunk, sidewalkLevel, sidewalkWidth - 1, sidewalkWidth - 1);
				if (lightPostSE && (crosswalkSouth || crosswalkEast))
					generateStreetSign(generator, chunk, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width - sidewalkWidth);
			}
		}
	}
	
	private final static double balloonOdds = 0.40;

	@Override
	protected boolean generateLightPost(WorldGenerator generator,
			RealChunk chunk, DataContext context, int sidewalkLevel, int x, int z) {
		boolean result = super.generateLightPost(generator, chunk, context, sidewalkLevel, x, z);
		if (result && chunkRandom.nextDouble() < balloonOdds)
			generator.balloonProvider.generateBalloon(generator, chunk, context, x, sidewalkLevel + lightpostHeight + 2, z, chunkRandom);
		return result;
	}
	
	
}
