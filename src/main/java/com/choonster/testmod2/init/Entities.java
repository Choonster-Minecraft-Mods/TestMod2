package com.choonster.testmod2.init;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntityZombieTest;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities {
	public static void preInit() {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityZombieTest.class, "testZombie", id, getRGBInt(96, 23, 173), getRGBInt(14, 2, 235));
		EntityRegistry.registerModEntity(EntityZombieTest.class, "testZombie", id, TestMod2.instance, 64, 1, true);
	}

	private static int getRGBInt(int rInt, int gInt, int bInt) {
		return (rInt << 16) + (gInt << 8) + bInt;
	}
}
