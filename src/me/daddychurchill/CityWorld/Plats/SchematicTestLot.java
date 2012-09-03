package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.ConstructClipboard;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class SchematicTestLot extends IsolatedLot {

	protected final static byte curbId = (byte) Material.DOUBLE_STEP.getId();
	
	public SchematicTestLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.ROUNDABOUT;
	}

	@Override
	public boolean isPlaceableAt(WorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inRoadRange(chunkX, chunkZ);
	}
	
	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.streetLevel + 1;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {

		// where to start?
		int y1 = generator.streetLevel + 1;
		chunk.setLayer(y1, curbId);
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		// where to start?
		int blockX = chunk.getOriginX();
		int blockZ = chunk.getOriginZ();
		int blockY = generator.streetLevel + 2;
		
		// try to paste it
		ConstructClipboard clip = generator.pasteProvider.findConstruct(generator, chunkRandom, 16, 16, "");
		if (clip != null) {
			clip.Paste(generator, chunk, blockX, blockY, blockZ);
		}
	}
}
