package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class AstralNexusLot extends AstralStructureLot {

	public enum NexusSegment { NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST };
	private NexusSegment segment;
	
	public AstralNexusLot(PlatMap platmap, int chunkX, int chunkZ, NexusSegment segment) {
		super(platmap, chunkX, chunkZ);
		
		this.segment = segment;
	}
	
	@Override
	protected boolean getSuperSpecial() {
		return true;
	}
	
	public final static int chunkX = 5;
	public final static int chunkZ = 5;
	public final static int blockX = (chunkX + 1) * RealBlocks.sectionBlockWidth;
	public final static int blockZ = (chunkZ + 1) * RealBlocks.sectionBlockWidth;

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		int y = generator.seaLevel + AstralTownEmptyLot.aboveSeaLevel - 1;
		
		switch (segment) {
		case NORTHWEST:
//			chunk.setBlocks(0, 16, y - 5, 0, 16, Material.GOLD_BLOCK);
			generateNorthWest(generator, chunk, y);
			break;
		case NORTHEAST:
//			chunk.setBlocks(0, 16, y - 5, 0, 16, Material.EMERALD_BLOCK);
			generateNorthEast(generator, chunk, y);
			break;
		case SOUTHWEST:
//			chunk.setBlocks(0, 16, y - 5, 0, 16, Material.DIAMOND_BLOCK);
			generateSouthWest(generator, chunk, y);
			break;
		case SOUTHEAST:
//			chunk.setBlocks(0, 16, y - 5, 0, 16, Material.IRON_BLOCK);
			generateSouthEast(generator, chunk, y);
			break;
		}
	}
	
	//TODO Map room (the local area)
	//TODO Transportation room (railroads to everywhere)
	//TODO Supply room (chests of happiness)
	//TODO Spawn room (where you arrive)
	
	private void generateNorthWest(CityWorldGenerator generator, RealBlocks chunk, int y) {
//		chunk.setBlocks(0, 16, y - 5, y + 20, 0, 16, Material.STAINED_GLASS);
		for (int i = 0; i < 16; i++) {
			Material material = getRoofMaterial(i);
			chunk.setBlocks(i, 16, y + i + 1, i, i + 1, material);
			chunk.setBlocks(i, i + 1, y + i + 1, i, 16, material);
			chunk.setBlocks(i + 1, 16, y + i + 1, i + 1, 16, Material.AIR);
			
			// special stuff for the base bit
			if (i == 0) {
				chunk.setBlocks(i, 16, y + i, i, i + 1, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(i, i + 1, y + i, i, 16, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(i + 1, 16, y + i, i + 1, 16, Material.STAINED_GLASS);
				
				chunk.setBlocks(i, 16, y + i - 2, y + i, i, i + 1, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(i, i + 1, y + i - 2, y + i, i, 16, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(i + 1, 16, y + i - 2, y + i, i + 1, 16, Material.AIR);
				
				chunk.setBlocks(i + 1, 16, y + i - 3, i + 1, 16, AstralTownEmptyLot.materialBase);
				
				chunk.setBlocks(2, 5, 1, y + i - 3, 2, 5, AstralTownEmptyLot.materialSupport);
			}
		}
		
		// now the map
		for (int x = 1; x < 16; x++) {
			for (int z = 1; z < 16; z++) {
				int originX = (16 - x) * -10;
				int originZ = (16 - z) * -10;
				DataContext context = generator.shapeProvider.getContext(originX, originZ);
				if (context != null) {
					chunk.setBlock(x, y - 2, z, context.getMapRepresentation());
				}
			}
		}
	}

	private void generateNorthEast(CityWorldGenerator generator, RealBlocks chunk, int y) {
//		chunk.setBlocks(0, 16, y - 5, y + 20, 0, 16, Material.STAINED_GLASS);
		for (int i = 0; i < 16; i++) {
			Material material = getRoofMaterial(i);
			chunk.setBlocks(0, 16 - i, y + i + 1, i, i + 1, material);
			chunk.setBlocks(15 - i, 16 - i, y + i + 1, i + 1, 16, material);
			chunk.setBlocks(0, 15 - i, y + i + 1, i + 1, 16, Material.AIR);
			
			// special stuff for the base bit
			if (i == 0) {
				chunk.setBlocks(0, 16 - i, y + i, i, i + 1, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(15 - i, 16 - i, y + i, i + 1, 16, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(0, 15 - i, y + i, i + 1, 16, Material.STAINED_GLASS);
				
				chunk.setBlocks(0, 16 - i, y + i - 2, y + i, i, i + 1, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(15 - i, 16 - i, y + i - 2, y + i, i + 1, 16, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(0, 15 - i, y + i - 2, y + i, i + 1, 16, Material.AIR);
				
				chunk.setBlocks(0, 15 - i, y + i - 3, i + 1, 16, AstralTownEmptyLot.materialBase);
				
				chunk.setBlocks(11, 14, 1, y + i - 3, 2, 5, AstralTownEmptyLot.materialSupport);
			}
		}

		// now the map
		for (int x = 0; x < 15; x++) {
			for (int z = 1; z < 16; z++) {
				int originX = x * 10;
				int originZ = (16 - z) * -10;
				DataContext context = generator.shapeProvider.getContext(originX, originZ);
				if (context != null) {
					chunk.setBlock(x, y - 2, z, context.getMapRepresentation());
				}
			}
		}
	}

	private void generateSouthWest(CityWorldGenerator generator, RealBlocks chunk, int y) {
//		chunk.setBlocks(0, 16, y - 5, y + 20, 0, 16, Material.STAINED_GLASS);
		for (int i = 0; i < 16; i++) {
			Material material = getRoofMaterial(i);
			chunk.setBlocks(i, i + 1, y + i + 1, 0, 16 - i, material);
			chunk.setBlocks(i + 1, 16, y + i + 1, 15 - i, 16 - i, material);
			chunk.setBlocks(i + 1, 16, y + i + 1, 0, 15 - i, Material.AIR);
			
			// special stuff for the base bit
			if (i == 0) {
				chunk.setBlocks(i, i + 1, y + i, 0, 16 - i, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(i + 1, 16, y + i, 15 - i, 16 - i, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(i + 1, 16, y + i, 0, 15 - i, Material.STAINED_GLASS);
				
				chunk.setBlocks(i, i + 1, y + i - 2, y + i, 0, 16 - i, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(i + 1, 16, y + i - 2, y + i, 15 - i, 16 - i, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(i + 1, 16, y + i - 2, y + i, 0, 15 - i, Material.AIR);
				
				chunk.setBlocks(i + 1, 16, y + i - 3, 0, 15 - i, AstralTownEmptyLot.materialBase);
				
				chunk.setBlocks(2, 5, 1, y + i - 3, 11, 14, AstralTownEmptyLot.materialSupport);
			}
		}

		// now the map
		for (int x = 1; x < 16; x++) {
			for (int z = 0; z < 15; z++) {
				int originX = (16 - x) * -10;
				int originZ = z * 10;
				DataContext context = generator.shapeProvider.getContext(originX, originZ);
				if (context != null) {
					chunk.setBlock(x, y - 2, z, context.getMapRepresentation());
				}
			}
		}
	}
	
	private void generateSouthEast(CityWorldGenerator generator, RealBlocks chunk, int y) {
//		chunk.setBlocks(0, 16, y - 5, y + 20, 0, 16, Material.STAINED_GLASS);
		for (int i = 0; i < 16; i++) {
			Material material = getRoofMaterial(i);
			chunk.setBlocks(15 - i, 16 - i, y + i + 1, 0, 16 - i, material);
			chunk.setBlocks(0, 15 - i, y + i + 1, 15 - i, 16 - i, material);
			chunk.setBlocks(0, 15 - i, y + i + 1, 0, 15 - i, Material.AIR);
			
			// special stuff for the base bit
			if (i == 0) {
				chunk.setBlocks(15 - i, 16 - i, y + i, 0, 16 - i, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(0, 15 - i, y + i, 15 - i, 16 - i, AstralTownEmptyLot.materialBase);
				chunk.setBlocks(0, 15 - i, y + i, 0, 15 - i, Material.STAINED_GLASS);
				
				chunk.setBlocks(15 - i, 16 - i, y + i - 2, y + i, 0, 16 - i, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(0, 15 - i, y + i - 2, y + i, 15 - i, 16 - i, AstralTownEmptyLot.materialCross);
				chunk.setBlocks(0, 15 - i, y + i - 2, y + i, 0, 15 - i, Material.AIR);
				
				chunk.setBlocks(0, 15 - i, y + i - 3, 0, 15 - i, AstralTownEmptyLot.materialBase);
				
				chunk.setBlocks(11, 14, 1, y + i - 3, 11, 14, AstralTownEmptyLot.materialSupport);
			}
		}

		// now the map
		for (int x = 0; x < 15; x++) {
			for (int z = 0; z < 15; z++) {
				int originX = x * 10;
				int originZ = z * 10;
				DataContext context = generator.shapeProvider.getContext(originX, originZ);
				if (context != null) {
					chunk.setBlock(x, y - 2, z, context.getMapRepresentation());
				}
			}
		}
	}
	
	private Material getRoofMaterial(int i) {
		if (i % 3 == 0)
			return AstralTownEmptyLot.materialCross;
		else
			return Material.STAINED_GLASS;
	}
}
