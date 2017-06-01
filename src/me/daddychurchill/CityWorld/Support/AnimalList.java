package me.daddychurchill.CityWorld.Support;

import org.bukkit.entity.EntityType;

public class AnimalList extends EntityList {

	public AnimalList(String name) {
		super(name);
	}

	public AnimalList(String name, EntityType... entities) {
		super(name, entities);
	}

	@Override
	public int getHerdSize(EntityType entity) {
		switch (entity) {
		default:
			return super.getHerdSize(entity);
		case WOLF:
		case OCELOT:
			return 1;
		case HORSE:
		case DONKEY:
		case LLAMA:
		case COW:
		case MUSHROOM_COW:
		case SHEEP:
		case PIG:
			return 2;
		case RABBIT:
			return 4;
		case CHICKEN:
			return 6;
		}
	}
	
	public enum EntityAffilation {HOSTILE, NEUTRAL, FRIENDLY, OTHER};
	public EntityAffilation getEntityAffilation(EntityType foo) {
		switch (foo) {
		
		// bad
		case BLAZE:
		case CAVE_SPIDER:
		case CREEPER:
		case ELDER_GUARDIAN:
		case ENDER_DRAGON:
		case ENDERMAN:
		case ENDERMITE:
		case EVOKER:
		case GHAST:
		case GIANT:
		case GUARDIAN:
		case HUSK:
		case ILLUSIONER:
		case MAGMA_CUBE:
		case SHULKER:
		case SILVERFISH:
		case SKELETON:
		case SKELETON_HORSE:
		case SLIME:
		case SPIDER:
		case STRAY:
		case VEX:
		case VINDICATOR:
		case WITCH:
		case WITHER:
		case WITHER_SKELETON:
		case ZOMBIE:
		case ZOMBIE_HORSE:
		case ZOMBIE_VILLAGER:
			return EntityAffilation.HOSTILE;
			
		// neutral
		case BAT:
		case IRON_GOLEM:
		case LLAMA:
		case OCELOT:
		case PIG_ZOMBIE:
		case POLAR_BEAR:
		case SNOWMAN:
		case SQUID:
		case RABBIT:
		case WOLF:
			return EntityAffilation.NEUTRAL;
			
		// friendly
		case CHICKEN:
		case COW:
		case DONKEY:
		case HORSE:
		case MULE:
		case MUSHROOM_COW:
		case PARROT:
		case PIG:
		case SHEEP:
		case VILLAGER:
			return EntityAffilation.FRIENDLY;

		// Other
		case AREA_EFFECT_CLOUD:
		case ARMOR_STAND:
		case ARROW:
		case BOAT:
		case COMPLEX_PART:
		case DRAGON_FIREBALL:
		case DROPPED_ITEM:
		case EGG:
		case ENDER_CRYSTAL:
		case ENDER_PEARL:
		case ENDER_SIGNAL:
		case EVOKER_FANGS:
		case EXPERIENCE_ORB:
		case FALLING_BLOCK:
		case FIREBALL:
		case FIREWORK:
		case FISHING_HOOK:
		case ITEM_FRAME:
		case LEASH_HITCH:
		case LIGHTNING:
		case LINGERING_POTION:
		case LLAMA_SPIT:
		case MINECART:
		case MINECART_CHEST:
		case MINECART_COMMAND:
		case MINECART_FURNACE:
		case MINECART_HOPPER:
		case MINECART_MOB_SPAWNER:
		case MINECART_TNT:
		case PAINTING:
		case PLAYER:
		case PRIMED_TNT:
		case SHULKER_BULLET:
		case SMALL_FIREBALL:
		case SNOWBALL:
		case SPECTRAL_ARROW:
		case SPLASH_POTION:
		case THROWN_EXP_BOTTLE:
		case TIPPED_ARROW:
		case UNKNOWN:
		case WEATHER:
		case WITHER_SKULL:
			return EntityAffilation.OTHER;
		}
		
		return EntityAffilation.OTHER;
	}
}
