package me.daddychurchill.CityWorld.Support.Materials;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.Material.STONE_SLAB;

import com.google.common.collect.Maps;

public class Slab extends Step {
	
	public Slab(SlabType type) {
		super(type.getMaterial());
	}
	
	public enum SlabType {
		STONE(0),
		SANDSTONE(1),
		WOODSTONE(2),
		COBBLESTONE(3),
		BRICK(4),
		SMOOTH_BRICK(5),
		NETHER_BRICK(6),
		QUARTZ(7);
		
		private final byte data;
		private static final Map<Byte, SlabType> BY_DATA;
		
		private SlabType(int data) {
			this.data = ((byte)data);
		}
		
		protected Material getMaterial() {
			switch (BY_DATA.get(this.data)) {
			default:
			case STONE:
				return Material.STONE;
			case SANDSTONE:
				return Material.SANDSTONE;
			case WOODSTONE:
				return Material.SPRUCE_WOOD;
			case COBBLESTONE:
				return Material.COBBLESTONE;
			case BRICK:
				return Material.BRICK;
			case SMOOTH_BRICK:
				return Material.SMOOTH_STONE;
			case NETHER_BRICK:
				return Material.NETHER_BRICK;
			case QUARTZ:
				return Material.QUARTZ;
			}
		}
		
		public static SlabType getSlabType(MaterialData material) {
			switch (material.getItemType()) {
			default:
			case STONE:
				return SlabType.STONE;
			case SANDSTONE:
				return SlabType.SANDSTONE;
			case WOOD:
				return SlabType.WOODSTONE;
			case COBBLESTONE:
				return SlabType.COBBLESTONE;
			case BRICK:
				return SlabType.BRICK;
			case SMOOTH_BRICK:
				return SlabType.SMOOTH_BRICK;
			case NETHER_BRICK:
				return SlabType.NETHER_BRICK;
			case QUARTZ:
				return SlabType.QUARTZ;
			}
		}
	
		@Deprecated
		public byte getData()
		{
			return this.data;
		}

		@Deprecated
		public static SlabType getByData(byte data)
		{
			return (SlabType)BY_DATA.get(Byte.valueOf(data));
		}

		static
		{
			BY_DATA = Maps.newHashMap();
			SlabType[] arrayOfSlabType;
			int i = (arrayOfSlabType = values()).length;
			for (int j = 0; j < i; j++)
			{
				SlabType type = arrayOfSlabType[j];
				BY_DATA.put(Byte.valueOf(type.data), type);
			}
		}
	}
}
