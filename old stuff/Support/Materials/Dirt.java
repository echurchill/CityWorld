package me.daddychurchill.CityWorld.Support.Materials;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import com.google.common.collect.Maps;

public class Dirt extends MaterialData {
	
	public Dirt() {
		super(Material.DIRT);
	}
	
	public Dirt(DirtType type) {
		this();
		setType(type);
	}
	
	@SuppressWarnings("deprecation")
	public DirtType getType() {
		return DirtType.getByData(getData());
	}
	
	@SuppressWarnings("deprecation")
	public void setType(DirtType type) {
		setData(type.getData());
	}
	
	public Dirt clone() {
		return (Dirt)super.clone();
	}
	
	public String toString() {
		return super.toString() + "." + getType();
	}

	public enum DirtType {
		DIRT(0),
		COARSE_DIRT(1), 
		PODZOL(2);
		
		private final byte data;
		private static final Map<Byte, DirtType> BY_DATA;
		
		private DirtType(int data) {
			this.data = ((byte)data);
		}
	
		@Deprecated
		public byte getData()
		{
			return this.data;
		}

		@Deprecated
		public static DirtType getByData(byte data)
		{
			return (DirtType)BY_DATA.get(Byte.valueOf(data));
		}

		static
		{
			BY_DATA = Maps.newHashMap();
			DirtType[] arrayOfDirtType;
			int i = (arrayOfDirtType = values()).length;
			for (int j = 0; j < i; j++)
			{
				DirtType type = arrayOfDirtType[j];
				BY_DATA.put(Byte.valueOf(type.data), type);
			}
		}
	}
}
