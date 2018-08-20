package me.daddychurchill.CityWorld.Support.Materials;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import com.google.common.collect.Maps;

public class Stone extends MaterialData {
	
	public Stone() {
		super(Material.STONE);
	}
	
	public Stone(StoneType type) {
		this();
		setType(type);
	}
	
	@SuppressWarnings("deprecation")
	public StoneType getType() {
		return StoneType.getByData(getData());
	}
	
	@SuppressWarnings("deprecation")
	public void setType(StoneType type) {
		setData(type.getData());
	}
	
	public Stone clone() {
		return (Stone)super.clone();
	}
	
	public String toString() {
		return super.toString() + "." + getType();
	}

	public enum StoneType {
		STONE(0),
		GRANITE(1), POLISHED_GRANITE(2),
		DIORITE(3), POLISHED_DIORITE(4),
		ANDESITE(5), POLISHED_ANDESITE(6);
		
		private final byte data;
		private static final Map<Byte, StoneType> BY_DATA;
		
		private StoneType(int data) {
			this.data = ((byte)data);
		}
	
		@Deprecated
		public byte getData()
		{
			return this.data;
		}

		@Deprecated
		public static StoneType getByData(byte data)
		{
			return (StoneType)BY_DATA.get(Byte.valueOf(data));
		}

		static
		{
			BY_DATA = Maps.newHashMap();
			StoneType[] arrayOfStoneType;
			int i = (arrayOfStoneType = values()).length;
			for (int j = 0; j < i; j++)
			{
				StoneType type = arrayOfStoneType[j];
				BY_DATA.put(Byte.valueOf(type.data), type);
			}
		}
	}
}
