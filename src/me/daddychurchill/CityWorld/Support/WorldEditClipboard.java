package me.daddychurchill.CityWorld.Support;

import java.io.File;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.SchematicFormat;

public class WorldEditClipboard extends ConstructClipboard {

	private CuboidClipboard clip;
	private int sizeX;
	private int sizeY;
	private int sizeZ;
	private int sizeBlocks;
	
	public WorldEditClipboard(WorldEditPlugin worldEditPlugin, File file) {
		super(file);
		
		try {
			clip = SchematicFormat.getFormat(file).load(file);
			sizeX = clip.getWidth();
			sizeZ = clip.getLength();
			sizeY = clip.getHeight();
			sizeBlocks = sizeX * sizeZ * sizeY;
			
			//TODO side load the schematic's YML if there is one
			//TODO ground level
			//TODO flipableX and flipableZ
			
			CityWorld.log.info("[CityWorld] Loaded schematic " + name);
			
		} catch (Exception e) {
			CityWorld.log.info("[CityWorld] " + e.getMessage());
		}
	}
	
	@Override
	public void Paste(WorldGenerator generator, RealChunk chunk, int blockX, int blockY, int blockZ) {
		Vector at = new Vector(blockX, blockY, blockZ);
		try {
			BukkitWorld bukkitWorld = new BukkitWorld(chunk.world);
			EditSession editSession = new EditSession(bukkitWorld, sizeBlocks);
			//editSession.setFastMode(true);
			clip.place(editSession, at, true);
		} catch (Exception e) {
			CityWorld.log.info("[CityWorld][WorldEdit] Place schematic " + name + " at " + at + " failed due to: " + e.getMessage());
		}
	}
	
//	public ConstructClipboard getFlippedX() {
//	}
//	
//	public ConstructClipboard getFlippedZ() {
//		
//	}
}
