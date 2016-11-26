package me.daddychurchill.CityWorld.Context;

import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.Clipboard;
import me.daddychurchill.CityWorld.Clipboard.ClipboardRoundaboutLot;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plats.Urban.RoundaboutCenterLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class RoadContext extends UrbanContext {

	public RoadContext(CityWorldGenerator generator) {
		super(generator);
		
		setSchematicFamily(SchematicFamily.ROUNDABOUT, 1);
	}

	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		super.populateMap(generator, platmap);

	}

	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
		
	}

	public PlatLot createRoadLot(CityWorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart, PlatLot oldLot) {
		PlatLot result = null;
		
		// see if the old lot has a suggestion?
		if (oldLot != null)
			result = oldLot.repaveLot(generator, platmap);
		
		// if not then lets do return the standard one
		if (result == null)
			result = new RoadLot(platmap, platmap.originX + x, platmap.originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
		
		// ok... we are done
		return result;
	}

	public PlatLot createRoundaboutStatueLot(CityWorldGenerator generator, PlatMap platmap, int x, int z) {

		// grab potential platlot's random
		Odds odds = platmap.getChunkOddsGenerator(platmap.originX + x, platmap.originZ + z);
		
		// what way are we facing?
		BlockFace facing = odds.getRandomFacing();
		
		// see if there is a schematic out there that fits
		Clipboard clip = getSchematics(generator).getSingleLot(generator, platmap, odds, x, z);
		if (clip != null) {
			
			// create it then
			return new ClipboardRoundaboutLot(platmap, platmap.originX + x, platmap.originZ + z, clip, facing, 0, 0);
			
		} else
			return new RoundaboutCenterLot(platmap, platmap.originX + x, platmap.originZ + z);
	}

}
