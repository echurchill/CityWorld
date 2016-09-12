package me.daddychurchill.CityWorld.Support;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wood;
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
	
	public static MaterialData Planks = new Wood();
	public static MaterialData Planks_Oak = new Wood(TreeSpecies.GENERIC);
	public static MaterialData Planks_Spruce = new Wood(TreeSpecies.REDWOOD);
	public static MaterialData Planks_Birch = new Wood(TreeSpecies.BIRCH);
	public static MaterialData Planks_Jungle = new Wood(TreeSpecies.JUNGLE);
	public static MaterialData Planks_Acacia = new Wood(TreeSpecies.ACACIA);
	public static MaterialData Planks_Dark_Oak = new Wood(TreeSpecies.DARK_OAK);
	
	public static MaterialData Sapling = new Sapling();
	public static MaterialData Sapling_Oak = new Wood(TreeSpecies.GENERIC);
	public static MaterialData Sapling_Spruce = new Wood(TreeSpecies.REDWOOD);
	public static MaterialData Sapling_Birch = new Wood(TreeSpecies.BIRCH);
	public static MaterialData Sapling_Jungle = new Wood(TreeSpecies.JUNGLE);
	public static MaterialData Sapling_Acacia = new Wood(TreeSpecies.ACACIA);
	public static MaterialData Sapling_Dark_Oak = new Wood(TreeSpecies.DARK_OAK);
	
	public static MaterialData BedRock = new MaterialData(Material.BEDROCK);
	
	public static MaterialData Water = new MaterialData(Material.STATIONARY_WATER);
	public static MaterialData Water_Flowing = new MaterialData(Material.WATER);
	public static MaterialData Lava = new MaterialData(Material.STATIONARY_LAVA);
	public static MaterialData Lava_Flowing = new MaterialData(Material.LAVA);
	
	public static MaterialData Sand = new MaterialData(Material.SAND);
	public static MaterialData Sand_Red = new MaterialData(Material.SAND, (byte)1);
	
	public static MaterialData Gravel = new MaterialData(Material.GRAVEL);
	
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

}
