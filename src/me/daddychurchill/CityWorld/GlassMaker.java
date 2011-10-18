package me.daddychurchill.CityWorld;

import java.util.Random;

import org.bukkit.Material;

public abstract class GlassMaker {

	protected enum Styles {RANDOM, SINGLE, DOUBLE};
	protected Styles style;
	protected Random rand;

	public GlassMaker(Random rand) {
		super();
		this.rand = rand;
		style = pickWindowStyle();
	}
	
	public GlassMaker(Random rand, Styles astyle) {
		super();
		this.rand = rand;
		style = astyle;
	}
	
	protected Styles pickWindowStyle() {
		switch (rand.nextInt(3)) {
		case 1:
			return Styles.SINGLE;
		case 2:
			return Styles.DOUBLE;
		default:
			return Styles.RANDOM;
		}		
	}
	
	static public Material pickGlassMaterial(Random rand) {
		switch (rand.nextInt(2)) {
		case 1:
			return Material.THIN_GLASS;
		default:
			return Material.GLASS;
		}
	}
	
	public abstract byte pickMaterial(byte wallId, byte glassId, int x, int z);
}
