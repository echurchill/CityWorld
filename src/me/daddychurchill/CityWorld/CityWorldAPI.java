package me.daddychurchill.CityWorld;

import java.util.HashMap;

import me.daddychurchill.CityWorld.Clipboard.ClipboardLot;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.CityWorldGenerator;

import org.bukkit.Chunk;

public class CityWorldAPI {
	// This class was created by Sablednah
	// https://github.com/echurchill/CityWorld/pull/4
	// https://github.com/echurchill/CityWorld/pull/5 (but with some changes)
	
	private CityWorld plugin;

	public CityWorldAPI(CityWorld plugin) {
		this.plugin = plugin;
	}
	
	public CityWorld getWorld() {
		return plugin;
	}

	public HashMap<String, String> getFullInfo(Chunk c) throws IllegalArgumentException, IndexOutOfBoundsException {
		//Unneeded debug info
		//plugin.reportMessage(CityWorld.pluginName + " API Full info called");

		HashMap<String, String> info = new HashMap<String, String>();
		String classname;

		CityWorldGenerator gen = (CityWorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(chunkX, chunkZ);
		if (platmap == null)
			throw new IllegalArgumentException("PlatMap not found for specified chunk");
		
		// figure out the lot
		PlatLot lot = platmap.getMapLot(chunkX, chunkZ);

		// add context type to returned hashmap
		info.put("context", platmap.context.schematicFamily.toString());

		// add last part of context classname to hashmap
		classname = platmap.context.getClass().getName();
		classname = classname.substring(classname.lastIndexOf(".") + 1);
		info.put("contextclass", classname);

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

	public int getRoadCount(Chunk c) throws IllegalArgumentException {

		CityWorldGenerator gen = (CityWorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(chunkX, chunkZ);
		if (platmap == null)
			throw new IllegalArgumentException("PlatMap not found for specified chunk");
		
		return platmap.getNumberOfRoads();
	}

	public String getContextName(Chunk c) throws IllegalArgumentException {
		CityWorldGenerator gen = (CityWorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(chunkX, chunkZ);
		if (platmap == null)
			throw new IllegalArgumentException("PlatMap not found for specified chunk");

		return platmap.context.schematicFamily.toString();
	}

	public DataContext getContext(Chunk c) throws IllegalArgumentException {

		CityWorldGenerator gen = (CityWorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(chunkX, chunkZ);
		if (platmap == null)
			throw new IllegalArgumentException("PlatMap not found for specified chunk");
		
		return platmap.context;
	}

	public String getLotStyleName(Chunk c) throws IllegalArgumentException, IndexOutOfBoundsException {

		CityWorldGenerator gen = (CityWorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(chunkX, chunkZ);
		if (platmap == null)
			throw new IllegalArgumentException("PlatMap not found for specified chunk");
		
		// figure out the lot
		PlatLot lot = platmap.getMapLot(chunkX, chunkZ);
		return lot.style.toString();
	}

	public LotStyle getLotStyle(Chunk c) throws IllegalArgumentException, IndexOutOfBoundsException {

		CityWorldGenerator gen = (CityWorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(chunkX, chunkZ);
		if (platmap == null)
			throw new IllegalArgumentException("PlatMap not found for specified chunk");
		
		// figure out the lot
		PlatLot lot = platmap.getMapLot(chunkX, chunkZ);
		return lot.style;
	}

	public String getSchematicName(Chunk c) throws IllegalArgumentException, IndexOutOfBoundsException {
		String name = null;

		CityWorldGenerator gen = (CityWorldGenerator) c.getWorld().getGenerator();
		int chunkX = c.getX();
		int chunkZ = c.getZ();

		// Setup info - seams to require this to prevent NPE's when server is restarted.
		gen.initializeWorldInfo(c.getWorld());

		// figure out what everything looks like. Again :/
		PlatMap platmap = gen.getPlatMap(chunkX, chunkZ);
		if (platmap == null)
			throw new IllegalArgumentException("PlatMap not found for specified chunk");
		
		// figure out the lot
		PlatLot lot = platmap.getMapLot(chunkX, chunkZ);
		
		// Now check if lot is a clipboardlot to get schematic name
		if (lot instanceof ClipboardLot) {
			ClipboardLot clot = (ClipboardLot) lot;
			name = clot.getClip().name;
		}

		return name;
	}
}
