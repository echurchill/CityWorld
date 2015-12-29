package me.daddychurchill.CityWorld.Plugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.CityWorldSettings;
import me.daddychurchill.CityWorld.Support.MaterialList;

public class MaterialProvider {

	public MaterialList itemsRandomMaterials_BunkerChests;
	public MaterialList itemsRandomMaterials_MineChests;
	public MaterialList itemsRandomMaterials_SewerChests;
	public MaterialList itemsRandomMaterials_StorageShedChests;
	public MaterialList itemsRandomMaterials_FarmChests;
	public MaterialList itemsRandomMaterials_FarmOutputChests;
	public MaterialList itemsRandomMaterials_LumberChests;
	public MaterialList itemsRandomMaterials_LumberOutputChests;
	public MaterialList itemsRandomMaterials_QuaryChests;
	public MaterialList itemsRandomMaterials_QuaryOutputChests;
	
	public MaterialList itemsSelectMaterial_BuildingWalls;
	public MaterialList itemsSelectMaterial_BuildingCeilings;
	public MaterialList itemsSelectMaterial_BuildingRoofs;
	public MaterialList itemsSelectMaterial_UnfinishedBuildings;
	public MaterialList itemsSelectMaterial_HouseWalls;
	public MaterialList itemsSelectMaterial_HouseFloors;
	public MaterialList itemsSelectMaterial_HouseCeilings;
	public MaterialList itemsSelectMaterial_HouseRoofs;
	public MaterialList itemsSelectMaterial_ShackWalls;
	public MaterialList itemsSelectMaterial_ShackRoofs;
	public MaterialList itemsSelectMaterial_ShedWalls;
	public MaterialList itemsSelectMaterial_ShedRoofs;
	public MaterialList itemsSelectMaterial_QuaryPiles;
	public MaterialList itemsSelectMaterial_Castles;
	
	public MaterialList itemsMaterialListFor_MazeWalls;
	public MaterialList itemsMaterialListFor_Roads;
	public MaterialList itemsMaterialListFor_NormalOres;
	public MaterialList itemsMaterialListFor_NetherOres;
	public MaterialList itemsMaterialListFor_TheEndOres;
	
	public final static String tagRandomMaterials_BunkerChests = "Random_Materials_For_Bunker_Chests";
	public final static String tagRandomMaterials_MineChests = "Random_Materials_For_Mine_Chests";
	public final static String tagRandomMaterials_SewerChests = "Random_Materials_For_Sewer_Chests";
	public final static String tagRandomMaterials_StorageShedChests = "Random_Materials_For_Storage_Shed_Chests";
	public final static String tagRandomMaterials_FarmChests = "Random_Materials_For_Farm_Chests";
	public final static String tagRandomMaterials_FarmOutputChests = "Random_Materials_For_Farm_Output_Chests";
	public final static String tagRandomMaterials_LumberChests = "Random_Materials_For_Lumber_Chests";
	public final static String tagRandomMaterials_LumberOutputChests = "Random_Materials_For_Lumber_Output_Chests";
	public final static String tagRandomMaterials_QuaryChests = "Random_Materials_For_Quary_Chests";
	public final static String tagRandomMaterials_QuaryOutputChests = "Random_Materials_For_Quary_Output_Chests";
	
	public final static String tagSelectMaterial_BuildingWalls = "Materials_For_BuildingWalls";
	public final static String tagSelectMaterial_BuildingCeilings = "Materials_For_BuildingCeilings";
	public final static String tagSelectMaterial_BuildingRoofs = "Materials_For_BuildingRoofs";
	public final static String tagSelectMaterial_UnfinishedBuildings = "Materials_For_UnfinishedBuildings";
	public final static String tagSelectMaterial_HouseWalls = "Materials_For_HouseWalls";
	public final static String tagSelectMaterial_HouseFloors = "Materials_For_HouseFloors";
	public final static String tagSelectMaterial_HouseCeilings = "Materials_For_HouseCeilings";
	public final static String tagSelectMaterial_HouseRoofs = "Materials_For_HouseRoofs";
	public final static String tagSelectMaterial_ShackWalls = "Materials_For_ShackWalls";
	public final static String tagSelectMaterial_ShackRoofs = "Materials_For_ShackRoofs";
	public final static String tagSelectMaterial_ShedWalls = "Materials_For_ShedWalls";
	public final static String tagSelectMaterial_ShedRoofs = "Materials_For_ShedRoofs";
	public final static String tagSelectMaterial_StoneWorksPiles = "Materials_For_QuaryPiles";
	public final static String tagSelectMaterial_Castles = "Materials_For_Castles";

	public final static String tagMaterialListFor_MazeWalls = "Materials_List_For_MazeWalls";
	public final static String tagMaterialListFor_Roads = "Materials_List_For_Roads";
	public final static String tagMaterialListFor_NormalOres = "Materials_List_For_NormalOres";
	public final static String tagMaterialListFor_NetherOres = "Materials_List_For_NetherOres";
	public final static String tagMaterialListFor_TheEndOres = "Materials_List_For_TheEndOres";
	
	
	private List<MaterialList> listOfLists;
	
	public MaterialProvider(CityWorldGenerator generator, CityWorldSettings settings) {
		
		listOfLists = new ArrayList<MaterialList>();

		itemsRandomMaterials_BunkerChests = createList(tagRandomMaterials_BunkerChests, 
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
		
		itemsRandomMaterials_MineChests = createList(tagRandomMaterials_MineChests, 
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

		itemsRandomMaterials_SewerChests = createList(tagRandomMaterials_SewerChests, 
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
		
		itemsRandomMaterials_StorageShedChests = createList(tagRandomMaterials_StorageShedChests,
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
		
		itemsRandomMaterials_FarmChests = createList(tagRandomMaterials_FarmChests,
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
		
		itemsRandomMaterials_FarmOutputChests = createList(tagRandomMaterials_FarmOutputChests,
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
		
		itemsRandomMaterials_LumberChests = createList(tagRandomMaterials_LumberChests,
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
		
		itemsRandomMaterials_LumberOutputChests = createList(tagRandomMaterials_LumberOutputChests,
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
		
		itemsRandomMaterials_QuaryChests = createList(tagRandomMaterials_QuaryChests,
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
		
		itemsRandomMaterials_QuaryOutputChests = createList(tagRandomMaterials_QuaryOutputChests,
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
		
		itemsSelectMaterial_BuildingWalls = createList(tagSelectMaterial_BuildingWalls,
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
		
		itemsSelectMaterial_BuildingCeilings = createList(tagSelectMaterial_BuildingCeilings,
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
		
		itemsSelectMaterial_BuildingRoofs = createList(tagSelectMaterial_BuildingRoofs,
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
		
		itemsSelectMaterial_UnfinishedBuildings = createList(tagSelectMaterial_UnfinishedBuildings,
				Material.CLAY,
				Material.CLAY,
				Material.HARD_CLAY,
				Material.HARD_CLAY,
				Material.STAINED_CLAY,
				Material.STAINED_CLAY,
				Material.STONE,
				Material.STONE,
				Material.WOOD);

		itemsSelectMaterial_HouseWalls = createList(tagSelectMaterial_HouseWalls,
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
		
		itemsSelectMaterial_HouseFloors = createList(tagSelectMaterial_HouseFloors,
				Material.COBBLESTONE,
				Material.STONE,
				Material.WOOL,
				Material.WOOD);
		
		itemsSelectMaterial_HouseCeilings = createList(tagSelectMaterial_HouseCeilings,
				Material.COBBLESTONE,
				Material.STONE,
				Material.SMOOTH_BRICK,
				Material.SANDSTONE,
				Material.RED_SANDSTONE,
				Material.WOOD);
		
		itemsSelectMaterial_HouseRoofs = createList(tagSelectMaterial_HouseRoofs,
				Material.COBBLESTONE,
				Material.MOSSY_COBBLESTONE,
				Material.STONE,
				Material.SMOOTH_BRICK,
				Material.SANDSTONE,
				Material.RED_SANDSTONE,
				Material.WOOD);
		
		itemsSelectMaterial_ShackWalls = createList(tagSelectMaterial_ShackWalls,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.WOOD,
				Material.MOSSY_COBBLESTONE,
				Material.RED_SANDSTONE,
				Material.NETHER_BRICK,
				Material.BRICK);
		
		itemsSelectMaterial_ShackRoofs = createList(tagSelectMaterial_ShackRoofs,
				Material.WOOD,
				Material.STEP,
				Material.WOOD_STEP);
		
		itemsSelectMaterial_ShedWalls = createList(tagSelectMaterial_ShedWalls,
				Material.STONE,
				Material.SANDSTONE,
				Material.RED_SANDSTONE,
				Material.WOOD,
				Material.COBBLESTONE,
				Material.BRICK,
				Material.SMOOTH_BRICK);
		
		itemsSelectMaterial_ShedRoofs = createList(tagSelectMaterial_ShedRoofs,
				Material.STEP,
				Material.WOOD_STEP);

		itemsSelectMaterial_QuaryPiles = createList(tagSelectMaterial_StoneWorksPiles,
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
		
		itemsSelectMaterial_Castles = createList(tagSelectMaterial_Castles,
				Material.STONE,
				Material.SMOOTH_BRICK,
				Material.COBBLESTONE,
				Material.MOSSY_COBBLESTONE,
				Material.ENDER_STONE,
				Material.DOUBLE_STEP,
				Material.DOUBLE_STONE_SLAB2);

		itemsMaterialListFor_MazeWalls = createList(tagMaterialListFor_MazeWalls,
				Material.OBSIDIAN,
				Material.OBSIDIAN);

		itemsMaterialListFor_Roads = createList(tagMaterialListFor_Roads,
				Material.STAINED_CLAY,
				Material.QUARTZ_BLOCK);
		
		itemsMaterialListFor_NormalOres = createList(tagMaterialListFor_NormalOres,
				Material.WATER,
				Material.LAVA,
				Material.GRAVEL,
				Material.COAL_ORE,
				Material.IRON_ORE,
				Material.GOLD_ORE,
				Material.LAPIS_ORE,
				Material.REDSTONE_ORE,
				Material.DIAMOND_ORE,
				Material.EMERALD_ORE);

		itemsMaterialListFor_NetherOres = createList(tagMaterialListFor_NetherOres,
				Material.LAVA,
				Material.GRAVEL,
				Material.SOUL_SAND,
				Material.GLOWSTONE,
				Material.QUARTZ_ORE,
				Material.OBSIDIAN);

		itemsMaterialListFor_TheEndOres = createList(tagMaterialListFor_TheEndOres,
				Material.WATER,
				Material.LAVA,
				Material.QUARTZ_BLOCK,
				Material.GLOWSTONE,
				Material.PRISMARINE);
	}
	
	private MaterialList createList(String name, Material ... materials) {
		
		// create the list and add all of the goodies
		MaterialList list = new MaterialList(name, materials);
		
		// add it to the big list so we can generically remember it
		listOfLists.add(list);
		
		// return it so we can specifically remember it
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
