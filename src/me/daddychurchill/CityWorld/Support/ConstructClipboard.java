package me.daddychurchill.CityWorld.Support;

import java.io.File;

import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class ConstructClipboard {

	protected String name;
	
	public ConstructClipboard(File file) {
		super();
		name = file.getName();
	}
	
	public abstract void Paste(WorldGenerator generator, RealChunk chunk, int blockX, int blockY, int blockZ);
}
