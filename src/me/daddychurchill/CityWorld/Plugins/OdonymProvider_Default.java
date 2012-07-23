package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OdonymProvider_Default extends OdonymProvider {

	public OdonymProvider_Default(int seed) {
		super(seed);
	}

	@Override
	public String[] generateNorthSouthOdonym(WorldGenerator generator, int x, int z) {
		int streetN = generateStreetNumber(z);
		String[] result = new String[4];
		result[0] = generateNumericPrefix(streetN, "North", "South");
		result[1] = generateNumericName(streetN, "Main");
		result[2] = "Street";
		result[3] = generateBlockNumbers(x);
		return result;
	}
	
	protected String generateBlockNumbers(int i) {
		//TODO need to work on orientation
//		int streetN = generateStreetNumber(i);
//		if (i < 0)
//			return Math.abs(streetN * 100) + "<->" + Math.abs((streetN + 1) * 100);
//		else
//			return Math.abs((streetN - 1) * 100) + "<->" + Math.abs(streetN * 100);
		return "";
	}

	protected int generateStreetNumber(int chunkN) {
		return (Math.max(0, Math.abs(chunkN - 2) + 2) / 5) * (chunkN < 0 ? -1 : 1); // normalize it and then re-sign it
	}
	
	protected String generateNumericName(int streetN, String central) {
		if (streetN == 0)
			return central;
		else {
			streetN = Math.abs(streetN);
			switch (streetN % 10) {
			case 1:
				return streetN + "st";
			case 2:
				return streetN + "nd";
			case 3:
				return streetN + "rd";
	//		case 4:
	//			return streetN + "th";
	//		case 5:
	//			return streetN + "th";
	//		case 6:
	//			return streetN + "th";
	//		case 7:
	//			return streetN + "th";
	//		case 8:
	//			return streetN + "th";
	//		case 9:
	//			return streetN + "th";
	//		case 9:
	//			return streetN + "th";
			default:
				return streetN + "th";
			}
		}
	}
	
	protected String generateNumericPrefix(int streetN, String negative, String positive) {
		if (streetN == 0)
			return "";
		else
			return streetN < 0 ? negative : positive;
	}
	
	@Override
	public String[] generateWestEastOdonym(WorldGenerator generator, int x, int z) {
		int streetN = generateStreetNumber(x);
		Random random = getRandomFor(streetN);
		String[] result = new String[4];
		result[0] = generateNamedPrefix(random, streetN, "West", "East");
		result[1] = generateNamedName(random, streetN, "Central");
		result[2] = getSuffixPart(random, streetN);
		result[3] = generateBlockNumbers(z);
		return result;
	}
	
	protected String generateNamedPrefix(Random random, int streetN, String negative, String positive) {
		if (streetN == 0)
			return "";
		else
			return (streetN < 0 ? negative : positive) + getPrefixPart(random);
	}
	
	protected String generateNamedName(Random random, int streetN, String central) {
		if (streetN == 0)
			return central;
		else {
			return getStartingPart(random) + getEndingPart(random);
		}
	}
	
	private final static String[] prefixes = new String[] 
		{"Mount", "Fort", "New", "Upper", "Lower", "Lake", "Ben", "Old", "Ole", "Saint"}; 
	
	private String getPrefixPart(Random random) {
		int pick = random.nextInt(prefixes.length * 2);
		if (pick < prefixes.length)
			return " " + prefixes[pick];
		else
			return "";
	}
	
	private final static String[] starts = new String[]
		{"Charles", "York", "Spring", "Cove", "Fair", "Meadow", "Mill", "Elm", "Oak", 
		 "Willow", "Hans", "Win", "Salem", "Mans", "Beech", "Layne", "Wood", "Crest", 
		 "Knox", "Ross", "Day", "Night", "Wes", "Sharon", "Ash", "Maple", "Quaker", 
		 "Iron", "Deer", "North", "South", "East", "West", "Royal", "Hart", "Dan", "Eagle", 
		 "Blan", "Haven", "Yellow", "Ferry", "Green", "White", "Linden", "Ox", "May", 
		 "Harvey", "Wins", "Stone", "High", "Plain", "Rose", "King", "Palm", "Kent", 
		 "Grand", "Owen", "Circle", "Cleve", "Bank", "Farming", "Rock", "Hunts", "Beach", 
		 "Bran", "River", "Blooming", "Harris", "Center", "Wolf", "Lion", "Middle", "Jack", 
		 "Mil", "Monk", "Park", "Indian", "Bruns", "Ridge", "Paines", "Ports", "San", 
		 "Moor", "Nelson", "Clay", "Sand", "Gold", "Mud", "Nor", "Walt", "Church", "Mudd", 
		 "Mac", "Mace", "Ogden", "Worthing", "Pitts", "Gar", "Hank", "Abby", "Max", "Alex", 
		 "Beth", "John", "Jon", "Low", "Lan", "Perry", "Mont", "Hearth", "Land", "Cherry", 
		 "Lime", "Orange", "Spartan", "Pine", "Child", "Granite", "Amber", "Ruby", "Mon", 
		 "Transyl", "Adams", "Hove", "Sussex", "Hoddle", "Peach", "Apple", "Phil", "Madi", 
		 "Wall", "Fleet", "Jane", "Finch", "Brook", "Ham", "Liver", "Worc", "Fil", "Man"};
	
	private String getStartingPart(Random random) {
		return starts[random.nextInt(starts.length)];
	}
	private final static String[] ends = new String[]
		{"grove", "ville", "town", "ship", "view", "bank", "bridge", "dell", "mount", 
		 "stead", "beach", "opolis", "way", "caster", "park", "brook", "vale", "wich", 
		 "ton", "dam", "line", "field", "mont", "more", "moore", "side", "bay", "ford", 
		 "vania", "hunt", "crest", "palm", "shire", "worth", "croft", "lawn", "mill", 
		 "burgh", "moor", "haven", "hart", "port", "dale", "ing", "willow", "vue", 
		 "towne", "ridge", "meadow", "mead", "slade", "tree", "son", "lyn", "pool", "hattan"};

	private String getEndingPart(Random random) {
		int pick = random.nextInt(ends.length * 3 / 2);
		if (pick < ends.length)
			return ends[pick];
		else
			return "";
	}
	
	private final static String[] suffixes = new String[] 
		{"Avenue", "Road", "Lane", "Street", "Way", "Pass", "Trail", "Court", "Route",
		 "Boulevard", "Grade", "Ridge", "Parkway", "Promenade", "Bypass", "Quay", "Motorway", 
		 "Vale", "Grove", "Gardens", "Fairway", "Bend", "Heights", "View", "Place", "Plaza", 
		 "Hill", "Pike", "Alley", "Gate", "Knoll", "Mews", "Terrace", "Cove"};
	
	private String getSuffixPart(Random random, int streetN) {
		if (streetN == 0)
			return suffixes[0];
		else
			return suffixes[random.nextInt(suffixes.length)];
	}
}
