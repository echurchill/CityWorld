package me.daddychurchill.CityWorld.Plugins;

public class Tekkit {
	private final static int dataScale = 4096;
	public enum Material {
//		RPFLOWER(139),
		INDIGO_FLOWER(139 + 0 * dataScale), // RPFLOWER
//		RUBBER_SAPLING(139 + 1 * dataScale),
		
//		RPORES(140),
		RUBY_ORE(140 + 0 * dataScale), // RPORE
		EMERALD_ORE(140 + 1 * dataScale),
		SAPPHIRE_ORE(140 + 2 * dataScale), 
		SILVER_ORE(140 + 3 * dataScale), 
//		TIN(140 + 4 * dataScale), 
//		COPPER(140 + 5 * dataScale), 
		TUNGSTEN_ORE(140 + 6 * dataScale), 
		NIKOLITE_ORE(140 + 7 * dataScale), 
		
		LEAVES(141),
		
//		RPSTONE(142),
		MARBLE_BLOCK(142 + 0 * dataScale), 
		BASALT_BLOCK(142 + 1 * dataScale), 
		MARBLE_BRICK(142 + 2 * dataScale), 
		BASALT_COBBLESTONE(142 + 3 * dataScale), 
		BASALT_BRICK(142 + 4 * dataScale), 
		
//		RUBBER_WOOD(143),
		
//		RPJEWEL(145),
		RUBY_BLOCK(145 + 0 * dataScale),
		EMERALD_BLOCK(145 + 1 * dataScale),
		SAPPHIRE_BLOCK(145 + 2 * dataScale),
		
//		RPLAMPON(146),
		LAMP_WHITE_ON(145 + 0 * dataScale),
		LAMP_ORANGE_ON(145 + 1 * dataScale),
		LAMP_MAGENTA_ON(145 + 2 * dataScale),
		LAMP_LIGHT_BLUE_ON(145 + 3 * dataScale),
		LAMP_YELLOW_ON(145 + 4 * dataScale),
		LAMP_LIME_ON(145 + 5 * dataScale),
		LAMP_PINK_ON(145 + 6 * dataScale),
		LAMP_GRAY_ON(145 + 7 * dataScale),
		LAMP_LIGHT_GRAY_ON(145 + 8 * dataScale),
		LAMP_CYAN_ON(145 + 9 * dataScale),
		LAMP_PURPLE_ON(145 + 10 * dataScale),
		LAMP_BLUE_ON(145 + 11 * dataScale),
		LAMP_BROWN_ON(145 + 12 * dataScale),
		LAMP_GREEN_ON(145 + 13 * dataScale),
		LAMP_RED_ON(145 + 14 * dataScale),
		LAMP_BLACK_ON(145 + 15 * dataScale),

//		RPLAMPOFF(147),
		LAMP_WHITE_OFF(147 + 0 * dataScale),
		LAMP_ORANGE_OFF(147 + 1 * dataScale),
		LAMP_MAGENTA_OFF(147 + 2 * dataScale),
		LAMP_LIGHT_BLUE_OFF(147 + 3 * dataScale),
		LAMP_YELLOW_OFF(147 + 4 * dataScale),
		LAMP_LIME_OFF(147 + 5 * dataScale),
		LAMP_PINK_OFF(147 + 6 * dataScale),
		LAMP_GRAY_OFF(147 + 7 * dataScale),
		LAMP_LIGHT_GRAY_OFF(147 + 8 * dataScale),
		LAMP_CYAN_OFF(147 + 9 * dataScale),
		LAMP_PURPLE_OFF(147 + 10 * dataScale),
		LAMP_BLUE_OFF(147 + 11 * dataScale),
		LAMP_BROWN_OFF(147 + 12 * dataScale),
		LAMP_GREEN_OFF(147 + 13 * dataScale),
		LAMP_RED_OFF(147 + 14 * dataScale),
		LAMP_BLACK_OFF(147 + 15 * dataScale),

//		CRAFTINGTABLEIII(149),
//		RPMACHINE(150),
//		
//		RPFRAME(152),
//		MACHINEBLOCK(153),
//		MARKERBLOCK(154),
//		FILLERBLOCK(155),
//		BUILDERBLOCK(157),
//		TEMPLATEBLOCK(158),
//
//		FRAMEBLOCK(160),
//
//		OILMOVING(162),
//		OILSTILL(163),
//		PUMPBLOCK(164),
//		TANKBLOCK(165),
//
//		REFINERYBLOCK(167),
//		AUTOWORKBENCHBLOCK(169),
//
//		PLAINPIPEBLOCK(171),
//
//		MININGWELLBLOCK(174),
//
//		ENDERCHEST(178),
//		TELEPORT_TETHER(179),
//		IRONCHEST(181),
//		COMPACTSOLAR(183),
//		BLOCKCHARGINGBENCH(187),
//
//		POWERCONVERTER(190),
//		BLOCKTHERMALMONITOR(192),
//
//		RAILCRAFTTRACK(206),
//		COMPUTER(207),
//		DISKDRIVE(208),
//		STRUCTUREBLOCK(209),
//		CARTDETECTOR(211),
//		ELEVATORRAIL(212),
//		BLOCKUTILITY(213),
//		BLOCKRAILCRAFTCUBE(214),
//
//		TURTLE(216),
//
//		CROP(218),
//		LUMINATORD(219),
//		SCAFFOLD(220),
//		//WALL(221),
//		CONSTRUCTION_FOAM(222),

		COPPER_BLOCK(224 + 0 * dataScale), 
		TIN_BLOCK(224 + 1 * dataScale),
		BRONZE_BLOCK(224 + 2 * dataScale), 
		URANIUM_BLOCK(224 + 3 * dataScale), 
		
		LUMINATOR(226),

//		REENFORCED_DOOR(229),
		REENFORCED_GLASS(230),
		REENFORCED_STONE(231),
		IRON_FENCE(232),
//		REACTOR_CHAMBER(233),
		RUBBER_SHEET(234),
		IRON_SCAFFOLD(235),
//		BLOCKDYNAMITE(236),
//		NUKE(237),
//		DYNAMITE_REMOTE(238),
//		INDUSTRIAL_TNT(239),

		RUBBER_SAPLING(241),
		RUBBER_LEAVES(242),
		RUBBER_WOOD(243),
		MINING_TIP(244),
		MINING_PIPE(245),

		URANIUM_ORE(247),
		TIN_ORE(248),
		COPPER_ORE(249);

		private int data;
		private Material(int d) {
			data = d;
		}
		public int getData() {
			return data;
		}
		
		public int getTypeId(Material value) {
			return value.data % dataScale;
		}
		
		public int getDataId(Material value) {
			return value.data / dataScale;
		}
	}
}
