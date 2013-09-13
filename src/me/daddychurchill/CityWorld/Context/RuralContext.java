package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class RuralContext extends CivilizedContext {

	public RuralContext(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		maximumFloorsAbove = 1;
		maximumFloorsBelow = 1;
	}

}
