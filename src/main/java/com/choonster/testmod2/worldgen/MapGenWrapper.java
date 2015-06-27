package com.choonster.testmod2.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;

public class MapGenWrapper extends MapGenBase {
	private final MapGenBase base;
	private final ImmutableList<MapGenBase> generators;

	public MapGenWrapper(MapGenBase base, MapGenBase... generators) {
		this.base = base;
		this.generators = ImmutableList.copyOf(generators);
	}

	@Override
	public void func_151539_a(IChunkProvider chunkProvider, World world, int centreX, int centreZ, Block[] blocks) {
		base.func_151539_a(chunkProvider, world, centreX, centreZ, blocks);

		for (MapGenBase generator : generators) {
			generator.func_151539_a(chunkProvider, world, centreX, centreZ, blocks);
		}
	}
}
