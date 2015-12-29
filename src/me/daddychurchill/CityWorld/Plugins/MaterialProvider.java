package me.daddychurchill.CityWorld.Plugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.CityWorldSettings;
import me.daddychurchill.CityWorld.Support.MaterialList;

public class MaterialProvider {

	public MaterialList itemsTreasureInBunkers;
	public MaterialList itemsTreasureInMines;
	public MaterialList itemsTreasureInSewers;
	public MaterialList itemsTreasureInStorageSheds;
	public MaterialList itemsTreasureInFarmWorks;
	public MaterialList itemsTreasureInFarmWorksOutput;
	public MaterialList itemsTreasureInWoodWorks;
	public MaterialList itemsTreasureInWoodWorksOutput;
	public MaterialList itemsTreasureInStoneWorks;
	public MaterialList itemsTreasureInStoneWorksOutput;
	
	public final static String tagListOfTreasuresInBunkers = "List_Of_Treasures_In_Bunkers";
	public final static String tagListOfTreasuresInMines = "List_Of_Treasures_In_Mines";
	public final static String tagListOfTreasuresInSewers = "List_Of_Treasures_In_Sewers";
	public final static String tagListOfTreasuresInStorageSheds = "List_Of_Treasures_In_Storage_Sheds";
	public final static String tagListOfTreasuresInFarmWorks = "List_Of_Treasures_In_Farm_Works";
	public final static String tagListOfTreasuresInFarmWorksOutput = "List_Of_Treasures_In_Farm_Works_Output";
	public final static String tagListOfTreasuresInWoodWorks = "List_Of_Treasures_In_Wood_Works";
	public final static String tagListOfTreasuresInWoodWorksOutput = "List_Of_Treasures_In_Works_Works_Output";
	public final static String tagListOfTreasuresInStoneWorks = "List_Of_Treasures_In_Stone_Works";
	public final static String tagListOfTreasuresInStoneWorksOutput = "List_Of_Treasures_In_Stone_Works_Output";
	
	public MaterialList itemsMaterialsForBuildingWalls;
	public MaterialList itemsMaterialsForBuildingCeilings;
	public MaterialList itemsMaterialsForBuildingRoofs;
	public MaterialList itemsMaterialsForUnfinishedBuildings;
	public MaterialList itemsMaterialsForHouseWalls;
	public MaterialList itemsMaterialsForHouseFloors;
	public MaterialList itemsMaterialsForHouseCeilings;
	public MaterialList itemsMaterialsForHouseRoofs;
	public MaterialList itemsMaterialsForShackWalls;
	public MaterialList itemsMaterialsForShackRoofs;
	public MaterialList itemsMaterialsForShedWalls;
	public MaterialList itemsMaterialsForShedRoofs;
	public MaterialList itemsMaterialsForStoneWorksPiles;
	public MaterialList itemsMaterialsForCastles;
	public MaterialList itemsMaterialsForMazeWalls;
	public MaterialList itemsMaterialsForRoads;
	
	public final static String tagMaterialsForBuildingWalls = "Materials_For_BuildingWalls";
	public final static String tagMaterialsForBuildingCeilings = "Materials_For_BuildingCeilings";
	public final static String tagMaterialsForBuildingRoofs = "Materials_For_BuildingRoofs";
	public final static String tagMaterialsForUnfinishedBuildings = "Materials_For_UnfinishedBuildings";
	public final static String tagMaterialsForHouseWalls = "Materials_For_HouseWalls";
	public final static String tagMaterialsForHouseFloors = "Materials_For_HouseFloors";
	public final static String tagMaterialsForHouseCeilings = "Materials_For_HouseCeilings";
	public final static String tagMaterialsForHouseRoofs = "Materials_For_HouseRoofs";
	public final static String tagMaterialsForShackWalls = "Materials_For_ShackWalls";
	public final static String tagMaterialsForShackRoofs = "Materials_For_ShackRoofs";
	public final static String tagMaterialsForShedWalls = "Materials_For_ShedWalls";
	public final static String tagMaterialsForShedRoofs = "Materials_For_ShedRoofs";
	public final static String tagMaterialsForStoneWorksPiles = "Materials_For_Stone_Works_Piles";
	public final static String tagMaterialsForCastles = "Materials_For_Castles";
	public final static String tagMaterialsForMazeWalls = "Materials_For_MazeWalls";
	public final static String tagMaterialsForRoads = "Materials_For_Roads";
	
	private List<MaterialList> listOfLists;
	
	public MaterialProvider(CityWorldGenerator generator, CityWorldSettings settings) {
		
		listOfLists = new ArrayList<MaterialList>();

		itemsTreasureInBunkers = createList(tagListOfTreasuresInBunkers, 
				Material.IRON_PICKAXE, 
				Material.IRON_SWORD,
				Material.IRON_SPADE,

				Material.IRON_BOOTS,
				Material.IRON_CHESTPLATE,
				Material.IRON_HELMET,
				Material.IRON_LEGGINGS,
				Material.CHAINMAIL_BOOTS,
				Material.CHAINMAIL_CHESTPLATE,
				Material.CHAINMAIL_HELMET,
				Material.CHAINMAIL_LEGGINGS,
				
				Material.TORCH,
				Material.BUCKET,
				Material.WATER_BUCKET,
				Material.LAVA_BUCKET,
				
				Material.REDSTONE);
		
		itemsTreasureInMines = createList(tagListOfTreasuresInMines, 
				Material.STONE_PICKAXE,
				Material.STONE_SWORD,
				Material.STONE_SPADE,
				Material.IRON_PICKAXE, 
				Material.IRON_SWORD,
				Material.IRON_SPADE,

				Material.LEATHER_BOOTS,
				Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET,
				Material.LEATHER_LEGGINGS,
				
				Material.TORCH,	
				Material.COMPASS,
				Material.WATCH,
				Material.FLINT,
				Material.FLINT_AND_STEEL, 
				Material.BUCKET,
				Material.WATER_BUCKET,
				Material.LAVA_BUCKET,
				Material.SULPHUR,
				Material.SAND,

				Material.BOOK,
				Material.COAL,
				Material.DIAMOND,
				Material.IRON_INGOT,
				Material.GOLD_INGOT,
				
				Material.APPLE,
				Material.ROTTEN_FLESH);

		itemsTreasureInSewers = createList(tagListOfTreasuresInSewers, 
				Material.WOOD_PICKAXE, 
				Material.WOOD_SWORD,
				Material.WOOD_SPADE,
				Material.STONE_PICKAXE,	
				Material.STONE_SWORD,
				Material.STONE_SPADE,
				
				Material.LEATHER_BOOTS,
				Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET,
				Material.LEATHER_LEGGINGS,
				
				Material.TORCH,	
				Material.COMPASS,
				Material.WATCH,
				Material.FLINT_AND_STEEL,

				Material.COAL,

				Material.APPLE,
				Material.ROTTEN_FLESH);
		
		itemsTreasureInStorageSheds = createList(tagListOfTreasuresInStorageSheds,
				Material.WOOD_AXE, 
				Material.WOOD_SPADE, 
				Material.WOOD_PICKAXE, 
				Material.IRON_AXE, 
				Material.IRON_SPADE, 
				Material.IRON_PICKAXE,	
				
				Material.IRON_HELMET, 

				Material.TORCH,	
				Material.PAPER,
				Material.BOOK,
				
				Material.COOKED_MUTTON,
				Material.BOWL,
				Material.MUSHROOM_SOUP);
		
		itemsTreasureInFarmWorks = createList(tagListOfTreasuresInFarmWorks,
				Material.WOOD_SPADE, 
				Material.WOOD_PICKAXE, 
				Material.WOOD_HOE,
				Material.IRON_SPADE, 
				Material.IRON_PICKAXE,	
				Material.IRON_HOE, 
				
				Material.LEATHER_BOOTS,
				Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET,
				Material.LEATHER_LEGGINGS,
				
				Material.BOW,
				Material.ARROW,
				
				Material.PAPER,
				Material.BOOK,
				Material.TORCH,
				Material.FISHING_ROD,
				Material.SHEARS,
				Material.BUCKET,
				Material.WATER_BUCKET,
				Material.MILK_BUCKET,
				Material.BONE,
				Material.BOWL,
				
				Material.COOKIE,
				Material.SUGAR,
				Material.GRILLED_PORK,
				Material.COOKED_BEEF,
				Material.COOKED_CHICKEN,
				Material.COOKED_MUTTON,
				Material.COOKED_RABBIT,
				Material.BAKED_POTATO,
				Material.MELON,
				Material.PUMPKIN_PIE,
				Material.MUSHROOM_SOUP);
		
		itemsTreasureInFarmWorksOutput = createList(tagListOfTreasuresInFarmWorksOutput,
				Material.LEATHER,
				Material.PORK,
				Material.RAW_BEEF,
				Material.RAW_CHICKEN,
				Material.RABBIT,
				Material.MUTTON,
				Material.SEEDS,
				Material.WOOL,
				Material.POTATO_ITEM,
				Material.CARROT_ITEM,
				Material.PUMPKIN_SEEDS,
				Material.MELON_SEEDS,
				Material.CARROT_ITEM,
				Material.POTATO_ITEM,
				Material.SUGAR_CANE,
				Material.APPLE);
		
		itemsTreasureInWoodWorks = createList(tagListOfTreasuresInWoodWorks,
				Material.WOOD_AXE, 
				Material.WOOD_SPADE, 
				Material.WOOD_PICKAXE,
				Material.IRON_AXE, 
				Material.IRON_SPADE, 
				Material.IRON_PICKAXE,	
				
				Material.IRON_HELMET, 

				Material.FLINT_AND_STEEL,
				Material.COAL,
				Material.TORCH,	
				
				Material.GRILLED_PORK,
				Material.COOKED_CHICKEN,
				Material.COOKIE);
		
		itemsTreasureInWoodWorksOutput = createList(tagListOfTreasuresInWoodWorksOutput,
				Material.WOOD, // simple but cheesy way to increase the odds for this one
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.LOG,
				Material.LOG,
				Material.LOG,
				Material.STICK,
				Material.STICK,
				Material.STICK,

				Material.WOOD_STEP,
				Material.WOODEN_DOOR,
				Material.FENCE,
				Material.FENCE_GATE,
				Material.WOOD_STAIRS,
				Material.TRAP_DOOR,
				Material.WOOD_PLATE,
				Material.SIGN,
				
				Material.SPRUCE_DOOR_ITEM,
				Material.SPRUCE_FENCE,
				Material.SPRUCE_FENCE_GATE,
				Material.SPRUCE_WOOD_STAIRS,
				
				Material.BIRCH_DOOR_ITEM,
				Material.BIRCH_FENCE,
				Material.BIRCH_FENCE_GATE,
				Material.BIRCH_WOOD_STAIRS,
				
				Material.JUNGLE_DOOR_ITEM,
				Material.JUNGLE_FENCE,
				Material.JUNGLE_FENCE_GATE,
				Material.JUNGLE_WOOD_STAIRS,
				
				Material.ACACIA_DOOR_ITEM,
				Material.ACACIA_FENCE,
				Material.ACACIA_FENCE_GATE,
				Material.ACACIA_STAIRS,
				
				Material.DARK_OAK_DOOR_ITEM,
				Material.DARK_OAK_FENCE,
				Material.DARK_OAK_FENCE_GATE,
				Material.DARK_OAK_STAIRS);
		
		itemsTreasureInStoneWorks = createList(tagListOfTreasuresInStoneWorks,
				Material.STONE_SPADE, 
				Material.STONE_PICKAXE,
				Material.IRON_SPADE, 
				Material.IRON_PICKAXE,
				
				Material.IRON_HELMET, 

				Material.FLINT_AND_STEEL,
				Material.TORCH,
				Material.BUCKET,

				Material.COOKED_BEEF,
				Material.COOKED_CHICKEN);
		
		itemsTreasureInStoneWorksOutput = createList(tagListOfTreasuresInStoneWorksOutput,
				Material.IRON_INGOT, // easy but stupid way to increase odds of some of these happening
				Material.IRON_INGOT,
				Material.IRON_INGOT,
				Material.IRON_INGOT,
				Material.IRON_INGOT,
				Material.IRON_INGOT,
				Material.IRON_INGOT,
				Material.COAL,
				Material.COAL,
				Material.COAL,
				Material.COAL,
				Material.COAL,
				Material.COAL,
				Material.COAL,
				Material.GOLD_INGOT,
				Material.GOLD_INGOT,
				Material.GOLD_INGOT,
				Material.REDSTONE,
				Material.REDSTONE,
				Material.REDSTONE,
				Material.DIAMOND,
				Material.EMERALD);
		
		itemsMaterialsForBuildingWalls = createList(tagMaterialsForBuildingWalls,
				Material.COBBLESTONE,
				Material.SAND,
				Material.GRAVEL,
				Material.WOOD,
				Material.SANDSTONE,
				Material.WOOL,
				Material.DOUBLE_STEP,
				Material.BRICK,
				Material.MOSSY_COBBLESTONE,
				Material.CLAY,
				Material.NETHERRACK,
				Material.SOUL_SAND,
				Material.SMOOTH_BRICK,
				Material.NETHER_BRICK,
				Material.QUARTZ_BLOCK,
				Material.HARD_CLAY,
				Material.STAINED_CLAY,
				Material.COAL_BLOCK,
				Material.ENDER_STONE,
				Material.STONE,
				Material.RED_SANDSTONE,
				Material.DOUBLE_STONE_SLAB2);
		
		itemsMaterialsForBuildingCeilings = createList(tagMaterialsForBuildingCeilings,
				Material.COBBLESTONE,
				Material.WOOD,
				Material.SANDSTONE,
				Material.WOOL,
				Material.DOUBLE_STEP,
				Material.BRICK,
				Material.MOSSY_COBBLESTONE,
				Material.CLAY,
				Material.NETHERRACK,
				Material.SOUL_SAND,
				Material.SMOOTH_BRICK,
				Material.NETHER_BRICK,
				Material.QUARTZ_BLOCK,
				Material.HARD_CLAY,
				Material.STAINED_CLAY,
				Material.COAL_BLOCK,
				Material.ENDER_STONE,
				Material.STONE,
				Material.RED_SANDSTONE,
				Material.DOUBLE_STONE_SLAB2);
		
		itemsMaterialsForBuildingRoofs = createList(tagMaterialsForBuildingRoofs,
				Material.COBBLESTONE,
				Material.WOOD,
				Material.SANDSTONE,
				Material.WOOL,
				Material.DOUBLE_STEP,
				Material.BRICK,
				Material.MOSSY_COBBLESTONE,
				Material.CLAY,
				Material.NETHERRACK,
				Material.SOUL_SAND,
				Material.SMOOTH_BRICK,
				Material.NETHER_BRICK,
				Material.QUARTZ_BLOCK,
				Material.HARD_CLAY,
				Material.STAINED_CLAY,
				Material.COAL_BLOCK,
				Material.ENDER_STONE,
				Material.STONE,
				Material.RED_SANDSTONE,
				Material.DOUBLE_STONE_SLAB2);
		
		itemsMaterialsForUnfinishedBuildings = createList(tagMaterialsForUnfinishedBuildings,
				Material.CLAY,
				Material.CLAY,
				Material.HARD_CLAY,
				Material.HARD_CLAY,
				Material.STAINED_CLAY,
				Material.STAINED_CLAY,
				Material.STONE,
				Material.STONE,
				Material.WOOD);

		itemsMaterialsForHouseWalls = createList(tagMaterialsForHouseWalls,
				Material.COBBLESTONE,
				Material.MOSSY_COBBLESTONE,
				Material.STONE,
				Material.SMOOTH_BRICK,
				Material.SANDSTONE,
				Material.RED_SANDSTONE,
				Material.NETHER_BRICK,
				Material.BRICK,
				Material.CLAY,
				Material.WOOD);
		
		itemsMaterialsForHouseFloors = createList(tagMaterialsForHouseFloors,
				Material.COBBLESTONE,
				Material.STONE,
				Material.WOOL,
				Material.WOOD);
		
		itemsMaterialsForHouseCeilings = createList(tagMaterialsForHouseCeilings,
				Material.COBBLESTONE,
				Material.STONE,
				Material.SMOOTH_BRICK,
				Material.SANDSTONE,
				Material.RED_SANDSTONE,
				Material.WOOD);
		
		itemsMaterialsForHouseRoofs = createList(tagMaterialsForHouseRoofs,
				Material.COBBLESTONE,
				Material.MOSSY_COBBLESTONE,
				Material.STONE,
				Material.SMOOTH_BRICK,
				Material.SANDSTONE,
				Material.RED_SANDSTONE,
				Material.WOOD);
		
		itemsMaterialsForShackWalls = createList(tagMaterialsForShackWalls,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.MOSSY_COBBLESTONE,
				Material.RED_SANDSTONE,
				Material.NETHER_BRICK,
				Material.BRICK);
		
		itemsMaterialsForShackRoofs = createList(tagMaterialsForShackRoofs,
				Material.WOOD,
				Material.STEP,
				Material.WOOD_STEP);
		
		itemsMaterialsForShedWalls = createList(tagMaterialsForShedWalls,
				Material.STONE,
				Material.SANDSTONE,
				Material.RED_SANDSTONE,
				Material.WOOD,
				Material.COBBLESTONE,
				Material.BRICK,
				Material.SMOOTH_BRICK);
		
		itemsMaterialsForShedRoofs = createList(tagMaterialsForShedRoofs,
				Material.STEP,
				Material.WOOD_STEP);

		itemsMaterialsForStoneWorksPiles = createList(tagMaterialsForStoneWorksPiles,
				Material.GRAVEL, // easy but stupid way to increase odds of some of these happening
				Material.GRAVEL,
				Material.GRAVEL,
				Material.GRAVEL,
				Material.GRAVEL,
				Material.COAL_ORE,
				Material.COAL_ORE,
				Material.COAL_ORE,
				Material.COAL_ORE,
				Material.IRON_ORE,
				Material.IRON_ORE,
				Material.IRON_ORE,
				Material.GOLD_ORE,
				Material.LAPIS_ORE,
				Material.REDSTONE_ORE,
				Material.DIAMOND_ORE,
				Material.EMERALD_ORE);
		
		itemsMaterialsForCastles = createList(tagMaterialsForCastles,
				Material.STONE,
				Material.SMOOTH_BRICK,
				Material.COBBLESTONE,
				Material.MOSSY_COBBLESTONE,
				Material.ENDER_STONE,
				Material.DOUBLE_STEP,
				Material.DOUBLE_STONE_SLAB2);

		itemsMaterialsForMazeWalls = createList(tagMaterialsForMazeWalls,
				Material.OBSIDIAN,
				Material.OBSIDIAN);

		itemsMaterialsForRoads = createList(tagMaterialsForRoads,
				Material.STAINED_CLAY,
				Material.QUARTZ_BLOCK);
	}
	
	private MaterialList createList(String name, Material ... materials) {
		
		// create the list and add all of the goodies
		MaterialList list = new MaterialList(name);
		list.add(materials);
		
		// add it to the big list so we can generically remember it
		listOfLists.add(list);
		
		// return it so we can especially remember it
		return list;
	}
	
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		for (MaterialList materialList : listOfLists) {
			materialList.read(generator, section);
		}
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		for (MaterialList materialList : listOfLists) {
			materialList.write(generator, section);
		}
	}

}
