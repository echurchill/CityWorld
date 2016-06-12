package me.daddychurchill.CityWorld.Plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

public class OdonymProvider_Normal extends OdonymProvider {

	public OdonymProvider_Normal(int seed) {
		super(seed);
	}

	@Override
	public String[] generateNorthSouthStreetOdonym(CityWorldGenerator generator, int x, int z) {
		int streetN = generateStreetNumber(z);
		String[] result = new String[4];
		result[0] = generateNumericPrefix(streetN, getNorth(), getSouth());
		result[1] = generateNumericName(streetN, getMain());
		result[2] = "Street";
		result[3] = generateStreetBlockNumbers(x);
		return result;
	}
	
	protected String generateStreetBlockNumbers(int i) {
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
	public String[] generateWestEastStreetOdonym(CityWorldGenerator generator, int x, int z) {
		int streetN = generateStreetNumber(x);
		Random random = getRandomFor(streetN);
		String[] result = new String[4];
		result[0] = generateStreetNamedPrefix(random, streetN, getWest(), getEast());
		result[1] = generateStreetNamedName(random, streetN, getCentral());
		result[2] = getSuffixPart(random, streetN);
		result[3] = generateStreetBlockNumbers(z);
		return result;
	}
	
	protected String generateStreetNamedPrefix(Random random, int streetN, String negative, String positive) {
		if (streetN == 0)
			return "";
		else
			return (streetN < 0 ? negative : positive) + getPrefixPart(random);
	}
	
	private String getPrefixPart(Random random) {
		int pick = random.nextInt(streetPrefixes.size() * 2);
		if (pick < streetPrefixes.size())
			return " " + getName(streetPrefixes, pick, "");
		else
			return "";
	}
	
	protected String generateStreetNamedName(Random random, int streetN, String central) {
		if (streetN == 0)
			return central;
		else {
			return getStartingPart(random) + getEndingPart(random);
		}
	}
	
	private String getStartingPart(Random random) {
		return getName(streetStarts, random, "Van");
	}

	private String getEndingPart(Random random) {
		int pick = random.nextInt(streetEnds.size() * 3 / 2);
		if (pick < streetEnds.size())
			return getName(streetEnds, pick, "Brocklin");
		else
			return "";
	}
	
	private String getSuffixPart(Random random, int streetN) {
		if (streetN == 0)
			return getName(streetSuffixes, 0, "Street");
		else
			return getName(streetSuffixes, random, "Street");
	}

	@Override
	public String[] generateFossilOdonym(CityWorldGenerator generator, Odds odds) {
		String[] result = new String[4];
		
		String prefix = getName(fossilPrefixes, odds, "Dino");
//		String middle = getFossilMiddlePart(odds);
		String suffix = getName(fossilSuffixes, odds, "saur");
		
//		result[1] = smartConcat(odds, smartConcat(odds, prefix, middle), suffix);
		result[1] = smartConcat(odds, prefix, suffix);
		return result;
	}
	
	private String smartConcat(Odds odds, String first, String second) {
		if (second.isEmpty())
			return first;
		else if (first.endsWith(second.substring(1)))// && odds.flipCoin())
			return first + second.substring(2, second.length() - 1);
		else
			return first + second;
	}

	@Override
	public String generateVillagerName(CityWorldGenerator generator, Odds odds) {
		return villagerPrefixes.get(odds.getRandomInt(villagerPrefixes.size())) + " " + 
			   villagerSuffixes.get(odds.getRandomInt(villagerSuffixes.size()));
	}
	
	private String tagVillagerPrefixes = "VillagerGivenNames";
	private String tagVillagerSuffixes = "VillagerSurnames";
	
	private String tagFossilPrefixes = "FossilPrefixes";
	private String tagFossilSuffixes = "FossilSuffixes";

	private String tagStreetTerms = "StreetTerms";
	private String tagStreetPrefixes = "StreetPrefixes";
	private String tagStreetStarts = "StreetStarts";
	private String tagStreetEnds = "StreetEnds";
	private String tagStreetSuffixes = "StreetSuffixes";
	
	private List<String> villagerPrefixes = createList(
			// these should be more global but it is hard to find the world wide list of the most popular names, if you find one send it my way
				
			// first hundred from http://names.mongabay.com/female_names.htm
			"Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret", "Dorothy", 
			"Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna", "Carol", "Ruth", "Sharon", 
			"Michelle", "Laura", "Sarah", "Kimberly", "Deborah", "Jessica", "Shirley", "Cynthia", "Angela", "Melissa", 
			"Brenda", "Amy", "Anna", "Rebecca", "Virginia", "Kathleen", "Pamela", "Martha", "Debra", "Amanda", 
			"Stephanie", "Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances", "Ann", "Joyce", "Diane", 
			"Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn", "Jean", "Cheryl", "Mildred", 
			"Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly", "Nicole", "Judy", "Christina", 
			"Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn", 
			"Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline", "Wanda", "Bonnie", "Julia", "Ruby", 
			"Lois", "Tina", "Phyllis", "Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin", 
			"Peggy", "Crystal", "Gladys", "Rita", "Dawn", "Connie", "Florence", "Tracy", "Edna", "Tiffany", 
			"Carmen", "Rosa", "Cindy", "Grace", "Wendy", "Victoria", "Edith", "Kim", "Sherry", "Sylvia", 
			"Josephine", "Thelma", "Shannon", "Sheila", "Ethel", "Ellen", "Elaine", "Marjorie", "Carrie", "Charlotte", 
			"Monica", "Esther", "Pauline", "Emma", "Juanita", "Anita", "Rhonda", "Hazel", "Amber", "Eva", 
			"Debbie", "April", "Leslie", "Clara", "Lucille", "Jamie", "Joanne", "Eleanor", "Valerie", "Danielle", 
			"Megan", "Alicia", "Suzanne", "Michele", "Gail", "Bertha", "Darlene", "Veronica", "Jill", "Erin", 
			"Geraldine", "Lauren", "Cathy", "Joann", "Lorraine", "Lynn", "Sally", "Regina", "Erica", "Beatrice", 
			"Dolores", "Bernice", "Audrey", "Yvonne", "Annette", "June", "Samantha", "Marion", "Dana", "Stacy", 
			"Ana", "Renee", "Ida", "Vivian", "Roberta", "Holly", "Brittany", "Melanie", "Loretta", "Yolanda", 
			"Jeanette", "Laurie", "Katie", "Kristen", "Vanessa", "Alma", "Sue", "Elsie", "Beth", "Jeanne", 
			
			// first hundred from http://names.mongabay.com/male_names.htm
			"James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Thomas", 
			"Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven", "Edward", "Brian", 
			"Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey", 
			"Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond", "Gregory", "Joshua", "Jerry", "Dennis", 
			"Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl", "Arthur", "Ryan", "Roger", 
			"Joe", "Juan", "Jack", "Albert", "Jonathan", "Justin", "Terry", "Gerald", "Keith", "Samuel", 
			"Willie", "Ralph", "Lawrence", "Nicholas", "Roy", "Benjamin", "Bruce", "Brandon", "Adam", "Harry", 
			"Fred", "Wayne", "Billy", "Steve", "Louis", "Jeremy", "Aaron", "Randy", "Howard", "Eugene", 
			"Carlos", "Russell", "Bobby", "Victor", "Martin", "Ernest", "Phillip", "Todd", "Jesse", "Craig", 
			"Alan", "Shawn", "Clarence", "Sean", "Philip", "Chris", "Johnny", "Earl", "Jimmy", "Antonio", 
			"Danny", "Bryan", "Tony", "Luis", "Mike", "Stanley", "Leonard", "Nathan", "Dale", "Manuel", 
			"Rodney", "Curtis", "Norman", "Allen", "Marvin", "Vincent", "Glenn", "Jeffery", "Travis", "Jeff", 
			"Chad", "Jacob", "Lee", "Melvin", "Alfred", "Kyle", "Francis", "Bradley", "Jesus", "Herbert", 
			"Frederick", "Ray", "Joel", "Edwin", "Don", "Eddie", "Ricky", "Troy", "Randall", "Barry", 
			"Alexander", "Bernard", "Mario", "Leroy", "Francisco", "Marcus", "Micheal", "Theodore", "Clifford", "Miguel", 
			"Oscar", "Jay", "Jim", "Tom", "Calvin", "Alex", "Jon", "Ronnie", "Bill", "Lloyd", 
			"Tommy", "Leon", "Derek", "Warren", "Darrell", "Jerome", "Floyd", "Leo", "Alvin", "Tim", 
			"Wesley", "Gordon", "Dean", "Greg", "Jorge", "Dustin", "Pedro", "Derrick", "Dan", "Lewis", 
			"Zachary", "Corey", "Herman", "Maurice", "Vernon", "Roberto", "Clyde", "Glen", "Hector", "Shane", 
			"Ricardo", "Sam", "Rick", "Lester", "Brent", "Ramon", "Charlie", "Tyler", "Gilbert", "Gene"
			);

	private List<String> villagerSuffixes = createList(
			// these should be more global but it is hard to find the world wide list of the most popular names, if you find one send it my way

			// first hundred from http://names.mongabay.com/data/1000.html
			"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", 
			"Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", 
			"Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", 
			"Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", 
			"Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", 
			"Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", 
			"Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", 
			"James", "Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross", 
			"Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", 
			"Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes", 
			"Myers", "Ford", "Hamilton", "Graham", "Sullivan", "Wallace", "Woods", "Cole", "West", "Jordan", 
			"Owens", "Reynolds", "Fisher", "Ellis", "Harrison", "Gibson", "Mcdonald", "Cruz", "Marshall", "Ortiz", 
			"Gomez", "Murray", "Freeman", "Wells", "Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter", 
			"Hicks", "Crawford", "Henry", "Boyd", "Mason", "Morales", "Kennedy", "Warren", "Dixon", "Ramos", 
			"Reyes", "Burns", "Gordon", "Shaw", "Holmes", "Rice", "Robertson", "Hunt", "Black", "Daniels", 
			"Palmer", "Mills", "Nichols", "Grant", "Knight", "Ferguson", "Rose", "Stone", "Hawkins", "Dunn", 
			"Perkins", "Hudson", "Spencer", "Gardner", "Stephens", "Payne", "Pierce", "Berry", "Matthews", "Arnold", 
			"Wagner", "Willis", "Ray", "Watkins", "Olson", "Carroll", "Duncan", "Snyder", "Hart", "Cunningham", 
			"Bradley", "Lane", "Andrews", "Ruiz", "Harper", "Fox", "Riley", "Armstrong", "Carpenter", "Weaver", 
			"Greene", "Lawrence", "Elliott", "Chavez", "Sims", "Austin", "Peters", "Kelley", "Franklin", "Lawson", 

			// "borrowed" by http://www.finedictionary.com/Testificate.html
			"Testificate", "Restificate", "Festificate", "Gestificate", "Yestificate", "Twstificate", "Tsstificate", 
			"Tdstificate", "Trstificate", "Teatificate", "Tewtificate", "Tedtificate", "Textificate", "Teztificate", 
			"Teatificate", "Tesrificate", "Tesfificate", "Tesgificate", "Tesyificate", "Testuficate", "Testjficate", 
			"Testkficate", "Testoficate", "Testidicate", "Testiricate", "Testigicate", "Testivicate", "Testicicate", 
			"Testifucate", "Testifjcate", "Testifkcate", "Testifocate", "Testifixate", "Testifidate", "Testififate", 
			"Testifivate", "Testificqte", "Testificwte", "Testificste", "Testificzte", "Testificare", "Testificafe", 
			"Testificage", "Testificaye", "Testificatw", "Testificats", "Testificatd", "Testificatr"
			);
	
	private List<String> streetTerms = createList(
			termDeclaration, 
			termNorth, termMain, termSouth, 
			termWest, termCentral, termEast); 
		
	private List<String> streetPrefixes = createList(
			"Mount", "Fort", "New", "Upper", "Lower", "Lake", "Ben", "Old", "Ole", "Saint"
			); 
	
	private List<String> streetStarts = createList(
			"Charles", "York", "Spring", "Cove", "Fair", "Meadow", "Mill", "Elm", "Oak", 
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
			"Wall", "Fleet", "Jane", "Finch", "Brook", "Ham", "Liver", "Worc", "Fil", "Man"
			);
	
	private List<String> streetEnds = createList(
			"grove", "ville", "town", "ship", "view", "bank", "bridge", "dell", "mount", 
			"stead", "beach", "opolis", "way", "caster", "park", "brook", "vale", "wich", 
			"ton", "dam", "line", "field", "mont", "more", "moore", "side", "bay", "ford", 
			"vania", "hunt", "crest", "palm", "shire", "worth", "croft", "lawn", "mill", 
			"burgh", "moor", "haven", "hart", "port", "dale", "ing", "willow", "vue", "ill",
			"towne", "ridge", "meadow", "mead", "slade", "tree", "son", "lyn", "pool", "hattan"
			);

	private List<String> streetSuffixes = createList(
			"Street", "Avenue", "Road", "Lane", "Way", "Pass", "Trail", "Court", "Route",
			"Boulevard", "Grade", "Ridge", "Parkway", "Promenade", "Bypass", "Quay", "Motorway", 
			"Vale", "Grove", "Gardens", "Fairway", "Bend", "Heights", "View", "Place", "Plaza", 
			"Hill", "Pike", "Alley", "Gate", "Knoll", "Mews", "Terrace", "Cove"
			);
	
	private List<String> fossilPrefixes = createList(
			"Archea", "Amphel", "Belo", "Bronto", "Camel", "Carno", "Dein", "Dino", 
			"Eoabel", "Eshano", "Fuku", "Futaba", "Gala", "Glypto", "Hadro", "Hesper", 
			"Iguano", "Ichthyo", "Jinta", "Jurave", "Kentro", "Krito", "Labo", "Lepto", 
			"Majun", "Melan", "Nano", "Ningy", "Omni", "Othni", "Pachy", "Parro", 
			"Quaesit", "Qantas", "Rinch", "Rug", "Shamo", "Sauro", "Tachi", "Tyranno", 
			"Tro", "Utah", "Ultra", "Vari", "Veloci", "Wakino", "Wintono", "Xeno", 
			"Xuwu", "Yang", "Yul", "Zana", "Zephyro"
			);
		
//	private List<String> fossilMiddles = createList(
//			"cera", "ingo", "oro", "uan", "ara", "cephal", "saur", "pose", "tarso"
//			);

	private List<String> fossilSuffixes = createList(
			"osaurus", "raptor", "utitan", "tops", "ornis", "mimus", "odon", "otia", 
			"ellia", "onychus", "enator", "opteryx", "ocania", "oid", "enia", "ops", 
			"elurus", "utitan", "long", "noid"
			);
	
	private List<String> createList(String ... items) {
		List<String> result = new ArrayList<String>();
		for (String item : items)
			result.add(item);
		return result;
	}
	
	private List<String> getNames(ConfigurationSection section, String name, List<String> values) {
		if (section.contains(name))
			return section.getStringList(name);
		else
			return values;
	}
	
	private String getName(List<String> values, Random random, String value) {
		if (values == null || values.size() == 0)
			return value;
		else
			return getName(values, random.nextInt(values.size()), value);
	}
	
	private String getName(List<String> values, Odds odds, String value) {
		if (values == null || values.size() == 0)
			return value;
		else
			return getName(values, odds.getRandomInt(values.size()), value);
	}
	
	private String getName(List<String> values, int index, String value) {
		if (values == null || values.size() == 0 || index < 0 || index >= values.size())
			return value;
		else
			return values.get(index);
	}
	
	private static String termDeclaration = "These strings represents cardinal prefixes and central roads";
	private static String termNorth = "North";
	private static String termMain = "Main";
	private static String termSouth = "South";
	
	private static String termWest = "West";
	private static String termCentral = "Central";
	private static String termEast = "East";
	
	private String getTerm(int index, String value) {
		if (streetTerms.size() > index)
			return streetTerms.get(index);
		else
			return value;
	}
	
	private String getNorth() {
		return getTerm(1, termNorth);
	}
	
	private String getMain() {
		return getTerm(2, termMain);
	}
	
	private String getSouth() {
		return getTerm(3, termSouth);
	}
	
	private String getWest() {
		return getTerm(4, termWest);
	}
	
	private String getCentral() {
		return getTerm(5, termCentral);
	}
	
	private String getEast() {
		return getTerm(6, termEast);
	}
	
	@Override
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		streetTerms = getNames(section, tagStreetTerms, streetTerms);
		streetPrefixes = getNames(section, tagStreetPrefixes, streetPrefixes);
		streetStarts = getNames(section, tagStreetStarts, streetStarts);
		streetEnds = getNames(section, tagStreetEnds, streetEnds);
		streetSuffixes = getNames(section, tagStreetSuffixes, streetSuffixes);
		
		fossilPrefixes = getNames(section, tagFossilPrefixes, fossilPrefixes);
		fossilSuffixes = getNames(section, tagFossilSuffixes, fossilSuffixes);
		
		villagerPrefixes = getNames(section, tagVillagerPrefixes, villagerPrefixes);
		villagerSuffixes = getNames(section, tagVillagerSuffixes, villagerSuffixes);
	}

	@Override
	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		section.set(tagStreetTerms, streetTerms);
		section.set(tagStreetPrefixes, streetPrefixes);
		section.set(tagStreetStarts, streetStarts);
		section.set(tagStreetEnds, streetEnds);
		section.set(tagStreetSuffixes, streetSuffixes);
		
		section.set(tagFossilPrefixes, fossilPrefixes);
		section.set(tagFossilSuffixes, fossilSuffixes);
		
		section.set(tagVillagerPrefixes, villagerPrefixes);
		section.set(tagVillagerSuffixes, villagerSuffixes);
	}

}
