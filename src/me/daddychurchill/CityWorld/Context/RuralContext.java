package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public abstract class RuralContext extends CivilizedContext {

	public RuralContext(CityWorldGenerator generator) {
		super(generator);

		maximumFloorsAbove = 1;
		maximumFloorsBelow = 1;
	}
}
