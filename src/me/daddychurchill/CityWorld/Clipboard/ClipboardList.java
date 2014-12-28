package me.daddychurchill.CityWorld.Clipboard;

import java.util.HashMap;
import java.util.Iterator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;


public class ClipboardList implements Iterable<Clipboard> {

	public ClipboardList() {
		super();
		
		list = new HashMap<String, Clipboard>();
	}
	
	private HashMap<String, Clipboard> list;
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public Clipboard get(String key) {
		return list.get(key);
	}
	
	public Clipboard put(Clipboard value) {
		list.put(value.name, value);
		return value;
	}

	@Override
	public Iterator<Clipboard> iterator() {
		return list.values().iterator();
	}
	
	public int count() {
		return list.size();
	}
	
	public void populate(CityWorldGenerator generator, PlatMap platmap) {

		// grab platmap's random
		Odds odds = platmap.getOddsGenerator();
		
		// for each schematic
		for (Clipboard clip: this) {

			// that succeeds the OddsOfAppearance
			if (odds.playOdds(clip.oddsOfAppearance)) {
				platmap.placeSpecificClip(generator, odds, clip);
			}
		}
	}

	public Clipboard getSingleLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int placeX, int placeZ) {

		// for each schematic
		for (Clipboard clip: this) {

			// that succeeds the OddsOfAppearance
			if (clip.chunkX == 1 && clip.chunkZ == 1 && odds.playOdds(clip.oddsOfAppearance))
				return clip;
		}
		
		// assume failure then
		return null;
	}
}
