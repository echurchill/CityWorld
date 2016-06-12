package me.daddychurchill.CityWorld.Plats.Floating;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingRoads;

public class FloatingRoadLot extends RoadLot {

	public FloatingRoadLot(PlatMap platmap, int chunkX, int chunkZ,	long globalconnectionkey, boolean roundaboutPart) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, roundaboutPart);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloatingRoadLot(platmap, chunkX, chunkZ, connectedkey, roundaboutRoad);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {

		// where do we start
		int pavementLevel = generator.streetLevel;
		int sidewalkLevel = getSidewalkLevel(generator);
//		Material sidewalkMaterial = getSidewalkMaterial();
		
//		// look around
//		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// draw pavement and clear out a bit
		chunk.setLayer(pavementLevel - 1, bridgeEdgeMaterial);
//		paveRoadLot(chunk, pavementLevel);
//		chunk.setLayer(pavementLevel, pavementId);
		chunk.airoutLayer(generator, sidewalkLevel);
		
//		// sidewalk corners
//		chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, 0, sidewalkWidth, sidewalkMaterial);
//		chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width, sidewalkMaterial);
//		chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, 0, sidewalkWidth, sidewalkMaterial);
//		chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width, sidewalkMaterial);
//		
//		// sidewalk edges
//		if (!roads.toWest())
//			chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkMaterial);
//		if (!roads.toEast())
//			chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkMaterial);
//		if (!roads.toNorth())
//			chunk.setBlocks(sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, 0, sidewalkWidth, sidewalkMaterial);
//		if (!roads.toSouth())
//			chunk.setBlocks(sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width, sidewalkMaterial);
//		
//		// round things out
//		if (!roads.toWest() && roads.toEast() && !roads.toNorth() && roads.toSouth())
//			generateRoundedOut(generator, context, chunk, sidewalkWidth, sidewalkWidth, 
//					false, false);
//		if (!roads.toWest() && roads.toEast() && roads.toNorth() && !roads.toSouth())
//			generateRoundedOut(generator, context, chunk, sidewalkWidth, chunk.width - sidewalkWidth - 4, 
//					false, true);
//		if (roads.toWest() && !roads.toEast() && !roads.toNorth() && roads.toSouth())
//			generateRoundedOut(generator, context, chunk, chunk.width - sidewalkWidth - 4, sidewalkWidth, 
//					true, false);
//		if (roads.toWest() && !roads.toEast() && roads.toNorth() && !roads.toSouth())
//			generateRoundedOut(generator, context, chunk, chunk.width - sidewalkWidth - 4, chunk.width - sidewalkWidth - 4, 
//					true, true);
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {

		// where do we start
		int pavementLevel = generator.streetLevel;
		int sidewalkLevel = pavementLevel + 1;
//		Material sidewalkMaterial = getSidewalkMaterial();
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
//		// draw pavement and clear out a bit
//		chunk.setLayer(pavementLevel - 1, bridgeEdgeMaterial);
		paveRoadLot(generator, chunk, pavementLevel);
////		chunk.setLayer(pavementLevel, pavementId);
//		chunk.setLayer(sidewalkLevel, getAirMaterial(generator, sidewalkLevel));
//		
		// sidewalk corners
		paveSidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
		paveSidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
		paveSidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, 0, sidewalkWidth);
		paveSidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
		
		// sidewalk edges
		if (!roads.toWest())
			paveSidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
		if (!roads.toEast())
			paveSidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
		if (!roads.toNorth())
			paveSidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
		if (!roads.toSouth())
			paveSidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
		
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

		// crosswalks?
		calculateCrosswalks(roads);
		
		// center bit
		paveRoadArea(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);
	
		// finally draw the crosswalks
		generateNSCrosswalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, 0, sidewalkWidth, crosswalkNorth);
		generateNSCrosswalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, chunk.width - sidewalkWidth, chunk.width, crosswalkSouth);
		generateWECrosswalk(generator, chunk, 0, sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkWest);
		generateWECrosswalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkEast);
		
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
			decaySidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
			decaySidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			decaySidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, 0, sidewalkWidth);
			decaySidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			
			// sidewalk edges
			if (!roads.toWest())
				decaySidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (!roads.toEast())
				decaySidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (!roads.toNorth())
				decaySidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
			if (!roads.toSouth())
				decaySidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			
		}
		
		// light posts
		if (inACity) {
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
		
		generateEntities(generator, chunk, sidewalkLevel);
	}
	
	private final static double oddsOfballoons = Odds.oddsSomewhatLikely;

	@Override
	protected boolean generateLightPost(CityWorldGenerator generator,
			RealBlocks chunk, DataContext context, int sidewalkLevel, int x, int z) {
		boolean result = super.generateLightPost(generator, chunk, context, sidewalkLevel, x, z);
		if (result && chunkOdds.playOdds(oddsOfballoons))
			generator.structureInAirProvider.generateBalloon(generator, chunk, context, x, sidewalkLevel + lightpostHeight + 2, z, chunkOdds);
		return result;
	}
	
	
}
