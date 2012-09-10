package me.daddychurchill.CityWorld.Clipboard;

import java.util.HashMap;

import me.daddychurchill.CityWorld.Clipboard.PasteProvider.PlatTypes;

public class PlatList {

	private HashMap<PlatTypes, ClipboardList> list;
	
	public PlatList() {
		super();

		list = new HashMap<PlatTypes, ClipboardList>();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public ClipboardList get(PlatTypes type) {
		return list.get(type);
	}
	
	public void put(PlatTypes type, ClipboardList value) {
		list.put(type, value);
	}

}
