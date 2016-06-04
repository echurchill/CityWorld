package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.CityWorldSettings;
import me.daddychurchill.CityWorld.Support.AnimalList;
import me.daddychurchill.CityWorld.Support.EntityList;
import me.daddychurchill.CityWorld.Support.SeaAnimalList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public class SpawnProvider extends Provider {

	public final static String tagEntities_Goodies = "Entities_For_Goodies";
	public EntityList itemsEntities_Goodies = createList(tagEntities_Goodies,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.WITCH);
	
	public final static String tagEntities_Baddies = "Entities_For_Baddies";
	public EntityList itemsEntities_Baddies = createList(tagEntities_Baddies,
			EntityType.CREEPER,
			EntityType.CREEPER,
			EntityType.CREEPER,
			EntityType.CREEPER,
			EntityType.SKELETON,
			EntityType.SKELETON,
			EntityType.SKELETON,
			EntityType.SKELETON,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE,
			EntityType.SPIDER,
			EntityType.SPIDER,
			EntityType.SPIDER,
			EntityType.WITCH,
			EntityType.WITCH,
			EntityType.PIG_ZOMBIE,
			EntityType.ENDERMAN,
			EntityType.BLAZE);
	
	public final static String tagEntities_Animals = "Entities_For_Animals";
	public EntityList itemsEntities_Animals = createAnimalList(tagEntities_Animals,
			EntityType.HORSE,
			EntityType.HORSE,
			EntityType.COW,
			EntityType.COW,
			EntityType.SHEEP,
			EntityType.SHEEP,
			EntityType.PIG,
			EntityType.PIG,
			EntityType.CHICKEN,
			EntityType.CHICKEN,
			EntityType.CHICKEN,
			EntityType.CHICKEN,
			EntityType.CHICKEN,
			EntityType.CHICKEN,
			EntityType.RABBIT,
			EntityType.RABBIT,
			EntityType.RABBIT,
			EntityType.RABBIT,
			EntityType.WOLF,
			EntityType.OCELOT);
	
	public final static String tagEntities_SeaAnimals = "Entities_For_SeaAnimals";
	public EntityList itemsEntities_SeaAnimals = createSeaAnimalList(tagEntities_SeaAnimals,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.GUARDIAN);
	
	public final static String tagEntities_Vagrants = "Entities_For_Vagrants";
	public EntityList itemsEntities_Vagrants = createList(tagEntities_Vagrants,
			EntityType.CHICKEN,
			EntityType.CHICKEN,
			EntityType.RABBIT,
			EntityType.RABBIT,
			EntityType.WOLF,
			EntityType.WOLF,
			EntityType.OCELOT,
			EntityType.OCELOT,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.SPIDER,
			EntityType.CREEPER);
	
	public final static String tagEntities_Sewers = "Entities_For_Sewers";
	public EntityList itemsEntities_Sewers = createList(tagEntities_Sewers,
			EntityType.ZOMBIE,
			EntityType.CREEPER,
			EntityType.SPIDER,
			EntityType.BAT);
	
	public final static String tagEntities_Mine = "Entities_For_Mine";
	public EntityList itemsEntities_Mine = createList(tagEntities_Mine,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE,
			EntityType.SKELETON,
			EntityType.SKELETON,
			EntityType.CREEPER,
			EntityType.CREEPER,
			EntityType.CAVE_SPIDER,
			EntityType.CAVE_SPIDER,
			EntityType.BAT,
			EntityType.BAT,
			EntityType.BAT,
			EntityType.BAT,
			EntityType.ENDERMITE);
	
	public final static String tagEntities_Bunker = "Entities_For_Bunker";
	public EntityList itemsEntities_Bunker = createList(tagEntities_Bunker,
			EntityType.PIG_ZOMBIE,
			EntityType.ENDERMAN,
			EntityType.SLIME,
			EntityType.BLAZE);
	
	public final static String tagEntities_WaterPit = "Entities_For_WaterPit";
	public EntityList itemsEntities_WaterPit = createList(tagEntities_WaterPit,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.SQUID,
			EntityType.GUARDIAN);
	
	public final static String tagEntities_LavaPit = "Entities_For_LavaPit";
	public EntityList itemsEntities_LavaPit = createList(tagEntities_LavaPit,
			EntityType.BLAZE,
			EntityType.WITHER,
			EntityType.MAGMA_CUBE,
			EntityType.SHULKER);
	
	private List<EntityList> listOfLists;
	private Map<EntityType, Biome> entityToBiome;
	
	public SpawnProvider(CityWorldGenerator generator, CityWorldSettings settings) {
		entityToBiome = new HashMap<EntityType, Biome>();
		entityToBiome.put(EntityType.WOLF, Biome.FOREST);
		entityToBiome.put(EntityType.OCELOT, Biome.JUNGLE);
	}
	
	public void setBiomeForEntity(World world, Location at, EntityType entity) {
		Biome biome = entityToBiome.get(entity);
		if (biome != null)
			world.setBiome(at.getBlockX(), at.getBlockZ(), biome);
	}
	
	private EntityList createList(EntityList list) {
		
		// add it to the big list so we can generically remember it
		if (listOfLists == null)
			listOfLists = new ArrayList<EntityList>();
		listOfLists.add(list);
		
		// return it so we can specifically remember it
		return list;
	}
	
	private EntityList createList(String name, EntityType ... entities) {
		return createList(new EntityList(name, entities));
	}
	
	private EntityList createAnimalList(String name, EntityType ... entities) {
		return createList(new AnimalList(name, entities));
	}
	
	private EntityList createSeaAnimalList(String name, EntityType ... entities) {
		return createList(new SeaAnimalList(name, entities));
	}
	
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		for (EntityList materialList : listOfLists) {
			materialList.read(generator, section);
		}
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		for (EntityList materialList : listOfLists) {
			materialList.write(generator, section);
		}
	}

}
