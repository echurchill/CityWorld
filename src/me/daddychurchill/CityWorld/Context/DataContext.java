package me.daddychurchill.CityWorld.Context;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.Clipboard;
import me.daddychurchill.CityWorld.Clipboard.ClipboardList;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.NatureLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class DataContext {

	// While these are initialized here, the real defaults live in CivilizedContext
	// and UncivilizedContext

	protected double oddsOfIsolatedLots = Odds.oddsNeverGoingToHappen;
	protected double oddsOfIsolatedConstructs = Odds.oddsNeverGoingToHappen;
	protected double oddsOfParks = Odds.oddsNeverGoingToHappen; // parks show up 1/n of the time

	public double oddsOfIdenticalBuildingHeights = Odds.oddsNeverGoingToHappen; // similar height 1/n of the time
	public double oddsOfSimilarBuildingHeights = Odds.oddsNeverGoingToHappen; // identical height 1/n of the time
	public final double oddsOfRoundedBuilding = Odds.oddsEnormouslyLikely;// Odds.oddsLikely; // how naturally rounded are
	// buildings that can be rounded
	public double oddsOfSimilarBuildingRounding = Odds.oddsNeverGoingToHappen; // like rounding 1/n of the time
	public double oddsOfStairWallMaterialIsWallMaterial = Odds.oddsNeverGoingToHappen; // stair walls are the same as
	// walls 1/n of the time
//	public int buildingWallInsettedMinLowPoint; // minimum building height before insetting is allowed
//	public int buildingWallInsettedMinMidPoint; // lowest point of inset
//	public int buildingWallInsettedMinHighPoint; // lowest highest point of inset
	public int rangeOfWallInset = 2; // 1 or 2 in... but not zero
	public final double oddsOfForcedNarrowInteriorMode = Odds.oddsLikely;
	public final double oddsOfDifferentInteriorModes = Odds.oddsUnlikely;

	public double oddsOfOnlyUnfinishedBasements = Odds.oddsNeverGoingToHappen; // unfinished buildings only have
	// basements 1/n of the time
	public double oddsOfCranes = Odds.oddsVeryLikely; // plop a crane on top of the last horizontal girder 1/n of the
	// time

	public double oddsOfBuildingWallInset = Odds.oddsNeverGoingToHappen; // building walls inset as they go up 1/n of
	// the time
	public double oddsOfSimilarInsetBuildings = Odds.oddsNeverGoingToHappen; // the east/west inset is used for
	// north/south inset 1/n of the time
	public double oddsOfFlatWalledBuildings = Odds.oddsNeverGoingToHappen; // the ceilings are inset like the walls 1/n
	// of the time

	// TODO oddsOfMissingRoad is current not used... I need to fix this
	// public double oddsOfMissingRoad = oddsNeverGoingToHappen; // roads are
	// missing 1/n of the time
	public double oddsOfRoundAbouts = Odds.oddsNeverGoingToHappen; // roundabouts are created 1/n of the time

	public double oddsOfArt = Odds.oddsNeverGoingToHappen; // art is missing 1/n of the time
	public double oddsOfNaturalArt = Odds.oddsNeverGoingToHappen; // sometimes nature is art 1/n of the time

	public final Material lightMat;
	public final Material torchMat;

	private static final int schematicMax = 4;
	private SchematicFamily schematicFamily = SchematicFamily.NATURE;
	private int schematicMaxX = schematicMax;
	private int schematicMaxZ = schematicMax;

	public static final int FloorHeight = 4;
	private static final int FudgeFloorsBelow = 2;
	private static final int FudgeFloorsAbove = 0;// 3;
	private static final int absoluteMinimumFloorsAbove = 5; // shortest tallest building
	private static final int absoluteAbsoluteMaximumFloorsBelow = 3; // that is as many basements as I personally can
	// tolerate
	private static final int absoluteAbsoluteMaximumFloorsAbove = 20; // that is tall enough folks
	public final int buildingMaximumY;
	public int maximumFloorsAbove = 2;
	public int maximumFloorsBelow = 2;
	private final int absoluteMaximumFloorsBelow;
	protected final int absoluteMaximumFloorsAbove;

	protected DataContext(CityWorldGenerator generator) {

		// lights?
		if (generator.getSettings().includeWorkingLights) {
			lightMat = Material.GLOWSTONE;
			torchMat = Material.TORCH;
		} else {
			lightMat = Material.REDSTONE_LAMP;
			torchMat = Material.REDSTONE_TORCH;
		}

		// where is the ground
		buildingMaximumY = Math.min(192 + FudgeFloorsAbove * FloorHeight, generator.height);
		absoluteMaximumFloorsBelow = Math.max(
				Math.min(generator.streetLevel / FloorHeight - FudgeFloorsBelow, absoluteAbsoluteMaximumFloorsBelow),
				0);
		absoluteMaximumFloorsAbove = Math
				.max(Math.min((buildingMaximumY - generator.streetLevel) / FloorHeight - FudgeFloorsAbove,
						absoluteAbsoluteMaximumFloorsAbove), absoluteMinimumFloorsAbove);

		// calculate the extremes for this plat
		maximumFloorsAbove = Math.min(maximumFloorsAbove, absoluteMaximumFloorsAbove);
		maximumFloorsBelow = Math.min(maximumFloorsBelow, absoluteMaximumFloorsBelow);

//		int floorsFourth = Math.max((maximumFloorsAbove) / 4, 1);
//		buildingWallInsettedMinLowPoint = floorsFourth;
//		buildingWallInsettedMinMidPoint = floorsFourth * 2;
//		buildingWallInsettedMinHighPoint = floorsFourth * 3;

	}

	public abstract void populateMap(CityWorldGenerator generator, PlatMap platmap);

	public abstract void validateMap(CityWorldGenerator generator, PlatMap platmap);

	private ClipboardList mapsSchematics;
	protected double oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen;

	Clipboard getSingleSchematic(CityWorldGenerator generator, PlatMap platmap, Odds odds, int x, int z) {
		ClipboardList clips = getSchematics(generator);
		if (clips != null)
			return clips.getSingleLot(generator, platmap, odds, x, z);
		else
			return null;
	}

	void populateSchematics(CityWorldGenerator generator, PlatMap platmap) {
		ClipboardList clips = getSchematics(generator);
		if (clips != null)
			clips.populate(generator, platmap);
	}

	private ClipboardList getSchematics(CityWorldGenerator generator) {
		if (mapsSchematics == null) {
//			CityWorld.log.info("LOADING SCHEMATIC FAMILY = " + schematicFamily.toString());
			if (generator.pasteProvider == null)
				generator.reportMessage("ERROR - PasteProvider is NULL");
			else
				mapsSchematics = generator.pasteProvider.getFamilyClips(generator, schematicFamily, schematicMaxX,
						schematicMaxZ);
		}
		return mapsSchematics;
	}

	protected void setSchematicFamily(SchematicFamily family) {
		setSchematicFamily(family, schematicMax);
	}

	void setSchematicFamily(SchematicFamily family, int maxWidth) {
//		CityWorld.log.info("SET SCHEMATIC FAMILY = " + family.toString());
		schematicFamily = family;
		schematicMaxX = maxWidth;
		schematicMaxZ = maxWidth;
	}

	public SchematicFamily getSchematicFamily() {
		return schematicFamily;
	}

	public PlatLot createNaturalLot(CityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return new NatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}

	public Material getMapRepresentation() {
		return Material.AIR;
	}

}
