package me.daddychurchill.CityWorld.Support.Materials;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import com.google.common.collect.Maps;

public class Sand extends MaterialData {
	
	public Sand() {
		super(Material.SAND);
	}
	
	public Sand(SandType type) {
		this();
		setType(type);
	}
	
	@SuppressWarnings("deprecation")
	public SandType getType() {
		return SandType.getByData(getData());
	}
	
	@SuppressWarnings("deprecation")
	protected void setType(SandType type) {
		setData(type.getData());
	}
	
	public Sand clone() {
		return (Sand)super.clone();
	}
	
	public String toString() {
		return super.toString() + "." + getType();
	}

	public enum SandType {
		SAND(0),
		RED(1);
		
		private final byte data;
		private static final Map<Byte, SandType> BY_DATA;
		
		private SandType(int data) {
			this.data = ((byte)data);
		}
	
		@Deprecated
		public byte getData()
		{
			return this.data;
		}

		@Deprecated
		public static SandType getByData(byte data)
		{
			return (SandType)BY_DATA.get(Byte.valueOf(data));
		}

		static
		{
			BY_DATA = Maps.newHashMap();
			SandType[] arrayOfSandType;
			int i = (arrayOfSandType = values()).length;
			for (int j = 0; j < i; j++)
			{
				SandType type = arrayOfSandType[j];
				BY_DATA.put(Byte.valueOf(type.data), type);
			}
		}
	}
}
