package me.daddychurchill.CityWorld.Support;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.material.Sapling;

@SuppressWarnings("deprecation")
public class MaterialMagic {

	public MaterialMagic() {
		// TODO Auto-generated constructor stub
	}
	
	public static MaterialData Air = new MaterialData(Material.AIR);
	
	public static MaterialData Stone = new MaterialData(Material.STONE);
	public static MaterialData Granite = new MaterialData(Material.STONE, (byte)1);
	public static MaterialData Granite_Polished = new MaterialData(Material.STONE, (byte)2);
	public static MaterialData Diorite = new MaterialData(Material.STONE, (byte)3);
	public static MaterialData Diorite_Polished = new MaterialData(Material.STONE, (byte)4);
	public static MaterialData Andesite = new MaterialData(Material.STONE, (byte)5);
	public static MaterialData Andesite_Polished = new MaterialData(Material.STONE, (byte)6);

	public static MaterialData Grass = new MaterialData(Material.GRASS);
	
	public static MaterialData Dirt = new MaterialData(Material.DIRT);
	public static MaterialData Dirt_Course = new MaterialData(Material.DIRT, (byte)1);
	public static MaterialData Podsol = new MaterialData(Material.DIRT, (byte)2);
	
	public static MaterialData Cobblestone = new MaterialData(Material.COBBLESTONE);
	
	public static MaterialData Planks = new MaterialData(Material.WOOD);
	public static MaterialData Planks_Oak = new MaterialData(Material.WOOD, TreeSpecies.GENERIC.getData());
	public static MaterialData Planks_Spruce = new MaterialData(Material.WOOD, TreeSpecies.REDWOOD.getData());
	public static MaterialData Planks_Birch = new MaterialData(Material.WOOD, TreeSpecies.BIRCH.getData());
	public static MaterialData Planks_Jungle = new MaterialData(Material.WOOD, TreeSpecies.JUNGLE.getData());
	public static MaterialData Planks_Acacia = new MaterialData(Material.WOOD, TreeSpecies.ACACIA.getData());
	public static MaterialData Planks_Dark_Oak = new MaterialData(Material.WOOD, TreeSpecies.DARK_OAK.getData());
	
	public static MaterialData Sapling = new Sapling();
	public static MaterialData Sapling_Oak = new Sapling(TreeSpecies.GENERIC);
	public static MaterialData Sapling_Spruce = new Sapling(TreeSpecies.REDWOOD);
	public static MaterialData Sapling_Birch = new Sapling(TreeSpecies.BIRCH);
	public static MaterialData Sapling_Jungle = new Sapling(TreeSpecies.JUNGLE);
	public static MaterialData Sapling_Acacia = new Sapling(TreeSpecies.ACACIA);
	public static MaterialData Sapling_Dark_Oak = new Sapling(TreeSpecies.DARK_OAK);
	
	public static MaterialData BedRock = new MaterialData(Material.BEDROCK);
	
	public static MaterialData Water = new MaterialData(Material.STATIONARY_WATER);
	public static MaterialData Water_Flowing = new MaterialData(Material.WATER);
	public static MaterialData Lava = new MaterialData(Material.STATIONARY_LAVA);
	public static MaterialData Lava_Flowing = new MaterialData(Material.LAVA);
	
	public static MaterialData Sand = new MaterialData(Material.SAND);
	public static MaterialData Sand_Red = new MaterialData(Material.SAND, (byte)1);
	
	public static MaterialData Gravel = new MaterialData(Material.GRAVEL);
	public static MaterialData Gold_Ore = new MaterialData(Material.GOLD_ORE);
	public static MaterialData Iron_Ore = new MaterialData(Material.IRON_ORE);
	public static MaterialData Coal_Ore = new MaterialData(Material.COAL_ORE);

	public static MaterialData Log = new MaterialData(Material.LOG);
	public static MaterialData Log_Oak = new MaterialData(Material.LOG, (byte)0);//TreeSpecies.GENERIC
	public static MaterialData Log_Spruce = new MaterialData(Material.LOG, (byte)1);//TreeSpecies.REDWOOD
	public static MaterialData Log_Birch = new MaterialData(Material.LOG, (byte)2);//TreeSpecies.BIRCH
	public static MaterialData Log_Jungle = new MaterialData(Material.LOG, (byte)3);//TreeSpecies.JUNGLE
	public static MaterialData Log_Acacia = new MaterialData(Material.LOG_2, (byte)1);//TreeSpecies.ACACIA;
	public static MaterialData Log_Dark_Oak = new MaterialData(Material.LOG_2, (byte)1);//TreeSpecies.DARK_OAK;
	
	public static MaterialData Leaves = new MaterialData(Material.LEAVES);
	public static MaterialData Leaves_Oak = new MaterialData(Material.LEAVES, (byte)0);//TreeSpecies.GENERIC
	public static MaterialData Leaves_Spruce = new MaterialData(Material.LEAVES, (byte)1);//TreeSpecies.REDWOOD
	public static MaterialData Leaves_Birch = new MaterialData(Material.LEAVES, (byte)2);//TreeSpecies.BIRCH
	public static MaterialData Leaves_Jungle = new MaterialData(Material.LEAVES, (byte)3);//TreeSpecies.JUNGLE
	public static MaterialData Leaves_Acacia = new MaterialData(Material.LEAVES_2, (byte)1);//TreeSpecies.ACACIA;
	public static MaterialData Leaves_Dark_Oak = new MaterialData(Material.LEAVES_2, (byte)1);//TreeSpecies.DARK_OAK;
	
	public static MaterialData Glass = new MaterialData(Material.GLASS);
	
	public static MaterialData Lapis_Ore = new MaterialData(Material.LAPIS_ORE);
	public static MaterialData Lapis_Block = new MaterialData(Material.LAPIS_BLOCK);
	
//	public static MaterialData Dispenser = new MaterialData(Material.DISPENSER);

	public static MaterialData SandStone = new MaterialData(Material.SANDSTONE);
	public static MaterialData SandStone_Chiseled = new MaterialData(Material.SANDSTONE, (byte)1);
	public static MaterialData SandStone_Smooth = new MaterialData(Material.SANDSTONE, (byte)1);

//	public static MaterialData Note_Block = new MaterialData(Material.NOTE_BLOCK);
//	public static MaterialData Bed = new MaterialData(Material.BED_BLOCK);
//	public static MaterialData Rail_Powered = new MaterialData(Material.);
//	public static MaterialData Rail_Detector = new MaterialData(Material.);
//	public static MaterialData Piston_Sticky = new MaterialData(Material.);
//	public static MaterialData Cobweb = new MaterialData(Material.);
//	public static MaterialData Grass = new MaterialData(Material.);
//	public static MaterialData Grass_Dead = new MaterialData(Material.);
//	public static MaterialData Piston = new MaterialData(Material.);
//	public static MaterialData Piston_Head = new MaterialData(Material.);
	
	public static MaterialData Wool = new Wool();
	public static MaterialData Wool_White = new Wool(DyeColor.WHITE);
	public static MaterialData Wool_Orange = new Wool(DyeColor.ORANGE);
	public static MaterialData Wool_Magenta = new Wool(DyeColor.MAGENTA);
	public static MaterialData Wool_Light_Blue = new Wool(DyeColor.LIGHT_BLUE);
	public static MaterialData Wool_Yellow = new Wool(DyeColor.YELLOW);
	public static MaterialData Wool_Lime = new Wool(DyeColor.LIME);
	public static MaterialData Wool_Pink = new Wool(DyeColor.PINK);
	public static MaterialData Wool_Gray = new Wool(DyeColor.GRAY);
	public static MaterialData Wool_Silver = new Wool(DyeColor.SILVER);
	public static MaterialData Wool_Cyan = new Wool(DyeColor.CYAN);
	public static MaterialData Wool_Purple = new Wool(DyeColor.PURPLE);
	public static MaterialData Wool_Blue = new Wool(DyeColor.BLUE);
	public static MaterialData Wool_Brown = new Wool(DyeColor.BROWN);
	public static MaterialData Wool_Green = new Wool(DyeColor.GREEN);
	public static MaterialData Wool_Red = new Wool(DyeColor.RED);
	public static MaterialData Wool_Black = new Wool(DyeColor.BLACK);

//	public static MaterialData Piston_Extension = new MaterialData(Material.);
//	public static MaterialData Flower_Yellow = new MaterialData(Material.);
//	public static MaterialData Flower_Poppy = new MaterialData(Material.);
//	public static MaterialData Flower_Blue_Orchard = new MaterialData(Material.);
//	public static MaterialData Flower_Allium = new MaterialData(Material.);
//	public static MaterialData Flower_Azure_Bluet = new MaterialData(Material.);
//	public static MaterialData Flower_Tulip_Red = new MaterialData(Material.);
//	public static MaterialData Flower_Tulip_Orange = new MaterialData(Material.);
//	public static MaterialData Flower_Tulip_White = new MaterialData(Material.);
//	public static MaterialData Flower_Tulip_Pink = new MaterialData(Material.);
//	public static MaterialData Flower_Oxeye_Daisy = new MaterialData(Material.);
//	public static MaterialData Mushroom_Brown = new MaterialData(Material.);
//	public static MaterialData Mushroom_Red = new MaterialData(Material.);

//	public static MaterialData Gold_Block = new MaterialData(Material.);
//	public static MaterialData Iron_Block = new MaterialData(Material.);
//	public static MaterialData Double_Stone_Slab = new MaterialData(Material.);
//	public static MaterialData Stone_Slab = new MaterialData(Material.);
//	public static MaterialData Brick = new MaterialData(Material.);
//	public static MaterialData TNT = new MaterialData(Material.);
//	public static MaterialData Bookshelf = new MaterialData(Material.);
//	public static MaterialData Cobblestone_Mossy = new MaterialData(Material.);
//	public static MaterialData Obsidian_Block = new MaterialData(Material.);
//	public static MaterialData Torch = new MaterialData(Material.);
//	public static MaterialData Fire = new MaterialData(Material.);
//	public static MaterialData Mob_Spawner = new MaterialData(Material.);
//	public static MaterialData Stairs_Oak = new MaterialData(Material.);
//	public static MaterialData Chest = new MaterialData(Material.);
//	public static MaterialData Redstone_Wire = new MaterialData(Material.);
//	public static MaterialData Diamond_Ore = new MaterialData(Material.);
//	public static MaterialData Diamond_Block = new MaterialData(Material.);
//	public static MaterialData Crafting_Table = new MaterialData(Material.);
//	public static MaterialData Wheat = new MaterialData(Material.);
//	public static MaterialData Farmland = new MaterialData(Material.);
//	public static MaterialData Furnace = new MaterialData(Material.);
//	public static MaterialData Furnace_Lit = new MaterialData(Material.);
//	public static MaterialData Sign_Standing = new MaterialData(Material.);
//	public static MaterialData Wood_Door = new MaterialData(Material.);
//	public static MaterialData Wood_Ladder = new MaterialData(Material.);
//	public static MaterialData Rail = new MaterialData(Material.);
//	public static MaterialData Stairs_Stone = new MaterialData(Material.);
//	public static MaterialData Sign_Wall = new MaterialData(Material.);
//	public static MaterialData Lever = new MaterialData(Material.);
//	public static MaterialData Pressure_Plate_Stone = new MaterialData(Material.);
//	public static MaterialData Iron_Door = new MaterialData(Material.);
//	public static MaterialData Pressure_Plate_Wood = new MaterialData(Material.);
//	public static MaterialData Redstone_Ore = new MaterialData(Material.);
//	public static MaterialData Redstone_Ore_Lit = new MaterialData(Material.);
//	public static MaterialData Torch_Redstone = new MaterialData(Material.);
//	public static MaterialData Torch_Redstone_Lit = new MaterialData(Material.);
//	public static MaterialData Button_Stone = new MaterialData(Material.);
//	public static MaterialData Snow_Layer = new MaterialData(Material.);
//	public static MaterialData Ice = new MaterialData(Material.);
//	public static MaterialData Snow = new MaterialData(Material.);
//	public static MaterialData Cactus = new MaterialData(Material.);
//	public static MaterialData Clay = new MaterialData(Material.);
//	public static MaterialData Reeds = new MaterialData(Material.);
//	public static MaterialData Jukebox = new MaterialData(Material.);
//	public static MaterialData Fence_Wood = new MaterialData(Material.);
//	public static MaterialData Pumpkin = new MaterialData(Material.);
//	public static MaterialData Netherrack = new MaterialData(Material.);
//	public static MaterialData Sand_Soul = new MaterialData(Material.);
//	public static MaterialData Glowstone = new MaterialData(Material.);
//	public static MaterialData Portal = new MaterialData(Material.);
//	public static MaterialData Pumpkin_Lit = new MaterialData(Material.);
//	public static MaterialData Cake = new MaterialData(Material.);
//	public static MaterialData Repeater = new MaterialData(Material.);
//	public static MaterialData Repeater_Powered = new MaterialData(Material.);
//	public static MaterialData Glass_White = new MaterialData(Material.STAINED_GLASS);
//	public static MaterialData Trapdoor = new MaterialData(Material.);
//	public static MaterialData Monster_Egg = new MaterialData(Material.);

	public static MaterialData Stone_Brick = new MaterialData(Material.SMOOTH_BRICK, (byte)0);
	public static MaterialData Stone_Brick_Mossy = new MaterialData(Material.SMOOTH_BRICK, (byte)1);
	public static MaterialData Stone_Brick_Cracked = new MaterialData(Material.SMOOTH_BRICK, (byte)2);
	public static MaterialData Stone_Brick_Chiseled = new MaterialData(Material.SMOOTH_BRICK, (byte)3);

	
	
	public static MaterialData RedSandStone = new MaterialData(Material.RED_SANDSTONE);
	public static MaterialData RedSandStone_Chiseled = new MaterialData(Material.RED_SANDSTONE, (byte)1);
	public static MaterialData RedSandStone_Smooth = new MaterialData(Material.RED_SANDSTONE, (byte)1);

//	public static MaterialData color(Material material, DyeColor color) {
//		return new MaterialData(material, color.getData());
//	}
//	
}
