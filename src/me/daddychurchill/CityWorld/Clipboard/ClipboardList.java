package me.daddychurchill.CityWorld.Clipboard;

import java.util.HashMap;
import java.util.Iterator;


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
	
}
