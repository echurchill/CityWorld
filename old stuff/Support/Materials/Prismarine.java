package me.daddychurchill.CityWorld.Support.Materials;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import com.google.common.collect.Maps;

public class Prismarine extends MaterialData {
	
	public Prismarine() {
		super(Material.PRISMARINE);
	}
	
	public Prismarine(PrismarineType type) {
		this();
		setType(type);
	}
	
	@SuppressWarnings("deprecation")
	public PrismarineType getType() {
		return PrismarineType.getByData(getData());
	}
	
	@SuppressWarnings("deprecation")
	public void setType(PrismarineType type) {
		setData(type.getData());
	}
	
	public Prismarine clone() {
		return (Prismarine)super.clone();
	}
	
	public String toString() {
		return super.toString() + "." + getType();
	}

	public enum PrismarineType {
		SMOOTH(0),
		BRICK(1), 
		DARK(2);
		
		private final byte data;
		private static final Map<Byte, PrismarineType> BY_DATA;
		
		private PrismarineType(int data) {
			this.data = ((byte)data);
		}
	
		@Deprecated
		public byte getData()
		{
			return this.data;
		}

		@Deprecated
		public static PrismarineType getByData(byte data)
		{
			return (PrismarineType)BY_DATA.get(Byte.valueOf(data));
		}

		static
		{
			BY_DATA = Maps.newHashMap();
			PrismarineType[] arrayOfDirtType;
			int i = (arrayOfDirtType = values()).length;
			for (int j = 0; j < i; j++)
			{
				PrismarineType type = arrayOfDirtType[j];
				BY_DATA.put(Byte.valueOf(type.data), type);
			}
		}
	}
}
