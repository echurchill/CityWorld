package me.daddychurchill.CityWorld.Support;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;

public class RealChunk {
	private Chunk chunk;
	public final static int Width = 16;
	public final static int Height = 128;

	public int X;
	public int Z;
	private boolean doPhysics;

	public RealChunk(Chunk chunk) {
		super();
		this.chunk = chunk;
		this.X = chunk.getX();
		this.Z = chunk.getZ();
		this.doPhysics = false;
	}
	
	public boolean getDoPhysics() {
		return doPhysics;
	}
	
	public void setDoPhysics(boolean dophysics) {
		doPhysics = dophysics;
	}

	public void setBlock(int x, int y, int z, Material material) {
		chunk.getBlock(x, y, z).setType(material);
	}

	public void setBlock(int x, int y, int z, int type, byte data) {
		chunk.getBlock(x, y, z).setTypeIdAndData(type, data, doPhysics);
	}
	
	public Location getBlockLocation(int x, int y, int z) {
		return chunk.getBlock(x, y, z).getLocation();
	}

	public Material getBlock(int x, int y, int z) {
		return chunk.getBlock(x, y, z).getType();
	}

	public void setBlocks(int x, int y1, int y2, int z, Material material) {
		for (int y = y1; y < y2; y++)
			chunk.getBlock(x, y, z).setType(material);
	}

	public void setBlocks(int x, int y1, int y2, int z, int type, byte data) {
		for (int y = y1; y < y2; y++)
			chunk.getBlock(x, y, z).setTypeIdAndData(type, data, doPhysics);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					chunk.getBlock(x, y, z).setType(material);
				}
			}
		}
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, int type, byte data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					chunk.getBlock(x, y, z).setTypeIdAndData(type, data, doPhysics);
				}
			}
		}
	}

	public int setLayer(int blocky, Material material) {
		setBlocks(0, Width, blocky, blocky + 1, 0, Width, material);
		return blocky + 1;
	}

	public int setLayer(int blocky, int height, Material material) {
		setBlocks(0, Width, blocky, blocky + height, 0, Width, material);
		return blocky + height;
	}

	public int setLayer(int blocky, int height, int inset, Material material) {
		setBlocks(inset, Width - inset, blocky, blocky + height, inset, Width
				- inset, material);
		return blocky + height;
	}
	
	private void setDoor(int x, int y, int z, int doorId, Direction.Door direction) {
		chunk.getBlock(x, y + 1, z).setTypeIdAndData(doorId, (byte) (direction.getData() + 8), doPhysics);
		chunk.getBlock(x, y    , z).setTypeIdAndData(doorId, direction.getData(), doPhysics);
	}

	public void setWoodenDoor(int x, int y, int z, Direction.Door direction) {
		setDoor(x, y, z, Material.WOODEN_DOOR.getId(), direction);
	}

	public void setIronDoor(int x, int y, int z, Direction.Door direction) {
		setDoor(x, y, z, Material.IRON_DOOR_BLOCK.getId(), direction);
	}

	public void setTrapDoor(int x, int y, int z, Direction.TrapDoor direction) {
		setBlock(x, y, z, Material.TRAP_DOOR.getId(), direction.getData());
	}

	public void setLadder(int x, int y1, int y2, int z, Direction.Ladder direction) {
		int ladderId = Material.LADDER.getId();
		byte data = direction.getData();
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, ladderId, data);
	}

	public void setStair(int x, int y, int z, Material material, Direction.Stair direction) {
		setBlock(x, y, z, material.getId(), direction.getData());
	}

	public void setStair(int x, int y, int z, int type, Direction.Stair direction) {
		setBlock(x, y, z, type, direction.getData());
	}

	public void setVine(int x, int y, int z, Direction.Vine direction) {
		setBlock(x, y, z, Material.VINE.getId(), direction.getData());
	}

}
