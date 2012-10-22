package me.daddychurchill.CityWorld;

import me.daddychurchill.CityWorld.Clipboard.ClipboardLot;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CityWorldEvent extends Event {
	private static final HandlerList	handlers	= new HandlerList();
	private int							chunkX;
	private int							chunkZ;
	private DataContext					context;
	private PlatLot						platlot;

	public CityWorldEvent(int x, int z, DataContext c, PlatLot p) {
		this.chunkX = x;
		this.chunkZ = z;
		this.context = c;
		this.platlot = p;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * @return the chunkX
	 */
	public int getChunkX() {
		return chunkX;
	}

	/**
	 * @return the chunkZ
	 */
	public int getChunkZ() {
		return chunkZ;
	}

	public DataContext getContext() {
		return context;
	}

	public PlatLot getPlatlot() {
		return platlot;
	}

	public String getContextName() {
		return context.schematicFamily.toString();
	}

	public boolean hasSchematic() {
		return platlot instanceof ClipboardLot;
	}

	public String getSchematicName() {
		if (hasSchematic()) {
			ClipboardLot clot = (ClipboardLot) platlot;
			return clot.getClip().name;
		}
		return null;
	}
}
