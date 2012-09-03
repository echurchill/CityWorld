package me.daddychurchill.CityWorld.Support;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bags.BlockBag;

public class WorldEditSession extends EditSession {

	public WorldEditSession(LocalWorld world, int maxBlocks) {
		super(world, maxBlocks);
		// TODO Auto-generated constructor stub
	}

	public WorldEditSession(LocalWorld world, int maxBlocks, BlockBag blockBag) {
		super(world, maxBlocks, blockBag);
		// TODO Auto-generated constructor stub
	}

}
