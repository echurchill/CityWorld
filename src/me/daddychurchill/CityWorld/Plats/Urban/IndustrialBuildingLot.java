package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class IndustrialBuildingLot extends FinishedBuildingLot {

	public IndustrialBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = 1;
		depth = 0;
		roofStyle = chunkOdds.flipCoin() ? RoofStyle.EDGED : RoofStyle.FLATTOP;
		roofFeature = roofFeature == RoofFeature.ANTENNAS ? RoofFeature.CONDITIONERS : roofFeature;
		insetStyle = InsetStyle.STRAIGHT;
		rounded = false;
	}

	@Override
	protected void calculateOptions(DataContext context) {
		super.calculateOptions(context);
		
		// how do the walls inset?
		insetWallWE = 1;
		insetWallNS = 1;
		
		// what about the ceiling?
		insetCeilingWE = insetWallWE;
		insetCeilingNS = insetWallNS;
		
		// nudge in a bit more as we go up
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetStyle = InsetStyle.STRAIGHT;
	}

}
