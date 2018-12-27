package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.AnimalList;
import me.daddychurchill.CityWorld.Support.BeingList;
import me.daddychurchill.CityWorld.Support.AbstractEntityList;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SeaAnimalList;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnProvider extends Provider {

	public final static String tagEntities_Goodies = "Entities_For_Goodies";
	public AbstractEntityList itemsEntities_Goodies = createBeingList(tagEntities_Goodies, EntityType.VILLAGER,
			EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER,
			EntityType.VILLAGER, EntityType.VILLAGER, EntityType.VILLAGER, EntityType.WITCH);

	public final static String tagEntities_Baddies = "Entities_For_Baddies";
	public AbstractEntityList itemsEntities_Baddies = createBeingList(tagEntities_Baddies, EntityType.CREEPER,
			EntityType.CREEPER, EntityType.CREEPER, EntityType.CREEPER, EntityType.SKELETON, EntityType.SKELETON,
			EntityType.SKELETON, EntityType.SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE,
			EntityType.ZOMBIE, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.SPIDER,
			EntityType.SPIDER, EntityType.SPIDER, EntityType.WITCH, EntityType.WITCH, EntityType.PIG_ZOMBIE,
			EntityType.ENDERMAN, EntityType.PHANTOM, EntityType.BLAZE);

	public final static String tagEntities_Animals = "Entities_For_Animals";
	public AbstractEntityList itemsEntities_Animals = createAnimalList(tagEntities_Animals, EntityType.HORSE,
			EntityType.HORSE, EntityType.DONKEY, EntityType.LLAMA, EntityType.COW, EntityType.COW, EntityType.SHEEP,
			EntityType.SHEEP, EntityType.PIG, EntityType.PIG, EntityType.PARROT, EntityType.PARROT, EntityType.CHICKEN,
			EntityType.CHICKEN, EntityType.CHICKEN, EntityType.CHICKEN, EntityType.CHICKEN, EntityType.CHICKEN,
			EntityType.RABBIT, EntityType.RABBIT, EntityType.RABBIT, EntityType.RABBIT, EntityType.WOLF,
			EntityType.OCELOT);

	public final static String tagEntities_SeaAnimals = "Entities_For_SeaAnimals";
	public AbstractEntityList itemsEntities_SeaAnimals = createSeaAnimalList(tagEntities_SeaAnimals,
			EntityType.TROPICAL_FISH, EntityType.TROPICAL_FISH, EntityType.TROPICAL_FISH, EntityType.TROPICAL_FISH,
			EntityType.TROPICAL_FISH, EntityType.PUFFERFISH, EntityType.PUFFERFISH, EntityType.PUFFERFISH,
			EntityType.SALMON, EntityType.SALMON, EntityType.SALMON, EntityType.DOLPHIN, EntityType.DOLPHIN,
//			EntityType.GUARDIAN,
//			EntityType.ELDER_GUARDIAN,
			EntityType.SQUID, EntityType.SQUID, EntityType.COD, EntityType.COD, EntityType.COD);

	public final static String tagEntities_Vagrants = "Entities_For_Vagrants";
	public AbstractEntityList itemsEntities_Vagrants = createBeingList(tagEntities_Vagrants, EntityType.CHICKEN,
			EntityType.CHICKEN, EntityType.RABBIT, EntityType.RABBIT, EntityType.WOLF, EntityType.WOLF,
			EntityType.OCELOT, EntityType.OCELOT, EntityType.HORSE, EntityType.DONKEY, EntityType.LLAMA,
			EntityType.VILLAGER, EntityType.VILLAGER, EntityType.PARROT,
//			EntityType.POLAR_BEAR,
			EntityType.SPIDER, EntityType.CREEPER);

	public final static String tagEntities_Sewers = "Entities_For_Sewers";
	public AbstractEntityList itemsEntities_Sewers = createBeingList(tagEntities_Sewers, EntityType.ZOMBIE,
			EntityType.ZOMBIE, EntityType.CREEPER, EntityType.SPIDER, EntityType.BAT, EntityType.BAT, EntityType.BAT,
			EntityType.BAT);

	public final static String tagEntities_Mine = "Entities_For_Mine";
	public AbstractEntityList itemsEntities_Mine = createBeingList(tagEntities_Mine, EntityType.ZOMBIE,
			EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SKELETON, EntityType.CREEPER, EntityType.CREEPER,
			EntityType.CAVE_SPIDER, EntityType.CAVE_SPIDER, EntityType.BAT, EntityType.BAT, EntityType.BAT,
			EntityType.BAT, EntityType.ENDERMITE);

	public final static String tagEntities_Bunker = "Entities_For_Bunker";
	public AbstractEntityList itemsEntities_Bunker = createBeingList(tagEntities_Bunker, EntityType.PIG_ZOMBIE,
			EntityType.ENDERMAN, EntityType.EVOKER, EntityType.ILLUSIONER, EntityType.BAT, EntityType.BAT,
			EntityType.BLAZE);

	public final static String tagEntities_WaterPit = "Entities_For_WaterPit";
	public AbstractEntityList itemsEntities_WaterPit = createBeingList(tagEntities_WaterPit, EntityType.SQUID,
			EntityType.SQUID, EntityType.SQUID, EntityType.SQUID, EntityType.SQUID, EntityType.SQUID, EntityType.SQUID,
			EntityType.SQUID, EntityType.GUARDIAN);

	public final static String tagEntities_LavaPit = "Entities_For_LavaPit";
	public AbstractEntityList itemsEntities_LavaPit = createBeingList(tagEntities_LavaPit, EntityType.BLAZE,
			EntityType.WITHER, EntityType.MAGMA_CUBE, EntityType.SHULKER);

	private List<AbstractEntityList> listOfLists;
	private Map<EntityType, Biome> entityToBiome;

	public SpawnProvider(CityWorldGenerator generator) {
		entityToBiome = new HashMap<EntityType, Biome>();
		entityToBiome.put(EntityType.WOLF, Biome.FOREST);
		entityToBiome.put(EntityType.OCELOT, Biome.JUNGLE);
	}

	// https://en.wikipedia.org/wiki/List_of_English_terms_of_venery,_by_animal
	public final void spawnAnimals(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z) {
		spawnAnimals(generator, blocks, odds, x, y, z, itemsEntities_Animals.getRandomEntity(odds));
	}

	public final void spawnAnimals(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType entity) {
		if (!generator.settings.includeDecayedBuildings) {
			int herdSize = itemsEntities_SeaAnimals.getHerdSize(odds, entity);
			if (herdSize > 0)
				spawnAnimal(generator, blocks, odds, x, y, z, entity);
			if (herdSize > 1)
				spawnAnimal(generator, blocks, odds, x + 1, y, z, entity);
			if (herdSize > 2)
				spawnAnimal(generator, blocks, odds, x, y, z + 1, entity);
			if (herdSize > 3)
				spawnAnimal(generator, blocks, odds, x + 1, y, z + 1, entity);
			if (herdSize > 4)
				spawnAnimal(generator, blocks, odds, x - 1, y, z, entity);
			if (herdSize > 5)
				spawnAnimal(generator, blocks, odds, x, y, z - 1, entity);
		}
	}

	private final void spawnAnimal(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType entity) {
		if (odds.playOdds(generator.settings.spawnAnimals))
			spawnEntity(generator, blocks, odds, x, y, z, entity, false, true);
	}

	public final void spawnSeaAnimals(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z) {
		spawnSeaAnimals(generator, blocks, odds, x, y, z, itemsEntities_SeaAnimals.getRandomEntity(odds));
	}

	private final void spawnSeaAnimals(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, EntityType entity) {
		if (!generator.settings.includeDecayedBuildings) {
			int herdSize = itemsEntities_SeaAnimals.getHerdSize(odds, entity);
			if (herdSize > 0)
				spawnSeaAnimal(generator, blocks, odds, x, y, z, entity);
			if (herdSize > 1)
				spawnSeaAnimal(generator, blocks, odds, x + 1, y, z, entity);
			if (herdSize > 2)
				spawnSeaAnimal(generator, blocks, odds, x, y, z + 1, entity);
			if (herdSize > 3)
				spawnSeaAnimal(generator, blocks, odds, x + 1, y, z + 1, entity);
			if (herdSize > 4)
				spawnSeaAnimal(generator, blocks, odds, x - 1, y, z, entity);
			if (herdSize > 5)
				spawnSeaAnimal(generator, blocks, odds, x, y, z - 1, entity);
		}
	}

	private final void spawnSeaAnimal(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, EntityType entity) {
		if (odds.playOdds(generator.settings.spawnAnimals) && blocks.isWater(x, y - 1, z) && blocks.isWater(x, y, z)) {
			// @@
//			blocks.setBlock(x, y + 10, z, Material.GLOWSTONE);
//			blocks.setSignPost(x, y + 11, z, BlockFace.EAST, "Spawn", entity.name());
			spawnEntity(generator, blocks, odds, x, y, z, entity, true, false);
		}
	}

	public final void spawnBeing(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z) {
		spawnBeing(generator, blocks, odds, x, y, z, itemsEntities_Goodies.getRandomEntity(odds),
				itemsEntities_Baddies.getRandomEntity(odds));
	}

	public final void spawnBeings(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z) {
		EntityType goodies = itemsEntities_Goodies.getRandomEntity(odds);
		EntityType baddies = itemsEntities_Baddies.getRandomEntity(odds);
		int count = odds.getRandomInt(1, 3); // shimmy
		for (int i = 0; i < count; i++)
			spawnBeing(generator, blocks, odds, blocks.clampXZ(x + i), y, blocks.clampXZ(z + i), goodies, baddies);
	}

	public final void spawnBeing(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType goody, EntityType baddy) {
		if (odds.playOdds(generator.settings.spawnBeings))
			spawnGoodOrBad(generator, blocks, odds, x, y, z, goody, baddy);
	}

	public final void spawnVagrants(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z) {
		EntityType goodies = itemsEntities_Vagrants.getRandomEntity(odds);
		EntityType baddies = itemsEntities_Baddies.getRandomEntity(odds);
		int count = odds.getRandomInt(1, 3); // shimmy
		for (int i = 0; i < count; i++)
			spawnVagrant(generator, blocks, odds, blocks.clampXZ(x + i), y, blocks.clampXZ(z + i), goodies, baddies);
	}

	public final void spawnVagrant(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType goody, EntityType baddy) {
		if (odds.playOdds(generator.settings.spawnVagrants))
			spawnGoodOrBad(generator, blocks, odds, x, y, z, goody, baddy);
	}

	private final void spawnGoodOrBad(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, EntityType goody, EntityType baddy) {
		if (!blocks.isEmpty(x, y, z) && !blocks.isEmpty(x, y - 1, z))
			if (generator.settings.includeDecayedBuildings || odds.playOdds(generator.settings.spawnBaddies))
				spawnEntity(generator, blocks, odds, x, y, z, baddy, false, true);
			else
				spawnEntity(generator, blocks, odds, x, y, z, goody, false, true);
	}

	private final void spawnEntity(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType entity, boolean ignoreFlood, boolean ensureSpace) {
		if (entity != null && entity.isAlive()) {

			// make sure we have room
			if (ensureSpace) {
				boolean foundSpot = false;
				int countDown = 10;
				while (countDown > 0) {
					int emptyY = blocks.findFirstEmptyAbove(x, y, z, y + 3);
					if (blocks.isEmpty(x, y, z) && blocks.isEmpty(x, y + 1, z)) {
						y = emptyY;
						foundSpot = true;
						break;
					} else
						countDown--;

					// shimmy
					x = blocks.clampXZ(x + odds.calcRandomRange(-2, 2));
					z = blocks.clampXZ(z + odds.calcRandomRange(-2, 2));
				}
				if (!foundSpot)
					return;
//				y = blocks.findFirstEmptyAbove(x, y, z, y + 3);
			}
			Location at = blocks.getBlockLocation(x, y, z);

			// ignore flood level or make sure that we are above it
//			blocks.setBlock(x, y + 10, z, Material.STONE);
			if (ignoreFlood || y > generator.shapeProvider.findFloodY(generator, at.getBlockX(), at.getBlockZ())) {
				World world = generator.getWorld();

				// make sure there is a clear space
				if (ensureSpace)
					blocks.clearBlocks(x, y, y + 2, z);

				// fix up the biome
				Biome biome = entityToBiome.get(entity);
				if (biome != null)
					world.setBiome(at.getBlockX(), at.getBlockZ(), biome);

				// now spawn it!
				Entity being = world.spawnEntity(at, entity);
				if (being != null) {
					being.setVelocity(odds.getRandomVelocity());

					if (generator.settings.nameVillagers && entity == EntityType.VILLAGER) {
						String beingName = generator.odonymProvider.generateVillagerName(generator, odds);
						being.setCustomName(beingName);
						if (generator.settings.showVillagersNames)
							being.setCustomNameVisible(true);
//						blocks.setSignPost(x, y + 11, z, BlockFace.NORTH, "Created", "named", beingName);
//					} else {
//						blocks.setSignPost(x, y + 11, z, BlockFace.NORTH, "Created", "a " + being.getType());
					}
//				} else {
//					blocks.setSignPost(x, y + 11, z, BlockFace.NORTH, "FAILED", "to spawn", "a " + entity);
				}
//			} else {
//				blocks.setSignPost(x, y + 11, z, BlockFace.NORTH, "FAILED", "to spawn", "a " + entity, "due to flood");
			}
		}
	}

	public final void setSpawnOrSpawner(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y,
			int z, boolean doSpawner, AbstractEntityList entities) {
		EntityType entity = entities.getRandomEntity(odds);
		if (doSpawner)
			setSpawner(generator, blocks, odds, x, y, z, entity);
		else
			spawnEntity(generator, blocks, odds, x, y, z, entity, false, true);
	}

	public final void setSpawner(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			AbstractEntityList list) {
		setSpawner(generator, blocks, odds, x, y, z, list.getRandomEntity(odds));
	}

	private final void setSpawner(CityWorldGenerator generator, SupportBlocks blocks, Odds odds, int x, int y, int z,
			EntityType entity) {
		if (entity.isAlive() && odds.playOdds(generator.settings.spawnBaddies)) {
			Block block = blocks.getActualBlock(x, y, z);
			block.setType(Material.SPAWNER);
			block.getState().update(true); // for good measure
			if (block.getType() == Material.SPAWNER) { // make sure it happened!
				CreatureSpawner spawner = (CreatureSpawner) block.getState();
				spawner.setSpawnedType(entity);
				spawner.update(true);
			}
		}
	}

	private AbstractEntityList createList(AbstractEntityList list) {

		// add it to the big list so we can generically remember it
		if (listOfLists == null)
			listOfLists = new ArrayList<AbstractEntityList>();
		listOfLists.add(list);

		// return it so we can specifically remember it
		return list;
	}

	private AbstractEntityList createBeingList(String name, EntityType... entities) {
		return createList(new BeingList(name, entities));
	}

	private AbstractEntityList createAnimalList(String name, EntityType... entities) {
		return createList(new AnimalList(name, entities));
	}

	private AbstractEntityList createSeaAnimalList(String name, EntityType... entities) {
		return createList(new SeaAnimalList(name, entities));
	}

	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		for (AbstractEntityList materialList : listOfLists) {
			materialList.read(generator, section);
		}
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		for (AbstractEntityList materialList : listOfLists) {
			materialList.write(generator, section);
		}
	}

}
