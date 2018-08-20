package me.daddychurchill.CityWorld.Support.Materials;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import com.google.common.collect.Maps;

public class Sponge extends MaterialData {
	
	public Sponge() {
		super(Material.SPONGE);
	}
	
	public Sponge(SpongeType type) {
		this();
		setType(type);
	}
	
	@SuppressWarnings("deprecation")
	public SpongeType getType() {
		return SpongeType.getByData(getData());
	}
	
	@SuppressWarnings("deprecation")
	public void setType(SpongeType type) {
		setData(type.getData());
	}
	
	public Sponge clone() {
		return (Sponge)super.clone();
	}
	
	public String toString() {
		return super.toString() + "." + getType();
	}

	public enum SpongeType {
		DRY(0),
		WET(1);
		
		private final byte data;
		private static final Map<Byte, SpongeType> BY_DATA;
		
		private SpongeType(int data) {
			this.data = ((byte)data);
		}
	
		@Deprecated
		public byte getData()
		{
			return this.data;
		}

		@Deprecated
		public static SpongeType getByData(byte data)
		{
			return (SpongeType)BY_DATA.get(Byte.valueOf(data));
		}

		static
		{
			BY_DATA = Maps.newHashMap();
			SpongeType[] arrayOfSpongeType;
			int i = (arrayOfSpongeType = values()).length;
			for (int j = 0; j < i; j++)
			{
				SpongeType type = arrayOfSpongeType[j];
				BY_DATA.put(Byte.valueOf(type.data), type);
			}
		}
	}
}
