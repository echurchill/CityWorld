package me.daddychurchill.CityWorld;

import me.daddychurchill.CityWorld.Clipboard.ClipboardLot;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CityWorldEvent extends Event {
	private Chunk chunk;
	private PlatMap platmap;
	private PlatLot platlot;

	public CityWorldEvent(Chunk chunk, PlatMap platmap, PlatLot platlot) {
		super();
		this.chunk = chunk;
		this.platmap = platmap;
		this.platlot = platlot;
	}

	private static final HandlerList handlers = new HandlerList();
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * @return the chunk's X
	 */
	public int getChunkX() {
		return chunk.getX();
	}

	/**
	 * @return the chunk's Z
	 */
	public int getChunkZ() {
		return chunk.getZ();
	}
	
	/**
	 * @return the chunk reference
	 */
	public Chunk getChunk() {
		return chunk;
	}

	/**
	 * @return the chunk's CityWorld PlatMap
	 */
	public PlatMap getPlatMap() {
		return platmap;
	}

	/**
	 * @return the chunk's CityWorld DataContext
	 */
	public DataContext getContext() {
		return platmap.context;
	}

	/**
	 * @return the chunk's CityWorld platlot
	 */
	public PlatLot getPlatlot() {
		return platlot;
	}

	/**
	 * @return the chunk's CityWorld general schematic family name
	 */
	public String getContextName() {
		return platmap.context.getSchematicFamily().toString();
	}

	/**
	 * @return the true if Chunk's CityWorld platlot uses a schematic
	 */
	public boolean hasSchematic() {
		return platlot instanceof ClipboardLot;
	}

	/**
	 * @return the Chunk's CityWorld platLot schematic if it uses one
	 */
	public String getSchematicName() {
		if (hasSchematic()) {
			ClipboardLot clot = (ClipboardLot) platlot;
			return clot.getClip().name;
		}
		return null;
	}
}
