package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;

abstract class RuralContext extends CivilizedContext {

	RuralContext(CityWorldGenerator generator) {
		super(generator);

		maximumFloorsAbove = 1;
		maximumFloorsBelow = 1;
	}
}
