package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;

import org.bukkit.block.Block;

public final class RelativeSection extends SupportSection {

	private int originX;
	private int originZ;
	
	public RelativeSection(CityWorldGenerator generator, SupportSection relative) {
		super(generator);
		
		this.originX = relative.getOriginX();
		this.originZ = relative.getOriginZ();
	}

	@Override
	public Block getActualBlock(int x, int y, int z) {
		return world.getBlockAt(originX + x, y, originZ + z);
	}
	
	@Override
	public boolean isSurroundedByEmpty(int x, int y, int z) {
		return isEmpty(x - 1, y, z) && 
				isEmpty(x + 1, y, z) &&
				isEmpty(x, y, z - 1) && 
				isEmpty(x, y, z + 1);
	}
	
	@Override
	public boolean isSurroundedByWater(int x, int y, int z) {
		return isWater(x - 1, y, z) && 
				isWater(x + 1, y, z) &&
				isWater(x, y, z - 1) && 
				isWater(x, y, z + 1);
	}
	
}
