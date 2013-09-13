package me.daddychurchill.CityWorld.Plugins.Tekkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

// tekkit support by gunre
// reworked a bit by daddychurchill

public class TekkitMaterial {
	public final static String pluginName = "mod_MinecraftForge";
	public final static String pluginMinVersion = "?.?";
	
	public final static int typeScale = 1000;
	
	public final static int INDIGO_FLOWER = 139 * typeScale + 0; // RPFLOWER
//	public final static int RUBBER_SAPLING = 139 * typeScale + 1;
	public final static int RUBY_ORE = 140 * typeScale + 0; // RPORE
	public final static int EMERALD_ORE = 140 * typeScale + 1;
	public final static int SAPPHIRE_ORE = 140 * typeScale + 2; 
	public final static int SILVER_ORE = 140 * typeScale + 3; 
//	public final static int TIN_ORE = 140 * typeScale + 4; 
//	public final static int COPPER_ORE = 140 * typeScale + 5; 
	public final static int TUNGSTEN_ORE = 140 * typeScale + 6; 
	public final static int NIKOLITE_ORE = 140 * typeScale + 7; 
	public final static int LEAVES = 141;
	public final static int MARBLE_BLOCK = 142 * typeScale + 0; 
	public final static int BASALT_BLOCK = 142 * typeScale + 1; 
	public final static int MARBLE_BRICK = 142 * typeScale + 2; 
	public final static int BASALT_COBBLESTONE = 142 * typeScale + 3; 
	public final static int BASALT_BRICK = 142 * typeScale + 4; 
//	public final static int RUBBER_WOOD = 143;
//  public final static int 144
	public final static int RUBY_BLOCK = 145 * typeScale + 0;
	public final static int EMERALD_BLOCK = 145 * typeScale + 1;
	public final static int SAPPHIRE_BLOCK = 145 * typeScale + 2;
	public final static int LAMP_WHITE_ON = 146 * typeScale + 0;
	public final static int LAMP_ORANGE_ON = 146 * typeScale + 1;
	public final static int LAMP_MAGENTA_ON = 146 * typeScale + 2;
	public final static int LAMP_LIGHT_BLUE_ON = 146 * typeScale + 3;
	public final static int LAMP_YELLOW_ON = 146 * typeScale + 4;
	public final static int LAMP_LIME_ON = 146 * typeScale + 5;
	public final static int LAMP_PINK_ON = 146 * typeScale + 6;
	public final static int LAMP_GRAY_ON = 146 * typeScale + 7;
	public final static int LAMP_LIGHT_GRAY_ON = 146 * typeScale + 8;
	public final static int LAMP_CYAN_ON = 146 * typeScale + 9;
	public final static int LAMP_PURPLE_ON = 146 * typeScale + 10;
	public final static int LAMP_BLUE_ON = 146 * typeScale + 11;
	public final static int LAMP_BROWN_ON = 146 * typeScale + 12;
	public final static int LAMP_GREEN_ON = 146 * typeScale + 13;
	public final static int LAMP_RED_ON = 146 * typeScale + 14;
	public final static int LAMP_BLACK_ON = 146 * typeScale + 15;
	public final static int LAMP_WHITE_OFF = 147 * typeScale + 0;
	public final static int LAMP_ORANGE_OFF = 147 * typeScale + 1;
	public final static int LAMP_MAGENTA_OFF = 147 * typeScale + 2;
	public final static int LAMP_LIGHT_BLUE_OFF = 147 * typeScale + 3;
	public final static int LAMP_YELLOW_OFF = 147 * typeScale + 4;
	public final static int LAMP_LIME_OFF = 147 * typeScale + 5;
	public final static int LAMP_PINK_OFF = 147 * typeScale + 6;
	public final static int LAMP_GRAY_OFF = 147 * typeScale + 7;
	public final static int LAMP_LIGHT_GRAY_OFF = 147 * typeScale + 8;
	public final static int LAMP_CYAN_OFF = 147 * typeScale + 9;
	public final static int LAMP_PURPLE_OFF = 147 * typeScale + 10;
	public final static int LAMP_BLUE_OFF = 147 * typeScale + 11;
	public final static int LAMP_BROWN_OFF = 147 * typeScale + 12;
	public final static int LAMP_GREEN_OFF = 147 * typeScale + 13;
	public final static int LAMP_RED_OFF = 147 * typeScale + 14;
	public final static int LAMP_BLACK_OFF = 147 * typeScale + 15;
//  public final static int 148
//	public final static int CRAFTINGTABLEIII = 149;
//	public final static int RPMACHINE = 150;
//	public final static int 151
//	public final static int RPFRAME = 152;
//	public final static int MACHINEBLOCK = 153;
//	public final static int MARKERBLOCK = 154;
//	public final static int FILLERBLOCK = 155;
//	public final static int BUILDERBLOCK = 157;
//	public final static int TEMPLATEBLOCK = 158;
//  public final static int 159
//	public final static int FRAMEBLOCK = 160;
//  public final static int 161
	public final static int OIL = 162;
	public final static int STATIONARY_OIL = 163;
//	public final static int PUMPBLOCK = 164;
//	public final static int TANKBLOCK = 165;
//  public final static int 166
//	public final static int REFINERYBLOCK = 167;
//	public final static int AUTOWORKBENCHBLOCK = 169;
//  public final static int 170
//	public final static int PLAINPIPEBLOCK = 171;
//  public final static int 172
//  public final static int 173
//	public final static int MININGWELLBLOCK = 174;
//  public final static int 175
//  public final static int 176
//  public final static int 178
//	public final static int ENDERCHEST = 178;
//	public final static int TELEPORT_TETHER = 179;
//  public final static int 180
//	public final static int IRONCHEST = 181;
//	public final static int COMPACTSOLAR = 183;
//	public final static int BLOCKCHARGINGBENCH = 187;
//  public final static int 188
//  public final static int 189
//	public final static int POWERCONVERTER = 190;
//	public final static int BLOCKTHERMALMONITOR = 192;
//  public final static int 193
//  public final static int 194
//  public final static int 195
//  public final static int 196
//  public final static int 197
//  public final static int 198
//  public final static int 199
//  public final static int 200
//  public final static int 201
//  public final static int 202
//  public final static int 203
//  public final static int 204
//  public final static int 205
//	public final static int RAILCRAFTTRACK = 206;
//	public final static int COMPUTER = 207;
//	public final static int DISKDRIVE = 208;
//	public final static int STRUCTUREBLOCK = 209;
//	public final static int CARTDETECTOR = 211;
//	public final static int ELEVATORRAIL = 212;
//	public final static int BLOCKUTILITY = 213;
//	public final static int BLOCKRAILCRAFTCUBE = 214;
//  public final static int 215
//	public final static int TURTLE = 216;
//  public final static int 216
//  public final static int 217
//	public final static int CROP = 218;
//	public final static int LUMINATORD = 219;
//	public final static int SCAFFOLD = 220;
//	public final static int WALL = 221;
//	public final static int CONSTRUCTION_FOAM = 222;
//  public final static int 223
	public final static int COPPER_BLOCK = 224 * typeScale + 0; 
	public final static int TIN_BLOCK = 224 * typeScale + 1;
	public final static int BRONZE_BLOCK = 224 * typeScale + 2; 
	public final static int URANIUM_BLOCK = 224 * typeScale + 3; 
//  public final static int 225
	public final static int LUMINATOR = 226;
//  public final static int 227
//  public final static int 228
//	public final static int REENFORCED_DOOR = 229;
	public final static int REENFORCED_GLASS = 230;
	public final static int REENFORCED_STONE = 231;
	public final static int IRON_FENCE = 232;
//	public final static int REACTOR_CHAMBER = 233;
	public final static int RUBBER_SHEET = 234;
	public final static int IRON_SCAFFOLD = 235;
//	public final static int BLOCKDYNAMITE = 236;
//	public final static int NUKE = 237;
//	public final static int DYNAMITE_REMOTE = 238;
//	public final static int INDUSTRIAL_TNT = 239;
//  public final static int 240
	public final static int RUBBER_SAPLING = 241;
	public final static int RUBBER_LEAVES = 242;
	public final static int RUBBER_WOOD = 243;
	public final static int MINING_TIP = 244;
	public final static int MINING_PIPE = 245;
//  public final static int 246
	public final static int URANIUM_ORE = 247;
	public final static int TIN_ORE = 248;
	public final static int COPPER_ORE = 249;
	
	public static final boolean isTekkitForgeEnabled() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		Plugin pluginTekkitForge = pm.getPlugin(pluginName);
		return pluginTekkitForge != null && pluginTekkitForge.isEnabled();
	}
}
