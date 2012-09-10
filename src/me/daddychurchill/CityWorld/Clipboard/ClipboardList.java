package me.daddychurchill.CityWorld.Clipboard;

import java.util.HashMap;


public class ClipboardList {

	private String hack;
	
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
	
	public Clipboard getHack() {
		return list.get(hack);
	}
	
	public void put(Clipboard value) {
		hack = value.name;
		list.put(value.name, value);
	}
	
}
