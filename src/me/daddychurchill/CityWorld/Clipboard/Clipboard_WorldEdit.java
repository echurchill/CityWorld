package me.daddychurchill.CityWorld.Clipboard;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.CuboidClipboard.FlipDirection;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

public class Clipboard_WorldEdit extends Clipboard {

	private BaseBlock[][][][] blocks;
	private int facingCount;
	private int sizeBlocks;
	private int GroundLevelY = 0;
	private boolean FlipableX = true;
	private boolean FlipableZ = true;
	private boolean ScalableXZ = false;
	private boolean ScalableY = false;
	private int FloorHeightY = DataContext.FloorHeight;
	private double OddsOfAppearance = 0.10;

	private final static String metaExtension = ".yml";
	private final static String tagGroundLevelY = "GroundLevelY";
	private final static String tagFlipableX = "FlipableX";
	private final static String tagFlipableZ = "FlipableZ";
	private final static String tagScalableXZ = "ScalableXZ";
	private final static String tagScalableY = "ScalableY";
	private final static String tagFloorHeightY = "FloorHeightY";
	private final static String tagOddsOfAppearance = "OddsOfAppearance";
	
	public Clipboard_WorldEdit(WorldGenerator generator, File file) throws IOException, DataException {
		super(generator, file.getAbsolutePath());
		
//		// prepare to read the meta data
//		YamlConfiguration metaYaml = new YamlConfiguration();
//		metaYaml.addDefault(tagGroundLevelY, GroundLevelY);
//		metaYaml.addDefault(tagFlipableX, FlipableX);
//		metaYaml.addDefault(tagFlipableZ, FlipableZ);
//		metaYaml.addDefault(tagScalableXZ, ScalableXZ);
//		metaYaml.addDefault(tagScalableY, ScalableY);
//		metaYaml.addDefault(tagFloorHeightY, FloorHeightY);
//		metaYaml.addDefault(tagOddsOfAppearance, OddsOfAppearance);
//		
//		// start reading it
//		File metaFile = new File(file.getAbsolutePath()+ metaExtension);
//		if (metaFile.exists()) {
//			GroundLevelY = Math.max(0, Math.min(generator.height, metaYaml.getInt(tagGroundLevelY, GroundLevelY)));
//			FlipableX = metaYaml.getBoolean(tagFlipableX, FlipableX);
//			FlipableZ = metaYaml.getBoolean(tagFlipableZ, FlipableZ);
//			ScalableXZ = metaYaml.getBoolean(tagScalableXZ, ScalableXZ);
//			ScalableY = metaYaml.getBoolean(tagScalableY, ScalableY);
//			FloorHeightY = Math.max(2, Math.min(16, metaYaml.getInt(tagFloorHeightY, FloorHeightY)));
//			OddsOfAppearance = Math.max(0.0, Math.min(1.0, metaYaml.getDouble(tagOddsOfAppearance, OddsOfAppearance)));
//		}
//		
//		// try and save the meta data if we can
//		try {
//			metaYaml.save(metaFile);
//		} catch (IOException e) {
//			CityWorld.reportError("[Clipboard] Could not resave " + metaFile.getAbsolutePath(), e);
//		}
//		
		// load the actual blocks
		CuboidClipboard cuboid = SchematicFormat.getFormat(file).load(file);
		
		// how big is it?
		sizeX = cuboid.getWidth();
		sizeZ = cuboid.getLength();
		sizeY = cuboid.getHeight();
		sizeBlocks = sizeX * sizeZ * sizeY;
		
		// allocate the blocks
		facingCount = 1;
		if (FlipableX)
			facingCount *= 2;
		if (FlipableZ)
			facingCount *= 2;
		
		// allocate room
		blocks = new BaseBlock[facingCount][sizeX][sizeY][sizeZ];
		
		// copy the cubes for each direction
		copyCuboid(cuboid, 0); // normal one
		if (FlipableX) {
			cuboid.flip(FlipDirection.WEST_EAST);
			copyCuboid(cuboid, 1);
			
			// z too? if so then make two more copies
			if (FlipableZ) {
				cuboid.flip(FlipDirection.NORTH_SOUTH);
				copyCuboid(cuboid, 3);
				cuboid.flip(FlipDirection.WEST_EAST);
				copyCuboid(cuboid, 2);
			}
		
		// just z
		} else if (FlipableZ) {
			cuboid.flip(FlipDirection.NORTH_SOUTH);
			copyCuboid(cuboid, 1);
		}
	}
	
	private void copyCuboid(CuboidClipboard cuboid, int facing) {
	    for (int x = 0; x < sizeX; x++)
	        for (int y = 0; y < sizeY; y++)
	          for (int z = 0; z < sizeZ; z++)
	        	  blocks[facing][x][y][z] = cuboid.getPoint(new Vector(x, y, z));
	}
	
	/*
Generator Style (Normal, Floating, etc.)
               Environment Style (Normal, Nether, The_End, etc.)
                              Context Style (Highrise, Neighborhoods, etc.)
                                             Construct.schematic
                                             Construct.schematic.yml

*.*.yml
GroundLevelY integer
FlipableX boolean
FlipableZ boolean
ScalableXZ boolean
ScalableY boolean
FloorHeightY integer
OddsOfAppearance double (0.0-1.0)

CityTemplate chunksX chunksZ floors floorHeight basements

	 */
	
	
	
	private EditSession getEditSession(WorldGenerator generator) {
		return new EditSession(new BukkitWorld(generator.getWorld()), sizeBlocks);
	}
	
	private int getFacingIndex(Direction.Facing facing) {
		switch (facing) {
		case NORTH:
			return 0;
		case SOUTH:
			return Math.max(facingCount, 1);
		case WEST:
			return Math.max(facingCount, 2);
		default: // case EAST:
			return Math.max(facingCount, 3);
		}
	}
	
	@Override
	public void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, int blockX, int blockY, int blockZ) {
		Vector at = new Vector(blockX, blockY, blockZ);
		try {
			EditSession editSession = getEditSession(generator);
			//editSession.setFastMode(true);
			place(editSession, getFacingIndex(facing), at, true);
		} catch (Exception e) {
			CityWorld.reportException("[WorldEdit] Place schematic " + name + " at " + at + " failed", e);
		}
	}

	@Override
	public void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
			int blockX, int blockY, int blockZ,
			int x1, int x2, int y1, int y2, int z1, int z2) {
		Vector at = new Vector(blockX, blockY, blockZ);
		Vector min = new Vector(x1, y1, z1);
		Vector max = new Vector(x2, y2, z2);
		try {
			EditSession editSession = getEditSession(generator);
			//editSession.setFastMode(true);
			place(editSession, getFacingIndex(facing), at, true, min, max);
		} catch (Exception e) {
			CityWorld.reportException("[WorldEdit] Partial place schematic " + name + " at " + at + " failed", e);
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
			Vector min, Vector max) throws MaxChangedBlocksException {
		int x1 = Math.max(min.getBlockX(), 0);
		int x2 = Math.min(max.getBlockX(), sizeX);
		int y1 = Math.max(min.getBlockY(), 0);
		int y2 = Math.min(max.getBlockY(), sizeY);
		int z1 = Math.max(min.getBlockZ(), 0);
		int z2 = Math.min(max.getBlockZ(), sizeZ);
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
