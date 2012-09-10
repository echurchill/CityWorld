package me.daddychurchill.CityWorld.Clipboard;

import java.util.HashMap;

import me.daddychurchill.CityWorld.Clipboard.PasteProvider.AreaTypes;

public class AreaList {

	private HashMap<AreaTypes, PlatList> list;
	
	public AreaList() {
		super();

		list = new HashMap<AreaTypes, PlatList>();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public PlatList get(AreaTypes type) {
		return list.get(type);
	}
	
	public void put(AreaTypes type, PlatList value) {
		list.put(type, value);
	}
	
}
