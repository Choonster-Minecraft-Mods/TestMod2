package com.choonster.testmod2.init;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntityBarrelBomb;
import com.choonster.testmod2.entity.EntityZombieTest;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

public class Entities {
	public static void preInit() {
		registerEntity(EntityZombieTest.class, "testZombie", 96, 23, 173, 14, 2, 235);
		registerEntity(EntityBarrelBomb.class, "barrelBomb");
	}

	// Register an Entity with a spawn egg
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bgRed, int bgGreen, int bgBlue, int fgRed, int fgGreen, int fgBlue) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID, getRGBInt(bgRed, bgGreen, bgBlue), getRGBInt(fgRed, fgGreen, fgBlue));
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, TestMod2.instance, 64, 3, true);
	}

	// Register an Entity without a spawn egg
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID);
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, TestMod2.instance, 64, 3, true);
	}

	private static int getRGBInt(int rInt, int gInt, int bInt) {
		return (rInt << 16) + (gInt << 8) + bInt;
	}
}
