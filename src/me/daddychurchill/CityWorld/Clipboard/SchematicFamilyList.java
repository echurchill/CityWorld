package me.daddychurchill.CityWorld.Clipboard;

import java.util.HashMap;

import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;

public class SchematicFamilyList {

	private HashMap<SchematicFamily, ClipboardList> list;
	
	public SchematicFamilyList() {
		super();

		list = new HashMap<SchematicFamily, ClipboardList>();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public ClipboardList get(SchematicFamily type) {
		return list.get(type);
	}
	
	public ClipboardList add(SchematicFamily type, ClipboardList value) {
		list.put(type, value);
		return value;
	}
	
}
