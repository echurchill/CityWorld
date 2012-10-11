package me.daddychurchill.CityWorld;

import java.util.HashMap;

import me.daddychurchill.CityWorld.Clipboard.ClipboardLot;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Chunk;
import org.bukkit.plugin.Plugin;

public class CityWorldAPI {
	private CityWorld	plugin;

	public CityWorldAPI(Plugin p) {
		this.plugin = (CityWorld) p;
	}

	public HashMap<String, String> getFullInfo(Chunk c) {
		plugin.reportMessage(CityWorld.pluginName + " API Full info called");

		HashMap<String, String> info = new HashMap<String, String>();
		String classname;

		WorldGenerator gen = (WorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();
		ByteChunk byteChunk = new ByteChunk(gen, chunkX, chunkZ);

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(byteChunk, chunkX, chunkZ);

		// add context type to returned hashmap
		info.put("context", platmap.context.schematicFamily.toString());

		// add last part of context classname to hashmap
		classname = platmap.context.getClass().getName();
		classname = classname.substring(classname.lastIndexOf(".") + 1);
		info.put("contextclass", classname);

		// Now time to get the lot info
		int platX = chunkX - platmap.originX;
		int platZ = chunkZ - platmap.originZ;

		PlatLot[][] lots = platmap.getPlatLots();
		PlatLot lot = lots[platX][platZ];

		// add lot style to hashmap
		info.put("lot", lot.style.toString());

		// add last part of lot style classname to hashmap
		classname = lot.getClass().getName();
		classname = classname.substring(classname.lastIndexOf(".") + 1);
		info.put("lotclass", classname);

		// Now check if lot is a clipboardlot to get schematic name
		if (lot instanceof ClipboardLot) {
			ClipboardLot clot = (ClipboardLot) lot;
			info.put("schematic", clot.getClip().name);
		}

		return info;
	}

	public int getRoadCount(Chunk c) {

		WorldGenerator gen = (WorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();
		ByteChunk byteChunk = new ByteChunk(gen, chunkX, chunkZ);

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(byteChunk, chunkX, chunkZ);

		return platmap.getNumberOfRoads();
	}

	public String getContextName(Chunk c) {
		WorldGenerator gen = (WorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();
		ByteChunk byteChunk = new ByteChunk(gen, chunkX, chunkZ);

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(byteChunk, chunkX, chunkZ);

		return platmap.context.schematicFamily.toString();
	}

	public DataContext getContext(Chunk c) {

		WorldGenerator gen = (WorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();
		ByteChunk byteChunk = new ByteChunk(gen, chunkX, chunkZ);

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(byteChunk, chunkX, chunkZ);

		return platmap.context;
	}

	public String getLotStyleName(Chunk c) {

		WorldGenerator gen = (WorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();
		ByteChunk byteChunk = new ByteChunk(gen, chunkX, chunkZ);

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(byteChunk, chunkX, chunkZ);

		// Now time to get the lot info
		int platX = chunkX - platmap.originX;
		int platZ = chunkZ - platmap.originZ;

		PlatLot[][] lots = platmap.getPlatLots();
		PlatLot lot = lots[platX][platZ];

		return lot.style.toString();
	}

	public LotStyle getLotStyle(Chunk c) {

		WorldGenerator gen = (WorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();
		ByteChunk byteChunk = new ByteChunk(gen, chunkX, chunkZ);

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(byteChunk, chunkX, chunkZ);

		// Now time to get the lot info
		int platX = chunkX - platmap.originX;
		int platZ = chunkZ - platmap.originZ;

		PlatLot[][] lots = platmap.getPlatLots();
		PlatLot lot = lots[platX][platZ];

		return lot.style;
	}

	public String getSchematicName(Chunk c) {

		WorldGenerator gen = (WorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();
		ByteChunk byteChunk = new ByteChunk(gen, chunkX, chunkZ);

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(byteChunk, chunkX, chunkZ);

		// Now time to get the lot info
		int platX = chunkX - platmap.originX;
		int platZ = chunkZ - platmap.originZ;

		PlatLot[][] lots = platmap.getPlatLots();
		PlatLot lot = lots[platX][platZ];

		String name = null;
		// Now check if lot is a clipboardlot to get schematic name
		if (lot instanceof ClipboardLot) {
			ClipboardLot clot = (ClipboardLot) lot;
			name = clot.getClip().name;
		}

		return name;
	}
}
