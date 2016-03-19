package me.daddychurchill.CityWorld.Plats.Nature;

import java.util.ArrayList;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class WoodframeLot extends WoodworksLot {

	public WoodframeLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		return new WoodframeLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		int y = generator.streetLevel + floorHeight + 1;
		
//		// set up the array
//		boolean[][] sections = new boolean[3][3];
//		for (int x = 0; x < 3; x++)
//			for (int z = 0; z < 3; z++)
//				sections[x][z] = true;
	
		// set up the list
		ArrayList<Integer> sectionsLeft = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++)
			sectionsLeft.add(i);
		
		// remember the tallest one
		int x = 0;
		int z = 0;
		
		// work through the list
		while (!sectionsLeft.isEmpty()) {
			for (int i = 0; i < sectionsLeft.size(); i++) {
				int v = sectionsLeft.get(i);
				x = v / 3;
				z = v - (x * 3);
				generateSection(chunk, x * sectionWidth, y, z * sectionWidth);
			}
			
			// remove one
			sectionsLeft.remove(chunkOdds.getRandomInt(sectionsLeft.size()));
			
			// remove an extra one?
			if (!sectionsLeft.isEmpty() && chunkOdds.playOdds(Odds.oddsSomewhatUnlikely))
				sectionsLeft.remove(chunkOdds.getRandomInt(sectionsLeft.size()));
			
			// up one
			y = y + floorHeight;
		}
		
		// add stairs up to the top
		y = y - floorHeight;
		while (y > generator.streetLevel + 1) {
			generateStairs(chunk, x * sectionWidth + 4, y, z * sectionWidth + 1);
			y = y - floorHeight;
		}
		
		// place snow
		generateSurface(generator, chunk, false);
	}
	
}
