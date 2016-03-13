package me.daddychurchill.CityWorld.Plugins.WorldEdit;

import java.io.File;
import java.io.IOException;

import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.Clipboard;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.BlackMagic;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.CuboidClipboard.FlipDirection;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;

@SuppressWarnings("deprecation")
public class Clipboard_WorldEdit extends Clipboard {

	private BaseBlock[][][][] blocks;
	private int facingCount;
	private boolean flipableX = false;
	private boolean flipableZ = false;
//  private boolean Rotatable = false;
//	private boolean ScalableXZ = false;
//	private boolean ScalableY = false;
//	private int FloorHeightY = DataContext.FloorHeight;

	private final static String metaExtension = ".yml";
	private final static String tagGroundLevelY = "GroundLevelY";
	private final static String tagFlipableX = "FlipableX";
	private final static String tagFlipableZ = "FlipableZ";
//	private final static String tagScalableX = "ScalableX";
//	private final static String tagScalableZ = "ScalableZ";
//	private final static String tagScalableY = "ScalableY";
//	private final static String tagFloorHeightY = "FloorHeightY";
	private final static String tagOddsOfAppearance = "OddsOfAppearance";
	private final static String tagBroadcastLocation = "BroadcastLocation";
	private final static String tagDecayable = "Decayable";
	
	public Clipboard_WorldEdit(CityWorldGenerator generator, File file) throws Exception {
		super(generator, file);
	}
	
	@Override
	protected void load(CityWorldGenerator generator, File file) throws Exception {
		
		// prepare to read the meta data
		YamlConfiguration metaYaml = new YamlConfiguration();
		metaYaml.options().header("CityWorld/WorldEdit schematic configuration");
		metaYaml.options().copyDefaults(true);
		
		// add the defaults
		metaYaml.addDefault(tagGroundLevelY, groundLevelY);
		metaYaml.addDefault(tagFlipableX, flipableX);
		metaYaml.addDefault(tagFlipableZ, flipableZ);
//		metaYaml.addDefault(tagScalableX, ScalableX);
//		metaYaml.addDefault(tagScalableZ, ScalableZ);
//		metaYaml.addDefault(tagScalableY, ScalableY);
//		metaYaml.addDefault(tagFloorHeightY, FloorHeightY);
		metaYaml.addDefault(tagOddsOfAppearance, oddsOfAppearance);
		metaYaml.addDefault(tagBroadcastLocation, broadcastLocation);
		metaYaml.addDefault(tagDecayable, decayable);
		
		// start reading it
		File metaFile = new File(file.getAbsolutePath() + metaExtension);
		if (metaFile.exists()) {
			metaYaml.load(metaFile);
			groundLevelY = Math.max(0, metaYaml.getInt(tagGroundLevelY, groundLevelY));
			flipableX = metaYaml.getBoolean(tagFlipableX, flipableX);
			flipableZ = metaYaml.getBoolean(tagFlipableZ, flipableZ);
//			ScalableX = metaYaml.getBoolean(tagScalableX, ScalableX) && sizeX == 3;
//			ScalableZ = metaYaml.getBoolean(tagScalableZ, ScalableZ) && sizeZ == 3;
//			ScalableY = metaYaml.getBoolean(tagScalableY, ScalableY);
//			FloorHeightY = Math.max(2, Math.min(16, metaYaml.getInt(tagFloorHeightY, FloorHeightY)));
			oddsOfAppearance = Math.max(0.0, Math.min(1.0, metaYaml.getDouble(tagOddsOfAppearance, oddsOfAppearance)));
			broadcastLocation = metaYaml.getBoolean(tagBroadcastLocation, broadcastLocation);
			decayable = metaYaml.getBoolean(tagDecayable, decayable);
		}
		
		// load the actual blocks
		CuboidClipboard cuboid = SchematicFormat.getFormat(file).load(file);
		
		// how big is it?
		sizeX = cuboid.getWidth();
		sizeZ = cuboid.getLength();
		sizeY = cuboid.getHeight();
		
		//TODO Validate the size
		
		// try and save the meta data if we can
		try {
			metaYaml.save(metaFile);
		} catch (IOException e) {
			
			// we can recover from this... so eat it!
			generator.reportException("[WorldEdit] Could not resave " + metaFile.getAbsolutePath(), e);
		}
		
		//TODO I need to fix this code over in ClipboardLot when I figure out how to use the new WorldEdit schematic code
		/* 
		chunk.setBlocks(0, edgeX1, edgeY2, 0, 16, clip.edgeMaterial);//, clip.edgeData);
		chunk.setBlocks(edgeX2, 16, edgeY2, 0, 16, clip.edgeMaterial);//, clip.edgeData);
		chunk.setBlocks(edgeX1, edgeX2, edgeY2, 0, edgeZ1, clip.edgeMaterial);//, clip.edgeData);
		chunk.setBlocks(edgeX1, edgeX2, edgeY2, edgeZ2, 16, clip.edgeMaterial);//, clip.edgeData);
		 */
		
		//TODO I need to change the edgeMaterial and edgeData types to be Material and MaterialData or something like that
		// grab the edge block
		BaseBlock edge = cuboid.getBlock(new Vector(0, groundLevelY, 0));
		edgeMaterial = BlackMagic.getMaterial(edge.getType());
//		edgeData = edge.getData(); // TODO: One of these days I need to get this working again
		edgeRise = generator.oreProvider.surfaceMaterial.equals(edgeMaterial) ? 0 : 1;
		
		// allocate the blocks
		facingCount = 1;
		if (flipableX)
			facingCount *= 2;
		if (flipableZ)
			facingCount *= 2;
		
		//TODO we should allocate only facing count, then allocate the size based on what comes out of the rotation.. once I do rotation
		// allocate room
		blocks = new BaseBlock[facingCount][sizeX][sizeY][sizeZ];
		
		// copy the cubes for each direction
		copyCuboid(cuboid, 0); // normal one
		if (flipableX) {
			cuboid.flip(FlipDirection.WEST_EAST);
			copyCuboid(cuboid, 1);
			
			// z too? if so then make two more copies
			if (flipableZ) {
				cuboid.flip(FlipDirection.NORTH_SOUTH);
				copyCuboid(cuboid, 3);
				cuboid.flip(FlipDirection.WEST_EAST);
				copyCuboid(cuboid, 2);
			}
		
		// just z
		} else if (flipableZ) {
			cuboid.flip(FlipDirection.NORTH_SOUTH);
			copyCuboid(cuboid, 1);
		}
	}
	
	private void copyCuboid(CuboidClipboard cuboid, int facing) {
	    for (int x = 0; x < sizeX; x++)
	        for (int y = 0; y < sizeY; y++)
	          for (int z = 0; z < sizeZ; z++)
	        	  blocks[facing][x][y][z] = cuboid.getBlock(new Vector(x, y, z));
	}
	
	private EditSession getEditSession(CityWorldGenerator generator) {
		return new EditSession(new BukkitWorld(generator.getWorld()), blockCount);
	}
	
	private int getFacingIndex(BlockFace facing) {
		int result = 0;
		switch (facing) {
		case SOUTH:
			result = 0;
			break;
		case WEST:
			result = 1;
			break;
		case NORTH:
			result = 2;
			break;
		default: // case EAST:
			result = 3; //TODO: This was 2 for some reason... shouldn't it have been 3????
			break;
		}
		return Math.min(facingCount - 1, result);
	}
	
	@Override
	public void paste(CityWorldGenerator generator, RealBlocks chunk, BlockFace facing, int blockX, int blockY, int blockZ) {
		Vector at = new Vector(blockX, blockY, blockZ);
		try {
			EditSession editSession = getEditSession(generator);
			//editSession.setFastMode(true);
			place(editSession, getFacingIndex(facing), at, true);
		} catch (Exception e) {
			generator.reportException("[WorldEdit] Place schematic " + name + " at " + at + " failed", e);
		}
	}

	
//	@Override
//	public void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
//			int blockX, int blockY, int blockZ,
//			int x1, int x2, int y1, int y2, int z1, int z2) {
//		
////		generator.reportMessage("Partial paste: origin = " + at + " min = " + min + " max = " + max);
//		
//		try {
//			int iFacing = getFacingIndex(facing);
//			EditSession editSession = getEditSession(generator);
//			//editSession.setFastMode(true);
//			for (int x = x1; x < x2; x++)
//				for (int y = y1; y < y2; y++)
//					for (int z = z1; z < z2; z++) {
////						generator.reportMessage("facing = " + iFacing + 
////								" x = " + x +
////								" y = " + y + 
////								" z = " + z);
//						if (blocks[iFacing][x][y][z].isAir()) {
//							continue;
//						}
//						editSession.setBlock(new Vector(x, y, z).add(blockX, blockY, blockZ), 
//								blocks[iFacing][x][y][z]);
//					}
//		} catch (Exception e) {
//			e.printStackTrace();
//			generator.reportException("[WorldEdit] Partial place schematic " + name + " failed", e);
//		}
//	}

	//TODO remove the editSession need by directly setting the blocks in the chunk
	@Override
	public void paste(CityWorldGenerator generator, RealBlocks chunk, BlockFace facing, 
			int blockX, int blockY, int blockZ,
			int x1, int x2, int y1, int y2, int z1, int z2) {
		Vector at = new Vector(blockX, blockY, blockZ);
//		Vector min = new Vector(x1, y1, z1);
//		Vector max = new Vector(x2, y2, z2);
//		generator.reportMessage("Partial paste: origin = " + at + " min = " + min + " max = " + max);

		try {
			EditSession editSession = getEditSession(generator);
			//editSession.setFastMode(true);
			place(editSession, getFacingIndex(facing), at, true, x1, x2, y1, y2, z1, z2);
		} catch (Exception e) {
			generator.reportException("[WorldEdit] Partial place schematic " + name + " at " + at + " failed", e);
			generator.reportMessage("Info: " + 
									" facing = " + facing + 
									" size = " + sizeX + ", " + sizeZ + 
									" chunk = " + chunkX + ", " + chunkZ + 
//									" origin = "+ blockX + ", " + blockY + ", " + blockZ + 
									" min = " + x1 + ", "+ y1 + ", "+ z1 + 
									" max = " + x2 + ", "+ y2 + ", "+ z2);

			e.printStackTrace();
		}
	}

	//TODO Pilfered from WorldEdit's CuboidClipboard... I need to remove this once the other Place function is used
	private void place(EditSession editSession, int facing, Vector pos, boolean noAir)
			throws MaxChangedBlocksException {
		for (int x = 0; x < sizeX; x++)
			for (int y = 0; y < sizeY; y++)
				for (int z = 0; z < sizeZ; z++) {
					if ((noAir) && (blocks[facing][x][y][z].isAir())) {
						continue;
					}
					editSession.setBlock(new Vector(x, y, z).add(pos),
							blocks[facing][x][y][z]);
				}
	}

	//TODO if WorldEdit ever gets this functionality I need to remove the modified code
	private void place(EditSession editSession, int facing, Vector pos, boolean noAir,
			int x1, int x2, int y1, int y2, int z1, int z2) throws MaxChangedBlocksException {
		x1 = Math.max(x1, 0);
		x2 = Math.min(x2, sizeX);
		y1 = Math.max(y1, 0);
		y2 = Math.min(y2, sizeY);
		z1 = Math.max(z1, 0);
		z2 = Math.min(z2, sizeZ);
		for (int x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++)
				for (int z = z1; z < z2; z++) {
					if ((noAir) && (blocks[facing][x][y][z].isAir())) {
						continue;
					}
					editSession.setBlock(new Vector(x, y, z).add(pos), blocks[facing][x][y][z]);
				}
	}	
}
