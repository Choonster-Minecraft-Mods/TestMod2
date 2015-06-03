package com.choonster.testmod2.biome;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Iterator;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2440392-removing-mobs-from-my-biome
public class BiomeGenTest extends BiomeGenBase{
	@SuppressWarnings("unchecked")
	public BiomeGenTest(int id) {
		super(id);

		for (Iterator<SpawnListEntry> iterator = spawnableMonsterList.iterator(); iterator.hasNext(); ){
			SpawnListEntry entry = iterator.next();
			if (entry.entityClass.equals(EntityCreeper.class) || entry.entityClass.equals(EntityZombie.class)){
				iterator.remove();
			}
		}
	}
}
