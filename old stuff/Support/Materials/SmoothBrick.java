package me.daddychurchill.CityWorld.Support.Materials;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import com.google.common.collect.Maps;

public class SmoothBrick extends MaterialData {
	
	public SmoothBrick() {
		super(Material.SMOOTH_STONE);
	}
	
	public SmoothBrick(SmoothBrickType type) {
		this();
		setType(type);
	}
	
	@SuppressWarnings("deprecation")
	public SmoothBrickType getType() {
		return SmoothBrickType.getByData(getData());
	}
	
	@SuppressWarnings("deprecation")
	public void setType(SmoothBrickType type) {
		setData(type.getData());
	}
	
	public SmoothBrick clone() {
		return (SmoothBrick)super.clone();
	}
	
	public String toString() {
		return super.toString() + "." + getType();
	}

	public enum SmoothBrickType {
		SMOOTH(0),
		MOSSY(1), 
		CRACKED(2),
		CHISELED(3);
		
		private final byte data;
		private static final Map<Byte, SmoothBrickType> BY_DATA;
		
		private SmoothBrickType(int data) {
			this.data = ((byte)data);
		}
	
		@Deprecated
		public byte getData()
		{
			return this.data;
		}

		@Deprecated
		public static SmoothBrickType getByData(byte data)
		{
			return (SmoothBrickType)BY_DATA.get(Byte.valueOf(data));
		}

		static
		{
			BY_DATA = Maps.newHashMap();
			SmoothBrickType[] arrayOfDirtType;
			int i = (arrayOfDirtType = values()).length;
			for (int j = 0; j < i; j++)
			{
				SmoothBrickType type = arrayOfDirtType[j];
				BY_DATA.put(Byte.valueOf(type.data), type);
			}
		}
	}
}
