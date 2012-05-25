package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RealChunk extends SupportChunk {
	private Chunk chunk;
	private boolean doPhysics;

	public RealChunk(WorldGenerator aGenerator, Random aRandom, Chunk aChunk) {
		super(aGenerator, aRandom);
		
		chunk = aChunk;
		chunkX = chunk.getX();
		chunkZ = chunk.getZ();
		
		doPhysics = false;
	}
	
	public boolean getDoPhysics() {
		return doPhysics;
	}
	
	public void setDoPhysics(boolean dophysics) {
		doPhysics = dophysics;
	}

	public void setBlock(int x, int y, int z, Material material) {
		chunk.getBlock(x, y, z).setTypeId(material.getId(), doPhysics);
	}

	public void setBlock(int x, int y, int z, int type, byte data) {
		chunk.getBlock(x, y, z).setTypeIdAndData(type, data, doPhysics);
	}
	
	public void setBlock(int x, int y, int z, Material material, boolean aDoPhysics) {
		chunk.getBlock(x, y, z).setTypeId(material.getId(), aDoPhysics);
	}

	public void setBlock(int x, int y, int z, int type, byte data, boolean aDoPhysics) {
		chunk.getBlock(x, y, z).setTypeIdAndData(type, data, aDoPhysics);
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
		setBlocks(0, width, blocky, blocky + 1, 0, width, material);
		return blocky + 1;
	}

	public int setLayer(int blocky, int height, Material material) {
		setBlocks(0, width, blocky, blocky + height, 0, width, material);
		return blocky + height;
	}

	public int setLayer(int blocky, int height, int inset, Material material) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
		return blocky + height;
	}
	
	private void setDoor(int x, int y, int z, int doorId, Direction.Door direction) {
//		chunk.getBlock(x, y + 1, z).setTypeIdAndData(doorId, (byte) (direction.getData() + 8), doPhysics);
		chunk.getBlock(x, y + 1, z).setTypeIdAndData(doorId, (byte) 8, false);
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

	public void setTorch(int x, int y, int z, Material material, Direction.Torch direction) {
		setBlock(x, y, z, material.getId(), direction.getData());
	}
	
	public void setChest(int x, int y, int z, Direction.Chest direction, ItemStack... items) {
		Block block = chunk.getBlock(x, y, z);
		block.setTypeIdAndData(Material.CHEST.getId(), direction.getData(), true);
		if (items.length > 0) {
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getInventory();
			inv.clear();
			inv.addItem(items);
		}
	}

	public void setSpawner(int x, int y, int z, EntityType aType) {
		Block block = chunk.getBlock(x, y, z);
		block.setType(Material.MOB_SPAWNER);
		CreatureSpawner spawner = (CreatureSpawner) block.getState();
		spawner.setSpawnedType(aType);
		spawner.update(true);
	}
}
