package me.daddychurchill.CityWorld;

import java.util.Random;

import org.bukkit.Material;

public class PlatPavedRoad extends PlatRoad {
	public static int sidewalkWidth = 3;
	public static int lightpostHeight = 3;
	
	protected static long connectedkeyForPavedRoads = 0;
	protected static byte pavementId = (byte) Material.STONE.getId();
	protected static byte sidewalkId = (byte) Material.STEP.getId();
	protected static byte lightpostbaseId = (byte) Material.DOUBLE_STEP.getId();
	protected static byte lightpostId = (byte) Material.FENCE.getId();
	protected static byte lightId = (byte) Material.GLOWSTONE.getId();

	public PlatPavedRoad(Random rand) {
		super(rand);

		// if the master key for paved roads isn't calculated then do it
		if (connectedkeyForPavedRoads == 0) {
			connectedkeyForPavedRoads = rand.nextLong();
		}

		// all paved roads are interconnected
		connectedkey = connectedkeyForPavedRoads;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateChunk(PlatMap platmap, Chunk chunk, int platX, int platZ) {

		// starting with the bottom
		generateBedrock(chunk, PlatMap.StreetLevel - 2);

		//TODO add sewers
		chunk.setBlocks(0, Chunk.Width, PlatMap.StreetLevel - 1, PlatMap.StreetLevel, 0, Chunk.Width, pavementId);
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// sidewalk corners
		chunk.setBlocks(0, sidewalkWidth, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, 0, sidewalkWidth, sidewalkId);
		chunk.setBlocks(0, sidewalkWidth, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkId);
		chunk.setBlocks(Chunk.Width - sidewalkWidth, Chunk.Width, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, 0, sidewalkWidth, sidewalkId);
		chunk.setBlocks(Chunk.Width - sidewalkWidth, Chunk.Width, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkId);
		
		// sidewalk edges
		if (!roads.toSouth())
			chunk.setBlocks(0, sidewalkWidth, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, sidewalkWidth, Chunk.Width - sidewalkWidth, sidewalkId);
		if (!roads.toNorth())
			chunk.setBlocks(Chunk.Width - sidewalkWidth, Chunk.Width, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, sidewalkWidth, Chunk.Width - sidewalkWidth, sidewalkId);
		if (!roads.toWest())
			chunk.setBlocks(sidewalkWidth, Chunk.Width - sidewalkWidth, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, 0, sidewalkWidth, sidewalkId);
		if (!roads.toEast())
			chunk.setBlocks(sidewalkWidth, Chunk.Width - sidewalkWidth, PlatMap.StreetLevel, PlatMap.StreetLevel + 1, Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkId);
		
		// light posts
		generateLightPost(chunk, sidewalkWidth - 1, sidewalkWidth - 1);
		generateLightPost(chunk, Chunk.Width - sidewalkWidth, Chunk.Width - sidewalkWidth);
	}
	
	protected void generateLightPost(Chunk chunk, int x, int z) {
		chunk.setBlock(x, PlatMap.StreetLevel, z, lightpostbaseId);
		chunk.setBlocks(x, PlatMap.StreetLevel + 1, PlatMap.StreetLevel + lightpostHeight + 1, z, lightpostId);
		chunk.setBlock(x, PlatMap.StreetLevel + lightpostHeight + 1, z, lightId);
	}
}
