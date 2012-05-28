package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ContextNature extends ContextRural {

	public ContextNature(CityWorld plugin, SupportChunk typicalChunk) {
		super(plugin, typicalChunk);

	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap, SupportChunk typicalChunk) {
		/* On top of tall mountains put...
		 *    Radio towers
		 *    Telescopes
		 * On hills/mountains put
		 *    Shacks
		 * Inside mountains put...
		 *    Mines
		 *    Bunkers
		 * On top of deep seas put...
		 *    Drilling platforms
		 * Isolated spots of buildable land...
		 *    Houses without roads
		 *    Residential 
		 *    Farms w/Farm house
		 */
	}
}
